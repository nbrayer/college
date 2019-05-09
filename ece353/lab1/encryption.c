#include <stdio.h>
#include <string.h>
#define LENGTH 256

int getKey(char[],char[]);
void permutation(int,char[],char[]);
char generateKeyByte(char[]);
void encrypt(char[],char[],char[]);

int main(int argc,char *argv[])
{	int i,keyLength;
	char key[LENGTH],S[LENGTH],T[LENGTH];
	if(argc!=4)
	{	printf("Not enough arguments");
		exit(0);
	}
	keyLength=getKey(argv[1],key);	
	for(i=0;i<keyLength;i++)
	{	S[i]=i;
		T[i]=key[i%keyLength];
	}
	permutation(keyLength,S,T);
	encrypt(argv[2],argv[3],S);
	return(1);
}
int getKey(char keyFileName[],char key[])
{	int length=0;
	char letter;
	FILE *keyFile;
	keyFile=fopen(keyFileName,"r");
	while((!feof(keyFile))&&(length<256))
	{	fscanf(keyFile,"%c",&letter);
		key[length]=letter;
		length++;
	}
	fclose(keyFile);
	return(length-1);
}
void permutation(int keyLength,char S[],char T[])
{	int i,j=0;
	char temp;
	for(i=0;i<keyLength;i++)
	{	j=(j+S[i]+T[i])%256;
		temp=S[i];
		S[i]=S[j];
		S[j]=temp;
	}
	return(1);
}
char generateKeyByte(char S[])
{	static int i=0,j=0;
	int t;
	char temp;
	i=(i+1)%LENGTH;
	j=(j+(int)S[i])%LENGTH;
	temp=S[i];
	S[i]=S[j];
	S[j]=temp;
	t=((int)S[i]+(int)S[j])%LENGTH;
	return(S[t]);
}
void encrypt(char plaintextFileName[],char encryptedFileName[],char S[])
{	char plain,coded,generated;
	FILE *plaintext,*encrypted;
	plaintext=fopen(plaintextFileName,"r");
	encrypted=fopen(encryptedFileName,"w");
	while(!feof(plaintext))
	{	fscanf(plaintext,"%c",&plain);
		generated=generateKeyByte(S);
		coded=plain^generated;
		fprintf(encrypted,"%c",coded);
	}
	fclose(plaintext);
	fclose(encrypted);
}