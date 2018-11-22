package com.webcheckers.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

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
  private GameCenter CuT;

  @BeforeEach
  public void testSetup(){
    player1 = new Player("p1");
    player2 = new Player("p2");
    CuT = new GameCenter();
  }

  /**
   * Test that you can construct a new Game Center.
   */
  @Test
  public void testCreateGameCenter() {
    new GameCenter();
  }

  /**
   * Test that you can construct a new Game.
   */
  @Test
  public void testCreateGame(){
    final Game game = CuT.createGame(player1, player2);
    // Check that the game is real
    assertNotNull(game);
  }

  /**
   * Tests the retrieval of a game a player is in
   */
  @Test
  public void testGetGames(){
    final Game game = CuT.createGame(player1, player1);
    final Game[] games = CuT.getGames(player1);

    //Check the game is real
    assertNotNull(games);
    //Check there is 1 game
    assertEquals(1, games.length);
    //Check the game is the first in the list of games
    assertEquals(game, games[0]);
  }

  /**
   * Tests if a player is in a Game
   */
  @Test
  public void testIsPlayerInGame(){
    CuT.createGame(player1, player1);
    final boolean isInGame = CuT.isPlayerInGame(player1);

    assertTrue(isInGame);
  }

  /**
   * Tests if a player is not in a Game
   */
  @Test
  public void testIsPlayerNotInGame(){
    final Player player3 = new Player("p3");
    final boolean isInGame = CuT.isPlayerInGame(player3);

    assertFalse(isInGame);
  }

  /**
   * Tests the resignation of a player from all games they are in
   */
  @Test
  public void testResignAll(){
    CuT.createGame(player1, player2);
    int resignCount = CuT.resignAll(player1);
    assertEquals(1, resignCount);
  }

  /**
   * Tests the resignation of a player when they aren't in a game
   */
  @Test
  public void testResignNone(){
    int resignCount = CuT.resignAll(player1);
    assertEquals(0, resignCount);
  }

  /**
   * Tests removing a game from a the list of games in GameCenter
   */
  @Test
  public void testRemoveGame(){
    final Game game = CuT.createGame(player1, player2);
    final boolean removeGame = CuT.removeGame(game);
    assertTrue(removeGame);
  }

  /**
   * Tests removing a game that does not exists
   */
  @Test
  public void testRemoveNoGame(){
    final Game game = new Game(player1, player2);
    final boolean removeGame = CuT.removeGame(game);
    assertFalse(removeGame);
  }
}
