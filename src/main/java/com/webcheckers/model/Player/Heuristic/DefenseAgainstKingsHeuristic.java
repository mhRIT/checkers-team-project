package com.webcheckers.model.Player.Heuristic;

import com.webcheckers.model.Board;
import com.webcheckers.model.Board.COLOR;

public class DefenseAgainstKingsHeuristic extends Heuristic {

  public DefenseAgainstKingsHeuristic() {
    super();
  }

  @Override
  public double calculate(Board board, COLOR color) {
    return 0;
  }
}
