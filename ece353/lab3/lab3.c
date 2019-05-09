//Group Members: Nigel Paine, Noah Brayer, Ivan Williams+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <assert.h>

#define H 0.005 // RK Algorithm Step Size
#define E_A 0.8 // Activation Energy in eV
#define K 8.617e-5 // Boltzmann's Constant in eV/K
#define T 300 // default ambient temperature in K

int rows;

void getCVals(char*,double[]);
void getRVals(char*,double[][5]);
void getPVals(char*,double[][4]);
void outputVals(char*,double[][5],double[][4]);
double agingFactor(double,double);
void rk(int,int,int,double[][5],double[],double[],double[]);
double f(int,int,double[][5],double[],double[],double[]);

int main(int argc,char *argv[])
{	extern int rows;
	double cVals[4];    // create array for capacitor values
	double rVals[4][5]; // create array for resistor values
	double pVals[10][4];    // create array for power values
	int i,j,aTemp;
	char *outputFileName;

	if(argc==4)     // if only three command line inputs are provided
	{	aTemp=T;    // use default ambient temperature
		outputFileName=argv[3]; // set output file to last input
	}
	else if(argc==5)    // if four command line inputs are provided
	{	aTemp=atoi(argv[3]);    // use ambient temperature provided
		outputFileName=argv[4]; // set output file to last input
	}
	else    // if three or four command line inputs are not provided
	{	printf("Please enter either 3 or 4 command line arguments\n");  // inform user
		exit(0);    // and exit
	}
	getCVals(argv[1],cVals);    // get cVals from file specified by first command line input
	getRVals(argv[1],rVals);    // get rVals from file specified by first command line input
	getPVals(argv[2],pVals);    // get pVals from file specified by second command line input

	double tVals[rows][5];  // create array for temperature values
	double aVals[rows][4];  // create array for age values
	
	for(i=0;i<5;i++)
	{   tVals[0][i]=aTemp;
	    aVals[0][i]=0;
	}
	for(i=1;i<rows;i++)
	{   for(j=0;j<5;j++)
	        tVals[i][j]=tVals[i-1][j];
	    rk(0,5,aTemp,rVals,cVals,pVals[i],tVals[i]);    // calculate temperature values
	    for(j=0;j<4;j++)
	        aVals[i][j]=aVals[i-1][j];
	    rk(1,5,aTemp,rVals,tVals[i-1],pVals[i],aVals[i]);   // calculate age values
	}
	outputVals(outputFileName,tVals,aVals);
}
void getCVals(char *paramFileName,double cVals[])
{	int i;
	FILE* paramFile;
	paramFile=fopen(paramFileName,"r"); // open parameter file
	for(i=0;i<4;i++)
	{   fscanf(paramFile,"%lf",&cVals[i]);  // read capacitor values
	    assert(cVals[i]>=0);    // check that capacitor values are non-negative
	}
	fclose(paramFile);
}
void getRVals(char *paramFileName,double rVals[][5])
{	int i,j;
	FILE* paramFile;
	paramFile=fopen(paramFileName,"r"); // open parameter file
	for(i=0;i<4;i++)
		fscanf(paramFile,"%lf",&rVals[0][i]);
	for(i=0;i<5;i++)
		for(j=0;j<5;j++)
		{	fscanf(paramFile,"%lf",&rVals[i][j]);   // read resistor values
		    assert(rVals[i][j]>=0); // check that resistor values are non-negative
		}
	fclose(paramFile);
}
void getPVals(char *powerFileName,double pVals[][4])
{	extern int rows;
	int i,n=0;
	FILE* powerFile;
	powerFile=fopen(powerFileName,"r"); // open power file
	do
	{	for(i=0;i<4;i++)
		{	fscanf(powerFile,"%lf",&pVals[n][i]);   // read power values
		    assert(pVals[n][i]>=0); // check that power values are non-negative
		}
		n++;
	}while(getc(powerFile)!=EOF);
	rows=n;
	fclose(powerFile);
}
void outputVals(char *outputFileName,double tVals[][5],double aVals[][4])
{	extern int rows;
	int i,n=0;
	double time;
	FILE* outputFile;
	outputFile=fopen(outputFileName,"w");   // open output file
	while(n<rows)
	{	time=(double)n*H;
		fprintf(outputFile,"%lf",time); // print time
		for(i=0;i<4;i++)
			fprintf(outputFile,"\t%lf\t%lf",tVals[n][i],aVals[n][i]);   // print temperature and age values
		n++;
	    fprintf(outputFile,"\n");
	}
	fclose(outputFile);
}
double agingFactor(double temp,double aTemp)
{	double ageEffect1,ageEffect2;
	ageEffect1=exp((-E_A/(K*temp)));
	ageEffect2=exp((-E_A/(K*aTemp)));
	return(ageEffect1/ageEffect2);
}
void rk(int function,int m, int temp, double rVals[][5], double cVals[], double pVals[], double tVals[]) //Runge-Kutta Algorithm 
{   int i,j;
    double k[4][m];
    double tempT[m];
    int n=m-1;
if(function==0)
{   for(i=0;i<n;i++)
        k[0][i]=H*f(n,i,rVals,cVals,pVals,tVals);
    for(i=0;i<n;i++)
        tempT[i]=tVals[i]+k[0][i]/2;
    for(i=0;i<n;i++)
        k[1][i]=H*f(n,i,rVals,cVals,pVals,tempT);
    for(i=0;i<n;i++)
        tempT[i]=tVals[i]+k[1][i]/2;
    for(i=0;i<n-1;i++)
        k[2][i]=H*f(n,i,rVals,cVals,pVals,tempT);
    for(i=0;i<n;i++)
        tempT[i]=tVals[i]+k[2][i]/2;
    for(i=0;i<n;i++)
        k[3][i]=H*f(n,i,rVals,cVals,pVals,tempT);
    for(i=0;i<n;i++)
    {   tVals[i]=tVals[i]+(k[1][i]+2*k[2][i]+2*k[3][i]+k[4][1])/6;
        assert(tVals[i]>=temp);
    }
}
else
{   for(i=0;i<n;i++)
        k[0][i]=H*agingFactor(cVals[i],temp);
    for(i=0;i<n;i++)
        tempT[i]=cVals[i]+k[0][i]/2;
    for(i=0;i<n;i++)
        k[1][i]=H*agingFactor(tempT[i],temp);
    for(i=0;i<n;i++)
        tempT[i]=cVals[i]+k[1][i]/2;
    for(i=0;i<n-1;i++)
        k[2][i]=H*agingFactor(tempT[i],temp);
    for(i=0;i<n;i++)
        tempT[i]=cVals[i]+k[2][i]/2;
    for(i=0;i<n;i++)
        k[3][i]=H*agingFactor(tempT[i],temp);
    for(i=0;i<n;i++)
        tVals[i]=tVals[i]+(k[1][i]+2*k[2][i]+2*k[3][i]+k[4][1])/6;
}
}
double f(int n,int core,double rVals[][5],double cVals[],double pVals[],double tVals[])
{   int i;
    double dTemp;
    double sum=0;
    for(i=0;i<n;i++)
        if(i!=core)
            sum+=(tVals[core]-tVals[i])/rVals[core][i];
    dTemp=(pVals[core]-sum)/cVals[core];
    return(dTemp);
}