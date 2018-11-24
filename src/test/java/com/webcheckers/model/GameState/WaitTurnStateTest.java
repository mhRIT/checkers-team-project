package com.webcheckers.model.GameState;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Board;
import com.webcheckers.model.GameState.GameState.STATE;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WaitTurnStateTest extends GameStateTest {

  @Override
  @BeforeEach
  void setUp() {
    super.setUp();
    cut = new WaitTurnState();
    gameContext.setState(cut);
  }

  @Override
  @Test
  void testExecute(){
    testExecuteSimpleSuccess();
    testExecuteSimpleFailure();

    testExecuteJumpSuccess();
    testExecuteJumpFailure();
  }

  private void testExecuteJumpFailure() {
    Move illegalMove = new Move(new Position(0,2), new Position(2,4));
    player1.addNextMove(gameContext, illegalMove);

    assertEquals(STATE.WAIT_TURN, cut.getState());
    assertFalse(cut.execute(gameContext));

    GameState gameState = gameContext.getState();
    assertEquals(STATE.WAIT_TURN, cut.getState());
    assertEquals(STATE.WAIT_TURN, gameState.getState());
  }

  private void testExecuteJumpSuccess() {
    Move wMove0 = new Move(new Position(1,5), new Position(2,4));
    Move wMove1 = new Move(new Position(2,4), new Position(1,3));

    Move legalMove = new Move(new Position(0,2), new Position(2,4));
    player1.addNextMove(gameContext, legalMove);

    Board currentBoard = gameContext.getCurrentBoard();
    currentBoard.movePiece(wMove0);
    currentBoard.movePiece(wMove1);
    gameContext.addNextBoard(currentBoard);

    assertEquals(STATE.WAIT_TURN, cut.getState());
    assertFalse(cut.execute(gameContext));

    GameState gameState = gameContext.getState();
    assertEquals(STATE.WAIT_TURN, cut.getState());
    assertEquals(STATE.WAIT_TURN, gameState.getState());
  }

  private void testExecuteSimpleFailure() {
    Move illegalMove = new Move(new Position(0,2), new Position(1,4));
    player1.addNextMove(gameContext, illegalMove);

    assertEquals(STATE.WAIT_TURN, cut.getState());
    assertFalse(cut.execute(gameContext));

    GameState gameState = gameContext.getState();
    assertEquals(STATE.WAIT_TURN, cut.getState());
    assertEquals(STATE.WAIT_TURN, gameState.getState());
  }

  private void testExecuteSimpleSuccess() {
    Move playerMove = new Move(new Position(0,2), new Position(1,3));
    player1.addNextMove(gameContext, playerMove);

    assertEquals(STATE.WAIT_TURN, cut.getState());
    assertTrue(cut.execute(gameContext));

    GameState gameState = gameContext.getState();
    assertEquals(STATE.WAIT_TURN, cut.getState());
    assertEquals(STATE.END_TURN, gameState.getState());
  }

  @Override
  @Test
  void testGetState() {
    STATE state = cut.getState();
    assertEquals(STATE.WAIT_TURN, state);
  }
}