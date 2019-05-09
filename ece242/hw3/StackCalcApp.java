/////////////////////////
class StackCalcApp
{
  public static void main(String[] args)
  {
    StackCalc mystack = new StackCalc(100); //100 max capacity
    EasyIn easy = new EasyIn(); 
    System.out.println("Welcome to the RPN calculator (enter 'exit' to quit)");
    String data=""; 
    
    while (!data.equals("exit")){
      
      System.out.print(">");
      data = easy.readString();
      
      switch(data)
      {
// to complete
      }
      
    }
  }
}
