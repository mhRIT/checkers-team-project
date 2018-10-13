package com.webcheckers.ui.boardView;

public class Space {

  private final int cellIdx;
  private Piece piece = null;
  private boolean validPos = false;

  public Space(int cellIdx, Piece piece, boolean validPos) {
    this.cellIdx = cellIdx;
    this.piece = piece;
    this.validPos = validPos;
  }

  public int getCellIdx() {

    return cellIdx;
  }

  public boolean isValid() {
    return piece == null && validPos;
  }

  public Piece getPiece() {

    return piece;
  }
}
