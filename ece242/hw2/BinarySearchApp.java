// BinarySearchiApp.java
// demonstrates basic binary search + analysis using simple ordered object arrays
// to compile this program: >javac BinarySearchApp.java
// to run this program: >java BinarySearchApp
////////////////////////////////////////////////////////////////

class HighArray{
    private int[] array;
    private int maxItems;          // number of max items (size of the array)
    private int N;                 // number of active items N<=MaxItems
    private int searchStepCounter; // number of step for search algorithm
    private int searchIndex;       // Index of the item if it is found

    // Constructors
  
    public HighArray(int maxItems) 
    { 
	this.maxItems=maxItems;
	array = new int[maxItems];
	N=0;
    }
   
    // Methods
  
    public void basicOrderedInitialization()  
    {
	N=maxItems;
	for (int i=0;i<N;i++){
	    array[i]=i+1;
	}
    }

 
    public boolean binarySearch(int searchKey, boolean comments)
    //searchkey is the value to find
    //comments can be turned on (true) or off (false) to display info about step on screen
    //method returns true if the searchkey is found
    //method also keeps track of values of searchIndex and searchStepCounter	
    {


///////////////////// Complete Method //////////////////////


    }


    public int getStep()
    {return searchStepCounter; 
    }

    public int getIndex()
    {return searchIndex; 
    }

}


////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////
class BinarySearchApp
{
    public static void main(String[] args)
    {

	EasyIn easy = new EasyIn();
	int key; 
	int maxItems; 

    
	System.out.println("Enter fixed size for the ordered array:");
	maxItems = easy.readInt();
	HighArray array = new HighArray(maxItems);    
	array.basicOrderedInitialization();    // Initialize ordered array                   


	/////////////////// Same example seen in class if maxItems=100

	key=33;    
	System.out.println("");
	System.out.println("1- Search single key element"+key);
	if (array.binarySearch(key,true)) {
	    System.out.println("found value "+key+" at index "+array.getIndex()+" in "+array.getStep()+" steps");
	} else
	    {   
		System.out.println("value "+key+" not found!");
	    }
	


	////////////////// Search all items in the array and report average and maximum number of steps
	System.out.println("");
	System.out.println("2- Search all elements of the array ");

	long startTime,endTime;
	long totalSteps=0;
	int count=0;
	int max=0;
	startTime = System.currentTimeMillis(); // capture time
	
	for (key=1;key<=maxItems;key++){
	    if (array.binarySearch(key,false)) {
		totalSteps=totalSteps+array.getStep();
		count++;
		if (array.getStep()>max) max=array.getStep();
	    } 
	}
	endTime = System.currentTimeMillis(); // capture time

	System.out.println("Maximum number of binary search steps "+max);
	System.out.println("Average number of binary search steps "+totalSteps/count);

	
        //////////////////// Compare the time average for binary search vs linear search

	long totalTime=endTime-startTime; // Get total time
	double stepTime=(totalTime*1.0/totalSteps); // find the time for one step of the algorithm
        double binaryTime=(stepTime*totalSteps)/count; // find the average time for a single binary search -- equivalent to totalTime/count
	double linearTime=(stepTime*count)/2;    // estimate the average time for a single linear search 
	

	System.out.println("");
	System.out.println("3- Timing and Performance");
	System.out.println("Time average for binary search (single search) "+binaryTime+" ms"); // equivalent to totalTime/count
	System.out.println("Estimated Time average for linear search (single search) "+linearTime+ " ms"); // equivalent to totalTime/count
	if (binaryTime!=0.0)
        System.out.println("Binary search is "+linearTime/binaryTime+" times faster than linear search for "+maxItems+" items");
        

 
    }  // end main()
}  // end class BinarySearchApp
