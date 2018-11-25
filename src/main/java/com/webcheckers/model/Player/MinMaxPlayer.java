package com.webcheckers.model.Player;

import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player.Heuristic.Heuristic;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.Map;

public class MinMaxPlayer extends AiPlayer {

  //
  // Attributes
  //
  private Map<Heuristic, Integer> heuristicCostMap;
  private int difficulty = 1;

  /**
   * The default constructor for the Player class.
   *
   * @param name the name of this player
   */
  public MinMaxPlayer(String name, int idNum) {
    super(name, idNum);
    heuristicCostMap = new HashMap<>();
  }

  public void setDifficulty(int difficultyLevel){
    this.difficulty = difficultyLevel;
  }
  public int evaluateBoard(GameContext gameContext) {
    return 0;
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
