function x=trisolve(a,b,c,f)
n=size(a,2);
for k=1:n-1
   l(k+1)=b(k)/a(k);
   a(k+1)=a(k+1)-l(k+1)*c(k);
end
y(1)=f(1);
for k=2:n
    y(k)=f(k)-l(k)*y(k-1);
end
x(n)=y(n)/a(n);
for k=n-1:-1:1
    x(k)=(y(k)-c(k)*x(k+1))/a(k);
end
x=x.';