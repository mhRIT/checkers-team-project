package com.webcheckers.model;

import java.util.ArrayList;

public class Row {

  private int index = -1;
  private Space[][] spaces;

  public Row(Space[][] spaces, int index){
    this.spaces = spaces;
    this.index = index;
  }

  public int getIndex(){
    return this.index;
  }

  public ArrayList<Space> iterator(){
    ArrayList<Space> cells = new ArrayList<Space>();
    for(int i = 0; i < 8; i++){
      cells.add(spaces[this.index][i]);
    }
    return cells;
  }
}
