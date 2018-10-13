package com.webcheckers.model;

import com.webcheckers.model.Board.SPACE_TYPE;

public class Game {

  public enum COLOR {RED, WHITE}

  private Player whitePlayer = null;
  private Player redPlayer = null;
  private COLOR activeColor;
  private Board board;

  public Game(Player rPlayer, Player wPlayer) {    this.redPlayer = rPlayer;
    this.whitePlayer = wPlayer;

    this.board = new Board();
    activeColor = COLOR.RED;
    board.initStart();
  }

  public Player getRedPlayer() {
    return redPlayer;
  }

  public Player getWhitePlayer() {
    return whitePlayer;
  }

  public COLOR getActiveColor() {
    return activeColor;
  }

  public boolean validateMove(){
    return true;
  }

  public boolean makeMove() {
    board.movePiece();
    switchTurn();
    return true;
  }

  public boolean switchTurn(){
    if (activeColor.equals(COLOR.RED)) {
      activeColor = COLOR.WHITE;
    } else {
      activeColor = COLOR.RED;
    }
    return true;
  }

  public SPACE_TYPE[][] getState(Player player) {
    if(player.equals(whitePlayer)){
      return board.getBoardTranspose();
    }
    return board.getState();
  }

  public boolean endGame() {
    return board.checkEnd();
  }

  public Boolean hasPlayer(Player player) {
    return player.equals(redPlayer) || player.equals(whitePlayer);
  }
}
