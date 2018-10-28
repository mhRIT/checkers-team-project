package com.webcheckers.model;

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
   * Validates the specified move.
   *
   * @param x0  the starting x position of the move
   * @param x1  the ending x position of the move
   * @param y0  the starting y position of the move
   * @param y1  the ending y position of the move
   * @return    whether the specified move is valid
   */
  public boolean validateMove(int x0, int x1, int y0, int y1){
    return true;
  }

  /**
   * Applies the specified move to the current board state
   *
   * @param x0  the starting x position of the move
   * @param x1  the ending x position of the move
   * @param y0  the starting y position of the move
   * @param y1  the ending y position of the move
   * @return    whether the specified move was successful
   */
  public boolean makeMove(int x0, int x1, int y0, int y1) {
    board.movePiece(x0, x1, y0, y1);
    switchTurn();
    return true;
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
   * @param player  the player to use for perspective of the board.
   * @return  the current board state
   */
  public Board getBoardState() {
    return board;
  }

  /**
   * Ends the game.
   *
   * @return  whether the game was successfully ended
   */
  public boolean endGame() {
    // TODO
    return board.checkEnd();
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
  public Boolean hasPlayer(Player player) {
    return player.equals(redPlayer) || player.equals(whitePlayer);
  }
}
