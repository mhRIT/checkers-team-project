package com.webcheckers.model.GameState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webcheckers.model.GameState.GameState.STATE;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InitStateTest extends GameStateTest {

  @Override
  @BeforeEach
  void setUp() {
    super.setUp();
    cut = new InitState();
    gameContext.setState(cut);
  }

  @Override
  @Test
  void testExecute() {
    assertEquals(STATE.INIT, cut.getState());
    assertTrue(cut.execute(gameContext));
    GameState gameState = gameContext.getState();
    assertEquals(STATE.INIT, cut.getState());
    assertEquals(STATE.WAIT_TURN, gameState.getState());
  }

  @Override
  @Test
  void testGetState() {
    STATE state = cut.getState();
    assertEquals(STATE.INIT, state);
  }
}