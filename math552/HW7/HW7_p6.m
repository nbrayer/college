format long;

u0=[3;4;2];
ti=0;
tf=8;

func=@(t,u)[-3*u(1)*u(2)+1*u(3);-3*u(1)*u(2)+1*u(3);3*u(1)*u(2)-1*u(3)];
k=0.1;
[uout,tout]=euler_fwd(func,ti,tf,k,u0);
figure;
plot(tout,uout);

func=@(t,u)[-300*u(1)*u(2)+1*u(3);-300*u(1)*u(2)+1*u(3);300*u(1)*u(2)-1*u(3)];
k=1e-3;
[uout,tout]=euler_fwd(func,ti,tf,k,u0);
figure;
plot(tout,uout);