function[x,y,k]=newtons_method_2(f,g,x0,y0,atol,nmax)
    format long;
    xk=rand;
    yk=rand;
    resx=xk-x0;
    resy=yk-y0;
    k=0;
    flag=0;
    syms x;
    fx=diff(f,x);
    gx=diff(g,x);
    syms y;
    fy=diff(f,y);
    gy=diff(g,y);
    while(abs(resx)>atol||abs(resy)>atol)
        FX=subs(subs(fx,x,x0),y,y0);
        FY=subs(subs(fy,x,x0),y,y0);
        GX=subs(subs(gx,x,x0),y,y0);
        GY=subs(subs(gy,x,x0),y,y0);
        denom=FX*GY-FY*GX;
        xk=x0-(feval(f,x0,y0)*GY-feval(g,x0,y0)*FY)/denom;
        yk=y0-(feval(g,x0,y0)*FX-feval(f,x0,y0)*GX)/denom;
        double(xk);
        double(yk);
        resx=xk-x0;
        resy=yk-y0;
        x0=xk;
        y0=yk;
        k=k+1;
        if(k>=nmax)
            flag=-1;
            break;
        end
    end
    if(flag==0)
        k = k + 1;
        x = double(xk)
        y = double(yk)
    else
        disp('Maximum number of iterations reached'); 
        x = []
        y = []
        k = []
    end
end