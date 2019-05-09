clearvars;
close all;
clc;
format long;

k=1;
a=0;
b=1;
m=124;
h=(b-a)/(m+1);
eps=10^-10;

u_2=@(u)3/2*u^2;
ua=4;
ub=1;
x=(ua:h:ub)';
xin=x(2:end-1);
u=(-1/3)*xin+4;
u0=u;

while(norm(dif,2)>eps*(1+norm(U(k),2)))
    U(k)=u_2(x);
    
    a1=-2*ones(m,1)/h^2;
    a2=ones(m,1)/h^2;
    a3=ones(m,1)/h^2;
    
    n=trisolve(a1,a2,a3,-f);
    
    x=x+n;
    dif(k)=U(k+1)-U(k);
    k=k+1;
end

figure('Name','');
plot(x,[a;u0;b],'-r');
hold on;
plot(x,[a;u;b],'-k');
xlabel('x');
ylabel('u(x)');
legend('Initial Guess','Numerical Solution');

table([1:k]',norm(resid,2),norm(dif,'inf'),'VariableNames',{'Step','l2-norm','infinity norm'})