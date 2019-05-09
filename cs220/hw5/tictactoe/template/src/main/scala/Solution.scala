class Game(turn:Player,board:Matrix) extends GameLike[Game] {

  def isFinished(): Boolean =
	{	if((getWinner().isEmpty())&&(board.rows()))
			false;
		else
			true;
	}

  /* Assume that isFinished is true */
  def getWinner(): Option[Player] = 
	{	if(board.rows().exists(row=>row.forAll(cell=>cell!=None))||board.cols().exists(col=>col.forAll(cell=>cell!=None))||board.mainDiagonal().forAll(cell=>cell!=None)||board.antiDiagonal().forAll(cell=>cell!=None))
		Some[X]
else
None
	}

  // def nextBoards(): List[Game] = ???
}

object Solution extends MinimaxLike {

  type T = Game // T is an "abstract type member" of MinimaxLike

  def createGame(turn: Player, dim: Int, board: Map[(Int, Int), Player]): Game = new Game(turn,Matrix.fromMap(dim,None,board.mapValues(p->Some(p))))

  def minimax(board: Game): Option[Player] = ???

}
