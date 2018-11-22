package com.webcheckers.model.GameState;

public class EndTurnState extends GameState {

  @Override
  public boolean execute(GameContext context) {
    context.switchTurn();
    context.setState(new WaitTurnState());
    return true;
  }

  @Override
  public STATE getState() {
    return STATE.END_TURN;
  }
}
