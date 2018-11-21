package com.webcheckers.model.GameState;

import com.webcheckers.model.Board;
import com.webcheckers.model.GameState.GameState.STATE;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import java.util.Stack;

public class GameContext {

  private GameState gameState;

  //
  // Enums
  //
  public enum COLOR {RED, WHITE}

  //
  // Attributes
  //
  private Player whitePlayer;
  private Player redPlayer;
  private COLOR activeColor;
  private Stack<Board> boardStack;

  private Stack<Move> moveStack;

  public GameContext(Player rPlayer, Player wPlayer) {
    this.redPlayer = rPlayer;
    this.whitePlayer = wPlayer;

    this.boardStack = new Stack<>();
    activeColor = COLOR.RED;

    Board board = new Board();
    boardStack.push(board);
    moveStack = new Stack<>();

    gameState = new InitState();
    gameState.execute(this);
  }

  public boolean proceed(){
    gameState.execute(this);
    return true;
  }

  GameState getState(){
    return gameState;
  }

  void setState(GameState state){
    gameState = state;
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

  public Player getActivePlayer(){
    Player activePlayer = redPlayer;
    if(activeColor.equals(COLOR.WHITE)){
      activePlayer = whitePlayer;
    }
    return activePlayer;
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
   *
   * TODO update
   *
   * Retrieves the current board.
   *
   * @return  the current board
   */
  public Board getCurrentBoard() {
    return boardStack.peek();
  }

  public boolean isTurnOver(){
    STATE state = gameState.getState();
    return state.equals(STATE.END_TURN);
  }

  public boolean isGameOver(){
    STATE state = gameState.getState();
    return state.equals(STATE.GAME_OVER);
  }
}
