package com.webcheckers.model.Player.Heuristic;

import com.webcheckers.model.Board.Board;
import com.webcheckers.model.Board.Board.COLOR;
import com.webcheckers.model.Board.Move;
import java.util.List;

public class JumpMoveHeuristic extends Heuristic {

  public JumpMoveHeuristic() {
    super();
  }

  @Override
  public double calculate(Board board, COLOR color) {
    List<Move> validJumpList = board.getAllJumpMoves(color);
    return validJumpList.size();
  }
}
