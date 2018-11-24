package com.webcheckers.model.GameState;

import com.webcheckers.model.Board;
import com.webcheckers.model.Board.COLOR;
import com.webcheckers.model.GameState.GameState.STATE;
import com.webcheckers.model.Player;
import java.util.Stack;

public class GameContext {

  private GameState gameState;

  //
  // Attributes
  //
  private Player whitePlayer;
  private Player redPlayer;
  private COLOR activeColor;
  private Stack<Board> boardStack;
  private int id;

  public GameContext(Player rPlayer, Player wPlayer, int idNum) {
    this.redPlayer = rPlayer;
    this.whitePlayer = wPlayer;
    id = idNum;

    this.boardStack = new Stack<>();
    activeColor = COLOR.RED;

    Board board = new Board();
    boardStack.push(board);

    gameState = new InitState();
    gameState.execute(this);
  }

  public boolean proceed(){
    return gameState.execute(this);
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
    Board toReturn = null;
    if(!boardStack.empty()){
      toReturn = boardStack.peek();
    }
    return toReturn;
  }

  public void addNextBoard(Board board) {
    boardStack.push(board);
  }

  public boolean isTurnOver(){
    STATE state = gameState.getState();
    return state.equals(STATE.END_TURN);
  }

  public boolean isGameOver(){
    STATE state = gameState.getState();
    return state.equals(STATE.GAME_OVER);
  }

  /**
   * Toggles the player who turn it currently is.
   *
   */
  public void switchTurn(){
    if(isTurnOver()) {
      if (activeColor.equals(COLOR.RED)) {
        activeColor = COLOR.WHITE;
      } else {
        activeColor = COLOR.RED;
      }
      gameState = new WaitTurnState();
    }
  }

  /**
   * TODO
   */
  public boolean revert() {
    if(!boardStack.empty()){
      boardStack.pop();
      gameState = new WaitTurnState();
      return true;
    }
    return false;
  }

  /**
   * Resigns a player from the game
   *
   * @param player the player who resigned
   * @return true if player resigned, otherwise false
   */
  public boolean resignPlayer(Player player){
    if(player.equals(redPlayer)){
      activeColor = COLOR.RED;
    } else if(player.equals(whitePlayer)) {
      activeColor = COLOR.WHITE;
    } else {
      return false;
    }
    gameState = new GameOverState();
    gameState.setMessage(String.format("Player \'%s\' resigned.", player.getName()));
    return true;
  }

  public String endMessage(){
    String toReturn = "Game is not over.";
    if(isGameOver()){
      toReturn = String.format("Game is over. \'%s\' lost.\n %s",
          getActivePlayer().getName(),
          gameState.getMessage());
    }
    return toReturn;
  }

  public int getId(){
    return id;
  }

  /**
   * Builds a user-friendly string representation
   * for this game.
   *
   * @return  the string representation for this game
   */
  @Override
  public String toString(){
    return String.format("%d | Red player: %s | White player: %s",
        id,
        redPlayer.getName(),
        whitePlayer.getName());
  }

  /**
   * Compares if other object is a GameContext and represents the
   * same game being played between two players.
   *
   * @return true if the two compared games are the same game
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (! (obj instanceof GameContext)) return false;
    final GameContext that = (GameContext) obj;
    return this.id == that.getId();
  }

  /**
   * Generates a hashCode for the game, based on the string
   * representation for this game.
   *
   * @return  the hashCode
   */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
