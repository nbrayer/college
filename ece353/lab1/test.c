#include <stdio.h>

void test(void);

int main(void)
{	char c,letter,encrypted,decrypted;
	FILE *toEncrypt,*test;
	toEncrypt=fopen("toEncrypt.txt","r");
	test=fopen("test.txt","w");
	scanf("%c",&c);
	while(!feof(toEncrypt))
	{	fscanf(toEncrypt,"%c",&letter);
		encrypted=c^letter;
		decrypted=encrypted^c;
		printf("\n%c\t%c\t%c",letter,encrypted,decrypted);
		fprintf(test,"%c",decrypted);
	}	
	return(0);
}