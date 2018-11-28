package com.webcheckers.model.GameState;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.GameState.GameState.STATE;
import com.webcheckers.model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the {@link Player} component.
 *
 * @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
@Tag("Model-tier")
class GameOverStateTest extends GameStateTest {

  @Override
  @BeforeEach
  void setUp() {
    super.setUp();
    cut = new GameOverState();
    gameContext.setState(cut);
  }

  @Override
  @Test
  void testExecute() {
    assertEquals(STATE.GAME_OVER, cut.getState());
    assertFalse(cut.execute(gameContext));

    GameState gameState = gameContext.getState();
    assertEquals(STATE.GAME_OVER, cut.getState());
    assertEquals(STATE.GAME_OVER, gameState.getState());
  }

  @Override
  @Test
  void testGetState() {
    STATE state = cut.getState();
    assertEquals(STATE.GAME_OVER, state);
  }
}