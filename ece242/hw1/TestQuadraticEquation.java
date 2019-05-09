public class TestQuadraticEquation {
    // simple test program for QuadraticEquation
    // solve  quadratic equation a*x^2 + b*x + c = 0
    // with a, b, c provided in command line  
 
    
    public static void main (String[] args) {
 
 // get the coefficients from the command line
 double a,b,c;
 double result[];
 int count;

 a = Double.parseDouble(args[0]);
 b = Double.parseDouble(args[1]);
 c = Double.parseDouble(args[2]);

 QuadraticEquation qe1 = new QuadraticEquation(a,b,c);

 qe1.print();    
 result = qe1.getSolution(); // array that contains solutions
 count =  result.length; 
 System.out.println("This equation has " + count + " solution(s)");
    
 if (count > 0) {
     System.out.println(result[0]);
 }
 if (count > 1) {
     System.out.println(result[1]);
 }
    }
}