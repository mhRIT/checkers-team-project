package com.webcheckers.model.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.GameState.GameContext;
import java.beans.PropertyChangeEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public abstract class AiPlayerTest extends PlayerTest {
  //
  // Attributes
  //
  Player rPlayer;
  GameContext gameSource;
  PropertyChangeEvent evt;

  //
  // Components under test
  //
  AiPlayer cut;

  @BeforeEach
  void setUp() {
    rPlayer = new Player("redPlayer", playerNonce);
    gameSource = new GameContext(rPlayer, cut, playerNonce);

    evt = mock(PropertyChangeEvent.class);
    when(evt.getSource()).thenReturn(gameSource);
  }

  /**
   * Tests the case where a player is created with a name.
   */
  @Test
  void testNotNull() {
    assertNotNull(cut);
  }

  /**
   * Tests if the current Player is an AI.
   */
  @Override
  @Test
  void testIsAi() {
    assertTrue(cut.isAi());
  }

  /**
   * Tests the the name of the player is the one specified at construction.
   */
  @Override
  @Test
  void testGetName() {
    assertEquals(TEST_NAME, cut.getName());
  }

  /**
   * Tests that two players are considered equal iff they share the same name.
   */
  @Override
  @Test
  void testEquals() {
    assertEquals(new Player(TEST_NAME, playerNonce), cut);
  }

  /**
   * Tests that the names of two players return the same hashcode iff they share the same name.
   */
  @Override
  @Test
  void testHashCode() {
    assertEquals(new Player(TEST_NAME, playerNonce).hashCode(), cut.hashCode());
  }

  @Test
  void testPropertyChanged(){
    testCurrentPlayer();
    testNotCurrentPlayer();
    testWaitTurn();
    testNotWaitTurn();
    testGameOver();
    testGameNotOver();
  }

  abstract void testCurrentPlayer();
  abstract void testNotCurrentPlayer();
  abstract void testWaitTurn();
  abstract void testNotWaitTurn();
  abstract void testGameOver();
  abstract void testGameNotOver();
}
