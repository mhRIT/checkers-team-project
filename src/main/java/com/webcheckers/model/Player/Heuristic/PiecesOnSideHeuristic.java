package com.webcheckers.model.Player.Heuristic;

import com.webcheckers.model.Board.Board;
import com.webcheckers.model.Board.Board.COLOR;

public class PiecesOnSideHeuristic extends Heuristic {

  public PiecesOnSideHeuristic() {
    super();
  }

  @Override
  public double calculate(Board board, COLOR color) {
    return board.getNumPiecesAlongSide(color);
  }
}
