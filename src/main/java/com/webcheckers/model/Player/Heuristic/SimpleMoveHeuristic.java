package com.webcheckers.model.Player.Heuristic;

import com.webcheckers.model.Board.Board;
import com.webcheckers.model.Board.Board.COLOR;
import com.webcheckers.model.Board.Move;
import java.util.List;

public class SimpleMoveHeuristic extends Heuristic {

  public SimpleMoveHeuristic() {
    super();
  }

  @Override
  public double calculate(Board board, COLOR color) {
    List<Move> validSimpleList = board.getAllSimpleMoves(color);
    return validSimpleList.size();
  }
}
