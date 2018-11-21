package com.webcheckers.model.GameState;

public class WaitTurnState extends GameState {

  @Override
  public boolean execute(GameContext context) {
    return false;
  }

  @Override
  public STATE getState() {
    return STATE.WAIT_TURN;
  }
}
