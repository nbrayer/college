package employee;

public class Employee{

  int employeeID;
  int age;
  String fName;
  String lName;
  
  
  public Employee(String idStr, String fn, String ln, int age){
     employeeID = Integer.parseInt(idStr);
     this.age=age;
     fName=fn;
     lName=ln;
  }

  public Employee(int id, String fn, String ln, int age){
     this.age=age;
     fName=fn;
     lName=ln;
     employeeID=id;
  }


  public int getAge() {
     return age;
  }
  
  public int getID(){
      return employeeID;
  }

  public String getFirstName() {
     return fName;
  }
  
  public String getLastName() {
     return lName;
  }
  
  public String toString(){
     return employeeID+" "+fName+" "+lName+" "+age;
  }
}