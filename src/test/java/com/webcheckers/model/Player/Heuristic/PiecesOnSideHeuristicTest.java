package com.webcheckers.model.Player.Heuristic;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Board.COLOR;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

@Tag("Model-tier")
class PiecesOnSideHeuristicTest extends HeuristicTest {

  @Override
  @BeforeEach
  void setUp() {
    super.setUp();
    cut = new PiecesOnSideHeuristic();
  }

  @Override
  void testCalculateRed() {
    assertEquals(3, cut.calculate(board, COLOR.RED));
  }

  @Override
  void testCalculateWhite() {
    assertEquals(3, cut.calculate(board, COLOR.WHITE));
  }
}