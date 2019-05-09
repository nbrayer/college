format long
xvals=[0;pi/6;pi/4;pi/3;pi/2];
yvals=[0;1/2;sqrt(2)/2;sqrt(3)/2;1];
x=linspace(0,pi/2);
y=sin(x);
n=size(xvals,1);
xint=ones(n,1);
for i=1:n
   xint=cat(2,xint,xvals.^i);
end
coeff=xint\yvals;
int=zeros(1,100);
for i=1:n
    int=int+coeff(i).*x.^(i-1);
end
hold on
plot(x,y,x,int)
scatter(xvals,yvals)