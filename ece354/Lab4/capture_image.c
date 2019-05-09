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
#define FPGA_CHAR_BASE 		0xC9000000

int bytes_in;
int segments;
float compression;
char pixel_in;
char binary[9600];
int threshold=0;
volatile int * KEY_ptr=(int *)KEY_BASE;
volatile int * SW_ptr=(int *)SW_BASE;
volatile int * Video_In_DMA_ptr=(int *)VIDEO_IN_BASE;
volatile short * Video_Mem_ptr=(short *)FPGA_ONCHIP_BASE;
volatile short * SDRAM_ptr=0xC0000000;
	
int main(void){
	int x,y;
	*(Video_In_DMA_ptr + 3)	= 0x4;	// Enable the video
	
	alt_write_byte(ALT_FPGA_BRIDGE_LWH2F_OFST+RLE_RESET_BASE,1);
	alt_write_byte(ALT_FPGA_BRIDGE_LWH2F_OFST+RLE_RESET_BASE,0);
	while(1){
		while(1){
			if (*KEY_ptr){						// check if any KEY was pressed
				*(Video_In_DMA_ptr + 3) = 0x0;			// Disable the video to capture one frame
				while (*KEY_ptr != 0);					// wait for pushbutton KEY release
				break;
			}
		}
		pixel_in=0;
		bytes_in=0;
		segments=0;
		binarize();
		compress();
		while(1){
			if (*KEY_ptr){
				*(Video_In_DMA_ptr + 3) = 0x0;
				while (*KEY_ptr != 0);
				break;
			}
		}
		for(y=0;y<240;y++){
			for(x=0;x<320;x++){	
				*(Video_Mem_ptr + (y << 9) + x)=0;
			}
		}
		while(1){
			if (*KEY_ptr){
				*(Video_In_DMA_ptr + 3) = 0x0;
				while (*KEY_ptr != 0);
				break;
			}
		}
		decompress();
		compression=((float)bytes_in*8)/((float)segments*4);
		printf("%d / %d",bytes_in*8,segments*4);
		printf("\n%f\n",compression);
		while(1){
			if (*KEY_ptr){
				*(Video_In_DMA_ptr + 3) = 0x4;
				while (*KEY_ptr != 0);
				break;
			}
		}
	}
}

int binarize(){
	int x,y;
	char bit;
	for(y=0;y<240;y++){
		for(x=0;x<320;x++){	
			int colorR,colorG,colorB;
			colorR=(*(Video_Mem_ptr+(y<<9)+x)&0xF800)>>11;
			colorG=(*(Video_Mem_ptr+(y<<9)+x)&0x07E0)>>6;
			colorB=(*(Video_Mem_ptr+(y<<9)+x)&0x001F);
			threshold+=.299*colorR+.587*colorG+.114*colorB;
		}
	}
	threshold/=76800;
	for(y=0;y<240;y++){
		for(x=0;x<320;x++){			
			int colorR,colorG,colorB;
			colorR=(*(Video_Mem_ptr+(y<<9)+x)&0xF800)>>11;
			colorG=(*(Video_Mem_ptr+(y<<9)+x)&0x07E0)>>6;
			colorB=(*(Video_Mem_ptr+(y<<9)+x)&0x001F);
			bit=((.299*colorR+.587*colorG+.114*colorB)>(threshold+1));
			*(Video_Mem_ptr + (y << 9) + x) = bit*0xFFFF;
			pixel_in |= bit<<(x%8);
			if(x%8 == 7){
				binary[bytes_in++] = pixel_in;
				pixel_in=0;
			}
		}
	}
}

int compress(){
	int i;
	alt_write_byte(ALT_FPGA_BRIDGE_LWH2F_OFST+FIFO_IN_WRITE_REQ_PIO_BASE,0);
	alt_write_byte(ALT_FPGA_BRIDGE_LWH2F_OFST+RLE_FLUSH_PIO_BASE,1);
	alt_write_byte(ALT_FPGA_BRIDGE_LWH2F_OFST+RLE_FLUSH_PIO_BASE,0);
	for(i=0;i<bytes_in;i++){
		pixel_in=binary[i];
		toRLE();
	}
	alt_write_byte(ALT_FPGA_BRIDGE_LWH2F_OFST+RLE_FLUSH_PIO_BASE,1);
	fromRLE();
	alt_write_byte(ALT_FPGA_BRIDGE_LWH2F_OFST+RLE_FLUSH_PIO_BASE,0);
}

int toRLE(){
	alt_write_byte((ALT_FPGA_BRIDGE_LWH2F_OFST+ODATA_PIO_BASE),pixel_in);
	alt_write_byte(ALT_FPGA_BRIDGE_LWH2F_OFST+FIFO_IN_WRITE_REQ_PIO_BASE,1);
	alt_write_byte(ALT_FPGA_BRIDGE_LWH2F_OFST+FIFO_IN_WRITE_REQ_PIO_BASE,0);
	while(!alt_read_byte(ALT_FPGA_BRIDGE_LWH2F_OFST+RESULT_READY_PIO_BASE))
		fromRLE();
}

int fromRLE(){
	int data;
	alt_write_byte(ALT_FPGA_BRIDGE_LWH2F_OFST+FIFO_OUT_READ_REQ_PIO_BASE,1);
	data=alt_read_word(ALT_FPGA_BRIDGE_LWH2F_OFST+IDATA_PIO_BASE);
	alt_write_byte(ALT_FPGA_BRIDGE_LWH2F_OFST+FIFO_OUT_READ_REQ_PIO_BASE,0);
	*(SDRAM_ptr + 2*segments)=(data&0x00FF0000)>>16;
	*(SDRAM_ptr + 2*segments + 1)=(data&0x0000FFFF);
	segments++;
}

int decompress(){
	int i,j,x=0,y=0;
	int data,pixel_out,pixelCount;
	for(i=0;i<segments;i++){
		data=(*(SDRAM_ptr + 2*i)<<16)+(*(SDRAM_ptr + 2*i + 1));
		pixel_out=data>>23;
		pixelCount=data&0x7FFFFF;
		for(j=0;j<pixelCount;j++){
			*(Video_Mem_ptr + (y<<9) + x)=pixel_out*0xFFFF;
			x++;
			if(x==320){
				y++;
				x=0;
			}
		}
	}
}