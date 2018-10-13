package com.webcheckers.model;


import com.webcheckers.ui.boardView.Piece;
import com.webcheckers.ui.boardView.Piece.COLOR;
import com.webcheckers.ui.boardView.Space;
import java.util.ArrayList;

public class Board {

  private Space[][] boardState = null;

  public void initStart() {
    boardState = new Space[8][8];
    int modVal = 1;
    COLOR pieceColor = COLOR.WHITE;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (i > 2 && i < 5) {
          boardState[i][j] = new Space(j, null, (i * 8 + j) % 2 == modVal);
        } else if ((i * 8 + j) % 2 == modVal) {
          boardState[i][j] = new Space(j, new Piece(pieceColor, i, j), true);
        } else {
          boardState[i][j] = new Space(j, null, (i * 8 + j) % 2 == modVal);
        }
      }
      modVal ^= 1;
      if (i > 3) {
        pieceColor = COLOR.RED;
      }
    }
  }

  public void initMid(int numRedPieces, int numWhitePieces) {
    boardState = new Space[8][8];
    int modVal = 1;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        boardState[i][j] = new Space(j, null, (i * 8 + j) % 2 == modVal);
      }
      modVal ^= 1;
    }

    while(numRedPieces > 0){
      int randRow = (int)(Math.random()*8);
      int randCol = (int)(Math.random()*8);
      if(boardState[randRow][randCol].isValid()){
        boardState[randRow][randCol] = new Space(randCol, new Piece(COLOR.RED, randRow, randCol), true);
        numRedPieces--;
      }
    }

    while(numWhitePieces > 0){
      int randRow = (int)(Math.random()*8);
      int randCol = (int)(Math.random()*8);
      if(boardState[randRow][randCol].isValid()){
        boardState[randRow][randCol] = new Space(randCol, new Piece(COLOR.WHITE, randRow, randCol), true);
        numWhitePieces--;
      }
    }
  }

  public boolean validateMove(){
    return false;
  }

  public boolean checkEnd() {
    int numRedPieces = getPiecesByColor(COLOR.RED).size();
    int numWhitePieces = getPiecesByColor(COLOR.WHITE).size();
    return numRedPieces > 0 && numWhitePieces > 0;
  }

  public ArrayList<Piece> getPiecesByColor(COLOR pieceColor){
    ArrayList<Piece> pieceList = new ArrayList<>();

    for(int i = 0; i < boardState.length; i++){
      Space[] eachBoardRow = boardState[i];
      for(int j = 0; j < eachBoardRow.length; j++){
        Space eachSpace = eachBoardRow[j];
        Piece eachPiece = eachSpace.getPiece();
        if(eachPiece != null){
          if(eachPiece.getColor() == pieceColor){
            pieceList.add(eachPiece);
          }
        }
      }
    }

    return pieceList;
  }

  public Space[][] getState() {
    return boardState;
  }
}
