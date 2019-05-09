//#include "address_map_arm.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "socal.h"
#include "hps_soc_system.h"
#include "hps.h"

#define SW_BASE				0xFF200040
#define KEY_BASE            0xFF200050
#define VIDEO_IN_BASE       0xFF203060
#define FPGA_ONCHIP_BASE    0xC8000000
#define MPCORE_PRIV_TIMER	0xFFFEC600
#define FPGA_CHAR_BASE 		0xC9000000
/*	This program demonstrates the use of the D5M camera with the DE1-SoC Board
 *	It performs the following: 
 *	1. Capture one frame of video when any key is pressed.
 *	2. Display the captured frame when any key is pressed.		  
*/
/*	Note: Set the switches SW1 and SW2 to high and rest of the switches to low
 *	for correct exposure timing while compiling and the loading the program in the Altera Monitor program.
*/
void VGA_text(int,int,char*);

int main(void)
{
	volatile int * KEY_ptr=(int *)KEY_BASE;
	volatile int * SW_ptr=(int *)SW_BASE;
	volatile int * Video_In_DMA_ptr=(int *)VIDEO_IN_BASE;
	volatile short * Video_Mem_ptr=(short *)FPGA_ONCHIP_BASE;
	volatile short * Video_Mem_ptr2=(short *)SDRAM_BASE+0xC0000000;
//	int ovflw = 100000;
//	*timer = ovflw;
//	*(timer+2) = 0b011;
//	int time = 0;		//time in .05s increments
	
	alt_write_byte(ALT_FPGA_BRIDGE_LWH2F_OFST+RLE_RESET_BASE,1);
	alt_write_byte(ALT_FPGA_BRIDGE_LWH2F_OFST+RLE_RESET_BASE,0);
	//alt_write_byte(ALT_FPGA_BRIDGE_LWH2F_OFST+RLE_FLUSH_PIO_BASE,1);
	while(1)
	{	*(Video_In_DMA_ptr + 3)	= 0x4;// Enable the video
		char pixel_in=0x00;
		int x_in,y_in;
		int pixel_out=0,x_out=0,y_out=0;
		int pixelCount=0,i;
		int out;
		int bytes_in,bytes_rle,bytes_out;
		int data;
		bytes_in=0;
		bytes_rle=0;
		x_out=0;
		y_out=0;
		
		//printf("hello");
		
		while (1)
		{	if (*KEY_ptr)						// check if any KEY was pressed
			{	*(Video_In_DMA_ptr + 3) = 0x0;			// Disable the video to capture one frame
				while (*KEY_ptr != 0);					// wait for pushbutton KEY release
				break;
			}
		}
		
		for(y_in=0;y_in<240;y_in++){
			for(x_in=0;x_in<320;x_in++){	
				int gray;
				int colorR,colorG,colorB;
				colorR=(*(Video_Mem_ptr+(y_in<<9)+x_in)&0xF800)>>11;
				colorG=(*(Video_Mem_ptr+(y_in<<9)+x_in)&0x07E0)>>6;
				colorB=*(Video_Mem_ptr+(y_in<<9)+x_in)&0x001F;
				gray=.299*colorR+.587*colorG+.114*colorB;
				out=(gray>9);
				*(Video_Mem_ptr + (y_in << 9) + x_in) = out*0xFFFF;
			}
		}
		//VGA_text(5,10,"hello");
		pixel_in=0;
		for(y_in=0;y_in<240;y_in++){
			for(x_in=0;x_in<320;x_in++){	
				pixel_in |= (*(Video_Mem_ptr + (y_in << 9) + x_in)&0x0001)<<(x_in%8);
				if(x_in%8 == 7){
					//VGA_text(5,20,"while");
					while(alt_read_byte(FIFO_IN_FULL_PIO_BASE+ALT_FPGA_BRIDGE_LWH2F_OFST));
					alt_write_byte(ALT_FPGA_BRIDGE_LWH2F_OFST+FIFO_IN_WRITE_REQ_PIO_BASE,1);
					alt_write_byte((ALT_FPGA_BRIDGE_LWH2F_OFST+ODATA_PIO_BASE),pixel_in);
					alt_write_byte(ALT_FPGA_BRIDGE_LWH2F_OFST+FIFO_IN_WRITE_REQ_PIO_BASE,0);
					bytes_in++;
					/*if(bytes_in>100)
						VGA_text(40,30,"100 bytes");*/
					int test = 5;
						if(test>71)
							test=5;
						VGA_text(test+=5,10,"TEST");
						if(!alt_read_byte(ALT_FPGA_BRIDGE_LWH2F_OFST+RESULT_READY_PIO_BASE)){
							alt_write_byte(ALT_FPGA_BRIDGE_LWH2F_OFST+FIFO_OUT_READ_REQ_PIO_BASE,1);
							data = alt_read_word(ALT_FPGA_BRIDGE_LWH2F_OFST+IDATA_PIO_BASE);
							alt_write_byte(ALT_FPGA_BRIDGE_LWH2F_OFST+FIFO_OUT_READ_REQ_PIO_BASE,0);
							*(Video_Mem_ptr2 + 2*bytes_rle/3) = (data&0xFF0000)>>16;
							*(Video_Mem_ptr2 + 2*bytes_rle/3 + 1) = data&0x00FFFF;
							bytes_rle += 3;
							VGA_text(5,30,"hi");
						}
					
					pixel_in=0;
				}//*/
			}
		}//*/
		alt_write_byte(ALT_FPGA_BRIDGE_LWH2F_OFST+RLE_FLUSH_PIO_BASE,1);
		data = alt_read_word(ALT_FPGA_BRIDGE_LWH2F_OFST+IDATA_PIO_BASE);
		*(Video_Mem_ptr2 + 2*bytes_rle/3) = (data&0xFF0000)>>16;
		*(Video_Mem_ptr2 + 2*bytes_rle/3 + 1) = data&0x00FFFF;
		/*VGA_text(5,40,"help");

		VGA_text(5,10,"*               ");
		VGA_text(5,20,"*               ");
		VGA_text(5,30,"*               ");
		VGA_text(5,40,"*               ");
		*/
		for(y_out=0;y_out<240;y_out++)
			for(x_out=0;x_out<320;x_out++)
				*(Video_Mem_ptr+(y_out<<9)+x_out)=0xFFFF;
		

		x_out=0;
		y_out=0;
		for(bytes_out=0;bytes_out<bytes_rle;bytes_out+=3){
			data=(*(Video_Mem_ptr2+2*bytes_out/3)<<16)+*(Video_Mem_ptr2+2*bytes_out/3+1);
			pixel_out = data>>23;
			pixelCount = data&0x7FFFFF;
			for(i=0; i<pixelCount; i++){
				*(Video_Mem_ptr + (y_out << 9) + x_out++) = pixel_out*0xFFFF;
				if(x_out == 320){	
					y_out++;
					x_out=0;
				}
			}
		}//*/
		//VGA_text(5,50,"why");

		while (1)
		{	if (*KEY_ptr)						// check if any KEY was pressed
			{	*(Video_In_DMA_ptr + 3) = 0x0;			// Disable the video to capture one frame
				//while (*KEY_ptr != 0);					// wait for pushbutton KEY release
				break;
			}
		}
		//VGA_text(5,50,"*               ");
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
}//*/