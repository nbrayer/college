import java.io.*;
import java.util.*;
public class MediaLibraryApplication
{  public static void main(String args[])
   {  MediaCollection store1=new MediaCollection("Best Media",20,100.0);
      store1.initializeCollection("bestmedia.txt");
      System.out.println("Welcome to "+store1.getName());
      System.out.println("==================");
      
      Scanner scan=new Scanner(System.in);
      int command=0;
      
      do
      {  System.out.println("Menu");
         System.out.println("====");
         System.out.println("1-See List of Items");
         System.out.println("2-See Item Description");
         System.out.println("3-Add an Item");
         System.out.println("4-Remove an Item");
         System.out.println("5-Set Maximum Price");
         System.out.println("6-Sort Collection");
         System.out.println("0-Exit");
         System.out.println("Command: ");
         command=scan.nextInt();
         if(command==1)
            store1.printCollection();
         else if(command==2)
            store1.detailedItem();
         else if(command==3)
            store1.addItem();
         else if(command==4)
            store1.removeItem();
         else if(command==5)
            store1.maxPrice();
         else if(command==6)
            store1.sort();
         else if(command==0)
            break;
      }while(command>=0&&command<7);
      System.out.println("Goodbye!");
   }
}