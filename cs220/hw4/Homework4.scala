object FunctionalDataStructures
{	
	def enqueue[A](element:A,q:Queue[A]):Queue[A]=Queue(q.front,element::q.back)
	def dequeue[A](q:Queue[A]):Option[(A,Queue[A])]=q.front match
	{	case Nil=>q.back match
		{	case Nil=>None
			case _=>dequeue(Queue(reverse(q.back,Nil),Nil))
		}
		case head::tail=>Some(head,Queue(tail,q.back))
	}
	def reverse[A](qIn:List[A],qOut:List[A]):List[A]=qIn match
	{	case Nil=>qOut
		case head::tail=>reverse(tail,head::qOut)
	}

	def max[A](lst:JoinList[A],compare:(A,A)=>Boolean):Option[A]=lst match
	{	case Empty()=>None
		case Singleton(x)=>Some(x)
		case Join(lst1,lst2,_)=>
		{	if(compare(max(lst1,compare).get,max(lst2,compare).get))
				Some(max(lst1,compare).get)
			else
				Some(max(lst2,compare).get)
		}
	}
	def first[A](lst:JoinList[A]):Option[A]=lst match
	{	case Empty()=>None
		case Singleton(x)=>Some(x)
		case Join(lst1,lst2,_)=>first(lst1)
	}
	def rest[A](lst:JoinList[A]):Option[JoinList[A]]=lst match
	{	case Empty()=>None
		case Singleton(x)=>None
		case Join(lst1,lst2,n)=>lst1 match
		{	case Singleton(x)=>Some(Join(Empty(),lst2,n-1))
			case _=>Some(Join(rest(lst1).get,lst2,n-1))
		}
	}
	def nth[A](lst:JoinList[A],index:Int):Option[A]=lst match
	{	case Empty()=>None
		case Singleton(x)=>index match
		{	case 0=>Some(x)
			case _=>None
		}
		case Join(lst1,lst2,n)=>
		{	if(index<(n/2))
				Some(nth(lst1,index).get)
			else
				Some(nth(lst2,index-(n/2)).get)
		}
	}
	def map[A,B](f:A=>B,lst:JoinList[A]):JoinList[B]=lst match
	{	case Empty()=>Empty()
		case Singleton(x)=>Singleton(f(x))
		case Join(lst1,lst2,n)=>Join(map(f,lst1),map(f,lst2),n)
	}
	def filter[A](pred:A=>Boolean,lst:JoinList[A]):JoinList[A]=lst match
	{	case Empty()=>Empty()
		case Singleton(x)=>
		{	if(pred(x))
				Singleton(x)
			else
				Empty()
		}
		case Join(lst1,lst2,n)=>deleteEmpty(Join(filter(pred,lst1),filter(pred,lst2),filter(pred,lst1).size+filter(pred,lst2).size))
	}
	def deleteEmpty[A](lst:JoinList[A]):JoinList[A]=lst match
	{	case Empty()=>Empty()
		case Singleton(x)=>Singleton(x)
		case Join(lst1,lst2,n)=>(lst1,lst2) match
		{	case (Empty(),Empty())=>Empty()
			case (Empty(),_)=>deleteEmpty(lst2)
			case (_,Empty())=>deleteEmpty(lst1)
			case _=>Join(deleteEmpty(lst1),deleteEmpty(lst2),deleteEmpty(lst1).size+deleteEmpty(lst2).size)
		}
	}
}