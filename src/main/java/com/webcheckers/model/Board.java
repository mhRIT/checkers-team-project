package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *  {@code Board}
 *  <p>
 *  Represents a single board, on which is played a single game.
 *  </p>
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mvm7902@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 *
 */
public class Board {

  public enum SPACE_TYPE {EMPTY, SINGLE_RED, SINGLE_WHITE, KING_RED, KING_WHITE}
  public static int X_BOARD_SIZE = 8;
  public static int Y_BOARD_SIZE = 8;

  // bit set 0 -> empty
  // bit set 1 -> occupied
  private int pieceLocations = 0b0000_0000_0000_0000_0000_0000_0000_0000;

  // bit set 0 -> white
  // bit set 1 -> red
  private int pieceColors = 0b0000_0000_0000_0000_0000_0000_0000_0000;

  // bit set 0 -> single
  // bit set 1 -> king
  private int pieceTypes = 0b0000_0000_0000_0000_0000_0000_0000_0000;

  public void initStart() {
    pieceLocations =  0b1111_1111_1111_0000_0000_1111_1111_1111;
    pieceColors =     0b0000_0000_0000_0000_0000_1111_1111_1111;
    pieceTypes =      0b0000_0000_0000_0000_0000_0000_0000_0000;
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

  public int cartesianToIndex(int x, int y){
    int position = (y*X_BOARD_SIZE) + (x);
    if((position % 2) == (y%2)){
      return position/2;
    }
    return -1;
  }

  public SPACE_TYPE getPieceAtLocation(int x, int y){
    int bitIdx = cartesianToIndex(x, y);
    if(bitIdx == -1){
      return SPACE_TYPE.EMPTY;
    }
    int bitMask = 1 << bitIdx;
    if((bitMask & pieceLocations) != 0){
      if((bitMask & pieceColors) != 0){
        if((bitMask & pieceTypes) != 0){
          return SPACE_TYPE.KING_RED;
        } else {
          return SPACE_TYPE.SINGLE_RED;
        }
      } else {
        if((bitMask & pieceTypes) != 0){
          return SPACE_TYPE.KING_WHITE;
        } else {
          return SPACE_TYPE.SINGLE_WHITE;
        }
      }
    } else {
      return SPACE_TYPE.EMPTY;
    }
  }

  public SPACE_TYPE[] getRow(int idx){
    // TODO complete
    SPACE_TYPE[] toReturn = new SPACE_TYPE[X_BOARD_SIZE];
    for(int i = 0; i < toReturn.length; i++){
      SPACE_TYPE eachSpace = getPieceAtLocation(i, idx);
      toReturn[i] = eachSpace;
    }
    return toReturn;
  }

  public SPACE_TYPE[] getRowReverse(int idx){
    // TODO complete
    SPACE_TYPE[] spaceList = getRow(idx);
    SPACE_TYPE[] toReturn = new SPACE_TYPE[spaceList.length];

    for(int i = 0; i < toReturn.length; i++) {
      toReturn[i] = spaceList[spaceList.length-(i+1)];
    }

    return toReturn;
  }

  public int getRedLocations(){
    return pieceLocations & pieceColors;
  }

  public int getNumRedPieces(){
    int redLocs = getRedLocations();
    return Integer.bitCount(redLocs);
  }

  public int getWhiteLocations(){
    return pieceLocations & (~pieceColors);
  }

  public int getNumWhitePieces(){
    int whiteLocs = getWhiteLocations();
    return Integer.bitCount(whiteLocs);
  }

  public int getNumPieces(){
    return Integer.bitCount(pieceLocations);
  }
}
