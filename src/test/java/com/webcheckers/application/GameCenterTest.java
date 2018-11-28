package com.webcheckers.application;

import static com.webcheckers.ui.HtmlRoutes.HtmlRouteTest.TEST_OPP_NAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.webcheckers.model.Board.InitConfig;
import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Player.Player;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the {@link GameCenter} component.
 *
 * @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 * @author <a href='mailto:mvm7902@rit.edu'>Matthew Milone</a>
 */
@Tag("Application-tier")
public class GameCenterTest {

  private Player player1;
  private Player player2;
  private Player player3;
  int playerNonce = 0;
  InitConfig initConfig = new InitConfig("p2");

  private GameCenter CuT;

  @BeforeEach
  public void testSetup(){
    player1 = new Player("p1", playerNonce++);
    player2 = new Player("p2", playerNonce++);
    player3 = new Player("p3", playerNonce++);
    CuT = new GameCenter();
  }

  /**
   * Test that you can construct a new Game Center.
   */
  @Test
  public void testCreateGame() {
    final GameCenter CuT = new GameCenter();
    final GameContext game = CuT.createGame(player1, player2, initConfig);

    assertNotNull(CuT);
    assertNotNull(game);
  }

  /**
   * Tests the retrieval of a game a player is in
   */
  @Test
  public void testGetGames(){
    GameContext game = CuT.createGame(player1, player2, initConfig);
    List<GameContext> games = CuT.getGames(player1);

    assertNotNull(games);
    assertEquals(1, games.size());
    assertEquals(game, games.get(0));

    games = CuT.getGames(player2);

    assertNotNull(games);
    assertEquals(1, games.size());
    assertEquals(game, games.get(0));
  }

  /**
   * Tests the retrieval of a game that does no exist
   */
  @Test
  public void testGetNoGames(){
    GameContext game = CuT.createGame(player1, player2, initConfig);
    final List<GameContext> games = CuT.getGames(player1);
    assertNotNull(games);
    assertEquals(0, games.size());
  }

  /**
   * Tests if a player is in a Game
   */
  @Test
  public void testPlayerInGame(){
    GameContext game = CuT.createGame(player1, player2, initConfig);
    boolean isInGame = CuT.getGame(player1) != null;
    assertTrue(isInGame);

    isInGame = CuT.getGame(player2) != null;
    assertTrue(isInGame);
  }

  /**
   * Tests if a player is not in a Game
   */
  @Test
  public void testIsPlayerNotInGame(){
    GameContext game = CuT.createGame(player1, player2, initConfig);
    boolean isInGame = CuT.getGame(player3) != null;
    assertNotNull(game);
    assertFalse(isInGame);
  }

  /**
   * Tests the resignation of a player from all games they are in
   */
  @Test
  public void testResignAllSuccess(){
    GameContext game = CuT.createGame(player1, player2, initConfig);
    boolean success = CuT.resignAll(player1);
    assertTrue(success);

    success= CuT.resignAll(player3);
    assertFalse(success);
  }

  /**
   * Tests the resignation of a player from all games they are in
   */
  @Test
  public void testResignAllFail(){
    GameContext game = CuT.createGame(player1, player2, initConfig);
    boolean success = CuT.resignAll(player3);
    assertFalse(success);
  }

  /**
   * Tests the resignation of a player when they aren't in a game
   */
  @Test
  public void testResignNone(){
    GameContext game = CuT.createGame(player1, player2, initConfig);
    boolean success = CuT.resign(game, player3);
    assertFalse(success);
  }
}
