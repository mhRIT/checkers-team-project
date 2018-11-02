package com.webcheckers.model;

import static com.webcheckers.ui.boardView.Message.MESSAGE_TYPE.error;
import static java.lang.Math.abs;

import com.webcheckers.ui.boardView.Message;

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
  //
  // Enums
  //
  public enum SPACE_TYPE {EMPTY, SINGLE_RED, SINGLE_WHITE, KING_RED, KING_WHITE}

  //
  // Constants
  //
  public static int X_BOARD_SIZE = 8;
  public static int Y_BOARD_SIZE = 8;

  /**
   * Tracks whether a current position on the board is occupied.
   *
   * bit set 0 -> empty
   * bit set 1 -> occupied
   */
  private int pieceLocations = 0b0000_0000_0000_0000_0000_0000_0000_0000;

  /**
   * Tracks the color of the last piece which was stored at a location
   * on the board.
   *
   * bit set 0 -> white
   * bit set 1 -> red
   */
  private int pieceColors = 0b0000_0000_0000_0000_0000_0000_0000_0000;

  /**
   * Tracks the rank of the last piece which was stored at a location
   * on the board.
   *
   * bit set 0 -> single
   * bit set 1 -> king
   */
  private int pieceTypes = 0b0000_0000_0000_0000_0000_0000_0000_0000;

  /**
   * Initializes the state of the board and places red and white pieces
   * in the correct positions, as would be expected when the game
   * first starts.
   *
   */
  public void initStart() {
    pieceLocations =  0b1111_1111_1111_0000_0000_1111_1111_1111;
    pieceColors =     0b0000_0000_0000_0000_0000_1111_1111_1111;
    pieceTypes =      0b0000_0000_0000_0000_0000_0000_0000_0000;
  }

  /**
   * Checks whether the move specified by the initial and end
   * positions is a valid move, based on the current state of the board.
   * This criteria can be stated as follows:
   *  the initial position must be occupied by a piece
   *  the end position must not be occupied by a space
   *  if the end position is farther than sqrt(2), then the intermediate
   *    location must be occupied by a piece of a different color
   *    than the piece on the starting location
   *
   * @param x0  the initial x coordinate
   * @param y0  the initial y coordinate
   * @param x1  the final x coordinate
   * @param y1  the final y coordinate
   * @return    true if the specified move is a valid move
   *            false otherwise
   */
  public boolean validateMove(int x0, int y0, int x1, int y1){
    // TODO
    SPACE_TYPE pieceToMove = getPieceAtLocation(x0, y0);
    if(x1 < 1 || x1 > X_BOARD_SIZE || y1 < 1 || y1 > Y_BOARD_SIZE){
      return false;
    }
    if(getPieceAtLocation(x1, y1) == SPACE_TYPE.EMPTY) {
      // Simple Move
      if (abs(x1 - x0) == 1 && abs(y1 = y0) == 1) {
        if (y1 - y0 == 1 || isKing(pieceToMove)) {
          return true;
        }
      }
      // Single Jump
      else if (abs(x1 - x0) == 2 && abs(y1 - y0) == 2) {
        SPACE_TYPE opponentPiece = getPieceAtLocation((x0 + x1) / 2, (y0 + y1) / 2);
        if (y1 - y0 == 2 || isKing(pieceToMove)) {
          if (isRed(pieceToMove) != isRed(opponentPiece))
            return true;
        }
      }
    }
    return false;
  }

  /**
   * Attempts to move a piece from the starting position to some ending location.
   * This method first verifies that the move is a valid move and then
   * tried to update the board to reflect the move, if it is valid.
   *
   * @param x0  the initial x coordinate
   * @param y0  the initial y coordinate
   * @param x1  the final x coordinate
   * @param y1  the final y coordinate
   * @return    true if the specified move was able to be performed
   *            false otherwise
   */
  public boolean movePiece(int x0, int y0, int x1, int y1) {
    // TODO
    if(validateMove(x0, y0, x1, y1)){
      SPACE_TYPE pieceType = getPieceAtLocation(x0, y0);
      removePiece(x0, y0);
      placePiece(x1, y1, pieceType);
      if(abs(x0 - x1) == 2) {
        removePiece((x0 + x1) / 2, (y0 + y1) / 2);
      }
      return true;
    }
    return false;
  }

  /**
   * TODO this method should likely be in the Game class
   *
   * Checks the state of the board in an attempt to detect an end state.
   * A board is considered to be in an end state when any of the following
   * conditions are met:
   *  there are no red pieces on the board
   *  there are no white pieces on the board
   *  the red player has no more valid moves to make
   *  the white player has no more valid moves to make
   *
   * @return  true  if the current state of the board is indicative of an
   *                end state
   *          false otherwise
   */
  public boolean checkEnd() {
    // TODO
    return false;
  }

  /**
   * Converts the x and y coordinate positions (as would be expected on a typical
   * cartesian coordinate system) into the corresponding bit index of the location
   * of the coordinate pair.
   *
   * @param   x x coordinate on the cartesian board
   * @param   y y coordinate on the cartesian board
   * @return    equivalent bit position of the coordinate paid
   */
  int cartesianToIndex(int x, int y){
    int position = (y*X_BOARD_SIZE) + (x);
    if((position % 2) == (y%2)){
      return position/2;
    }
    return -1;
  }

  /**
   * Determines whether a given piece is red.
   *
   * @param   piece a SPACE_TYPE representing the piece in question
   * @return    a boolean stating whether the piece is red
   */
  public boolean isRed(SPACE_TYPE piece){
    return piece == SPACE_TYPE.SINGLE_RED || piece == SPACE_TYPE.KING_RED;
  }

  /**
   * Determines whether a given piece is a king.
   *
   * @param   piece a SPACE_TYPE representing the piece in question
   * @return    a boolean stating whether the piece is a king
   */
  public boolean isKing(SPACE_TYPE piece){
    return piece == SPACE_TYPE.KING_RED || piece == SPACE_TYPE.KING_WHITE;
  }

  /**
   * Retrieves the piece at the location specified by the (x,y)
   * coordinate pair.
   * If no piece is present at the location, the returned piece
   * is an EMPTY piece.
   *
   * @param   x x coordinate on the cartesian board
   * @param   y y coordinate on the cartesian board
   * @return    the piece located at the specified location
   */
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

  /**
   * Attempts to place a specified piece at the location designate by
   * the (x,y) coordinate pair.
   * TODO make a note of the conditions for failure/success
   *
   * @param   x     x coordinate on the cartesian board
   * @param   y     y coordinate on the cartesian board
   * @param   piece the type of piece to place
   * @return  true  if the board was altered to reflect the placed piece
   *          else  otherwise
   */
  public boolean placePiece(int x, int y, SPACE_TYPE piece){
    int bitIdx = cartesianToIndex(x, y);
    if(bitIdx == -1){
      return false;
    }
    int bitMask = 1 << bitIdx;
    if((bitMask & pieceLocations) == 0){
      pieceLocations |= bitMask;
      switch (piece){
        case SINGLE_RED:
          pieceColors |= bitMask;
          pieceTypes &= ~bitMask;
          break;
        case KING_RED:
          pieceColors |= bitMask;
          pieceTypes |= bitMask;
          break;
        case SINGLE_WHITE:
          pieceColors &= ~bitMask;
          pieceTypes &= ~bitMask;
          break;
        case KING_WHITE:
          pieceColors &= ~bitMask;
          pieceTypes |= bitMask;
          break;
        case EMPTY:
          return false;
      }
      return true;
    } else {
      return false;
    }
  }

  /**
   * Attempts to remove the piece that is at the location designated
   * by the (x,y) coordinate pair.
   * Note that if the location either does not contain a piece
   * or if the location is not a valid position for a piece to be placed,
   * then this method returns that the removed piece was EMPTY.
   *
   * @param   x x coordinate on the cartesian board
   * @param   y y coordinate on the cartesian board
   * @return    the piece that was removed
   */
  public SPACE_TYPE removePiece(int x, int y){
    SPACE_TYPE remPiece = getPieceAtLocation(x, y);
    int bitIdx = cartesianToIndex(x, y);
    int bitMask = 1 << bitIdx;
    pieceLocations &= ~bitMask;
    return remPiece;
  }

  /**
   * Checks if the locations specified by the (x,y) coordinate pair
   * is a location on which a piece could be place, ie. if the location
   * is a black square. This is due to the fact that as the board is setup,
   * all pieces must reside only on black spaces.
   *
   * @param   x     x coordinate on the cartesian board
   * @param   y     y coordinate on the cartesian board
   * @return  true  if the specified location is a black square
   *          false otherwise
   */
  public boolean isValidLocation(int x, int y){
    return cartesianToIndex(x, y) != -1;
  }

  /**
   * Retrieves a single, indexed row from the current state of the board.
   * Note that the side initialized with the red pieces is considered
   * to be the bottom of the board.
   *
   * @param   idx the index from the bottom of the board
   * @return      an array of pieces that make up the specified row
   */
  public SPACE_TYPE[] getRow(int idx){
    SPACE_TYPE[] toReturn = new SPACE_TYPE[X_BOARD_SIZE];
    for(int i = 0; i < toReturn.length; i++){
      SPACE_TYPE eachSpace = getPieceAtLocation(i, idx);
      toReturn[i] = eachSpace;
    }
    return toReturn;
  }

  /**
   * Retrieves a single, indexed row, in reverse order, from the current state of the board.
   * Note that the side initialized with the red pieces is considered
   * to be the bottom of the board.
   *
   * @param   idx the index from the bottom of the board
   * @return      an array of pieces that make up the specified row, in reverse order
   */
  public SPACE_TYPE[] getRowReverse(int idx){
    SPACE_TYPE[] spaceList = getRow(idx);
    SPACE_TYPE[] toReturn = new SPACE_TYPE[spaceList.length];

    for(int i = 0; i < toReturn.length; i++) {
      toReturn[i] = spaceList[spaceList.length-(i+1)];
    }

    return toReturn;
  }

  /**
   * Retrieves the locations of all red pieces on the board.
   *
   * @return  the bit indices of the positions of the red pieces
   */
  public int getRedLocations(){
    return pieceLocations & pieceColors;
  }

  /**
   * Retrieves the number of red pieces currently on the board.
   *
   * @return  the number of red pieces on the board
   */
  public int getNumRedPieces(){
    int redLocs = getRedLocations();
    return Integer.bitCount(redLocs);
  }

  /**
   * Retrieves the locations of all white pieces on the board.
   *
   * @return  the bit indices of the positions of the white pieces
   */
  public int getWhiteLocations(){
    return pieceLocations & (~pieceColors);
  }

  /**
   * Retrieves the number of white pieces currently on the board.
   *
   * @return  the number of white pieces on the board
   */
  public int getNumWhitePieces(){
    int whiteLocs = getWhiteLocations();
    return Integer.bitCount(whiteLocs);
  }

  /**
   * Retrieves the total number of pieces currently placed
   * on the board.
   *
   * @return  the number of pieces on the board
   */
  public int getNumPieces(){
    return Integer.bitCount(pieceLocations);
  }
}
