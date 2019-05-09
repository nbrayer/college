f1=@(x) 3*x+1;
f2=@(x) x.*exp(-x.^2);
f3=@(x) cos(x)+1;
f={f1;f2;f3};
a=[0;0;0];
b=[1;1;2*pi];
for i=1:3
    for j=1:6
        N=2^j;
        h(i,j)=((b(i)-a(i))/N)
        trapsum(i,j)=trap(f{i},a(i),b(i),N)
        err(i,j)=integral(f{i},a(i),b(i))-trapsum(i,j)
        errh(i,j)=err(i,j)/(h(i,j)^2)
    end
end


