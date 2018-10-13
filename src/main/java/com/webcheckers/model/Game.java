package com.webcheckers.model;

import static com.webcheckers.model.ModelPiece.COLOR.RED;
import static com.webcheckers.model.ModelPiece.COLOR.WHITE;

import com.webcheckers.ui.boardView.BoardView;
import com.webcheckers.model.ModelPiece.COLOR;

public class Game {
  private Player whitePlayer = null;
  private Player redPlayer = null;
  private COLOR activeColor;
  private Board board;

  public Game(Player rPlayer, Player wPlayer) {    this.redPlayer = rPlayer;
    this.whitePlayer = wPlayer;

    this.board = new Board();
    activeColor = RED;
    board.initStart();
//    board.initMid(5, 5);
  }

  public Player getRedPlayer() {
    return redPlayer;
  }

  public Player getWhitePlayer() {
    return whitePlayer;
  }

  public ModelPiece.COLOR getActiveColor() {
    return activeColor;
  }

  public boolean validateMove(){
    return board.validateMove();
  }

  public boolean makeMove() {
    return true;
  }

  public boolean switchTurn(){
    if (activeColor.equals(RED)) {
      activeColor = WHITE;
    } else {
      activeColor = RED;
    }
    return true;
  }

  public BoardView getState(Player player) {
    BoardView bView = new BoardView(board.getState());
    if(player.equals(whitePlayer)){
      bView.transpose();
    }
    return bView;
  }

  public boolean endGame() {
    return board.checkEnd();
  }

  public Boolean hasPlayer(Player player) {
    return player.equals(redPlayer) || player.equals(whitePlayer);
  }
}
