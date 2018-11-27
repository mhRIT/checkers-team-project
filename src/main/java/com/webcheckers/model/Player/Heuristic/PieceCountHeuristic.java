package com.webcheckers.model.Player.Heuristic;

import com.webcheckers.model.Board.Board;
import com.webcheckers.model.Board.Board.COLOR;

public class PieceCountHeuristic extends Heuristic {

  public PieceCountHeuristic() {
    super();
  }

  @Override
  public double calculate(Board board, COLOR color) {
    int numPlusPieces = board.getNumSinglePieces(color);
    int numNegPieces = board.getNumSinglePieces() - board.getNumSinglePieces(color);
    return (numPlusPieces-numNegPieces)*weight;
  }
}
