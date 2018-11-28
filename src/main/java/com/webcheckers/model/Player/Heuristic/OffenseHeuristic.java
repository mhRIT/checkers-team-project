package com.webcheckers.model.Player.Heuristic;

import com.webcheckers.model.Board.Board;
import com.webcheckers.model.Board.Board.COLOR;
import com.webcheckers.model.Board.Board.SPACE_TYPE;
import java.util.List;

public class OffenseHeuristic extends Heuristic {

  public OffenseHeuristic() {
    super();
    setWeight(0.25);
  }

  @Override
  public double calculate(Board board, COLOR color) {
    int toReturn = 0;

    for(int rowIdx = 0; rowIdx < Board.BOARD_SIZE; rowIdx++){
      List<SPACE_TYPE> rowSpaces = board.getRow(rowIdx);
      for (SPACE_TYPE eachSpace : rowSpaces) {
        if(eachSpace.isWhite()){
          toReturn += eachSpace.getValue()*(Board.BOARD_SIZE - rowIdx);
        } else if(eachSpace.isRed()){
          toReturn += eachSpace.getValue()*(rowIdx + 1);
        }
      }
    }

    return toReturn;
  }
}
