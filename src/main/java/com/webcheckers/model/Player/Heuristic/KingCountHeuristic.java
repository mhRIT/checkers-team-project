package com.webcheckers.model.Player.Heuristic;

import com.webcheckers.model.Board.Board;
import com.webcheckers.model.Board.Board.COLOR;

public class KingCountHeuristic extends Heuristic {

  public KingCountHeuristic() {
    super();
  }

  @Override
  public double calculate(Board board, COLOR color) {
    int numPlusPieces = board.getNumKingPieces(color);
    int numNegPieces = board.getNumKings() - board.getNumKingPieces(color);
    return (numPlusPieces-numNegPieces)*weight;
  }
}
