package com.webcheckers.model.Player;

import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.GameState.GameState.STATE;
import com.webcheckers.model.Move;
import java.beans.PropertyChangeEvent;

public abstract class AiPlayer extends Player {

  /**
   * The default constructor for the Player class.
   *
   * @param name the name of this player
   */
  AiPlayer(String name, int idNum) {
    super(name, idNum);
  }

  public abstract Move getNextMove(GameContext gameContext);

  /**
   * Returns if this player is an AI program.
   *
   * @return true
   */
  @Override
  public boolean isAi(){
    return true;
  }

  /**
   *
   * @param evt
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    GameContext gameSource = (GameContext) evt.getSource();
    Player currentPlayer = gameSource.getActivePlayer();
    String propName = evt.getPropertyName();

    if(currentPlayer.equals(this) && propName.equals(STATE.WAIT_TURN.toString()) && !gameSource.isGameOver()){
//      System.out.printf("Start of my turn: %s\n", this.getName());
//      System.out.printf("\tGame: %d\n", gameSource.getId());

      while(!gameSource.isTurnOver()){
        this.putNextMove(gameSource, getNextMove(gameSource));
//        System.out.printf("\tMove: %s\n", this.getNextMove(gameSource));
        gameSource.proceed();
      }
      gameSource.proceed();
//      System.out.printf("Finished my turn: %s\n", this.getName());

    } else {
//      System.out.printf("Not my turn: %s\n", this.getName());
    }
  }
}
