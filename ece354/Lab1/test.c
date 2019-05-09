#include <stdio.h>
int main()
{	int dan=28953469
	int jordan=28292397
	int noah=28886025

	int id={dan,jordan,noah};
	int i=0,j=0,sum=0;
	for(i=0;i<3;i++)
	{	for(j=0;j<8;j++)
		{	sum+=id[i]%10;
			id[i]=id[i]/10;
			printf("new id:%d",id[i]);
		}
	}
	endCount=sum%100;
	printf("endCount:%d",endcount);
}