package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.webcheckers.Application;

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
     *
     * @return
     */
    int getValue() {
      return value;
    }
  }

  //
  // Constants
  //
//  public static int X_BOARD_SIZE = 8;
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
  void initStart() {
    if (Application.demoMode) {
      pieceLocations = 0b0000_0000_0100_0100_0000_0000_0000_0000;
      pieceColors = 0b0000_0000_0000_0100_0000_0000_0000_0000;
      pieceTypes = 0b0000_0000_0000_0000_0000_0000_0000_0000;
    } else {
      pieceLocations = 0b1111_1111_1111_0000_0000_1111_1111_1111;
      pieceColors = 0b0000_0000_0000_0000_0000_1111_1111_1111;
      pieceTypes = 0b0000_0000_0000_0000_0000_0000_0000_0000;
    }
  }

  /**
   * TODO
   *
   * @param position the Position containing the coordinates to check
   * @return        if the specified location lies within
   *                the bounds of the board
   */
  private boolean isOnBoard(Position position){
    return isOnBoard(position.getCell(), position.getRow());
  }

  /**
   * TODO
   *
   * @param   x     x coordinate on the cartesian board
   * @param   y     y coordinate on the cartesian board
   * @return        if the specified location lies within
   *                the bounds of the board
   */
  private boolean isOnBoard(int x, int y){
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
  boolean isValidLocation(Position position){
    return cartesianToIndex(position) != -1 && isOnBoard(position);
  }

  /**
   * TODO
   */
  private int cartesianToIndex(Position position){
    int x = position.getCell();
    int y = position.getRow();

    int idx = (y*BOARD_SIZE) + (x);
    if((idx % 2) == (y%2)){
      return idx/2;
    }
    return -1;
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
    int idx = (y*BOARD_SIZE) + (x);
    if((idx % 2) == (y%2)){
      return idx/2;
    }
    return -1;
  }

  /**
   * TODO
   */
  private int getBitX(int bitIdx){
    return (bitIdx*2 % BOARD_SIZE) + (getBitY(bitIdx)%2);
  }

  /**
   * TODO
   */
  private int getBitY(int bitIdx){
    return bitIdx*2 / BOARD_SIZE;
  }

  /**
   * TODO
   */
  private Position getPosition(int bitIdx){
    return new Position(getBitX(bitIdx), getBitY(bitIdx));
  }

  /**
   * Determines whether a given piece is white.
   *
   * @param   piece a SPACE_TYPE representing the piece in question
   * @return    a boolean stating whether the piece is white
   */
  static boolean isWhite(SPACE_TYPE piece){
    return piece == SPACE_TYPE.SINGLE_WHITE || piece == SPACE_TYPE.KING_WHITE;
  }

  /**
   * Determines whether a given piece is red.
   *
   * @param   piece a SPACE_TYPE representing the piece in question
   * @return    a boolean stating whether the piece is red
   */
  static boolean isRed(SPACE_TYPE piece){
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

  /**
   * TODO
   */
  SPACE_TYPE getMiddlePiece(Position pos0, Position pos1){
    return getMiddlePiece(pos0.getCell(), pos0.getRow(), pos1.getCell(), pos1.getRow());
  }

  /**
   * TODO
   */
  private SPACE_TYPE getMiddlePiece(int x0, int y0, int x1, int y1){
    return getPieceAtLocation(Math.floorDiv(x0+x1,2), Math.floorDiv(y0+y1,2));
  }

  /**
   * Retrieves the piece at the specified Position.
   * If no piece is present at the location, the returned piece
   * is an EMPTY piece.
   *
   * @param   position  the Position containing the coordinates of the piece
   * @return    the piece located at the specified location
   */
  SPACE_TYPE getPieceAtLocation(Position position){
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
  SPACE_TYPE getPieceAtLocation(int x, int y){
    int bitIdx = cartesianToIndex(x, y);
    return getPieceAtLocation(bitIdx);
  }

  /**
   * TODO
   */
  private SPACE_TYPE getPieceAtLocation(int bitIdx) {
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
   * Attempts to place a specified piece at the location designated by
   * the position.
   *
   * @param   position  the Position containing the coordinates of the
   *                    piece to be removed
   * @param   piece     the type of piece to place
   * @return  true      if the board was altered to reflect the placed piece
   *          false     otherwise
   */
  boolean placePiece(Position position, SPACE_TYPE piece){
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
   * @param   position  the Position containing the coordinates of the
   *                    piece to be removed
   * @return            the piece that was removed
   */
  SPACE_TYPE removePiece(Position position){
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
  SPACE_TYPE removePiece(int x, int y){
    SPACE_TYPE remPiece = getPieceAtLocation(x, y);
    int bitIdx = cartesianToIndex(x, y);
    int bitMask = 1 << bitIdx;
    pieceLocations &= ~bitMask;
    return remPiece;
  }

  /**
   * TODO
   */
  boolean movePiece(Move move){
    Position start = move.getStart();
    Position end = move.getEnd();

    SPACE_TYPE pieceAtStart = getPieceAtLocation(start);
    return placePiece(end, pieceAtStart) && removePiece(start) == pieceAtStart;
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
    SPACE_TYPE[] toReturn = new SPACE_TYPE[BOARD_SIZE];
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
   * Gets a list of the valid, simple moves able to be made by the red player.
   *
   */
  List<Move> getAllRedSimpleMoves(){
    List<Move> moveList = new ArrayList<>();
    for(int i = 0; i < 32; i++){
      if(isRed(getPieceAtLocation(i))){
        moveList.addAll(getPieceSimpleMoves(i));
      }
    }
    return moveList;
  }

  /**
   * Gets a list of the valid, simple moves able to be made by the white player.
   *
   */
  List<Move> getAllWhiteSimpleMoves(){
    List<Move> moveList = new ArrayList<>();
    for(int i = 0; i < 32; i++){
      if(isWhite(getPieceAtLocation(i))){
        moveList.addAll(getPieceSimpleMoves(i));
      }
    }
    return moveList;
  }

  /**
   * TODO
   */
  private List<Move> getPieceSimpleMoves(int bitIdx){
    List<Move> moveList = new ArrayList<>();
    Position startPos = getPosition(bitIdx);

    SPACE_TYPE startPiece = getPieceAtLocation(bitIdx);
    int colorParity = startPiece.getValue()/Math.abs(startPiece.getValue());
    int rowParity = colorParity > 0 ? getBitY(bitIdx)%2 : (getBitY(bitIdx)+1)%2;
    int totalParity = colorParity*rowParity;

    for(int eachSimpleShiftAmt : SIMPLE_SHIFT_AMTS){
      int endBitIdx = bitIdx + eachSimpleShiftAmt*colorParity + totalParity;
      Position endPos = getPosition(endBitIdx);
      Move testMove = new Move(startPos, endPos);
      if(validateSimpleMove(testMove)){
        moveList.add(testMove);
      }
    }
    return moveList;
  }

  /**
   * TODO
   */
  private boolean validateSimpleMove(Move move){
    return validateSimpleMove(move.getStart(), move.getEnd());
  }

  /**
   * TODO
   */
  private boolean validateSimpleMove(Position pos0, Position pos1){
    return validateSimpleMove(
        cartesianToIndex(pos0.getCell(), pos0.getRow()),
        cartesianToIndex(pos1.getCell(), pos1.getRow()));
  }

  /**
   * TODO
   */
  private boolean validateSimpleMove(int idx0, int idx1){
    SPACE_TYPE idx0Piece = getPieceAtLocation(idx0);
    SPACE_TYPE idx1Piece = getPieceAtLocation(idx1);

    boolean validStartIdx = isRed(idx0Piece) || isWhite(idx0Piece);
    boolean validEndIdx = idx1Piece.equals(SPACE_TYPE.EMPTY);

    int idxDiff = Math.abs(idx1 - idx0);
    boolean idx0RightEdge = (idx0 & 0x7) == 7;
    boolean idx0LeftEdge = (idx0 & 0x7) == 0;

    boolean validIdxDiff = false;

    if(isRed(idx0Piece)){
      int adjIdxDiff = idxDiff - getBitY(idx0)%2;
      boolean rightPieceWrap = idx0RightEdge && idxDiff == 5;
      boolean leftPieceWrap = idx0LeftEdge && idxDiff == 3;

      validIdxDiff = !rightPieceWrap && !leftPieceWrap && SIMPLE_SHIFT_AMTS.contains(adjIdxDiff);
    } else if(isWhite(idx0Piece)){
      int adjIdxDiff = idxDiff - (getBitY(idx0)+1)%2;
      boolean rightPieceWrap = idx0RightEdge && idxDiff == 3;
      boolean leftPieceWrap = idx0LeftEdge && idxDiff == 5;

      validIdxDiff = !rightPieceWrap && !leftPieceWrap && SIMPLE_SHIFT_AMTS.contains(adjIdxDiff);
    }

    return validStartIdx && validEndIdx && validIdxDiff;
  }

  /**
   * Gets a list of the valid, jump moves able to be made by the red player.
   *
   */
  List<Move> getAllRedJumpMoves(){
    List<Move> moveList = new ArrayList<>();
    for(int i = 0; i < 32; i++){
      if(isRed(getPieceAtLocation(i))){
        moveList.addAll(getPieceJumpMoves(i));
      }
    }
    return moveList;
  }

  /**
   * Gets a list of the valid, jump moves able to be made by the white player.
   *
   */
  List<Move> getAllWhiteJumpMoves(){
    List<Move> moveList = new ArrayList<>();
    for(int i = 0; i < 32; i++){
      if(isWhite(getPieceAtLocation(i))){
        moveList.addAll(getPieceJumpMoves(i));
      }
    }
    return moveList;
  }

  /**
   * TODO
   */
  private List<Move> getPieceJumpMoves(int bitIdx){
    List<Move> moveList = new ArrayList<>();
    Position startPos = getPosition(bitIdx);

    SPACE_TYPE startPiece = getPieceAtLocation(bitIdx);
    int colorParity = startPiece.getValue()/Math.abs(startPiece.getValue());

    for(int eachJumpShiftAmt : JUMP_SHIFT_AMTS){
      int endBitIdx = bitIdx + eachJumpShiftAmt*colorParity;
      Position endPos = getPosition(endBitIdx);
      Move testMove = new Move(startPos, endPos);
      if(validateJumpMove(testMove)){
        moveList.add(testMove);
      }
    }
    return moveList;
  }

  /**
   * TODO
   */
  private boolean validateJumpMove(Move move){
    return validateJumpMove(move.getStart(), move.getEnd());
  }

  /**
   * TODO
   */
  private boolean validateJumpMove(Position pos0, Position pos1){
    return validateJumpMove(
        cartesianToIndex(pos0.getCell(), pos0.getRow()),
        cartesianToIndex(pos1.getCell(), pos1.getRow()));
  }

  /**
   * TODO
   */
  private boolean validateJumpMove(int idx0, int idx1){
    SPACE_TYPE idx0Piece = getPieceAtLocation(idx0);
    SPACE_TYPE idx1Piece = getPieceAtLocation(idx1);
    Position startPos = getPosition(idx0);
    Position endPos = getPosition(idx1);
    SPACE_TYPE middlePiece = getMiddlePiece(startPos, endPos);

    boolean validStartIdx = isRed(idx0Piece) || isWhite(idx0Piece);
    boolean validEndIdx = idx1Piece.equals(SPACE_TYPE.EMPTY);
    boolean validMidIdx = !middlePiece.equals(SPACE_TYPE.EMPTY)
        && isRed(idx0Piece) != isRed(middlePiece);

    int idxDiff = Math.abs(idx1 - idx0);
    boolean idx0RightEdge = (idx0 & 0x3) == 3;
    boolean idx0LeftEdge = (idx0 & 0x3) == 0;

    boolean validIdxDiff = false;

    if(isRed(idx0Piece)){
      boolean rightPieceWrap = idx0RightEdge && idxDiff == 9;
      boolean leftPieceWrap = idx0LeftEdge && idxDiff == 7;

      validIdxDiff = !rightPieceWrap && !leftPieceWrap && JUMP_SHIFT_AMTS.contains(idxDiff);
    } else if(isWhite(idx0Piece)){
      boolean rightPieceWrap = idx0RightEdge && idxDiff == 7;
      boolean leftPieceWrap = idx0LeftEdge && idxDiff == 9;

      validIdxDiff = !rightPieceWrap && !leftPieceWrap && JUMP_SHIFT_AMTS.contains(idxDiff);
    }

    return validStartIdx && validEndIdx && validMidIdx && validIdxDiff;
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
    int whiteLocs = getWhiteLocations();
    return Integer.bitCount(whiteLocs);
  }

  /**
   * Retrieves the total number of pieces currently placed
   * on the board.
   *
   * @return  the number of pieces on the board
   */
  int getNumPieces(){
    return Integer.bitCount(pieceLocations);
  }
}
