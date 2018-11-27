package com.webcheckers.model.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.Board.Board;
import com.webcheckers.model.Board.Board.COLOR;
import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.GameState.GameState.STATE;
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
  Board board;
  PropertyChangeEvent evt;

  //
  // Components under test
  //
  AiPlayer cut;

  @BeforeEach
  void setUp() {
    super.setUp();
    rPlayer = new Player(PLAYER1_NAME, PLAYER1_NONCE);
    board = new Board();
    board.initStart();

    game = mock(GameContext.class);
    when(game.isTurnOver()).thenReturn(true);
    when(game.getActiveColor()).thenReturn(COLOR.WHITE);
    when(game.getActiveColor()).thenReturn(COLOR.RED);
    when(game.getCurrentBoard()).thenReturn(board);
    when(game.proceed()).thenReturn(true);

    evt = mock(PropertyChangeEvent.class);
    when(evt.getSource()).thenReturn(game);
    when(evt.getPropertyName()).thenReturn(STATE.WAIT_TURN.toString());
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
    assertEquals(PLAYER1_NAME, cut.getName());
  }

  /**
   * Tests that two players are considered equal iff they share the same name.
   */
  @Override
  @Test
  void testEquals() {
    assertEquals(new Player(PLAYER2_NAME, PLAYER2_NONCE), cut);
  }

  /**
   * Tests that the names of two players return the same hashcode iff they share the same name.
   */
  @Override
  @Test
  void testHashCode() {
    assertEquals(new Player(PLAYER2_NAME, PLAYER2_NONCE).hashCode(), cut.hashCode());
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

  void testCurrentPlayer() {
    when(game.getActivePlayer()).thenReturn(cut);
    cut.propertyChange(evt);
  }

  void testNotCurrentPlayer() {
    when(game.getActivePlayer()).thenReturn(rPlayer);
    cut.propertyChange(evt);
  }

  void testWaitTurn() {
    cut.propertyChange(evt);
  }

  void testNotWaitTurn() {
    cut.propertyChange(evt);
  }

  void testGameOver() {
    cut.propertyChange(evt);
  }

  void testGameNotOver() {
    cut.propertyChange(evt);
  }
}
