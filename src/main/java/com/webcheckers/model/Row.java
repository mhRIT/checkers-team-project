package com.webcheckers.model;

import java.util.ArrayList;

public class Row {

  private int index = -1;

  public Row(int index){
    this.index = index;
  }

  public int getIndex(){
    return this.index;
  }

  public ArrayList<Space> iterator(){
    ArrayList<Space> cells = new ArrayList<Space>();
    for(int i = 0; i < 8; i++){
      cells.add(new Space(i, this.index));
    }
    return cells;
  }
}
