package com.webcheckers.model;

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

  public boolean isOnBoard(Position position){
    return position.getCell() >= 0 && position.getCell() < X_BOARD_SIZE
            && position.getRow() >= 0 && position.getRow() < Y_BOARD_SIZE;
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
   * Determines whether a given piece is white.
   *
   * @param   piece a SPACE_TYPE representing the piece in question
   * @return    a boolean stating whether the piece is white
   */
  public static boolean isWhite(SPACE_TYPE piece){
    return piece == SPACE_TYPE.SINGLE_WHITE || piece == SPACE_TYPE.KING_WHITE;
  }

  /**
   * Determines whether a given piece is red.
   *
   * @param   piece a SPACE_TYPE representing the piece in question
   * @return    a boolean stating whether the piece is red
   */
  public static boolean isRed(SPACE_TYPE piece){
    return piece == SPACE_TYPE.SINGLE_RED || piece == SPACE_TYPE.KING_RED;
  }

  /**
   * Determines whether a given piece is a king.
   *
   * @param   piece a SPACE_TYPE representing the piece in question
   * @return    a boolean stating whether the piece is a king
   */
  public static boolean isKing(SPACE_TYPE piece){
    return piece == SPACE_TYPE.KING_RED || piece == SPACE_TYPE.KING_WHITE;
  }

  public SPACE_TYPE getPieceAtLocation(Position position){
    return getPieceAtLocation(position.getCell(), position.getRow());
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
   * the position.
   *
   * @param   position  the Position containing the coordinates of the
   *                    piece to be removed
   * @param   piece     the type of piece to place
   * @return  true      if the board was altered to reflect the placed piece
   *          false     otherwise
   */
  public boolean placePiece(Position position, SPACE_TYPE piece){
    return placePiece(position.getCell(), position.getRow(), piece);
  }

  /**
   * Attempts to place a specified piece at the location designate by
   * the (x,y) coordinate pair.
   * TODO make a note of the conditions for failure/success
   *
   * @param   x       x coordinate on the cartesian board
   * @param   y       y coordinate on the cartesian board
   * @param   piece   the type of piece to place
   * @return  true    if the board was altered to reflect the placed piece
   *          false   otherwise
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
   * @param   position  the Position containing the coordinates of the
   *                    piece to be removed
   * @return            the piece that was removed
   */
  public SPACE_TYPE removePiece(Position position){
    return removePiece(position.getCell(), position.getRow());
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
