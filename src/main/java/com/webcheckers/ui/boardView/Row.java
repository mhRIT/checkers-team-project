package com.webcheckers.ui.boardView;

import com.webcheckers.model.Board.SPACE_TYPE;
import com.webcheckers.ui.boardView.Piece.COLOR;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import spark.Spark;

public class Row implements Iterable {

  private final int index;
  private ArrayList<Space> spaces;

  public Row(int idx, SPACE_TYPE[] spaceList) {
//    this.spaces = new ArrayList<>(Arrays.asList(spaceList));
    this.spaces = new ArrayList<>();
    for(int i = 0; i < spaceList.length; i++){
//      public Space(int cellIdx, Piece piece, boolean validPos) {
//      public Piece(COLOR color, int row, int col) {
      SPACE_TYPE eachSpace = spaceList[i];
      boolean validPos = (idx*8 + i) % 2 != idx % 2;

      if(eachSpace.equals(SPACE_TYPE.SINGLE_RED)){
        spaces.add(new Space(i, new Piece(COLOR.RED, idx, i), validPos));
      } else if(eachSpace.equals(SPACE_TYPE.SINGLE_WHITE)) {
        spaces.add(new Space(i, new Piece(COLOR.WHITE, idx, i), validPos));
      } else {
        spaces.add(new Space(i, null, validPos));
      }
    }
    this.index = idx;
  }

  public int getIndex() {

    return index;
  }

  public void reverse(){
    Collections.reverse(spaces);
  }

  @Override
  public Iterator iterator() {
    return spaces.iterator();
  }
}
