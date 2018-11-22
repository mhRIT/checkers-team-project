package com.webcheckers.model.GameState;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.GameState.GameContext.COLOR;
import com.webcheckers.model.GameState.GameState.STATE;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EndTurnStateTest extends GameStateTest {

  @Override
  @BeforeEach
  void setUp() {
    super.setUp();
    cut = new EndTurnState();
    gameContext.setState(cut);
  }

  @Override
  @Test
  void testExecute() {
    COLOR activeColor = gameContext.getActiveColor();

    assertEquals(STATE.END_TURN, cut.getState());
    assertTrue(cut.execute(gameContext));
    assertNotEquals(activeColor, gameContext.getActiveColor());

    GameState gameState = gameContext.getState();
    assertEquals(STATE.END_TURN, cut.getState());
    assertEquals(STATE.WAIT_TURN, gameState.getState());
  }

  @Override
  @Test
  void testGetState() {
    STATE state = cut.getState();
    assertEquals(STATE.END_TURN, state);
  }
}