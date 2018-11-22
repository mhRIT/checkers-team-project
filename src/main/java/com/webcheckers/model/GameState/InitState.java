package com.webcheckers.model.GameState;

import com.webcheckers.model.Board;

public class InitState extends GameState {

  @Override
  public boolean execute(GameContext context) {
    Board currentBoard = context.getCurrentBoard();
    currentBoard.initStart();

    context.setState(new WaitTurnState());
    return true;
  }

  @Override
  public STATE getState() {
    return STATE.INIT;
  }
}
