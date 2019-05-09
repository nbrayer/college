function x=sor(A,b,w)
n=size(A,1);
x=zeros(n);
while(tol>
    for i=1:n
        o=0;
        for j=1:n
            if(j!=i)
                o=o+A(i,j)*x(j);
            end
        end
        x(i)=(1-w)*x(i)+w/A(i,i)*(b(i)-o);
    end
end