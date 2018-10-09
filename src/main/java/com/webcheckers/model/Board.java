package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

public class Board {

  public static final int BOARD_LENGTH = 8;

  private Space[][] spaces = new Space[BOARD_LENGTH][BOARD_LENGTH];

  public void initialize() {

  }

  public Boolean checkEnd() {
    return null;
  }

  public ArrayList<Row> getState() {
    ArrayList<Row> rows = new ArrayList<Row>();
    for(int i=0; i<BOARD_LENGTH; i++){
      rows.add(new Row(spaces, i));
    }
    return rows;
  }
}
