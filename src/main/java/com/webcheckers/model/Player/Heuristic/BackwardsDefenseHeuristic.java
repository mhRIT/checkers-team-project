package com.webcheckers.model.Player.Heuristic;

import com.webcheckers.model.Board;
import com.webcheckers.model.Board.COLOR;
import com.webcheckers.model.Board.SPACE_TYPE;
import java.util.List;

public class BackwardsDefenseHeuristic extends Heuristic {

  public BackwardsDefenseHeuristic() {
    super();
  }

  @Override
  public double calculate(Board board, COLOR color) {
    int toReturn = 0;

    for(int rowIdx = 1; rowIdx < Board.BOARD_SIZE - 1; rowIdx++){
      List<SPACE_TYPE> nextRow = board.getRow(rowIdx + 1);
      List<SPACE_TYPE> currentRow = board.getRow(rowIdx);
      List<SPACE_TYPE> previousRow = board.getRow(rowIdx - 1);

      for (int colIdx = 1; colIdx < currentRow.size() - 1; colIdx++) {
        SPACE_TYPE eachSpace = currentRow.get(colIdx);
        if(!eachSpace.isEmpty()){
          SPACE_TYPE neighbor_tl = nextRow.get(colIdx - 1);
          SPACE_TYPE neighbor_tr = nextRow.get(colIdx + 1);

          SPACE_TYPE neighbor_bl = previousRow.get(colIdx - 1);
          SPACE_TYPE neighbor_br = previousRow.get(colIdx + 1);

          if(eachSpace.isRed() && color.equals(COLOR.RED)){
            if(neighbor_tl.isWhite()){
              if(!neighbor_br.isEmpty()){
                toReturn++;
              }
            }

            if(neighbor_tr.isWhite()){
              if(!neighbor_bl.isEmpty()){
                toReturn++;
              }
            }
          } else if(eachSpace.isWhite() && color.equals(COLOR.WHITE)){
            if(neighbor_bl.isRed()){
              if(!neighbor_tr.isEmpty()){
                toReturn++;
              }
            }

            if(neighbor_br.isRed()){
              if(!neighbor_tl.isEmpty()){
                toReturn++;
              }
            }
          }
        }
      }
    }

    return toReturn;
  }
}
