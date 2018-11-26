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
      while(!gameSource.isTurnOver()){
        this.putNextMove(gameSource, getNextMove(gameSource));
        gameSource.proceed();
      }
      gameSource.proceed();
    }
  }
}
