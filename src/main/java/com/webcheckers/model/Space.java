package com.webcheckers.model;

public class Space {
  private int row = -1;
  private int col = -1;
  private Piece occupant = null;

  public Space(int row, int cellIndex){
    this.row = row;
    this.col = cellIndex;
  }

  public int getCellIdx(){
    return col;
  }

  public Piece getPiece(){
    return occupant;
  }

  public Boolean isValid(){
    if(getPiece() == null && (row + col)%2 == 0){
      return true;
    }
    return false;
  }
}
