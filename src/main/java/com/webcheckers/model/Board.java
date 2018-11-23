package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Arrays;
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
public class Board implements Cloneable {
  //
  // Enums
  //
  /**
   * COLOR
   */
  public enum COLOR {RED, WHITE, NONE}

  /**
   * RANK
   */
  public enum RANK {SINGLE, KING}

  /**
   * SPACE_TYPE
   */
  public enum SPACE_TYPE {EMPTY(0), SINGLE_RED(1), SINGLE_WHITE(-1), KING_RED(2), KING_WHITE(-2);

    private final int value;

    /**
     * TODO
     *
     * @param i
     */
    SPACE_TYPE(int i) {
      this.value = i;
    }

    /**
     * TODO
     * @return
     */
    int getValue() {
      return value;
    }

    /**
     * TODO
     * @param toCompare
     * @return
     */
    boolean compareColor(SPACE_TYPE toCompare){
      return (this.isWhite() && toCompare.isWhite()) || (this.isRed() && toCompare.isRed());
    }

    /**
     * TODO
     * @param toCompare
     * @return
     */
    boolean compareRank(SPACE_TYPE toCompare){
      return (this.isKing() && toCompare.isKing()) || (this.isSingle() && toCompare.isSingle());
    }

    /**
     * TODO
     * @return
     */
    boolean isKing(){
      return Math.abs(value) == 2;
    }

    /**
     * TODO
     * @return
     */
    boolean isSingle(){
      return Math.abs(value) == 1;
    }

    /**
     * TODO
     * @return
     */
    boolean isEmpty(){
      return value == 0;
    }

    /**
     * TODO
     * @return
     */
    boolean isRed(){
      return value > 0;
    }

    /**
     * TODO
     * @return
     */
    boolean isWhite(){
      return value < 0;
    }

    /**
     * TODO
     * @param color
     * @return
     */
    boolean isColor(COLOR color){
      return this.getColor().equals(color);
    }

    /**
     * TODO
     * @return
     */
    COLOR getColor(){
      if(isRed()){
        return COLOR.RED;
      } else if(isWhite()){
        return COLOR.RED;
      } else {
        return COLOR.NONE;
      }
    }
  }

  //
  // Constants
  //
  public static int BOARD_SIZE = 8;
  private static List<Integer> SIMPLE_SHIFT_AMTS = Arrays.asList(3,4);
  private static List<Integer> JUMP_SHIFT_AMTS = Arrays.asList(7,9);

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
      pieceLocations = 0b1111_1111_1111_0000_0000_1111_1111_1111;
      pieceColors = 0b0000_0000_0000_0000_0000_1111_1111_1111;
      pieceTypes = 0b0000_0000_0000_0000_0000_0000_0000_0000;

//    pieceLocations =  0b0000_1111_0001_0000_0000_0001_1111_0000;
//    pieceColors =     0b0000_1101_0001_0000_0000_0000_0001_0000;
//    pieceTypes =      0b0000_0000_0000_0000_0000_0000_0000_0000;
  }

  /**
   * Initializes the state of the board and places red and white pieces
   * in random positions, as is approaching the midpoint way
   * of the game.
   *
   */
  public void initMid(){
//    pieceLocations =  0b0000_1111_0001_0000_0000_0001_1111_0000;
//    pieceColors =     0b0000_1110_0001_0000_0000_0000_0001_0000;
//    pieceTypes =      0b0000_0000_0000_0000_0000_0000_0000_0000;
    pieceLocations = 0b0000_0101_1100_0100_0010_1001_0010_0000;
    pieceColors = 0b0000_0000_0000_0000_0010_1001_0010_0000;
    pieceTypes = 0b0000_0000_0000_0000_0000_0000_0000_0000;
  }

  /**
   * Initializes the state of the board and places red and white pieces
   * in set positions, as is approaching the endpoint of the game.
   *
   */
  public void initEnd() {
    pieceLocations = 0b0000_0000_0100_0100_0000_0000_0000_0000;
    pieceColors = 0b0000_0000_0000_0100_0000_0000_0000_0000;
    pieceTypes = 0b0000_0000_0000_0000_0000_0000_0000_0000;
  }

  /**
   * TODO
   *
   * @param move
   * @return
   */
  public static Move invertMove(Move move){
    Position startPos = move.getStart();
    Position endPos = move.getEnd();

    Position whiteStart = new Position(
        (Board.BOARD_SIZE-1) - startPos.getCell(),
        (Board.BOARD_SIZE -1) - startPos.getRow());
    Position whiteEnd = new Position(
        (Board.BOARD_SIZE-1) - endPos.getCell(),
        (Board.BOARD_SIZE -1) - endPos.getRow());

    return new Move(whiteStart, whiteEnd);
  }

  /**
   * TODO
   *
   * @param   x     x coordinate on the cartesian board
   * @param   y     y coordinate on the cartesian board
   * @return        if the specified location lies within
   *                the bounds of the board
   */
  private static boolean isOnBoard(int x, int y){
    return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
  }

  /**
   * Checks if the locations specified by the (x,y) coordinate pair
   * is a location on which a piece could be place, ie. if the location
   * is a black square. This is due to the fact that as the board is setup,
   * all pieces must reside only on black spaces.
   *
   * @param   position  the Position containing the coordinates to check
   * @return  true  if the specified location is a black square
   *          false otherwise
   */
  static boolean isValidLocation(Position position){
    int x = position.getCell();
    int y = position.getRow();
    return cartesianToIndex(x, y) != -1 && isOnBoard(x, y);
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
  static int cartesianToIndex(int x, int y){
    int idx = (y*BOARD_SIZE) + (x);
    if((idx % 2) == (y%2)){
      return idx/2;
    }
    return -1;
  }

  /**
   * TODO
   * @param bitIdx
   * @return
   */
  private static int getBitX(int bitIdx){
    return (bitIdx*2 % BOARD_SIZE) + (getBitY(bitIdx)%2);
  }

  /**
   * TODO
   * @param bitIdx
   * @return
   */
  private static int getBitY(int bitIdx){
    return bitIdx*2 / BOARD_SIZE;
  }

  /**
   * TODO
   * @param x0
   * @param y0
   * @param x1
   * @param y1
   * @return
   */
  private SPACE_TYPE getMiddlePiece(int x0, int y0, int x1, int y1){
    return getPieceAtLocation(Math.floorDiv(x0+x1,2), Math.floorDiv(y0+y1,2));
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
  SPACE_TYPE getPieceAtLocation(int x, int y) {
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
   * @param   x       x coordinate on the cartesian board
   * @param   y       y coordinate on the cartesian board
   * @param   piece   the type of piece to place
   * @return  true    if the board was altered to reflect the placed piece
   *          false   otherwise
   */
  boolean placePiece(int x, int y, SPACE_TYPE piece){
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
  * Changes a single piece to a king.
  *
  * @param   x x coordinate on the cartesian board
  * @param   y y coordinate on the cartesian board
  */
  public void promotePiece(int x, int y){
    int bitIdx = cartesianToIndex(x, y);
    int bitMask = 1 << bitIdx;
    pieceTypes |= bitMask;
  }

  /**
   * TODO
   * @param move
   * @return
   */
  public boolean movePiece(Move move){
    Position start = move.getStart();
    Position end = move.getEnd();

    SPACE_TYPE pieceAtStart = getPieceAtLocation(start.getCell(), start.getRow());
    return placePiece(end.getCell(), end.getRow(), pieceAtStart)
        && removePiece(start.getCell(), start.getRow()) == pieceAtStart;
  }

  /**
   * Retrieves a single, indexed row from the current state of the board.
   * Note that the side initialized with the red pieces is considered
   * to be the bottom of the board.
   *
   * @param   idx the index from the bottom of the board
   * @return      an array of pieces that make up the specified row
   */
  public List<SPACE_TYPE> getRow(int idx){
    List<SPACE_TYPE> toReturn = new ArrayList<>();
    for(int i = 0; i < BOARD_SIZE; i++){
      SPACE_TYPE eachSpace = getPieceAtLocation(i, idx);
      toReturn.add(eachSpace);
    }
    return toReturn;
  }

  /**
   * TODO
   * @param color
   * @return
   */
  public List<Move> getAllSimpleMoves(COLOR color){
    List<Move> moveList = new ArrayList<>();
    for(int i = 0; i < 32; i++){
      if(getPieceAtLocation(getBitX(i), getBitY(i)).isColor(color)){
        moveList.addAll(getPieceSimpleMoves(getBitX(i), getBitY(i)));
      }
    }
    return moveList;
  }

  /**
   * TODO
   * @param x
   * @param y
   * @return
   */
  private List<Move> getPieceSimpleMoves(int x, int y){
    int bitIdx = cartesianToIndex(x, y);
    List<Move> moveList = new ArrayList<>();
    Position startPos = new Position(getBitX(bitIdx), getBitY(bitIdx));

    SPACE_TYPE startPiece = getPieceAtLocation(x, y);
    int colorParity = startPiece.getValue()/Math.abs(startPiece.getValue());
    int rowParity = colorParity > 0 ? getBitY(bitIdx)%2 : (getBitY(bitIdx)+1)%2;
    int totalParity = colorParity*rowParity;

    for(int eachSimpleShiftAmt : SIMPLE_SHIFT_AMTS){
      int endBitIdx = bitIdx + eachSimpleShiftAmt*colorParity + totalParity;
      Position endPos = new Position(getBitX(endBitIdx), getBitY(endBitIdx));
      Move testMove = new Move(startPos, endPos);
      if(validateSimpleMove(testMove)){
        moveList.add(testMove);
      }
    }
    return moveList;
  }

  /**
   * TODO
   * @param move
   * @return
   */
  private boolean validateSimpleMove(Move move){
    Position pos0 = move.getStart();
    Position pos1 = move.getEnd();
    int idx0 = cartesianToIndex(pos0.getCell(), pos0.getRow());
    int idx1 = cartesianToIndex(pos1.getCell(), pos1.getRow());

    SPACE_TYPE idx0Piece = getPieceAtLocation(pos0.getCell(), pos0.getRow());
    SPACE_TYPE idx1Piece = getPieceAtLocation(pos1.getCell(), pos1.getRow());

    boolean validStartIdx = !idx0Piece.isEmpty();
    boolean validEndIdx = idx1Piece.isEmpty();

    int idxDiff = Math.abs(idx1 - idx0);
    boolean idx0RightEdge = (idx0 & 0x7) == 7;
    boolean idx0LeftEdge = (idx0 & 0x7) == 0;

    boolean validIdxDiff = false;

    if(idx0Piece.isRed()){
      int adjIdxDiff = idxDiff - getBitY(idx0)%2;
      boolean rightPieceWrap = idx0RightEdge && idxDiff == 5;
      boolean leftPieceWrap = idx0LeftEdge && idxDiff == 3;

      validIdxDiff = !rightPieceWrap && !leftPieceWrap && SIMPLE_SHIFT_AMTS.contains(adjIdxDiff);
    } else if(idx0Piece.isWhite()){
      int adjIdxDiff = idxDiff - (getBitY(idx0)+1)%2;
      boolean rightPieceWrap = idx0RightEdge && idxDiff == 3;
      boolean leftPieceWrap = idx0LeftEdge && idxDiff == 5;

      validIdxDiff = !rightPieceWrap && !leftPieceWrap && SIMPLE_SHIFT_AMTS.contains(adjIdxDiff);
    }

    return validStartIdx && validEndIdx && validIdxDiff;
  }

  /**
   * TODO
   * @param color
   * @return
   */
  public List<Move> getAllJumpMoves(COLOR color){
    List<Move> moveList = new ArrayList<>();
    for(int i = 0; i < 32; i++){
      if(getPieceAtLocation(getBitX(i), getBitY(i)).isColor(color)){
        moveList.addAll(getPieceJumpMoves(getBitX(i), getBitY(i)));
      }
    }
    return moveList;
  }

  /**
   * TODO
   * @param x
   * @param y
   * @return
   */
  public List<Move> getPieceJumpMoves(int x, int y){
    int bitIdx = cartesianToIndex(x, y);
    List<Move> moveList = new ArrayList<>();
    Position startPos = new Position(getBitX(bitIdx), getBitY(bitIdx));

    SPACE_TYPE startPiece = getPieceAtLocation(x, y);
    int colorParity = startPiece.getValue()/Math.abs(startPiece.getValue());

    for(int eachJumpShiftAmt : JUMP_SHIFT_AMTS){
      int endBitIdx = bitIdx + eachJumpShiftAmt*colorParity;
      Position endPos = new Position(getBitX(endBitIdx), getBitY(endBitIdx));
      Move testMove = new Move(startPos, endPos);
      if(validateJumpMove(testMove)){
        moveList.add(testMove);
      }
    }
    return moveList;
  }

  /**
   * TODO
   * @param move
   * @return
   */
  private boolean validateJumpMove(Move move){
    Position pos0 = move.getStart();
    Position pos1 = move.getEnd();
    int idx0 = cartesianToIndex(pos0.getCell(), pos0.getRow());
    int idx1 = cartesianToIndex(pos1.getCell(), pos1.getRow());

    if(idx0 > 32 || idx1 > 32 || idx0 < 0 || idx1 < 0){
      return false;
    }

    SPACE_TYPE idx0Piece = getPieceAtLocation(pos0.getCell(), pos0.getRow());
    SPACE_TYPE idx1Piece = getPieceAtLocation(pos1.getCell(), pos1.getRow());
    SPACE_TYPE middlePiece = getMiddlePiece(getBitX(idx0), getBitY(idx0),
                                            getBitX(idx1), getBitY(idx1));

    boolean validStartIdx = !idx0Piece.isEmpty();
    boolean validEndIdx = idx1Piece.equals(SPACE_TYPE.EMPTY);
    boolean validMidIdx = !middlePiece.isEmpty() && idx0Piece.compareColor(middlePiece);

    int idxDiff = Math.abs(idx1 - idx0);
    boolean idx0RightEdge = (idx0 & 0x3) == 3;
    boolean idx0LeftEdge = (idx0 & 0x3) == 0;

    boolean validIdxDiff = false;

    if(idx0Piece.isRed()){
      boolean rightPieceWrap = idx0RightEdge && idxDiff == 9;
      boolean leftPieceWrap = idx0LeftEdge && idxDiff == 7;

      validIdxDiff = !rightPieceWrap && !leftPieceWrap && JUMP_SHIFT_AMTS.contains(idxDiff);
    } else if(idx0Piece.isWhite()){
      boolean rightPieceWrap = idx0RightEdge && idxDiff == 7;
      boolean leftPieceWrap = idx0LeftEdge && idxDiff == 9;

      validIdxDiff = !rightPieceWrap && !leftPieceWrap && JUMP_SHIFT_AMTS.contains(idxDiff);
    }

    return validStartIdx && validEndIdx && validMidIdx && validIdxDiff;
  }

  /**
   *
   * @param color
   * @return
   */
  public int getNumPieces(COLOR color){
    int toReturn = getNumRedPieces();
    if(color.equals(COLOR.WHITE)){
      toReturn = getNumWhitePieces();
    }
    return toReturn;
  }

  /**
   * Retrieves the locations of all red pieces on the board.
   *
   * @return  the bit indices of the positions of the red pieces
   */
  int getRedLocations(){
    return pieceLocations & pieceColors;
  }

  /**
   * Retrieves the number of red pieces currently on the board.
   *
   * @return  the number of red pieces on the board
   */
  int getNumRedPieces(){
    int redLocs = getRedLocations();
    return Integer.bitCount(redLocs);
  }

  /**
   * Retrieves the locations of all white pieces on the board.
   *
   * @return  the bit indices of the positions of the white pieces
   */
  int getWhiteLocations(){
    return pieceLocations & (~pieceColors);
  }

  /**
   * Retrieves the number of white pieces currently on the board.
   *
   * @return  the number of white pieces on the board
   */
  int getNumWhitePieces(){
    return Integer.bitCount(getWhiteLocations());
  }

//  /**
//   * Retrieves the total number of pieces currently placed
//   * on the board.
//   *
//   * @return  the number of pieces on the board
//   */
//  int getNumPieces(){
//    return Integer.bitCount(pieceLocations);
//  }

  /**
   * TODO
   *
   * @return
   * @throws CloneNotSupportedException
   */
  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
