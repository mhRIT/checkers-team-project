package com.webcheckers.model;

import static java.lang.Math.abs;

import com.webcheckers.model.Board.SPACE_TYPE;

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

  //
  // Attributes
  //

  private Player whitePlayer = null;
  private Player redPlayer = null;
  private COLOR activeColor;
  private Board board;

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

    if(!board.isOnBoard(start) || !board.isOnBoard(end)){
      return false;
    }

    SPACE_TYPE pieceAtStart = board.getPieceAtLocation(start);
    SPACE_TYPE pieceAtEnd = board.getPieceAtLocation(end);

    if(pieceAtStart == SPACE_TYPE.EMPTY || pieceAtEnd != SPACE_TYPE.EMPTY){
      return false;
    }

    int x0 = start.getCell();
    int y0 = start.getRow();

    int x1 = end.getCell();
    int y1 = end.getRow();

    if(abs(x1 - x0) != 1){
      return false;
    } else {
      if(Board.isRed(pieceAtStart)){
        return (y1 - y0) == 1;
      } else {
        return (y0 - y1) == 1;
      }
    }
  }

  /**
   * Attempts to move a piece from the starting position to some ending
   * location. This method first verifies that the move is a valid move
   * and then tries to update the board to reflect the move, if it is valid.
   *
   * @param   move  the move to be checked
   * @return        true if the move was successfully made
   */
  public boolean makeMove(Move move) {
    if(!validateMove(move)){
      return false;
    }

    Position start = move.getStart();
    Position end = move.getEnd();

    SPACE_TYPE pieceAtStart = board.getPieceAtLocation(start);

    return board.placePiece(end, pieceAtStart) && board.removePiece(start) == pieceAtStart;
  }

  /**
   * Toggles the player who turn it currently is.
   *
   * @return true if the turn was successfully switched
   */
  public boolean switchTurn(){
    if (activeColor.equals(COLOR.RED)) {
      activeColor = COLOR.WHITE;
    } else {
      activeColor = COLOR.RED;
    }
    return true;
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
   * @return  true  if the current state of the board is indicative of an
   *                end state
   *          false otherwise
   */
  public boolean checkEnd() {
    // TODO
    return false;
  }

  /**
   * Ends the game.
   *
   * @return  whether the game was successfully ended
   */
  public boolean endGame() {
    // TODO
    return checkEnd();
  }


  /**
   * Checks if one of the players in the game is the specified player.
   *
   * @param player  the player to check
   * @return  whether the specified player is a participant of the game
   */
  public Boolean hasPlayer(Player player) {
    return player.equals(redPlayer) || player.equals(whitePlayer);
  }
}
