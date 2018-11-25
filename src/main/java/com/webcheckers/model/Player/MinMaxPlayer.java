package com.webcheckers.model.Player;

import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Move;
import java.beans.PropertyChangeEvent;

public class MinMaxPlayer extends AiPlayer {

  /**
   * The default constructor for the Player class.
   *
   * @param name the name of this player
   */
  public MinMaxPlayer(String name, int idNum) {
    super(name, idNum);
  }

  public int evaluateBoard(GameContext gameContext) {
    return 0;
  }

  private int minimizeCost(){
    return -1;
  }

  private int maximizeCost(){
    return 1;
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
