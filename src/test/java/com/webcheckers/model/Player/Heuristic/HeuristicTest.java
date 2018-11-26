package com.webcheckers.model.Player.Heuristic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webcheckers.model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for a {@link Heuristic} component.
 *
 * @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
@Tag("Model-tier")
abstract class HeuristicTest {

  //
  // Attributes
  //
  Board board;

  //
  // Components under test
  //
  Heuristic cut;

  @BeforeEach
  void setUp() {
    board = new Board();
    board.initStart();
  }

  @Test
  void testName(){
    assertEquals(cut.getClass().getName(), cut.getName());
  }

  @Test
  void testWeight(){
    assertEquals(1, cut.getWeight());
  }

  @Test
  void testCalculate(){
    testCalculateRed();
    testCalculateWhite();
  }

  abstract void testCalculateRed();
  abstract void testCalculateWhite();
}
