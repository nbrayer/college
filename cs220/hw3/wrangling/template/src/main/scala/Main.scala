object Homework3 {

  import edu.umass.cs.CSV


  // WARNING: this may take a very long time. Cut the file or work with a
  // small, made-up dataset if you have trouble.
  // val allBirths = CSV.fromFile("ssa-births.csv")

  val lifeExpectancy = CSV.fromFile("cdc-life-expectancy.csv")

  /** Restrict the data to the year `year`. */
  def yearIs(data: List[List[String]], n: Int): List[List[String]] = data.filter(Integer.parseInt(List[List[0]])=>Integer.parseInt(List[List[0]])==n)


  /** Restrict the data to years greater than `bound`. */
  def yearGT(data: List[List[String]], bound: Int): List[List[String]] = data.filter(Integer.parseInt(List[List[0]])=>Integer.parseInt(List[List[0]])>n)

  /** Restrict the data to years less than `bound` */
  def yearLT(data: List[List[String]], bound: Int): List[List[String]] = data.filter(Integer.parseInt(List[List[0]])=>Integer.parseInt(List[List[0]])<n)

  /** Restrict the data to the name `name`. */
  def onlyName(data: List[List[String]], name: String): List[List[String]] = data.filter(List[List[1]]=>List[List[1]]==name)

  /** Calculate the most popular name and the number of children born with
      that name. */
  def mostPopular(data: List[List[String]]): (String, Int) = ???

  /** Calculate the number of children born in the given dataset. */
  def count(data: List[List[String]]): Int = data match
{	case Nil=>0
	case head::tail=>Integer.parseInt(data[[4]])+count(tail)
}

  /** Produce a tuple with the number of girls and boys respectively. */
  def countGirlsAndBoys(data: List[List[String]]): (Int, Int) = (count(data.filter(data[[3]]=>data[[3]]==F)),count(data.filter(data[[3]]=>data[[3]]==M)))

  /** Calculate the set of names that are given to both girls and boys. */
  def unisexNames(data: List[List[String]]): Set[String] = ???


  /** Determine if a person with the specified `gender` (either "M" or "F") who
      was born in `birthYear` is expected to be alive, according to the CDC
      life-expectancy data.

      If `currentYear` is the last year the person is estimated to be alive, be
      optimistic and produce `true`.

      The CDC data only ranges from 1930 -- 2010. Therefore, assume that
      `birthYear` is in this range too. */
  def expectedAlive(gender: String, birthYear: Int, currentYear: Int): Boolean = match gender
{	case "M"=>
	{	if(currentYear>(birthYear+lifeExpectancy.map(
	case "F"=>

  /** Estimate how many people from `data` will be alive in `year`. */
  def estimatePopulation(data: List[List[String]], year: Int): Int = ???

}