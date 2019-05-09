function x=my_linsolver(A,b)
a=[A b];
n=size(A,1);
for i=1:n-1
    for j=i+1:n
        for k=i+1:n+1
            a(j,k)=a(j,k)-a(j,i)/a(i,i)*a(i,k);
        end
    end
end
x(n)=a(n,n+1)/a(n,n);
for i=n-1:-1:1
    sum=0;
    for j=i+1:n
        sum=sum+a(i,j)*x(j);
    end
    x(i)=1/a(i,i)*(a(i,n+1)-sum);
end
x=x.';