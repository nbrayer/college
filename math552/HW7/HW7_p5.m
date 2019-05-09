format long;
func=@(t,u)[-6 4; 4 -6]*u;
u0=[2;-8];
ti=0;
tf=2;
tol=1e-15;
nmax=100;

k=0.2;
[uout,tout]=euler_back(func,ti,tf,k,u0,tol,nmax);
uout
figure;
plot(tout,uout);
[uout,tout]=euler_fwd(func,ti,tf,k,u0);
uout
figure;
plot(tout,uout);

k=0.15;
[uout,tout]=euler_back(func,ti,tf,k,u0,tol,nmax);
uout
figure;
plot(tout,uout);
[uout,tout]=euler_fwd(func,ti,tf,k,u0);
uout
figure;
plot(tout,uout);

k=0.1;
[uout,tout]=euler_back(func,ti,tf,k,u0,tol,nmax);
uout
figure;
plot(tout,uout);
[uout,tout]=euler_fwd(func,ti,tf,k,u0);
uout
figure;
plot(tout,uout);

k=0.05;
[uout,tout]=euler_back(func,ti,tf,k,u0,tol,nmax);
uout
figure;
plot(tout,uout);
[uout,tout]=euler_fwd(func,ti,tf,k,u0);
uout
figure;
plot(tout,uout);