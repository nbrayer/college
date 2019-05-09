import java.io.*;
import java.util.*;
import java.lang.Math;

class fastDictionary
{  String dictionaryName;
   String[] dictionary;
   int dictionarySize;
   boolean isLoaded;
   boolean isSorted;
   int steps;
   
   public fastDictionary()
   {   
   }
   public void menu()
   {  System.out.println("Menu");
      System.out.println("====");
   	System.out.println("1-Load dictionary file");          
   	System.out.println("2-Sort dictionary using Insertion sort");
   	System.out.println("3-Sort dictionary using Enhanced Insertion sort");
   	System.out.println("4-Shuffle dictionary");
   	System.out.println("5-Search word");
   	System.out.println("6-SpellChecker");
   	System.out.println("7-Anagram");
   	System.out.println("0-Exit");
   	System.out.println("");
   	System.out.print("Command: ");
   }
   public boolean loadDictionary(String dictionaryName)
   {  this.dictionaryName=dictionaryName;
      int numberOfItems=0;
      isSorted=false;
      try
      {  File file=new File(dictionaryName);
         Scanner scanFile=new Scanner(file);
         dictionarySize=Integer.parseInt(scanFile.nextLine());
         dictionary=new String[dictionarySize];
         while(scanFile.hasNext()&&numberOfItems<dictionarySize)
         {  dictionary[numberOfItems]=scanFile.nextLine();
            numberOfItems++;
         }
      }
      catch(FileNotFoundException e)
      {  e.printStackTrace();
      }
      isLoaded=true;
      return(isLoaded);
   }
   public void saveDictionary()
   {  String fileName=new String(dictionaryName+"-"+getStatus()+".txt");
      try
      {  PrintWriter out=new PrintWriter(fileName);
         out.println(dictionarySize);
         for(int i=0;i<dictionarySize;i++)
            out.println(dictionary[i]);
         out.close();
      }
      catch(IOException e)
      {  e.printStackTrace();
      }
      System.out.print(" and saved in file "+fileName);
   }
   public void insertionSort()
   {  long startTime=System.currentTimeMillis();
      int in,out;
      for(out=1;out<dictionarySize;out++)
      {  String temp=new String(dictionary[out]);
         in=out;
         while(in>0&&dictionary[in-1].compareTo(temp)>0)
         {  dictionary[in]=dictionary[in-1];
            --in;
         }
         dictionary[in]=temp;
      }
      long endTime=System.currentTimeMillis();
      isSorted=true;
      long time=endTime-startTime;
      System.out.println("Dictionary sorted in "+time+"ms");
      saveDictionary();
   }
   public void insertionSortEnhanced()
   {  long startTime=System.currentTimeMillis();
      int in,out,index;
      for(out=1;out<dictionarySize;out++)
      {  index=binarySearch(dictionary[out],out);
         if(dictionary[index].compareTo(dictionary[out])<0)
            index++;
         String temp=new String(dictionary[out]);
         in=out;
         while(in>index)
         {  dictionary[in]=dictionary[in-1];
            in--;
         }
         dictionary[index]=temp;
      }
      long endTime=System.currentTimeMillis();
      isSorted=true;
      long time=endTime-startTime;
      System.out.println("Dictionary sorted in "+time+"ms");
      saveDictionary();
   }
   public int binarySearch(String word,int limit)
   {  int lower=0,middle=(limit-1)/2,upper=limit-1;
      int searchResult=2;
      steps=0;
      while(searchResult!=0&&upper>=lower)
      {  searchResult=word.compareTo(dictionary[middle]);
         if(searchResult<0)
            upper=middle-1;
         else if(searchResult>0)
            lower=middle+1;
         else if(searchResult==0)
            break;
         middle=(upper+lower)/2;
         steps++;
      }
      if(middle<0)
         middle=-middle;
      return(middle);
   }
   public void shuffleDictionary()
   {  isSorted=false;
      for(int i=dictionarySize-1;i>0;i--)
      {  int index=(int)(Math.random()*(double)dictionarySize);
         String temp=new String(dictionary[index]);
         dictionary[index]=dictionary[i];
         dictionary[i]=temp;
      }
      System.out.println("Dictionary shuffled");
      saveDictionary();
   }
   public void search(String word)
   {  String word1=word.replaceAll("[^\\p{L}","").toLowerCase();
      int index=binarySearch(word1,dictionarySize);
      if(dictionary[index].compareTo(word1)==0)
         System.out.println("Word found using Binary search in "+steps+" steps and at position "+(index+1));
      else
         System.out.println("Word not found!");
   }
   public void spellCheckFile(String fileName)
   {  try
      {  File file=new File(fileName);
         Scanner scanFile=new Scanner(file);
         while(scanFile.hasNextLine())
         {  String line=scanFile.nextLine();
            StringTokenizer lineSt=new StringTokenizer(line);
            while(lineSt.hasMoreTokens())
            {  String word=lineSt.nextToken();
               String word1=word.replaceAll("[^\\p{L}]","").toLowerCase();
               if(dictionary[binarySearch(word1,dictionarySize)].compareTo(word1)!=0)
                  word="("+word+")";
               System.out.print(word+" ");
            }
            System.out.println("");
         }
      }
      catch(FileNotFoundException e)
      {  e.printStackTrace();
      }
   }
   public void anagram(String word)
   {  for(int i=0;i<dictionarySize;i++)
      {  if(dictionary[i].length()==word.length())
         {  String sortedWord=sortWord(word);
            String sortedWord1=sortWord(dictionary[i]);
            if(sortedWord.compareTo(sortedWord1)==0)
               System.out.println(dictionary[i]);
         }  
      }
   }
   protected String sortWord(String word)
   {  char[] wordArray=word.toCharArray();
      char placeholder;
      for(int i=0;i<word.length()-1;i++)
      {  for(int j=1;j<word.length();j++)
         {  if(wordArray[i]<wordArray[j])
            {  placeholder=wordArray[i];
               wordArray[i]=wordArray[j];
               wordArray[j]=placeholder;
            }
         }
      }
      String sortedWord=new String(wordArray);
      return(sortedWord);
   }
   public String getName()
   {  String returnName;
      if(isLoaded)
         returnName=dictionaryName;
      else
         returnName="N/A";
      return(returnName);
   }
   public int getSize()
   {  int returnSize;
      if(isLoaded)
         returnSize=dictionarySize;
      else
         returnSize=0;
      return(returnSize);
   }
   public String getStatus()
   {  String status="";
      if(!isSorted)
        status=status+"un";
      status=status+"sorted";
      return(status);
   }
}