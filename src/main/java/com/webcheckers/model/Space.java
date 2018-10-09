package com.webcheckers.model;

public class Space {
  private int x = -1;
  private int y = -1;
  private Piece occupant = null;

  public Space(int row, int index){
    this.x = index;
    this.y = row;
  }

  public int getCellIdx(){
    return x;
  }

  public Piece getPiece(){
    return occupant;
  }

  public Boolean isValid(){
    if(getPiece() == null && (x + y)%2 == 0){
      return true;
    }
    return false;
  }
}
