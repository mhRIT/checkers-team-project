package com.webcheckers.model.GameState;

public class GameOver extends GameState {

  @Override
  public boolean execute(GameContext context) {
    return false;
  }

  @Override
  public STATE getState() {
    return STATE.GAME_OVER;
  }
}
