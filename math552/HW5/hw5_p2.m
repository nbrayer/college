m=100;
f=rand(m,1);
u0=rand(m,1);

cg=struct('tol',1e-10,'nmax',100);
std=struct('tol',1e-10,'nmax',100);
params=struct('cg',cg,'std',std);

for p=1:3
    for i=1:m
        l1(i)=10^(-p)+i*(1-10^(-p))/(m-1);
        l2(i)=10^(-p)+(1-10^(-p))*cos(i*pi/(2*(m-1)));
    end
    A1=diag(l1);
    A2=diag(l2);

    [uk_std1,resout_std1]=my_std(A1,f,u0,params);
    [uk_cg1,resout_cg1]=my_cg(A1,f,u0,params);
    [uk_std2,resout_std2]=my_std(A2,f,u0,params);
    [uk_cg2,resout_cg2]=my_cg(A2,f,u0,params);
end