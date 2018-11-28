package com.webcheckers.model.Player.Heuristic;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Board.Board.COLOR;
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
  void testName() {
    String name = cut.getName();
    assertEquals("PiecesOnSideHeuristic", name);
  }

  @Override
  void testWeight() {
    double weight = cut.getWeight();
    assertEquals(1, weight);
  }

  @Override
  void testCalculateRed() {
    double cost = cut.calculate(board, COLOR.RED);
    System.out.println(board);
    System.out.printf("%s: %f\n", cut.getName(), cost);
    assertEquals(3, cost);
  }

  @Override
  void testCalculateWhite() {
    double cost = cut.calculate(board, COLOR.WHITE);
    System.out.println(board);
    System.out.printf("%s: %f\n", cut.getName(), cost);
    assertEquals(3, cost);
  }
}