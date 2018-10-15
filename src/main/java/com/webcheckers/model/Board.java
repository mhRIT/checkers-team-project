package com.webcheckers.model;

public class Board {

  public enum SPACE_TYPE {EMPTY, SINGLE_RED, SINGLE_WHITE, KING_RED, KING_WHITE}
  private SPACE_TYPE[][] boardState = null;

  public void initStart() {
    boardState = new SPACE_TYPE[8][8];
    int modVal = 1;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        boardState[i][j] = SPACE_TYPE.EMPTY;
        if(i < 3 && (i * 8 + j) % 2 == modVal){
          boardState[i][j] = SPACE_TYPE.SINGLE_WHITE;
        } else if(i > 4 && (i * 8 + j) % 2 == modVal){
          boardState[i][j] = SPACE_TYPE.SINGLE_RED;
        }
      }
      modVal ^= 1;
    }
  }

  public void initMid(int numRedPieces, int numWhitePieces) {
    // TODO
  }

  public boolean validateMove(){
    // TODO
    return false;
  }

  public void movePiece() {
    // TODO
  }

  public boolean checkEnd() {
    // TODO
    return false;
  }

  public int getNumPieces(SPACE_TYPE pieceType){
    // TODO
    return 1;
  }

  public SPACE_TYPE[][] getState() {
    return boardState;
  }

  public SPACE_TYPE[][] getBoardTranspose(){
    SPACE_TYPE[][] toReturnBoard = new SPACE_TYPE[8][8];
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        toReturnBoard[i][j] = boardState[7-i][7-j];
      }
    }
    return toReturnBoard;
  }
}
