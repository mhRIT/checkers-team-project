package com.webcheckers.model.Player;

import com.webcheckers.model.Board;
import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Move;
import java.beans.PropertyChangeEvent;
import java.util.List;

public class RandomMovementPlayer extends AiPlayer {

  /**
   * The default constructor for the Player class.
   *
   * @param name the name of this player
   */
  public RandomMovementPlayer(String name, int idNum) {
    super(name, idNum);
  }

  @Override
  public Move getNextMove(GameContext game) {
    Board currentBoard = game.getCurrentBoard();

    List<Move> validJumpList = currentBoard.getAllJumpMoves(game.getActiveColor());
    List<Move> validSimpleList = currentBoard.getAllSimpleMoves(game.getActiveColor());

    Move nextMove;
    if(!validJumpList.isEmpty()){
      nextMove = validJumpList.get(0);
    } else {
      nextMove = validSimpleList.get(0);
    }

    return nextMove;
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    GameContext gameSource = (GameContext) evt.getSource();
    Player currentPlayer = gameSource.getActivePlayer();
    if(currentPlayer.equals(this)){
      System.out.printf("Start of my turn: %s\n", this.getName());
      System.out.printf("\tGame: %d\n", gameSource.getId());

      while(!gameSource.isTurnOver()){
        this.putNextMove(gameSource, getNextMove(gameSource));
        System.out.printf("\tMove: %s\n", this.getNextMove(gameSource));
        gameSource.proceed();
      }
      gameSource.proceed();
      System.out.printf("Finished my turn: %s\n", this.getName());

    } else {
      System.out.printf("Not my turn: %s\n", this.getName());
    }
  }
}
