package com.webcheckers.model.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Board.Move;
import com.webcheckers.model.Board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the {@link Player} component.
 *
 * @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
@Tag("Model-tier")
class PlayerTest {
  //
  // Constants
  //
  static final String PLAYER1_NAME = "testName";
  static final String PLAYER2_NAME = "testName";
  private static final String NOT_TEST_NAME = "notTestName";
  int PLAYER1_NONCE = 0;
  int PLAYER2_NONCE = 1;
  int GAME_NONCE = 0;

  //
  // Attributes
  //
  GameContext game;

  //
  // Components under test
  //
  private Player cut;

  /**
   * Setup the state of the instance for each test.
   */
  @BeforeEach
  void setUp() {
    cut = new Player(PLAYER1_NAME, PLAYER1_NONCE);
    game = new GameContext(cut, new Player(PLAYER2_NAME, PLAYER2_NONCE), GAME_NONCE);
  }

  /**
   * Tests the case where a player is created with a name.
   */
  @Test
  void testNotNull() {
    assertNotNull(cut);
  }

  /**
   * Tests the case where a player is created with no name.
   */
  @Test
  void testNullName() {
    assertThrows(NullPointerException.class, () -> new Player(null, 0));
  }

  /**
   * Tests if the current Player is an AI.
   */
  @Test
  void testIsAi() {
    assertFalse(cut.isAi());
  }

  /**
   * Tests the the name of the player is the one specified at construction.
   */
  @Test
  void testGetName() {
    assertEquals(PLAYER1_NAME, cut.getName());
  }

  @Test
  void testPutNextMove(){
    Move testMove = new Move(new Position(0,0), new Position(1,1));
    cut.putNextMove(game, testMove);
    Move nextMove = cut.getNextMove(game);
    assertNotNull(nextMove);
    assertEquals(testMove, nextMove);
  }

  @Test
  void testGetNextMove(){
    Move nextMove = cut.getNextMove(game);
    assertNull(nextMove);
  }

  /**
   * Tests that two players are considered equal iff they share the same name.
   */
  @Test
  void testEquals() {
    boolean playersEqual = cut.equals(new Player(PLAYER1_NAME, PLAYER1_NONCE));
    assertTrue(playersEqual);

    playersEqual = cut.equals(new Player(PLAYER2_NAME, PLAYER2_NONCE));
    assertFalse(playersEqual);

    playersEqual = cut.equals(new Object());
    assertFalse(playersEqual);
  }

  /**
   * Tests that the names of two players return the same hashcode iff they share the same name.
   */
  @Test
  void testHashCode() {
    assertEquals(new Player(PLAYER1_NAME, PLAYER1_NONCE).hashCode(), cut.hashCode());
    assertNotEquals(NOT_TEST_NAME.hashCode(), cut.hashCode());
  }
}