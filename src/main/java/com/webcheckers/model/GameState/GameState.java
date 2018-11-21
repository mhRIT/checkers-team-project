package com.webcheckers.model.GameState;

public abstract class GameState {

  public enum STATE {
    INIT,
    VALIDATION,
    WAIT_TURN,
    END_TURN,
    GAME_OVER
  }

  public abstract boolean execute(GameContext context);
  public abstract STATE getState();
}
