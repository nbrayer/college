clearvars;
format long;
clc;
close all;

a = 0; 
b = 1; 
m = 101;                       % Number of internal grid points.
h = (b-a)/(m+1);               % Lattice step-size.
x = linspace(a,b,m+2);         % Grid points x including boundaries.
y = linspace(a,b,m+2);         % Grid points y including boundaries.

[X,Y] = meshgrid(x,y);         % 2D arrays of x,y values.
X = X';                        % Transpose both so that they are
Y = Y';                        % coordinates of (i,j) point.

Iint = 2:m+1;                  % Indices of interior points in x.
Jint = 2:m+1;                  % Indices of interior points in y.
Xint = X(Iint,Jint);           % Interior points.
Yint = Y(Iint,Jint);           %         >>     .

   f = @(x,y) 2*x*(y-1)*(y-2*x+x*y+2)*exp(x-y); % Source term: f(x,y).
 rhs = f(Xint,Yint);           % Evaluate f at interior points for rhs.
                               % Rhs is modified below for boundary conditions.
 
utrue = exp(X-Y)*X*(1-X)*Y*(1-Y);            % True solution for test problem
usoln = utrue;              

rhs(:,1) = rhs(:,1) - usoln(Iint,1)/h^2;
rhs(:,m) = rhs(:,m) - usoln(Iint,m+2)/h^2;
rhs(1,:) = rhs(1,:) - usoln(1,Jint)/h^2;
rhs(m,:) = rhs(m,:) - usoln(m+2,Jint)/h^2;

F = reshape(rhs,m*m,1);

I = speye(m);
e = ones(m,1);
T = spdiags([e -4*e e],[-1 0 1],m,m);
S = spdiags([e e],[-1 1],m,m);
A = (kron(I,T) + kron(S,I)) / h^2;

uvec = A \ F;  
 
usoln(Iint,Jint) = reshape(uvec,m,m);

err = max(max(abs(usoln-utrue))); 
fprintf('Error relative to true solution of PDE = %10.3e \n',err)

figure;
set(gca,'fontsize',20,'fontname','times');
imagesc(x,y,usoln); colorbar; 
xlabel('$y$','interpreter','latex');
ylabel('$x$','interpreter','latex');
set(gca,'fontsize',20,'fontname','times');

figure;
set(gca,'fontsize',20,'fontname','times');
surf(X,Y,usoln);
xlabel('$x$','interpreter','latex');
ylabel('$y$','interpreter','latex');
zlabel('$z$','interpreter','latex');
shading interp;
lightangle(-45,30)
lighting gouraud;
set(gca,'fontsize',20,'fontname','times');