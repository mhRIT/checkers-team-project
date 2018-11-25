package com.webcheckers.ui.boardView;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Player.Player;
import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("UI-tier")
class BoardViewTest {

  //
  // Constants
  //
  private static final String TEST_RED_NAME = "testName";
  private static final String TEST_WHITE_NAME = "testName";
  private static final int NUM_ROWS = 8;

  //
  // Components under test
  //
  private BoardView redCut;
  private BoardView whiteCut;

  private GameContext game;
  private Player whitePlayer;
  private Player redPlayer;
  int gameNonce = 0;
  int playerNonce = 0;

  /**
   * Setup new mock objects for each test.
   */
  @BeforeEach
  void setUp() {
    redPlayer = new Player(TEST_RED_NAME, playerNonce++);
    whitePlayer = new Player(TEST_WHITE_NAME, playerNonce++);
    game = new GameContext(redPlayer, whitePlayer, gameNonce++);

    redCut = new BoardView(game, redPlayer);
    whiteCut = new BoardView(game, whitePlayer);
  }

  @Test
  void testCreation(){
    assertNotNull(redCut);
    assertNotNull(whiteCut);
  }

  @Test
  void testRedIterator(){
    Iterator testIter = redCut.iterator();
    int elementCount = 0;
    while(testIter.hasNext()){
      testIter.next();
      elementCount++;
    }

    assertEquals(NUM_ROWS, elementCount);
  }

  @Test
  void testWhiteIterator() {
    Iterator testIter = whiteCut.iterator();
    int elementCount = 0;
    while(testIter.hasNext()){
      testIter.next();
      elementCount++;
    }

    assertEquals(NUM_ROWS, elementCount);
  }
}