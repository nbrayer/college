object Lecture1
{	val oddNumbers=1::3::5::Nil

	def sumDouble(lst:List[Int]):Int=lst match
	{	case Nil=>0
		case n::rest=>2*n+sumDouble(rest)
	}
	def removeZeroes(lst:List[Int]):List[Int]=lst match
	{	case Nil=>Nil
		case 0::rest=>removeZeroes(rest)
		case n::rest=>n::removeZeroes(rest)
	}
	def countEvens(lst:List[Int]):Int=lst match
	{	case Nil=>0
		case n::rest=>
		{	if(n%2==0)
				1+countEvens(rest)
			else
				countEvens(rest)
		}
	}
	def removeAlternating(lst:List[String]):List[String]=lst match
	{	case Nil=>Nil
		case _ =>removeAlternatingHelper(lst,false)
	}
	def removeAlternatingHelper(lst:List[String],remove:Boolean):List[String]=lst match
	{	case Nil=>Nil
		case head::tail=>
		{	if(remove)
				removeAlternatingHelper(tail,false)
			else
				head::removeAlternatingHelper(tail,true)
		}
	}
	def isAscending(lst:List[Int]):Boolean=lst match
	{	case Nil=>true
		case n::rest=>isAscendingHelper(rest,n)
	}
	def isAscendingHelper(lst:List[Int],previous:Int):Boolean=lst match
	{	case Nil=>true
		case n::rest=>
		{	if(n<previous)
				false
			else
				isAscendingHelper(rest,n)
		}
	}
	def addSub(lst:List[Int]):Int=lst match
	{	case Nil=>0
		case rest=>addSubHelper(lst,false)
	}
	def addSubHelper(lst:List[Int],subtract:Boolean):Int=lst match
	{	case Nil=>0
		case n::rest=>
		{	if(subtract)
				addSubHelper(rest,false)-n
			else
				addSubHelper(rest,true)+n
		}
	}
	def alternate(lst1:List[Int],lst2:List[Int]):List[Int]=lst1 match
	{	case Nil=>lst2
		case head::tail=>head::alternate(lst2,tail)
	}
	def fromTo(int1:Int,int2:Int):List[Int]=(int1,int2) match
	{	case _ =>
		{	if(int1<int2)
				int1::fromTo(int1+1,int2)
			else if(int1>int2)
				int2::fromTo(int2+1,int1)
			else
				int1::Nil
		}
	}
	def insertOrdered(n:Int,lst:List[Int]):List[Int]=lst match
	{	case Nil=>n::Nil
		case head::tail=>
		{	if(n>head)
				head::insertOrdered(n,tail)
			else
				n::head::tail
		}
	}
	def sort(lst:List[Int]):List[Int]=lst match
	{	case Nil=>Nil
		case head::tail=>insertOrdered(head,sort(tail))
	}
}
