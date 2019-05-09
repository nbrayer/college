m=124;
h=1/(m+1);
x=[0:h:1]';

func=@(x)400*cos(pi*x).^2+2*pi^2*cos(2*pi*x);
a=0;
b=0;

d1=(-2-400*h^2)*ones(m,1);
d2=ones(m,1);
d3=ones(m,1);
F=h^2*[func(x(2))-a/h^2;func(x(3:end-2));func(x(end-1))-b/h^2];
A=(diag(d1,0)+diag(d2(1:m-1),-1)+diag(d3(2:m),1));
U=A\F;    
U=[a;U;b];

u=@(x)exp(-20)/(1+exp(-20))*exp(20*x)+1/(1+exp(-20))*exp(-20*x)-cos(pi*x).^2;
 
norm(u(x)-U,inf)

figure;
set(gca,'FontSize',24,'Fontname','Times');
plot(x,u(x),'-k','LineWidth',2);
hold on;
plot(x,U,'or','LineWidth',2);
xlabel('$x$','Interpreter','latex');
hg = legend('Exact','Numerical');
set(hg,'FontSize',20,'Fontname','Times');
str = ['$||u_{\textrm{exact}}-u_{\textrm{numerical}}||_{inf}=$',...
num2str(norm(u(x)-U,inf))];
ht = title(str);
set(ht,'Interpreter','latex'); 