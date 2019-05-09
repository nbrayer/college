format long;
x=linspace(2,4);
f=1./x;
p=(13/12)-(3/8).*x+(1/24).*x.^2;
e=f-p;
norm(e,inf)