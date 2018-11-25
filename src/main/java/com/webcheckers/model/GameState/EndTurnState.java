package com.webcheckers.model.GameState;

import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player.Player;
import java.util.List;

public class EndTurnState extends GameState {

  @Override
  public boolean execute(GameContext context) {
    context.switchTurn();
    Board currentBoard = context.getCurrentBoard();

    int numPieces = currentBoard.getNumPieces(context.getActiveColor());
    List<Move> jumpMoves = currentBoard.getAllJumpMoves(context.getActiveColor());
    List<Move> simpleMoves = currentBoard.getAllSimpleMoves(context.getActiveColor());
    Player activePlayer = context.getActivePlayer();

    if(numPieces == 0){
      GameState nextState = new GameOverState();
      nextState.setMessage(String.format("Player \'%s\' has no more pieces.", activePlayer.getName()));
      context.setState(nextState);
    } else if(jumpMoves.isEmpty() && simpleMoves.isEmpty()){
      GameState nextState = new GameOverState();
      nextState.setMessage(String.format("Player \'%s\' is unable to move.", activePlayer.getName()));
      context.setState(nextState);
    } else {
      context.setState(new WaitTurnState());
    }
    return true;
  }

  @Override
  public STATE getState() {
    return STATE.END_TURN;
  }
}
