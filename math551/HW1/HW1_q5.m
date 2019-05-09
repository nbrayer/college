format long
a=10;
N=20;
func=@(x) 1./(x+a);
y0=integral(func,0,1)
y=vertcat(y0,zeros(19,1));
n=vertcat(1,zeros(19,1));
for i=2:N
   y(i)=1/(i-1)-a*y(i-1);
   n(i)=i;
end
y
figure
plot(n,y)
title('yn as a function of n');
xlabel('n');
ylabel('yn');