package com.webcheckers.model.Player;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.webcheckers.model.Board.Board.COLOR;
import com.webcheckers.model.Board.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
class MinMaxPlayerTest extends AiPlayerTest{
  //
  // Attributes
  //
  int diffLevel = 0;

  @Override
  @BeforeEach
  void setUp(){
    super.setUp();
    cut = new MinMaxPlayer(PLAYER2_NAME, PLAYER2_NONCE, diffLevel);
  }

  @Test
  void testEvaluation(){
    double cost = ((MinMaxPlayer) cut).evaluateBoard(board, COLOR.WHITE);
    System.out.println(board);
    System.out.println(cost);
  }

  @Test
  void testMinCost(){
    Move minMove = ((MinMaxPlayer) cut).minCostMove(board, COLOR.WHITE, 0);
  }

  @Test
  void testMaxCost(){
    Move maxMove = ((MinMaxPlayer) cut).maxCostMove(board, COLOR.WHITE, 0);
  }

  @Override
  @Test
  void testGetNextMove(){
    Move nextMove = cut.getNextMove(game);
    assertNotNull(nextMove);
  }
}