#include "address_map_arm.h"
#include <stdlib.h>
#include <time.h>

/*	#define KEY_BASE              0xFF200050
 *	#define VIDEO_IN_BASE         0xFF203060
 *	#define FPGA_ONCHIP_BASE      0xC8000000
*/
/*	This program demonstrates the use of the D5M camera with the DE1-SoC Board
 *	It performs the following: 
 *	1. Capture one frame of video when any key is pressed.
 *	2. Display the captured frame when any key is pressed.		  
*/
/*	Note: Set the switches SW1 and SW2 to high and rest of the switches to low
 *	for correct exposure timing while compiling and the loading the program in the Altera Monitor program.
*/
void VGA_text(int,int,char*);
void displayTime();


int main(void)
{
	volatile int * KEY_ptr=(int *)KEY_BASE;
	volatile int * SW_ptr=(int *)SW_BASE;
	volatile int * Video_In_DMA_ptr=(int *)VIDEO_IN_BASE;
	volatile short * Video_Mem_ptr=(short *)FPGA_ONCHIP_BASE;
	volatile short * Video_Mem_ptr2=(short *)SDRAM_BASE;
	volatile int * timer=(short *)MPCORE_PRIV_TIMER;
	int ovflw = 100000;
	*timer = ovflw;
	*(timer+2) = 0b011;
	int time = 0;		//time in .05s increments
	int x, y;
	int counter=0;
	short colorR,colorG,colorB;
	int digits;
	char* count;
	char* test;
	char* timeStamp;
	int SW_value;
	short disp_time;
	short disp_count;
	short flipHoriz;		// rotate by doing both flipHoriz and flipVert
	short flipVert;
	short mirrorRight;
	short mirrorTop;
	short invertColor;
	short grayScale;
	short blackWhite;
	short edgeDetect;
	short trippy;
	
	while(1)
	{	*(Video_In_DMA_ptr + 3)	= 0x4;// Enable the video
		if(*(timer+3)!=0)
		{	time++;
			*(timer+3)=1;
		}
		while (1)
		{	if (*KEY_ptr != 0)						// check if any KEY was pressed
			{	*(Video_In_DMA_ptr + 3) = 0x0;			// Disable the video to capture one frame
				while (*KEY_ptr != 0);					// wait for pushbutton KEY release
				break;
			}
			if(*(timer+3)!=0)
			{	time++;
				*(timer+3)=1;
			}
		}
		
		counter++;

		while (1)
		{	if(*(timer+3)!=0)
			{	time++;
				*(timer+3)=1;
			}
			if (*KEY_ptr != 0)						// check if any KEY was pressed
				while(*KEY_ptr!=0)
					if(*(timer+3)!=0)
					{	time++;
						*(timer+3)=1;
					}
				break;
		}
	
		SW_value=*(SW_ptr);
		disp_time=(SW_value>>0)%2;
		disp_count=(SW_value>>1)%2;
		flipHoriz=(SW_value>>2)%2;
		flipVert=(SW_value>>3)%2;
		mirrorRight=(SW_value>>4)%2;
		mirrorTop=(SW_value>>5)%2;
		invertColor=(SW_value>>6)%2;
		grayScale=(SW_value>>7)%2;
		edgeDetect=(SW_value>>8)%2;
		trippy=(SW_value>>9)%2;
		
		if(*(timer+3)!=0)
		{	time++;
			*(timer+3)=1;
		}
		
		if(flipVert)
			for (y=0;y<240;y++)
				for (x=0;x<160;x++)
				{	short temp=*(Video_Mem_ptr+(y<<9)+(320-x));
					short temp2=*(Video_Mem_ptr+(y<<9)+x);
					*(Video_Mem_ptr+(y<<9)+x)=temp;
					*(Video_Mem_ptr+(y<<9)+(320-x))=temp2;
				}
		
		if(flipHoriz)
			for(x=0;x<320;x++)
				for(y=0;y<120;y++)
				{	short temp=*(Video_Mem_ptr+((240-y)<<9)+x);
					short temp2=*(Video_Mem_ptr+(y<<9)+x);
					*(Video_Mem_ptr+(y<<9)+x)=temp;
					*(Video_Mem_ptr+((240-y)<<9)+x)=temp2;
				}

		if(mirrorRight)
			for(y=0;y<240;y++)
				for(x=0;x<160;x++)
					*(Video_Mem_ptr+(y<<9)+x)=*(Video_Mem_ptr+(y<<9)+(320-x));
				
		if(mirrorTop)
			for(y=0;y<120;y++)
				for(x=0;x<320;x++)
					*(Video_Mem_ptr+(y<<9)+x)=*(Video_Mem_ptr+((240-y)<<9)+x);
				
		if(invertColor)
			for (y=0;y<240;y++)
				for (x = 0; x < 320; x++)	
					*(Video_Mem_ptr+(y<<9)+x)=0xFFFF-*(Video_Mem_ptr+(y<<9)+x);

		if(grayScale)
			for(y=0;y<240;y++)
				for(x=0;x<320;x++)
				{	int gray;
					colorR=(*(Video_Mem_ptr+(y<<9)+x)&0xF800)>>11;
					colorG=(*(Video_Mem_ptr+(y<<9)+x)&0x07E0)>>6;
					colorB=*(Video_Mem_ptr+(y<<9)+x)&0x001F;
					gray=.299*colorR+.587*colorG+.114*colorB;
					*(Video_Mem_ptr+(y<<9)+x)=(gray<<11)+(gray<<6)+gray;
				}
				
		sprintf(timeStamp,"%.4f",time*.0005);
		if(disp_time)
			displayTime();	//VGA_text(68,57,timeStamp);
		
		sprintf(count, "%d", counter);
		if(disp_count)	
			VGA_text(1,1,count);

		while(1) 
		{	if(*KEY_ptr!=0)
			{	while(*KEY_ptr!=0);
				break;
			}
			if(*(timer+3)!=0)
			{	time++;
				*(timer+3)=1;
			}
		}
		
		if(*(timer+3)!=0)
		{	time++;
			*(timer+3)=1;
		}
		
		VGA_text(1,1,"       ");
		VGA_text(40,57,"                                      ");
	}
}
void VGA_text(int x,int y,char * text_ptr)
{	int offset;
  	volatile char * character_buffer =(char *)FPGA_CHAR_BASE;	// VGA character buffer

	offset=(y<<7)+x;
	while(*(text_ptr))
	{	*(character_buffer+offset)=*(text_ptr);	// write to the character buffer
		text_ptr++;
		offset++;
	}
}
void displayTime()
{	time_t rawtime;
	struct tm *info;
	char buffer[80];
	
	setenv("TZ","EST5EDT",1);
	tzset();
	time(&rawtime);
	info=localtime(&rawtime);
	strftime(buffer,80,"%A %B %d, %Y - %I:%M:%S %Z",info);
	VGA_text(40,57,buffer);
}
