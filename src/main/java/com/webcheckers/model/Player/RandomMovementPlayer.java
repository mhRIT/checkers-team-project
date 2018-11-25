package com.webcheckers.model.Player;

import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Move;
import java.beans.PropertyChangeEvent;

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
    return null;
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    System.out.printf("Property changed event raised: %s\n", this.getName());
  }
}
