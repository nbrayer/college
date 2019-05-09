function x = forsub(A,b)
%
% function x = forsub(A,b)
%
% Given a lower triangular, nonsingular n by n matrix A and
% an n-vector b, return vector x which solves Ax = b

n = length(b); x = b;
x(1) = b(1) / A(1,1);
for k = 2:n
  x(k) = ( b(k) - A(k,1:k-1)*x(1:k-1) ) / A(k,k);
end