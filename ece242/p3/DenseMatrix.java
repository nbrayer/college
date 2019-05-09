class DenseMatrix implements Matrix
{  private int size=0; // size of the matrix- number of rows/columns
   private int nnz=0; // number of non-zero elements
   private double[][] data;
   // Constructor used to initialize the matrix (all elements to zero)
   DenseMatrix(int size)
   {  this.size=size;
      data=new double[size][size]; // all elements take the values 0.0d
   }
   // Constructor with Random generator (using nnz random non-zero numbers between 0.01<= x < 1.01
   // on any random row,column coordinates)
   DenseMatrix(int size,int nnz)
   {  int nnzCheck=nnz; // set non-zero counter for while() loop
      data=new double[size][size];   // all elements take the values 0.0d
      while(nnzCheck>0)    // while there are still non-zero cels that need to be filled
      {  int i=(int)(Math.random()*size); // find a random row
         int j=(int)(Math.random()*size); // find a random column
         if(data[i][j]==0)    // if the entry in that random cel is 0
         {  data[i][j]=Math.random()+.01; // replace it with a random non-zero number
            nnzCheck--;    // and decrement the non-zero counter
         }         
      }
   }
   // Constructor from any other matrix storage using the interface to Dense storage
   DenseMatrix(Matrix A)
   {  this.size=A.getSize();  // take the know size
      this.nnz=A.getNnz();    // take the know non-zero value
      data=new double[size][size];
      for (int i=0;i<size;i++)   // increment the rows of the matrix
         for (int j=0;j<size;j++)// increment the columns of the matrix
            data[i][j]=A.get(i,j);
   }
   // get the size of the matrix
   public int getSize()
   {  return size;
   }
   // get the number of non-zero elements
   public int getNnz()
   {  return nnz;
   }
   // Assign the value x to element i,j
   public void set(int i,int j, double x)
   {  if ((data[i][j]==0)&&(x!=0.0))// if the existing entry is zero and the new entry is non-zero,
         nnz++;                     // increment the non-zero counter
      if ((data[i][j]!=0)&&(x==0.0))// if the existing entry is non-zero and the new entry is zero,
         nnz--;                     // decrement the non-zero counter 
      data[i][j]=x;
   }
   // get the value of the element i,j
   public double get(int i, int j)
   {  return(data[i][j]);
   }
   // Print matrix using a specific format
   public void display()
   {  System.out.println();
      System.out.println("Display in dense format");
      for (int i=0;i<size;i++)
      {  for (int j=0;j<size;j++)
            System.out.format("%.4f ",get(i,j));
         System.out.println();
      }
      System.out.println();
   }
   // get the elements of the diagonal
   public double[] getDiagonal()
   {  double[] diagonal=new double[size];
      for(int i=0;i<size;i++)    // since the diagonal has the same indices for row and column,
         diagonal[i]=get(i,i);   // we only need one looping variable
      return(diagonal);
   }
   // Multiply a matrix by a vector
   public Vector multiply(Vector B)
   {  Vector result=new Vector(size);
      int sum;
      for(int i=0;i<size;i++) // loops through the matrix rows
      {  sum=0;   // initializes the sum for reuse
         for(int j=0;j<size;j++) // loops through the matrix columns and vector rows
            sum+=B.get(j)*data[i][j];  // adds the vector rows multiplied by the matrix columns
         result.set(i,sum); // assigns the sum to the rows of the resultant
      }
      return(result);
   }
}