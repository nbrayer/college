function y=trap(f,a,b,N) 

    h=(b-a)/N;
    sum=vpa(subs(f,a))+vpa(subs(f,b));
    for i=1:N-1
        sum=sum+2*vpa(subs(f,a+i*h));
    end
    y=(h/2)*sum;
end

