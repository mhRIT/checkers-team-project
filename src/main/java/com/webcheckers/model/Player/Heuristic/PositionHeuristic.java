package com.webcheckers.model.Player.Heuristic;

import com.webcheckers.model.Board;
import com.webcheckers.model.Board.COLOR;

public class PositionHeuristic extends Heuristic {

  public PositionHeuristic() {
    super();
  }

  @Override
  public double calculate(Board board, COLOR color) {
    return 0;
  }
}
