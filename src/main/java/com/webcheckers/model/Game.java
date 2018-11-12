package com.webcheckers.model;

import static java.lang.Math.abs;

import com.webcheckers.model.Board.SPACE_TYPE;
import java.util.ArrayList;
import java.util.List;

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
  }

  //
  // Attributes
  //

  private Player whitePlayer;
  private Player redPlayer;
  private COLOR activeColor;
  private Board board;
  private List<Move> pendingMoves;

  private Player winner = null;
  private EndState endState = EndState.NOT_OVER;

  /**
   * The constructor for the Game class.
   *
   * @param rPlayer the red player
   * @param wPlayer the white player
   */
  public Game(Player rPlayer, Player wPlayer) {
    this.redPlayer = rPlayer;
    this.whitePlayer = wPlayer;

    this.board = new Board();
    this.pendingMoves = new ArrayList<>();
    activeColor = COLOR.RED;
    board.initStart();
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
    COLOR c = getActiveColor();
    switch(c){
      case RED:
        return getRedPlayer();
      case WHITE:
        return getWhitePlayer();
      default:
        return null;
    }
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
   * current state of the board.
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

    if(!board.isValidLocation(start) || !board.isValidLocation(end)){
      return false;
    }

    SPACE_TYPE pieceAtStart = board.getPieceAtLocation(start);
    SPACE_TYPE pieceAtEnd = board.getPieceAtLocation(end);

    if(pieceAtStart == SPACE_TYPE.EMPTY || pieceAtEnd != SPACE_TYPE.EMPTY){
      return false;
    }

    List<Move> validSimpleMoves = Board.isRed(pieceAtStart) ? board.getAllRedSimpleMoves() : board.getAllWhiteSimpleMoves();
    List<Move> validJumpMoves = Board.isRed(pieceAtStart) ? board.getAllRedJumpMoves() : board.getAllWhiteJumpMoves();

    if(validJumpMoves.size() != 0){
      return validJumpMoves.contains(move);
    }

    return validSimpleMoves.contains(move);
  }

  /**
   * Attempts to move a piece from the starting position to some ending
   * location. This method first verifies that the move is a valid move
   * and then tries to update the board to reflect the move, if it is valid.
   *
   * @param   move  the move to be checked
   * @return        true if the move was successfully made
   */
  boolean makeMove(Move move) {
    Position startPos = move.getStart();
    Position endPos = move.getEnd();
    Position midPos = new Position(
        (startPos.getCell() + endPos.getCell()) / 2,
        (startPos.getRow() + endPos.getRow()) / 2);

    if(!validateMove(move)){
      return false;
    }

    if(Math.abs(startPos.getRow() - endPos.getRow()) == 2){
      board.removePiece(midPos);
    }
    return board.movePiece(move);
  }

  /**
   * TODO
   */
  public void removeLastMove() {
    pendingMoves.remove(pendingMoves.size() - 1);
  }

  /**
   * TODO
   *
   * @param move
   */
  public void addPendingMove(Move move) {
    pendingMoves.add(move);
  }

  /**
   * TODO
   */
  public void applyMoves() {
    for (Move eachMove : pendingMoves) {
      makeMove(eachMove);
    }
    pendingMoves.clear();
  }

  /**
   * Toggles the player who turn it currently is.
   *
   */
  public void switchTurn(){
    if (activeColor.equals(COLOR.RED)) {
      activeColor = COLOR.WHITE;
    } else {
      activeColor = COLOR.RED;
    }
  }

  /**
   *
   * TODO update
   *
   * Retrieves the current state of the board, as seen by the specified player.
   *
   * @return  the current board state
   */
  public Board getBoardState() {
    return board;
  }

  /**
   *
   * Checks the state of the board in an attempt to detect an end state.
   * A board is considered to be in an end state when any of the following
   * conditions are met:
   *  there are no red pieces on the board
   *  there are no white pieces on the board
   *  the red player has no more valid moves to make
   *  the white player has no more valid moves to make
   *
   *  Sets a player as the winner
   *
   * @return  an enum indicating the reason the game ended
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
   * Ends the game.
   *
   * @return true if the game has ended
   *          false if the game has not ended
   */
  private boolean endGame() {
    // TODO
    return checkEnd();
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
      return endGame();
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
}
