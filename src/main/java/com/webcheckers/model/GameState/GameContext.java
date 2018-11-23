package com.webcheckers.model.GameState;

import com.webcheckers.model.Board;
import com.webcheckers.model.Board.COLOR;
import com.webcheckers.model.GameState.GameState.STATE;
import com.webcheckers.model.Player;
import java.util.Stack;

public class GameContext {

  private GameState gameState;

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
  private String[] endInfo = new String[2];

  public GameContext(Player rPlayer, Player wPlayer) {
    this.redPlayer = rPlayer;
    this.whitePlayer = wPlayer;

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
    return boardStack.peek();
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
    //checking that game has not already ended
    if(endState != EndState.NOT_OVER){
      return false;
    }
    Player opponent = getOpponent(player);
    //checking if player has an opponent, thus in a game
    if(opponent == null){
      return false;
    }

    winner = opponent;
    endState = EndState.RESIGNATION;

    //if the player is the active player make the opponent the active player
    if(getActivePlayer().equals(player)){
      switchTurn();
    }
    endGame();
    return true;
  }

  /**
   * Checks if a player has resigned
   *
   * @param player the player who has resigned
   * @returned true, if the player has resigned, false otherwise
   */
  public boolean hasResigned(Player player){
    if(endState == EndState.RESIGNATION && winner.equals(getOpponent(player))){
      return true;
    }
    return false;
  }

  public String endMessage(){
    return String.format("Game is over. \'%s\' is the winner. They won because %s",this.endInfo[0],this.endInfo[1]);
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

  /**
   * Gets a player's opponent
   *
   * @param player the specified player
   * @return the player's opponent
   */
  public Player getOpponent(Player player){
    if(player.equals(whitePlayer)){
      return redPlayer;
    }
    else if(player.equals(redPlayer)){
      return whitePlayer;
    }
    else
      return null;
  }

  /**
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
    Board currentBoard = boardStack.peek();

    if(endState != EndState.NOT_OVER){
      return true;
    }
    if(currentBoard.getNumPieces(COLOR.RED) ==  0){
      this.winner = getWhitePlayer();
      this.endState = EndState.ALL_PIECES;
      return true;
    }
    if(currentBoard.getNumPieces(COLOR.WHITE) == 0){
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
}
