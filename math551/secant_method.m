function[x,k]=secant_method(f,x0,x1,atol,nmax)
    format long;
    xk=rand;
    res=xk-x1;
    k=0;
    flag=0;
    while(abs(res)>atol)
        xk=x1-(feval(f,x1)*(x1-x0))/(feval(f,x1)-feval(f,x0));
        res=xk-x1;
        x1=x0;
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