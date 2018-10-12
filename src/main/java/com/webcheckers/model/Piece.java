package com.webcheckers.model;

public class Piece {

  private int row;
  private int col;

  private COLOR pieceColor = COLOR.RED;
  private TYPE type = TYPE.SINGLE;

  public Piece(COLOR color, int row, int col) {
    this.pieceColor = color;
    this.row = row;
    this.col = col;
  }

  public COLOR getColor() {
    return pieceColor;
  }

  public TYPE getType() {
    return type;
  }

  public boolean move() {
    return false;
  }

  public boolean validate() {
    // -selected piece must be of correct color
    // -destination must not be occupied
    // -destination must be a red square
    // -must be player's turn?
    return false;
  }

  public enum COLOR {RED, WHITE}

  public enum TYPE {SINGLE, KING}

}
