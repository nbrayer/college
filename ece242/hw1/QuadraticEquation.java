public class QuadraticEquation {
    // a quadratic equation a*x^2 + b*x + c = 0
  
    double a;
    double b;
    double c;
   
    // Constructor
    
    QuadraticEquation(double a, double b, double c) {
 // create a new quadratic equation with the given coefficients
 this.a = a;
 this.b = b;
 this.c = c;
    }

    // Methods
    
    private String toSignedString(double d) {
 // creates string from double value with explicit sign
 if (d >= 0) {
     return("+ " + d);
 } else {
     return("- " + (-d));
 }
    }      


    private double discriminant() {
 // the discriminant of the equation
 return(b*b/(4*a*a) - c/a); 
    }


    public double[] getSolution() {
 // returns vector of solution values
 double a1,d,dRoot,result[];

 a1 = -b/(2*a);
 d = discriminant();

 if (d > 0) {
     result = new double[2];
     dRoot = Math.sqrt(d);
     result[0] = a1 + dRoot;
     result[1] = a1 - dRoot;
 } 
 else if (d == 0) {
     result = new double[1];
     result[0] = a1;
 }
 else {
     // d<0: result is empty in real arithmetics
     result = new double[0];
 }
    
 return(result);
    }


    public void print() {
 // print the equation to the screen
 System.out.println(a + "*x^2 "+ toSignedString(b) + "*x " + toSignedString(c)+" = 0");
    }

}


