function[x,k]=newtons_method(f,x0,atol,nmax)
    format long;
    xk=rand;
    res=xk-x0;
    k=0;
    flag=0;
    syms x;
    fprime=diff(f,x);
    while(abs(res)>atol)
        xk=x0-feval(f,x0)/subs(fprime,x0);
        res=xk-x0;
        x0=xk;
        k=k+1;
        if(k>=nmax)
            flag=-1;
            break;
        end
    end
    if(flag==0)
        k = k + 1;
        x = double(xk);
    else
        disp('Maximum number of iterations reached'); 
        x = [];
        k = [];
    end
end