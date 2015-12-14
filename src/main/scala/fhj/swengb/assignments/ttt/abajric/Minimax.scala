package fhj.swengb.assignments.ttt.abajric

/**
  * Created by Amar on 14.12.2015.
  */



class Minimax {


  private val maxDepth = 3

  def play(state:TicTacToe):TMove = {
    val moveScores = {
      for {
        move <- state.remainingMoves
      } yield move -> minimax(state.turn(move,state.nextPlayer))
    }
    val bestMove = moveScores.minBy(_._2)._1
    bestMove
  }

  private def minimax(state:TicTacToe):Int = {
    minimize(state, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE)
  }

  private def minimize(state:TicTacToe, depth:Int, alpha:Int, beta:Int):Int = {
    if(state.gameOver || depth == 0) return state.hVal
    var newBeta = beta
    state.remainingMoves.foreach(move => {
      val newState = state.turn(move,state.nextPlayer)
      newBeta = math.min(beta, maximize(newState, depth - 1, alpha, newBeta))
      if (alpha >= newBeta) return alpha
    })
    newBeta
  }

  private def maximize(state:TicTacToe, depth:Int, alpha:Int, beta:Int):Int = {
    if(state.gameOver || depth == 0) return state.hVal
    var newAlpha = alpha
    state.remainingMoves.foreach(move => {
      val newState = state.turn(move,state.nextPlayer)
      newAlpha = math.max(newAlpha, minimize(newState, depth - 1, newAlpha, beta))
      if (newAlpha >= beta) return beta
    })
    newAlpha
  }

  override def toString = "MINIMAX"

}
