package com.webcheckers.model.Player.Heuristic;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Board.COLOR;
import org.junit.jupiter.api.BeforeEach;

class PositionHeuristicTest extends HeuristicTest {

  @Override
  @BeforeEach
  void setUp() {
    super.setUp();
    cut = new PositionHeuristic();
  }

  @Override
  void testCalculateRed() {
    assertEquals(0, cut.calculate(board, COLOR.RED));
  }

  @Override
  void testCalculateWhite() {
    assertEquals(0, cut.calculate(board, COLOR.WHITE));
  }
}