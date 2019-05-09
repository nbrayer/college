class Link {
    public int data; // data item
    public Link next;   // reference to next link
    public Link prev;   // reference to previous link
         
    // Constructor
    public Link(int data){
	this.data=data; 
	next = null;     // optional
	prev = null;     // optional
    }       
}


//////////////////////////////////////////////////////
//////////////////////////////////////////////////////



class DoublyLinkListFact {

    private Link first;// Reference to the first link
    private Link last; // Reference to the last link
   
    // constructor
    public DoublyLinkListFact() { first = null; last= null;} 

    // methods
    public void displayListForward() {
    // to complete    
    }

    public void recdisplayListForward(Link current) 
    {
    // to complete
	}
    }

    public void displayListBackward() {
    // to complete
    }

    public void recdisplayListBackward(Link current)
    {
    // to complete
    }   

    public int factForward() {
	if (!isEmpty()) return(recFactForward(first));
	else
	    return 0;
    }

    public int recFactForward(Link current) 
    {
    // to complete
    }


    public int factBackward() {
	if (!isEmpty()) return(recFactBackward(last));
	else
	    return 0;
    }


    public int recFactBackward(Link current) 
    {
    // to complete
    }
    

    public void insertLast(int n)
    {
	Link newLink = new Link(n); // create link
	if (isEmpty()) 
	    first= newLink; // special case
	else
	    last.next = newLink; // (step 1)
	newLink.prev = last;     // (step 2)
	last = newLink;          // (step 3)
    }

    public boolean isEmpty() {return(first==null);}


}




//////////////////////////////////////////////////////
//////////////////////////////////////////////////////


class DLLRecursion{

    public static void main(String[] args)
    {
	DoublyLinkListFact mylist = new DoublyLinkListFact();
	EasyIn easy= new EasyIn();

  
	int i,n;
	System.out.println("Enter List size n ");
	n=easy.readInt();
  
	for(i=1;i<=n; i++){
	    mylist.insertLast(i);      
	}

	System.out.println("Print list forward");
	mylist.displayListForward();
	System.out.println("Factorial <forward> "+mylist.factForward());
	System.out.println("Print list backward");
	mylist.displayListBackward();
	System.out.println("Factorial <backward> "+mylist.factBackward());
  
    }
}
