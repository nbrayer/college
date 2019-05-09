package employee;

import java.util.ArrayList;

public class EmployeeDriver{

	public static void main (String[] args){
	   Employee[] employees = new Employee[5];
	   employees[0] = new Employee("1234", null, "Smith", 23);
      employees[1] = new Employee("1235","Tom", "Smythe", 18);
      employees[2] = new Employee("1236","Tony", "Stark", 26);
      employees[3] = new Employee("1237","Sabrina", "Cruz", 59);
      employees[4] = new Employee("1238","Lisa", "Hall", 23);
      
     // printArray(employees);
      
      
     int k=0;
      
      while(employees[k]!=null && k<employees.length) {
           System.out.println(employees[k].getLastName());
           System.out.print("test 1");
           k++;
      }
  }
  
  public static void printArray(Employee[] employees){
       System.out.println("EmpID  First   Last   Age");
       System.out.print("test 2");
      for(int k=0;k<employees.length;k++){
         if(employees[k]!=null)
           System.out.println(employees[k].toString());
      }

  }
 }
