package com.webcheckers.model.GameState;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the {@link Player} component.
 *
 * @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
@Tag("Model-tier")
abstract class GameStateTest {

  //
  // Constants
  //
  private static final String PLAYER1_NAME = "player1";
  private static final String PLAYER2_NAME = "player2";

  //
  // Attributes
  //
  private GameContext gameContext;
  private Player player1;
  private Player player2;

  //
  // Components under test
  //
  private GameState cut;

  @BeforeEach
  void setUp() {
    Player player1 = new Player(PLAYER1_NAME);
    Player player2 = new Player(PLAYER2_NAME);
    gameContext = new GameContext(player1, player2);

    cut = new GameOver();
  }

  @Test
  abstract void testExecute();

  @Test
  abstract void testGetState();
}