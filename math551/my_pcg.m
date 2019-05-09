function [ xk, resout ] = my_pcg( A, b, x0, P, params )
%
% This function utilizes the preconditioned-conjugate
% gradient (PCG) method. 
%
% Input:    1) nxn matrix A and rhs vector b.
%              2) Initial iterate x0.
%              3) Preconditioner P.
%              4) Structure params containing tol and nmax.
%
% Output: 1) Solution vector xk.
%              2) Residuals and number of iterations resout.
%

% Unfold parameters:
     tol = params.pcg.tol;
nmax = params.pcg.nmax;

% Initializations:
invP = inv(P);       % Inverse of the preconditioner. 
   r0 = b - A * x0;  
   h0 = invP * r0; 
   d0 = dot(r0,h0);
   bd = dot( b, invP * b ); 
   p0 = h0;

 iflag = 0;
 fprintf('%s \n','k      ||r_{k}||_{2}');
fprintf('%s \n','-------------------');
for k = 1:nmax
 %      resout(k,1) = k-1;
 %      resout(k,2) = norm(b-A*x0,2);
       resout = 0; % something dummy. 
%       Comment out the lines 31-32
%       and 47-48. Then comment resout=0.
       fprintf ('%d     %e     \n',k-1,norm(r0,2) )
       sk = A * p0;
       ak = d0 / dot(p0,sk);       % Find the minimizer.
       xk = x0 + ak * p0;          % Update x along the steepest descent.
       rk = r0 - ak * sk;             % Update the residual.
      hk = invP * rk; 
      dk = dot(rk,hk);          
      pk = hk + ( dk / d0 ) * p0; % Search directions.
       if( dk <= tol^2 * bd )       % Checkpoint
           fprintf ('%d     %e     \n',k,norm(rk,2) );
 %          resout(k,1) = k;
 %          resout(k,2) = norm(b-A*xk,2);
           iflag = 1;
           break
       end
      % Substitutions:
      x0 = xk;
       r0 = rk;
       p0 = pk;
       d0 = dk;
end
if(k==nmax&&iflag==0)
    disp('PCG did not converge--increase iterations');
end
end