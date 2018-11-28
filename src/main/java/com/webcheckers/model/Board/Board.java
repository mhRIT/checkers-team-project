package com.webcheckers.model.Board;

import com.webcheckers.model.Board.InitConfig.PRE_SET_BOARD;
import com.webcheckers.model.Board.InitConfig.START_TYPE;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
  public enum COLOR {RED, WHITE, NONE;

    /**
     *
     * @return
     */
    public COLOR opposite() {
      if(this.equals(COLOR.RED)){
        return WHITE;
      } else {
        return RED;
      }
    }
  }

  /**
   * SPACE_TYPE
   */
  public enum SPACE_TYPE {EMPTY(0), SINGLE_RED(1), SINGLE_WHITE(-1), KING_RED(2), KING_WHITE(-2);

    private final int value;

    /**
     * Gets the space type based on its numerical value
     *
     * @param i
     */
    SPACE_TYPE(int i) {
      this.value = i;
    }

    public int getValue(){
      return value;
    }
    /**
     * Checks if toCompare is equal to the current space type
     * @param toCompare
     * @return whether they are equal
     */
    boolean compareColor(SPACE_TYPE toCompare){
      return (this.isWhite() && toCompare.isWhite()) || (this.isRed() && toCompare.isRed());
    }

    /**
     * @return whether a space contains a king
     */
    public boolean isKing(){
      return Math.abs(value) == 2;
    }

    /**
     * @return whether a space contains a normal piece
     */
    boolean isSingle(){
      return Math.abs(value) == 1;
    }

    /**
     * @return whether a square is empty
     */
    public boolean isEmpty(){
      return value == 0;
    }

    /**
     * @return whether a space is occupied by red
     */
    public boolean isRed(){
      return value > 0;
    }

    /**
     * @return whether a space is occupied by white
     */
    public boolean isWhite(){
      return value < 0;
    }

    /**
     * @param color
     * @return whether a space is the same color as the parameter
     */
    boolean isColor(COLOR color){
      return this.getColor().equals(color);
    }

    /**
     * @return the color occupying the space
     */
    COLOR getColor(){
      if(isRed()){
        return COLOR.RED;
      } else if(isWhite()){
        return COLOR.WHITE;
      } else {
        return COLOR.NONE;
      }
    }
  }

  //
  // Constants
  //
  public static int BOARD_SIZE = 8;

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
   *
   * @param config
   */
  public void init(InitConfig config){
    START_TYPE configType = config.getStartType();
    switch (configType){
      case NORMAL:
        initStart();
        break;
      case RANDOM:
        initRandom(config.getNumRedPieces(), config.getNumWhitePieces());
        break;
      case PRESET:
        PRE_SET_BOARD presetBoard = config.getPreSetBoard();
        switch (presetBoard){
          case START:
            initStart();
            break;
          case MIDDLE:
            initMid();
            break;
          case END:
            initEnd();
            break;
          case NONE:
            initStart();
            break;
        }
        break;
    }
  }

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
//    pieceLocations =  0b1011_0100_0100_0100_0000_1001_0000_0111;
//    pieceColors =     0b0011_0000_0100_0100_0000_1001_0000_0111;
//    pieceTypes =      0b0011_0000_0000_0000_0000_0000_0000_0000;
  }

  /**
   * Initializes the state of the board and places red and white pieces
   * in random positions, as is approaching the midpoint of the game.
   *
   */
  void initMid() {
    pieceLocations =  0b0100_1111_1111_0011_1111_1111_1100_1010;
    pieceColors =     0b0000_0000_1000_0010_0011_1111_1100_1010;
    pieceTypes =      0b0000_0000_0000_0000_0000_0000_0000_0000;
  }

  /**
   * Initializes the state of the board and places red and white pieces
   * in set positions, as is approaching the endpoint of the game.
   *
   */
  void initEnd() {
    pieceLocations =  0b0110_0001_1010_0001_0010_0001_0100_0110;
    pieceColors =     0b0010_0000_1010_0000_0010_0001_0000_0000;
    pieceTypes =      0b0010_0000_0000_0000_0000_0000_0100_0110;
  }

  /**
   *
   * @param numRedPieces
   * @param numWhitePieces
   */
  public void initRandom(int numRedPieces, int numWhitePieces){
    for(int i = 0; i < numRedPieces; i++){
      Position nextPosition = getRandomPosition();
      if(nextPosition.getRow() == BOARD_SIZE-1){
        placePiece(nextPosition.getCell(), nextPosition.getRow(), SPACE_TYPE.KING_RED);
      } else {
        placePiece(nextPosition.getCell(), nextPosition.getRow(), SPACE_TYPE.SINGLE_RED);
      }
    }

    /**
     *
     */
    for(int i = 0; i < numWhitePieces; i++){
      Position nextPosition = getRandomPosition();
      if(nextPosition.getRow() == 0){
        placePiece(nextPosition.getCell(), nextPosition.getRow(), SPACE_TYPE.KING_WHITE);
      } else {
        placePiece(nextPosition.getCell(), nextPosition.getRow(), SPACE_TYPE.SINGLE_WHITE);
      }
    }
  }

  /**
   *
   * @return
   */
  Position getRandomPosition(){
    Random rand = new Random();
    int randIntCol = rand.nextInt(BOARD_SIZE);
    int randIntRow = rand.nextInt(BOARD_SIZE);
    Position toReturn = new Position(randIntCol, randIntRow);
    SPACE_TYPE pieceAtLoc = getPieceAtLocation(toReturn.getCell(), toReturn.getRow());
    while(!isValidLocation(toReturn) || !pieceAtLoc.isEmpty()){
      randIntCol = rand.nextInt(BOARD_SIZE);
      randIntRow = rand.nextInt(BOARD_SIZE);
      toReturn = new Position(randIntCol, randIntRow);
      pieceAtLoc = getPieceAtLocation(randIntCol, randIntRow);
    }
    return toReturn;
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
   * Checks if a pair of coordinates is within the boundaries of the board
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
   * is a location on which a piece could be placed, ie. if the location
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
   * Gets the space type being jumped over in a jump move
   * @param x0
   * @param y0
   * @param x1
   * @param y1
   * @return the space type being jumped over
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
  public SPACE_TYPE getPieceAtLocation(int x, int y) {
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
   * @return  true if a piece was successfully removed
   */
  public boolean removePiece(int x, int y){
    SPACE_TYPE remPiece = getPieceAtLocation(x, y);
    if(remPiece.isEmpty()){
      return false;
    }

    int bitIdx = cartesianToIndex(x, y);
    if(bitIdx == -1){
      return false;
    }

    int bitMask = 1 << bitIdx;
    pieceLocations &= ~bitMask;
    return true;
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
   * Moves a piece
   * @param move
   * @return whether the move was successful
   *
   */
  public boolean makeMove(Move move){
    Position start = move.getStart();
    Position end = move.getEnd();
    Position mid = new Position((start.getCell() + end.getCell()) / 2, (start.getRow() + end.getRow()) / 2);

    SPACE_TYPE pieceAtStart = getPieceAtLocation(start.getCell(), start.getRow());
    boolean piecePlaced = placePiece(end.getCell(), end.getRow(), pieceAtStart);
    boolean pieceRemoved = removePiece(start.getCell(), start.getRow());
    boolean jumped = true;
    if(move.isJump()){
      jumped = removePiece(mid.getCell(), mid.getRow());
    }

    return piecePlaced && pieceRemoved && jumped;
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
   * Get a list of all possible simple moves from a side
   * @param color the side to get the list from
   * @return a list of all possible simple moves
   */
  public List<Move> getAllSimpleMoves(COLOR color){
    List<Move> moveList = new ArrayList<>();
    for(int i = 0; i < 32; i++){
      SPACE_TYPE eachPiece = getPieceAtLocation(getBitX(i), getBitY(i));
      if(eachPiece.isColor(color)){
        moveList.addAll(getPieceSimpleMoves(getBitX(i), getBitY(i)));
      }
    }
    return moveList;
  }

  /**
   * Get a list of the simple moves a single piece can make
   * @param x x coordinate of the piece
   * @param y y coordinate of the piece
   * @return a list of the simple moves a single piece can make
   */
  private List<Move> getPieceSimpleMoves(int x, int y){
    int bitIdx = cartesianToIndex(x, y);
    List<Move> moveList = new ArrayList<>();
    Position startPos = new Position(getBitX(bitIdx), getBitY(bitIdx));

    // check each position of the four corners
    Position endPos_tr = new Position(x+1, y+1);
    Position endPos_tl = new Position(x-1, y+1);
    Position endPos_br = new Position(x+1, y-1);
    Position endPos_bl = new Position(x-1, y-1);

    Move testMove_tr = new Move(startPos, endPos_tr);
    Move testMove_tl = new Move(startPos, endPos_tl);
    Move testMove_br = new Move(startPos, endPos_br);
    Move testMove_bl = new Move(startPos, endPos_bl);

    if(validateSimpleMove(testMove_tr)){
      moveList.add(testMove_tr);
    }
    if(validateSimpleMove(testMove_tl)){
      moveList.add(testMove_tl);
    }
    if(validateSimpleMove(testMove_br)){
      moveList.add(testMove_br);
    }
    if(validateSimpleMove(testMove_bl)){
      moveList.add(testMove_bl);
    }

    return moveList;
  }

  /**
   * Validates that a move can be made
   * @param move the move to be validated
   * @return whether the move is valid
   */
  private boolean validateSimpleMove(Move move){
    if(move.isJump()){
      return false;
    }

    Position pos0 = move.getStart();
    Position pos1 = move.getEnd();

    if(!isValidLocation(pos0) || !isValidLocation(pos1)){
      return false;
    }

    SPACE_TYPE idx0Piece = getPieceAtLocation(pos0.getCell(), pos0.getRow());
    SPACE_TYPE idx1Piece = getPieceAtLocation(pos1.getCell(), pos1.getRow());

    boolean validStartIdx = !idx0Piece.isEmpty();
    boolean validEndIdx = idx1Piece.isEmpty();
    boolean validDirection = isValidDirection(pos0, pos1, idx0Piece);

    return validStartIdx && validEndIdx && validDirection;
  }

  /**
   * Get a list of all possible jump moves from a side
   * @param color the side to get the list from
   * @return the list of all possible jump moves from a side
   */
  public List<Move> getAllJumpMoves(COLOR color){
    List<Move> moveList = new ArrayList<>();
    for(int i = 0; i < 32; i++){
      SPACE_TYPE eachPiece = getPieceAtLocation(getBitX(i), getBitY(i));
      if(eachPiece.isColor(color)){
        moveList.addAll(getPieceJumpMoves(getBitX(i), getBitY(i)));
      }
    }
    return moveList;
  }

  /**
   * Get a list of the simple moves a single piece can make
   * @param x x coordinate of the piece
   * @param y y coordinate of the piece
   * @return the list of the simple moves a single piece can make
   */
  public List<Move> getPieceJumpMoves(int x, int y){
    int bitIdx = cartesianToIndex(x, y);
    List<Move> moveList = new ArrayList<>();
    Position startPos = new Position(getBitX(bitIdx), getBitY(bitIdx));
    int offset = 2;

    Position endPos_tr = new Position(x+offset, y+offset);
    Position endPos_tl = new Position(x-offset, y+offset);
    Position endPos_br = new Position(x+offset, y-offset);
    Position endPos_bl = new Position(x-offset, y-offset);

    Move testMove_tr = new Move(startPos, endPos_tr);
    Move testMove_tl = new Move(startPos, endPos_tl);
    Move testMove_br = new Move(startPos, endPos_br);
    Move testMove_bl = new Move(startPos, endPos_bl);

    if(validateJumpMove(testMove_tr)){
      moveList.add(testMove_tr);
    }
    if(validateJumpMove(testMove_tl)){
      moveList.add(testMove_tl);
    }
    if(validateJumpMove(testMove_br)){
      moveList.add(testMove_br);
    }
    if(validateJumpMove(testMove_bl)){
      moveList.add(testMove_bl);
    }

    return moveList;
  }

  /**
   * Validates a potential jump move
   * @param move the jump move to be validated
   * @return whether the move is valid
   */
  private boolean validateJumpMove(Move move){
    if(!move.isJump()){
      return false;
    }

    Position pos0 = move.getStart();
    Position pos1 = move.getEnd();

    if(!isValidLocation(pos0) || !isValidLocation(pos1)){
      return false;
    }

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
    boolean validMidIdx = !middlePiece.isEmpty() && !idx0Piece.compareColor(middlePiece);
    boolean validDirection = isValidDirection(pos0, pos1, idx0Piece);

    return validStartIdx && validEndIdx && validMidIdx && validDirection;
  }

  /**
   * Determines if a piece is able to move in the direction it is attempting to
   * @param pos0 the starting position of the piece
   * @param pos1 the ending position of the piece
   * @param idx0Piece the type of piece being moved
   * @return whether it can move forwards/backwards
   */
  private boolean isValidDirection(Position pos0, Position pos1, SPACE_TYPE idx0Piece) {
    boolean validDirection = false;
    if(idx0Piece.isKing()){
      validDirection = true;
    } else if(idx0Piece.isRed()){
      validDirection = pos0.getRow() < pos1.getRow();
    } else if(idx0Piece.isWhite()){
      validDirection = pos0.getRow() > pos1.getRow();
    }
    return validDirection;
  }

  /**
   * Gets the number of pieces at the defending edge of the board
   * @param color the side to get the list from
   * @return the number of pieces at the defending edge of the board
   */
  public int getNumPiecesAlongBase(COLOR color){
    int[] redBasePositions = {0, 1, 2, 3};
    int[] whiteBasePositions = {28, 29, 30, 31};

    int[] basePositions = color.equals(COLOR.WHITE) ? whiteBasePositions : redBasePositions;
    int colorLocs = color.equals(COLOR.WHITE) ? getWhiteLocations() : getRedLocations();

    return compareLocationColor(basePositions, colorLocs);
  }

  /**
   * Gets the number of pieces along the side of the board
   * @param color the side to get the list from
   * @return the number of pieces along the side of the board
   */
  public int getNumPiecesAlongSide(COLOR color){
    int[] edgePositions = {0, 8, 16, 24, 7, 15, 23, 31};
    int colorLocs = color.equals(COLOR.WHITE) ? getWhiteLocations() : getRedLocations();
    return compareLocationColor(edgePositions, colorLocs);
  }

  /**
   *
   * @param basePositions
   * @param colorLocs
   * @return
   */
  private int compareLocationColor(int[] basePositions, int colorLocs) {
    int toReturn = 0;
    for(int eachPos : basePositions){
      int shiftVal = (1 << eachPos);
      int baseVal = colorLocs & shiftVal;
      if(baseVal != 0){
        toReturn++;
      }
    }
    return toReturn;
  }
  /**
   *
   * @param color the side to get the list from
   * @return the number of pieces a side possesses
   */
  public int getNumPieces(COLOR color){
    return getNumSinglePieces(color) + getNumKingPieces(color);
  }

  /**
   * @return the total number of pieces on the board
   */
  public int getNumPieces(){
    return Integer.bitCount(pieceLocations & ~pieceTypes);
  }

  /**
   * Gets the number of normal pieces a side possesses
   * @param color the side to get the list from
   * @return the number of normal pieces a side possesses
   */
  public int getNumSinglePieces(COLOR color){
    int toReturn = getNumSingleRedPieces();
    if(color.equals(COLOR.WHITE)){
      toReturn = getNumSingleWhitePieces();
    }
    return toReturn;
  }

  /**
   * Retrieves the total number of pieces currently on the board.
   *
   * @return  the number of pieces on the board
   */
  public int getNumSinglePieces(){
    return Integer.bitCount(pieceLocations & ~pieceTypes);
  }

  /**
   * Gets the number of king pieces a side possesses
   * @param color the side to get the amount from
   * @return the number of king pieces a side possesses
   */
  public int getNumKingPieces(COLOR color){
    int toReturn = getNumKingRedPieces();
    if(color.equals(COLOR.WHITE)){
      toReturn = getNumKingWhitePieces();
    }
    return toReturn;
  }

  /**
   * Retrieves the total number of king currently on the board.
   *
   * @return  the number of pieces on the board
   */
  public int getNumKings(){
    return Integer.bitCount(pieceLocations & pieceTypes);
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
   * Retrieves the number of single red pieces currently on the board.
   *
   * @return  the number of red pieces on the board
   */
  int getNumSingleRedPieces(){
    return Integer.bitCount(getRedLocations() & ~pieceTypes);
  }

  /**
   * Retrieves the number of single red pieces currently on the board.
   *
   * @return  the number of red pieces on the board
   */
  int getNumKingRedPieces(){
    return Integer.bitCount(getRedLocations() & pieceTypes);
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
  int getNumSingleWhitePieces(){
    return Integer.bitCount(getWhiteLocations() & ~pieceTypes);
  }

  /**
   * Retrieves the number of single red pieces currently on the board.
   *
   * @return  the number of red pieces on the board
   */
  int getNumKingWhitePieces(){
    return Integer.bitCount(getWhiteLocations() & pieceTypes);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString(){
    StringBuilder toReturn = new StringBuilder();
    for(int i = 0; i < BOARD_SIZE*2 + 1; i++){
      toReturn.append("-");
    }
    toReturn.append("\n");

    for(int eachRow = BOARD_SIZE - 1; eachRow >= 0; eachRow--){
      toReturn.append("|");
      for(int eachCol = 0; eachCol < BOARD_SIZE; eachCol++){
        SPACE_TYPE eachPiece = getPieceAtLocation(eachCol, eachRow);
        switch (eachPiece){
          case EMPTY:
            toReturn.append(" |");
            break;
          case SINGLE_RED:
            toReturn.append("r|");
            break;
          case SINGLE_WHITE:
            toReturn.append("w|");
            break;
          case KING_RED:
            toReturn.append("R|");
            break;
          case KING_WHITE:
            toReturn.append("W|");
            break;
        }
      }
      toReturn.append("\n");
    }
    for(int i = 0; i < BOARD_SIZE*2 + 1; i++){
      toReturn.append("-");
    }

    return toReturn.toString();
  }

  /**
   * TODO
   *
   * @return
   */
  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
      return null;
    }
  }
}
