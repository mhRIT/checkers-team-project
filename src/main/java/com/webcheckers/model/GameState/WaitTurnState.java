package com.webcheckers.model.GameState;

import com.webcheckers.model.Board;
import com.webcheckers.model.Board.COLOR;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player.Player;
import com.webcheckers.model.Position;
import java.util.List;

public class WaitTurnState extends GameState {

  @Override
  public boolean execute(GameContext context) {
    boolean toReturn;
    Player currentPlayer = context.getActivePlayer();
    Move playerMove = currentPlayer.getNextMove(context);
    Board currentBoard = context.getCurrentBoard();

//    Position startPos = playerMove.getStart();
    Position endPos = playerMove.getEnd();
//    Position midPos = new Position(
//        (startPos.getCell() + endPos.getCell()) / 2,
//        (startPos.getRow() + endPos.getRow()) / 2);

    List<Move> validJumpList = currentBoard.getAllJumpMoves(context.getActiveColor());
    List<Move> validSimpleList = currentBoard.getAllSimpleMoves(context.getActiveColor());

    if(!validJumpList.isEmpty()){
      toReturn = validJumpList.contains(playerMove);
    } else {
      toReturn = validSimpleList.contains(playerMove);
    }

    if(toReturn){
      Board nextBoard = (Board) currentBoard.clone();
      context.addNextBoard(nextBoard);
      nextBoard.makeMove(playerMove);

      validJumpList = nextBoard.getPieceJumpMoves(endPos.getCell(), endPos.getRow());
      if(validJumpList.isEmpty()){
        context.setState(new EndTurnState());
      } else {
        if(!playerMove.isJump()){
          context.setState(new EndTurnState());
        } else {
          context.setState(new WaitTurnState());
        }
      }

      if(context.getActiveColor().equals(COLOR.RED) && endPos.getRow() == Board.BOARD_SIZE - 1){
        nextBoard.promotePiece(endPos.getCell(), endPos.getRow());
      } else if(context.getActiveColor().equals(COLOR.WHITE) && endPos.getRow() == 0) {
        nextBoard.promotePiece(endPos.getCell(), endPos.getRow());
      }
    } else {
      context.setState(new WaitTurnState());
    }

    return toReturn;
  }

  @Override
  public STATE getState() {
    return STATE.WAIT_TURN;
  }
}
