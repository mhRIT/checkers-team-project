package com.webcheckers.model.Player.Heuristic;

import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Player.Player;

public class DefenseHeuristic extends Heuristic {

  public DefenseHeuristic() {
    super();
  }

  @Override
  public int calculate(GameContext game, Player player) {
    return 0;
  }
}
