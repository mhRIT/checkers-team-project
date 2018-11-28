package com.webcheckers.model.Player.Heuristic;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Board.Board.COLOR;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

@Tag("Model-tier")
class JumpMoveHeuristicTest extends HeuristicTest {

  @Override
  @BeforeEach
  void setUp() {
    super.setUp();
    cut = new JumpMoveHeuristic();
  }

  @Override
  void testName() {
    String name = cut.getName();
    assertEquals("JumpMoveHeuristic", name);
  }

  @Override
  void testWeight() {
    double weight = cut.getWeight();
    assertEquals(1, weight);
  }

  @Override
  void testCalculateRed() {
    double cost = cut.calculate(board, COLOR.RED);
    assertEquals(0.0, cost);
  }

  @Override
  void testCalculateWhite() {
    double cost = cut.calculate(board, COLOR.WHITE);
    assertEquals(0.0, cost);
  }
}