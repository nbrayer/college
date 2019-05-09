clearvars;
close all;
clc;
format long;

m=124;
a=0;
b=1;
h=(b-a)/(m+1);
x=(a:h:b)';
xin=x(2:end-1);
ua=4;
ub=1;

u=-3*xin+4;
u0=u;
u_2=@(u)[(3/2)*u.^2];

f=@(u)[(ua-2*u(1)+u(2))/h^2-u_2(u(1));diff(u,2)/h^2-u_2(u(2:m-1));(u(m-1)-2*u(m)+ub)/h^2-u_2(u(m))];

eps=1e-10;
nmax=100;
fprintf('k      ||f(x_k)||      ||delta|| \n');

for k=1:nmax
    fu=f(u);
    
    a1=-2*ones(m,1)/h^2+cos(u);
    a2=ones(m,1)/h^2;
    a3=ones(m,1)/h^2;
    d=trisolve(a1,a2,a3,-fu);

    fprintf('%d     %e    %e \n',k-1,norm(fu),norm(d,'inf'))
    u=u+d;
    if(norm(d)<eps*(1+norm(u)))
        fu=feval(f,u);
        fprintf('%d     %e    %e \n',k,norm(fu),norm(d,'inf'))
        break
    end
end

figure('Name','Problem 2');
plot(x,[ua;u0;ub],'-r');
hold on;
plot(x,[ua;u;ub],'-k');
xlabel('u');
ylabel('u(x)');
legend('Initial guess','Numerical solution');