pcg=struct('tol',1e-13,'nmax',1e5);
cg=struct('tol',1e-13,'nmax',1e5);
params=struct('pcg',pcg,'cg',cg);

f = @(x,y) 2*x*(y-1)*(y-2*x+x*y+2)*exp(x-y);
m = 49;
u0 = zeros(m,1);
uexact = @(x,y) exp(x-y)*x*(1-x)*y*(1-y);
omega=1.75;