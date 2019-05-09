format long
N=20;
func=@(x) exp(x);
x0=1/2;
h=vertcat((0.1),zeros(N-1,1));
f1=zeros(N,1);
for i=1:N
    f1(i)=(func(x0+h(i))-func(x0-h(i)))/(2*h(i));
    if(i<N)
        h(i+1)=h(i)/2;
    end
end
f1
x1=func(x0);     %since the derivative of e^x is e^x
f2=repmat(x1,N,1);
abserr=abs(f2-f1)
loglog(h,abserr)