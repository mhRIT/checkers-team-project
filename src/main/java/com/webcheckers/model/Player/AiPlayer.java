package com.webcheckers.model.Player;

import com.webcheckers.model.GameState.GameContext;
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
  public abstract void propertyChange(PropertyChangeEvent evt);
}
