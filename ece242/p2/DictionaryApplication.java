import java.io.*;
import java.util.*;
class dictionaryApplication
{  public static void main(String args[])
   {  fastDictionary dictionary=new fastDictionary();
      Scanner scan=new Scanner(System.in);
      long startTime,endTime;
      String junk,dictionaryName="",word;
      boolean isLoaded=false,isSorted=false;
      int command;
      
      System.out.println();
      System.out.println("Welcome to the Dictionary App");
	   System.out.println("=============================");
	   do
      {  System.out.println();
	      System.out.println("Current Dictionary is '"+dictionary.getName()+"' of size "+dictionary.getSize()+" and "+dictionary.getStatus());  
	      System.out.println();
            
	      dictionary.menu();
	      String commandLine=scan.nextLine();
         if((int)commandLine.charAt(0)<48||(int)commandLine.charAt(0)>57||commandLine.length()>1)
            command=-1;
         else
            command=Integer.parseInt(commandLine);
         if(isLoaded=false&&command!=1)
         {  System.out.println("Dictionary must be loaded first");
            command=-1;
         }
         if(isSorted=false&&command>4)
         {  System.out.println("Dictionary must be sorted first");
            command=-1;         
         }
         switch(command)
         {  case 1:
               System.out.println("Enter dictionary name:");
               dictionaryName=scan.nextLine();
               isLoaded=dictionary.loadDictionary(dictionaryName);
               break;
            case 2:
               isSorted=true;
               dictionary.insertionSort();
               break;
            case 3:
               isSorted=true;
               dictionary.insertionSortEnhanced();
               break;
            case 4:
               isSorted=false;
               dictionary.shuffleDictionary();
               break;
            case 5:  
               System.out.println("Enter word to be searched:");
               word=scan.nextLine();
               dictionary.search(word);
               break;
            case 6:
               System.out.println("Enter File to be spell-checked:");
               String fileName=scan.nextLine();
               dictionary.spellCheckFile(fileName);
               break;
            case 7:
               System.out.println("Enter word to find anagrams:");
               word=scan.nextLine();               
               dictionary.anagram(word);
               break;
            case 0:
               break;
            default:
               System.out.println("Selection Invalid! - Try Again");   
         }
         junk=scan.nextLine();
      }while(command!=0);
      System.out.println("Goodbye!");
   }
}