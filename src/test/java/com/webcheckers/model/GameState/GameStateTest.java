package com.webcheckers.model.GameState;

import static com.webcheckers.ui.HtmlRoutes.HtmlRouteTest.TEST_OPP_NAME;

import com.webcheckers.model.Board.InitConfig;
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
abstract class GameStateTest {

  //
  // Constants
  //
  private static final String PLAYER1_NAME = "player1";
  private static final String PLAYER2_NAME = "player2";

  //
  // Attributes
  //
  GameContext gameContext;
  Player player1;
  Player player2;
  int gameNonce = 0;
  int playerNonce = 0;

  //
  // Components under test
  //
  GameState cut;

  @BeforeEach
  void setUp() {
    player1 = new Player(PLAYER1_NAME, playerNonce++);
    player2 = new Player(PLAYER2_NAME, playerNonce++);
    InitConfig initConfig = new InitConfig(TEST_OPP_NAME);
    gameContext = new GameContext(player1, player2, initConfig, gameNonce++);
  }

  @Test
  abstract void testExecute();

  @Test
  abstract void testGetState();
}