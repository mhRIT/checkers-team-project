package com.webcheckers.model.Player;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.webcheckers.model.Board.COLOR;
import com.webcheckers.model.Move;
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
  }

  @Test
  void testMinCost(){
    try {
      Move minMove = ((MinMaxPlayer) cut).minCostMove(board, COLOR.WHITE, COLOR.RED, 0);
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testMaxCost(){
    try {
      Move maxMove = ((MinMaxPlayer) cut).maxCostMove(board, COLOR.WHITE, COLOR.RED, 0);
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
  }

  @Override
  @Test
  void testGetNextMove(){
    Move nextMove = cut.getNextMove(game);
    assertNotNull(nextMove);
  }
}