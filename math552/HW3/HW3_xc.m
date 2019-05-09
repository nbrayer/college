R=1000;
P=0.7*R;

a=0;
ua=0;
u_1a=0;
wa=0;
ta=0;

b=1;
ub=1;
u_1b=0;
wb=0;
tb=1;

m=124;
h=1/(m+1);
eps=1e-6;
x=(a:h:b)';
xin=x(2:end-1);

u=xin;
u0=u;

D4u=
D3u=
D0u=