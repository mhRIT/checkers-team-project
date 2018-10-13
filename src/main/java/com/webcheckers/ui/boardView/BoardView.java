package com.webcheckers.ui.boardView;

import com.webcheckers.model.Board.SPACE_TYPE;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class BoardView implements Iterable {

  private ArrayList<Row> rows = new ArrayList<>();

  public BoardView(SPACE_TYPE[][] spaceMat) {
    for (int i = 0; i < spaceMat.length; i++) {
      Row eachRow = new Row(i, spaceMat[i]);
      rows.add(eachRow);
    }
  }

  public void transpose(){
    for (Row eachRow : rows) {
      eachRow.reverse();
    }
    Collections.reverse(rows);
  }

  @Override
  public Iterator iterator() {
    return rows.iterator();
  }
}
