package com.webcheckers.model.Player.Heuristic;

import com.webcheckers.model.Board.Board;
import com.webcheckers.model.Board.Board.COLOR;

public class BasePiecesHeuristic extends Heuristic {

  public BasePiecesHeuristic() {
    super();
  }

  @Override
  public double calculate(Board board, COLOR color) {
    return board.getNumPiecesAlongBase(color);
  }
}
