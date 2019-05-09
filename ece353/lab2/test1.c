#include <avr/io.h>
#include <avr/delay.h>
#include <avr/iom32.h>
#include <avr/interrupt.h>

#define USART_BAUDRATE 31250
#define BAUD_PRESCALE (((F_CPU / (USART_BAUDRATE * 16UL))) - 1)
//#define MOD_PIN PA5
//#define REC_PIN PA6
//#define PLAY_PIN PA7
//#define CUSTOM_PIN PA1
//#define CUSTOM_PIN2 PA0

#define MINCHARVAL 0
#define MAXCHARVAL 255
#define MAXADDRVAL 1023

unsigned char EEPROM_read(unsigned int);
void EEPROM_write(unsigned int,unsigned char);
unsigned char USART_receive(void);
void USART_transmit(unsigned char);
void LED(unsigned char);
unsigned char modify(unsigned char,double,int);

 ISR(TIMER1_OVF_vect, ISR_NAKED)
      {
        TIMSK|=(1<<TOIE1);
      }

int main(void)
{	unsigned char noteRead;	// note read
	unsigned char notePlay;	// note played
	unsigned int noteReadAddr=0;	// address of note read
	unsigned int notePlayAddr=0;	// address of note play
	double modMult=1.0;	// multiplier for 1st modify function
	int modAdd=0;		// addend for 2nd modify function
	
	UCSRB = (1 << RXEN) | (1 << TXEN);   // Turn on the transmission and reception circuitry
	UCSRC = (1 << URSEL) | (1 << UCSZ0) | (1 << UCSZ1); // Use 8-bit character sizes - URSEL bit set to select the UCRSC register

	UBRRH = (BAUD_PRESCALE >> 8); // Load upper 8-bits of the baud rate value into the high byte of the UBRR register
	UBRRL = BAUD_PRESCALE; // Load lower 8-bits of the baud rate value into the low byte of the UBRR register
	
	while(1)
	{	if(PINA & (1<<PA7))	//record
		{	if(noteReadAddr>MAXADDRVAL)
				break;
			noteRead=USART_receive();
			EEPROM_write(noteReadAddr,noteRead);	
			noteReadAddr++;
		}
		if(PINA & (1<<PA6))	//play
		{	if(notePlayAddr>MAXADDRVAL)
				break;
			notePlay=EEPROM_read(notePlayAddr);
			if(PINA & (1<<PA5))	// modify
			{	if(1<<PA0)
					modMult=2.0;	// halves speed of playback
				else
					modMult=0.5;	// doubles speed of playback
				if(1<<PA1)
					modAdd=12;	// shifts notes up an octave
				else
					modAdd=-12;	// shifts notes down an octave
				notePlay=modify(notePlay,modMult,modAdd);
			}
			USART_transmit(notePlay);
			notePlayAddr++;
		}
		if(!(1<<RESET))
		{	noteReadAddr=0;	// reset note read address
			notePlayAddr=0;	// reset note play address
			modMult=1.0;	// reset modify multiplier
			modAdd=0;	// reset modify addend
		}
	}
}

unsigned char EEPROM_read(unsigned int uiAddress)
{	while(EECR&(1<<EEWE))	// wait for completion of previous write
	EEAR=uiAddress;		// set up address register
	EECR|=(1<<EERE); 	// start eeprom read by writing EERE
	return(EEDR);		// return data from data register
}
cout<<1;
void EEPROM_write(unsigned int uiAddress,unsigned char ucData)
{	while(EECR&(1<<EEWE))	// wait for completion of previous write
	;
	EEAR=uiAddress;		// set up address
	EEDR=ucData;		// and data registers
	EECR|=(1<<EEMWE);	// write logical one to EEMWE
	EECR|=(1<<EEWE);	// start eeprom write by setting EEWE
}
unsigned char USART_receive(void)
{	while(!(UCSRA&(1<<RXC)))
	;			// wait for data to be received
	return(UDR);		// get and return received data from buffer
}
void USART_transmit(unsigned char data)
{	while(!(UCSRA&(1<<UDRE)))
	;			// wait for empty transmit buffer
	UDR=data;		// put data into buffer, sends the data
}
void LED(unsigned char note)
{	int led=0,noteLED;	// initialize LED pin value
	noteLED=(int)note;	// convert note to an int
	DDRB=0x00;			// set DDRB to output
	while(led<8)		// loop until max LED pin value 
	{	//led,noteLED%2;	// display lowest bit of note
		noteLED=noteLED/2;	// basically a shift right
		led++;			// increment LED pin value
	}
}
unsigned char modify(unsigned char note,double mult,int add)
{	int note1;
	unsigned char note2;
	note1=(int)note+add;	// calculate new value of note
	if(note1>MAXCHARVAL)
		note2=(unsigned char)(note1-MAXCHARVAL);	// if value exceeds max value, shift the note to lowest octave
	else if(note1<MINCHARVAL)
		note2=(unsigned char)(note1+MAXCHARVAL);	// if value is below min value, shift note to highest octave
	else
		note2=(unsigned char)note1;	// if value is in correct range, leave it as is
	return(note2);	
}