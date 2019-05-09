// bank.java
// demonstrates basic OOP syntax
// to compile this program: >javac bank.java
// to run this program: >java BankApp
////////////////////////////////////////////////////////////////
class BankAccount
{
  // Variables
  
  private double balance;            
  
  // Constructors
  
  public BankAccount(double balance) 
  {
    this.balance = balance;
  }
  
  // Methods
  
  public void deposit(double amount)        // makes deposit
  {
    System.out.println("Deposit requested "+amount);
    balance = balance + amount;
    display();
  }
  
  public void withdraw(double amount)       // makes withdrawal
  {
    System.out.println("Withdrawal requested "+amount);
    if (balance-amount<0.0)
      System.out.println("Sorry you do not have enough in your account");
    else
      balance = balance - amount; 
    display();        
  } 
  
  public void display()                     // displays balance
  {
    System.out.println("balance=" + balance);
    System.out.println("");
  }
  
}  // end class BankAccount
////////////////////////////////////////////////////////////////

class BankApp
{
  public static void main(String[] args)
  {
    BankAccount ba1 = new BankAccount(100.00); // create acct
    
    ba1.display();                         // display balance                    
    
    ba1.deposit(74.35);                    // make deposit
    ba1.withdraw(200.00);                  // make withdrawal
    
  }  // end main()
}  // end class BankApp
