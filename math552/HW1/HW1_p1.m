format long;

u=@(x)cos(x)*sin(x);
uprime=@(x)cos(x)^2-sin(x)^2;
xbar=1.2;
d2u=zeros(11,1);
e2u=zeros(11,1);
h=zeros(11,1);
h(1)=0.1;

for i=1:11
    d2u(i)=(3*u(xbar)-4*u(xbar-h(i))+u(xbar-2*h(i)))/(2*h(i));
    e2u(i)=abs(d2u(i)-uprime(xbar));
    if(i<11)
        h(i+1)=h(i)/2;
    end
end

table=cat(2,h,e2u)
slope=regress(log(h),log(e2u))
order=1/slope

figure('Name','Exercise 1');
loglog(h,e2u);
title('Error vs. h');
xlabel('h');
ylabel('Error E2u');
grid on;