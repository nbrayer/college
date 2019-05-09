function x=my_linsolver_lu(A,b)
n=size(A,1);
L=eye(n);
U=A;
for i=1:n
    for j=i+1:n
        L(j,i)=U(j,i)/U(i,i);
        for k=1:n
            U(j,k)=U(j,k)-L(j,i)*U(i,k);
        end
    end
end
for i=1:n
    y(i)=b(i);
    for j=1:i-1
        y(i)=y(i)-L(i,j)*y(j);
    end
end
for i=n:-1:1
    x(i)=y(i);
    for j=i+1:n
        x(i)=x(i)-U(i,j)*x(j);
    end
    x(i)=x(i)/U(i,i);
end
x=x.';