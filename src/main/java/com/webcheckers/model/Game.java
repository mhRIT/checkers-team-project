package com.webcheckers.model;

import static java.lang.Math.abs;

import com.webcheckers.Application.DEMO_STATE;
import com.webcheckers.model.Board.SPACE_TYPE;
import java.util.List;
import java.util.Stack;

/**
 *  {@code Game}
 *  <p>
 *  Represents a single checkers Game played by two players.
 *  </p>
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mvm7902@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 *
 */
public class Game {
  //
  // Enums
  //

  public enum COLOR {RED, WHITE}

  public enum EndState{
    ALL_PIECES, NO_MOVES, RESIGNATION, NOT_OVER;

    public String toString(){
      switch(this){
        case ALL_PIECES:
          return " they captured all of their opponent's pieces.";
        case NO_MOVES:
          return " their opponent could not make a move.";
        case RESIGNATION:
          return " their opponent resigned.";
        default:
          return " I am a terrible programmer.";
      }
    }
  }

  //
  // Attributes
  //

  private Player whitePlayer;
  private Player redPlayer;
  private COLOR activeColor;
  private Stack<Board> boardStack;

  private Player winner = null;
  private EndState endState = EndState.NOT_OVER;
  String[] endInfo = new String[2];

  private boolean turnOver;
  private Stack<Move> moveStack;
  
  /**
   * The constructor for the Game class.
   *
   * @param rPlayer the red player
   * @param wPlayer the white player
   */
  public Game(Player rPlayer, Player wPlayer) {
    this.redPlayer = rPlayer;
    this.whitePlayer = wPlayer;

    this.boardStack = new Stack<>();
    activeColor = COLOR.RED;

    Board board = new Board();
    boardStack.push(board);
    initializeStart();

    turnOver = false;
    moveStack = new Stack<>();
  }

  public void initializeStart(){
    Board currentBoard = boardStack.peek();
    currentBoard.initStart();
  }

  public void initializeMid(){
    Board currentBoard = boardStack.peek();
    currentBoard.initMid();
  }

  public void initializeEnd(){
    Board currentBoard = boardStack.peek();
    currentBoard.initEnd();
  }

  /**
   * Retrieves the red player.
   *
   * @return the red Player
   */
  public Player getRedPlayer() {
    return redPlayer;
  }

  /**
   * Retrieves the white player.
   *
   * @return the white Player
   */
  public Player getWhitePlayer() {
    return whitePlayer;
  }

  /**
   * Retrieves the color of the player who is currently
   * making a move.
   *
   * @return  the color of the player whose turn it is
   */
  public COLOR getActiveColor() {
    return activeColor;
  }

  public Player getActivePlayer(){
    COLOR c = activeColor;
    Player activePlayer = redPlayer;
    if(c.equals(COLOR.WHITE)){
      activePlayer = whitePlayer;
    }
    return activePlayer;
  }

  /**
   * TODO
   *
   * @param move
   * @return
   */
  public Move invertMove(Move move){
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
   * Checks whether the move specified is a valid move, based on the
   * current state of the currentBoard.
   * This criteria can be stated as follows:
   *  the initial position must be occupied by a piece
   *  the end position must not be occupied by a space
   *  if the end position is farther than sqrt(2), then the intermediate
   *    location must be occupied by a piece of a different color
   *    than the piece on the starting location
   *
   * @param   move  the move to be checked
   * @return        true if the specified move is a valid move
   *                false otherwise
   */
  public boolean validateMove(Move move){
    Position start = move.getStart();
    Position end = move.getEnd();
    Board currentBoard = boardStack.peek();

    if(!currentBoard.isValidLocation(start) || !currentBoard.isValidLocation(end)){
      return false;
    }

    SPACE_TYPE pieceAtStart = currentBoard.getPieceAtLocation(start);
    SPACE_TYPE pieceAtEnd = currentBoard.getPieceAtLocation(end);

    if(pieceAtStart == SPACE_TYPE.EMPTY || pieceAtEnd != SPACE_TYPE.EMPTY){
      return false;
    }

    List<Move> validSimpleMoves = Board.isRed(pieceAtStart) ? currentBoard.getAllRedSimpleMoves() : currentBoard
        .getAllWhiteSimpleMoves();
    List<Move> validJumpMoves = Board.isRed(pieceAtStart) ? currentBoard.getAllRedJumpMoves() : currentBoard
        .getAllWhiteJumpMoves();

    //
    // TODO add check for if this is the first move or a subsequent move
    //      if not first move, a player is only able to make a jump move
    //      a player is expected to make a jump move after making a simple move
    //
    if(validJumpMoves.size() != 0){
      return validJumpMoves.contains(move);
    }

    return validSimpleMoves.contains(move);
  }

  public List<Move> getActivePlayerJumpMoves(){
    List<Move> moveList = getBoardState().getAllRedJumpMoves();
    if(activeColor.equals(COLOR.WHITE)){
      moveList = getBoardState().getAllWhiteJumpMoves();
    }
    return moveList;
  }

  /**
   * TODO
   *
   * @return
   */
  public boolean isTurnOver(){
    if(moveStack.empty()){
      return false;
    }

    List<Move> moveList = getActivePlayerJumpMoves();
    Move lastMove = moveStack.peek();
    Position lastMoveStart = lastMove.getStart();
    Position lastMoveEnd = lastMove.getEnd();

    if(Math.abs(lastMoveStart.getRow() - lastMoveEnd.getRow()) == 1){
      return true;
    }

    return moveList.isEmpty();
//    for (Move eachJumpMove : moveList) {
//      if(lastMoveEnd.equals(eachJumpMove.getStart())){
//        return false;
//      }
//    }
//
//    return true;
  }

  /**
   * Attempts to move a piece from the starting position to some ending
   * location. This method first verifies that the move is a valid move
   * and then tries to update the currentBoard to reflect the move, if it is valid.
   *
   * @param   move  the move to be checked
   * @return        true if the move was successfully made
   */
  public boolean makeMove(Move move) {
    Position startPos = move.getStart();
    Position endPos = move.getEnd();
    Position midPos = new Position(
        (startPos.getCell() + endPos.getCell()) / 2,
        (startPos.getRow() + endPos.getRow()) / 2);

    if(!validateMove(move)){
      return false;
    }

    if(!moveStack.empty()){
      Move prevMove = moveStack.peek();
      Position previousEnd = prevMove.getEnd();
      if(!previousEnd.equals(startPos)){
        return false;
      }
    }

    Board currentBoard = boardStack.peek();
    try {
      Board nextBoard = (Board) currentBoard.clone();

      turnOver = true;
      boardStack.push(nextBoard);
      moveStack.push(move);

      if(Math.abs(startPos.getRow() - endPos.getRow()) == 2){
        nextBoard.removePiece(midPos);
      }
      nextBoard.movePiece(move);

      return true;
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * TODO
   */
  public void undoLastMove() {
    turnOver = false;
    moveStack.pop();
    boardStack.pop();
  }

  /**
   * Toggles the player who turn it currently is.
   *
   */
  public void switchTurn(){
    if(turnOver){
      moveStack = new Stack<>();
      if (activeColor.equals(COLOR.RED)) {
        activeColor = COLOR.WHITE;
      } else {
        activeColor = COLOR.RED;
      }
    }
  }

  /**
   *
   * TODO update
   *
   * Retrieves the current state of the currentBoard, as seen by the specified player.
   *
   * @return  the current currentBoard state
   */
  public Board getBoardState() {
    return boardStack.peek();
  }

  /**
   *
   * Checks the state of the currentBoard in an attempt to detect an end state.
   * A currentBoard is considered to be in an end state when any of the following
   * conditions are met:
   *  there are no red pieces on the board
   *  there are no white pieces on the board
   *
   *  Sets a player as the winner
   *
   * @return  true  if the current state of the currentBoard is indicative of an
   *                end state
   *          false otherwise
   */
  public boolean checkEnd() {
    if(board.getNumRedPieces() ==  0){
      this.winner = getWhitePlayer();
      this.endState = EndState.ALL_PIECES;
      return true;
    }
    if(board.getNumWhitePieces() == 0){
      this.winner = getRedPlayer();
      this.endState = EndState.ALL_PIECES;
      return true;
    }
    return false;
  }

  /**
   * Explains the end of a game
   *
   * @return a string array containing information from the ended game
   */
  public void endGame() {
    endInfo[0] = winner.getName();
    endInfo[1] = endState.toString();
  }

  public String endMessage(){
    return String.format("Game is over. \'%s\' is the winner. They won because %s",this.endInfo[0],this.endInfo[1]);
  }

  /**
   * Resigns the specified player from the game and returns
   * if the player was successfully removed and the game
   * was successfully ended.
   *
   * @param resignPlayer  the player that resigned
   * @return true         if the player was successfully removed and
   *                      and the game was successfully ended
   *         false        otherwise
   */
  public boolean resign(Player resignPlayer){
    // TODO complete and verify functionality
    if(hasPlayer(resignPlayer)){
      return checkEnd();
    }
    return false;
  }

  /**
   * Checks if one of the players in the game is the specified player.
   *
   * @param player  the player to check
   * @return  whether the specified player is a participant of the game
   */
  public boolean hasPlayer(Player player) {
    return player.equals(redPlayer) || player.equals(whitePlayer);
  }

  public Player getWinner(){return winner;}
  public EndState getEndState(){return endState;}
  public void setBoardTest(Board board){this.board = board;}
}
