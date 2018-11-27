package com.webcheckers.model.GameState;

import com.sun.org.apache.xml.internal.security.Init;
import com.webcheckers.model.Board.Board;
import com.webcheckers.model.Board.InitConfig;

public class InitState extends GameState {

  @Override
  public boolean execute(GameContext context) {
    Board currentBoard = context.getCurrentBoard();
    InitConfig config = context.getInitConfig();
    currentBoard.init(config);

    context.setState(new WaitTurnState());
    return true;
  }

  @Override
  public STATE getState() {
    return STATE.INIT;
  }
}
