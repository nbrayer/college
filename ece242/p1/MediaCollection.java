import java.io.*;
import java.util.*;
public class MediaCollection
{  private String name;
   private MediaItem[] item;
   private static int maxItems;
   private double maxItemPrice;
   private int numberOfItems;
   private static String mediaType;
   
   public MediaCollection(String name,int maxItems,double maxItemPrice)
   {  this.name=name;
      this.maxItems=maxItems;
      this.maxItemPrice=maxItemPrice;
      item=new MediaItem[maxItems];
   }
   public String getName()
   {  return(name);
   }
   
   public void initializeCollection(String name)
   {  numberOfItems=0;
      maxItemPrice=-1;
	   try
      {  File file = new File(name);
	      Scanner scanner = new Scanner(file);
         while(scanner.hasNext()&&numberOfItems<=maxItems)
         {  mediaType=scanner.nextLine();
            String itemTitle=scanner.nextLine();
            String itemReference=scanner.nextLine();
            double itemPrice=Double.parseDouble(scanner.nextLine());
            item[numberOfItems]=new MediaItem(mediaType,itemTitle,itemReference,itemPrice);
            if(mediaType.compareTo("Book")==0)
               item[numberOfItems].setAuthor(scanner.nextLine());
            else if(mediaType.compareTo("Movie")==0)
            {  item[numberOfItems].setDirector(scanner.nextLine());
               item[numberOfItems].setActor(scanner.nextLine());            
		      }
            numberOfItems++;
            String junk;
            if(scanner.hasNext())
               junk=scanner.nextLine();
	      }
      }
      catch (FileNotFoundException e)
	   {   e.printStackTrace();
      }
   }
   public void printCollection()
   {  for(int i=0;i<item.length;i++)
         if(item[i]!=null)
            if(maxItemPrice>0)
            {  if(item[i].getPrice()<maxItemPrice)
                  System.out.println(i+1+" "+item[i].getMediaType()+" '"+item[i].getTitle()+"' "+item[i].getPrice());
            }
            else
               System.out.println(i+1+" "+item[i].getMediaType()+" '"+item[i].getTitle()+"' "+item[i].getPrice());
   }
   public void detailedItem()
   {  Scanner scan=new Scanner(System.in);
      int i;
      do
      {  System.out.println("Enter item:");
         i=scan.nextInt()-1;
      }while(i>=numberOfItems);
      System.out.println(item[i].WhoAmI());
   }
   public void addItem()
   {  Scanner scan=new Scanner(System.in);
      System.out.println("Menu");
      System.out.println("====");
      System.out.println("1-Book");
      System.out.println("2-Movie");
      int media=scan.nextInt();
      if(media==1)
         mediaType=new String("Book");
      else if(media==2)
         mediaType=new String("Movie");
      String junk=scan.nextLine();
      System.out.println("Enter "+mediaType+" Title:");
      String itemTitle=scan.nextLine();
      System.out.println("Enter "+mediaType+" Reference:");
      String itemReference=scan.nextLine();
      System.out.println("Enter "+mediaType+" Price:");
      double itemPrice=scan.nextDouble();
      junk=scan.nextLine();
      item[numberOfItems]=new MediaItem(mediaType,itemTitle,itemReference,itemPrice);
      if(media==1)
      {  System.out.println("Enter Author Name:");
         item[numberOfItems].setAuthor(scan.nextLine());
      }
      else if(media==2)
      {  System.out.println("Enter Director Name:");
         item[numberOfItems].setDirector(scan.nextLine());
         System.out.println("Enter Main Actor Name:");
         item[numberOfItems].setActor(scan.nextLine());
      }
      numberOfItems++;
   }
   public void removeItem()
   {  Scanner scan=new Scanner(System.in);
      System.out.println("Which item do you want to remove?");
      int deleteItem=scan.nextInt();
      for(int i=deleteItem-1;i<numberOfItems;i++)
         item[i]=item[i+1];
   }
   public void maxPrice()
   {  Scanner scan=new Scanner(System.in);  
      System.out.println("Which Maximum Price? (currently"+maxItemPrice+")");
      maxItemPrice=scan.nextDouble();
   }   
   public void sort()
   {  MediaItem intermediate;
      for(int i=numberOfItems-1;i>1;i--)
         for(int j=0;j<i;j++)
            if((item[j].getMediaType().compareTo(item[j+1].getMediaType())==0&&item[j].getPrice()>item[j+1].getPrice())||(item[j].getMediaType().compareTo(item[j+1].getMediaType())>0))
            {  intermediate=item[j];
               item[j]=item[j+1];
               item[j+1]=intermediate;
            }
   }
}