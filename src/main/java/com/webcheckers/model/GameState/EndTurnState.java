package com.webcheckers.model.GameState;

import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import java.util.List;

public class EndTurnState extends GameState {

  @Override
  public boolean execute(GameContext context) {
    context.switchTurn();
    Board currentBoard = context.getCurrentBoard();

    int numPieces = currentBoard.getNumPieces(context.getActiveColor());
    List<Move> jumpMoves = currentBoard.getAllJumpMoves(context.getActiveColor());
    List<Move> simpleMoves = currentBoard.getAllSimpleMoves(context.getActiveColor());

    if(numPieces == 0){
      context.setState(new GameOverState());
    } else if(jumpMoves.isEmpty() && simpleMoves.isEmpty()){
      context.setState(new GameOverState());
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
