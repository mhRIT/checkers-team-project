package com.webcheckers.model.GameState;

public abstract class GameState {

  public enum STATE {
    INIT,
    WAIT_TURN,
    END_TURN,
    GAME_OVER
  }

  private String message;

  public void setMessage(String stateMessage){
    message = stateMessage;
  }

  public String getMessage(){
    return message;
  }

  public abstract boolean execute(GameContext context);
  public abstract STATE getState();
}
