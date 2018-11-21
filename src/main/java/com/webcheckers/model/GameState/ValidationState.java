package com.webcheckers.model.GameState;

public class ValidationState extends GameState {

  @Override
  public boolean execute(GameContext context) {
    return false;
  }

  @Override
  public STATE getState() {
    return STATE.VALIDATION;
  }
}
