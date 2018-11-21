package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the {@link Player} component.
 *
 * @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
@Tag(""
    + "Model-tier")
class PlayerTest {

  //
  // Constants
  //
  private static final String TEST_NAME = "testName";
  private static final String NOT_TEST_NAME = "notTestName";

  //
  // Components under test
  //
  private Player cut;

  /**
   * Setup the state of the instance for each test.
   */
  @BeforeEach
  void setUp() {
    cut = new Player(TEST_NAME);
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
    assertThrows(NullPointerException.class, () -> new Player(null));
  }

  /**
   * Tests the the name of the player is the one specified at construction.
   */
  @Test
  void testGetName() {
    assertEquals(TEST_NAME, cut.getName());
    assertNotEquals(TEST_NAME, NOT_TEST_NAME);
  }

  /**
   * Tests that two players are considered equal iff they share the same name.
   */
  @Test
  void testEquals() {
    assertEquals(cut, new Player(TEST_NAME));
    assertNotEquals(cut, NOT_TEST_NAME);
  }

  /**
   * Tests that the names of two players return the same hashcode iff they share the same name.
   */
  @Test
  void testHashCode() {
    assertEquals(cut.hashCode(), new Player(TEST_NAME).hashCode());
    assertNotEquals(cut.hashCode(), NOT_TEST_NAME.hashCode());
  }
}