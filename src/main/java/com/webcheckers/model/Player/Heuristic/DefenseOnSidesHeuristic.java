package com.webcheckers.model.Player.Heuristic;

import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Player.Player;

public class DefenseOnSidesHeuristic extends Heuristic {

  public DefenseOnSidesHeuristic() {
    super();
  }

  @Override
  public int calculate(GameContext game, Player player) {
    return 0;
  }
}
