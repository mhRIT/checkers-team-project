package com.webcheckers.model;

public class King extends ModelPiece {

  public King(COLOR color, int row, int col){
    super(color, row, col);
  }

  @Override
  public boolean validate(){
    return false;
  }
}
