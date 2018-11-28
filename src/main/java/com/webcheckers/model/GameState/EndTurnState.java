package com.webcheckers.model.GameState;

import com.webcheckers.model.Board.Board;
import com.webcheckers.model.Board.Move;
import com.webcheckers.model.Player.Player;
import java.util.List;

public class EndTurnState extends GameState {

  @Override
  public boolean execute(GameContext context) {
    Board currentBoard = context.getCurrentBoard();

    int numPieces = currentBoard.getNumPieces(context.getNonActiveColor());
    List<Move> jumpMoves = currentBoard.getAllJumpMoves(context.getNonActiveColor());
    List<Move> simpleMoves = currentBoard.getAllSimpleMoves(context.getNonActiveColor());
    Player nonActivePlayer = context.getNonActivePlayer();

    if(numPieces == 0){
      GameState nextState = new GameOverState();
      nextState.setMessage(String.format("\'%s\' has no more pieces.", nonActivePlayer.getName()));
      context.setState(nextState);
    } else if(jumpMoves.isEmpty() && simpleMoves.isEmpty()){
      GameState nextState = new GameOverState();
      nextState.setMessage(String.format("\'%s\' is unable to move.", nonActivePlayer.getName()));
      context.setState(nextState);
    } else {
      context.setState(new WaitTurnState());
    }
    context.switchTurn();
    return true;
  }

  @Override
  public STATE getState() {
    return STATE.END_TURN;
  }
}
