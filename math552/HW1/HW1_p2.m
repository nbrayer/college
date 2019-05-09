format long;

u=@(x)sin(2*x);
u2prime=@(x)-4*sin(2*x);
xbar=1;
d4u=zeros(11,1);
e4u=zeros(11,1);
h=zeros(11,1);
h(1)=0.1;

for i=1:11
    d4u(i)=(-1*u(xbar-2*h(i))+16*u(xbar-h(i))-30*u(xbar)+16*u(xbar+h(i))-1*u(xbar+2*h(i)))/(12*h(i)^2);
    e4u(i)=abs(d4u(i)-u2prime(xbar));
    if(i<11)
        h(i+1)=h(i)/2;
    end
end

n=6;

table=cat(2,h,e4u)
slope=regress(log(h(1:n)),log(e4u(1:n)))
order=1/slope

figure('Name','Exercise 2');
loglog(h,e4u);
title('Error vs. h');
xlabel('h');
ylabel('Error E4u');
grid on;
