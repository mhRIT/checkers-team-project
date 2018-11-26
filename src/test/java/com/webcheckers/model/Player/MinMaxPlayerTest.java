package com.webcheckers.model.Player;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import com.webcheckers.model.Board;
import com.webcheckers.model.Board.COLOR;
import com.webcheckers.model.GameState.GameOverState;
import com.webcheckers.model.GameState.GameState.STATE;
import com.webcheckers.model.GameState.WaitTurnState;
import com.webcheckers.model.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
class MinMaxPlayerTest extends AiPlayerTest{
  //
  // Attributes
  //
  int diffLevel = 0;
  Board board;

  @Override
  @BeforeEach
  void setUp(){
    super.setUp();
    cut = new MinMaxPlayer(TEST_NAME, playerNonce, diffLevel);
    board = new Board();
    board.initStart();

    when(evt.getPropertyName()).thenReturn(STATE.WAIT_TURN.toString());
  }

  @Override
  void testCurrentPlayer() {
  }

  @Override
  void testNotCurrentPlayer() {
  }

  @Override
  void testWaitTurn() {
    when(evt.getPropertyName()).thenReturn(STATE.WAIT_TURN.toString());
  }

  @Override
  void testNotWaitTurn() {
    when(evt.getPropertyName()).thenReturn(STATE.END_TURN.toString());
  }

  @Override
  void testGameOver() {
    gameSource.setState(new GameOverState());
  }

  @Override
  void testGameNotOver() {
    gameSource.setState(new WaitTurnState());
  }

  @Test
  void testEvaluation(){
    double cost = ((MinMaxPlayer) cut).evaluateBoard(board, COLOR.WHITE);
  }

  @Test
  void testMinCost(){
    try {
      Move minMove = ((MinMaxPlayer) cut).minCostMove(board, COLOR.WHITE, COLOR.RED, 0);
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testMaxCost(){
    try {
      Move maxMove = ((MinMaxPlayer) cut).maxCostMove(board, COLOR.WHITE, COLOR.RED, 0);
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
  }
}