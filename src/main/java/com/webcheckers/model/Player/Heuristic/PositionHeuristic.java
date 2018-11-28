package com.webcheckers.model.Player.Heuristic;

import com.webcheckers.model.Board.Board;
import com.webcheckers.model.Board.Board.COLOR;
import com.webcheckers.model.Board.Move;
import java.util.List;

public class PositionHeuristic extends Heuristic {

  public PositionHeuristic() {
    super();
  }

  @Override
  public double calculate(Board board, COLOR color) {
    List<Move> validJumpList = board.getAllJumpMoves(color);
    List<Move> validSimpleList = board.getAllSimpleMoves(color);

    int toReturn = validJumpList.size() + validSimpleList.size();
    validJumpList = board.getAllJumpMoves(color.opposite());
    validSimpleList = board.getAllSimpleMoves(color.opposite());
    toReturn -= validJumpList.size() + validSimpleList.size();

    return toReturn;
  }
}
