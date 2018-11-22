package com.webcheckers.model.GameState;

import static org.junit.jupiter.api.Assertions.*;

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
  void testExecute() {
    Move playerMove = new Move(new Position(0,2), new Position(1,4));
    player1.addNextMove(gameContext, playerMove);

    assertEquals(STATE.WAIT_TURN, cut.getState());
    assertFalse(cut.execute(gameContext));

    GameState gameState = gameContext.getState();
    assertEquals(STATE.WAIT_TURN, cut.getState());
    assertEquals(STATE.WAIT_TURN, gameState.getState());
  }

  @Override
  @Test
  void testGetState() {
    STATE state = cut.getState();
    assertEquals(STATE.WAIT_TURN, state);
  }
}