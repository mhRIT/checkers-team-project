package com.webcheckers.model.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
  static final String TEST_NAME = "testName";
  private static final String NOT_TEST_NAME = "notTestName";

  //
  // Attributes
  //
  int playerNonce = 0;

  //
  // Components under test
  //
  private Player cut;

  /**
   * Setup the state of the instance for each test.
   */
  @BeforeEach
  void setUp() {
    cut = new Player(TEST_NAME, playerNonce);
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
    assertEquals(TEST_NAME, cut.getName());
  }

  /**
   * Tests that two players are considered equal iff they share the same name.
   */
  @Test
  void testEquals() {
    assertEquals(new Player(TEST_NAME, playerNonce), cut);
    assertNotEquals(NOT_TEST_NAME, cut);
  }

  /**
   * Tests that the names of two players return the same hashcode iff they share the same name.
   */
  @Test
  void testHashCode() {
    assertEquals(new Player(TEST_NAME, playerNonce).hashCode(), cut.hashCode());
    assertNotEquals(NOT_TEST_NAME.hashCode(), cut.hashCode());
  }
}