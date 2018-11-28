package com.webcheckers.model.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.webcheckers.model.Board.Board.COLOR;
import com.webcheckers.model.Board.Move;
import com.webcheckers.model.Board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the {@link Player} component.
 *
 * @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
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
  void testMiniMax3(){
    cut = new MinMaxPlayer(PLAYER2_NAME, PLAYER2_NONCE, 3);
    Move bestMove = new Move(new Position(1,5), new Position(0,4));
    Move playerMove = ((MinMaxPlayer) cut).miniMax(board, COLOR.WHITE);
    assertEquals(bestMove, playerMove);
  }

  @Test
  void testMiniMax0(){
    cut = new MinMaxPlayer(PLAYER2_NAME, PLAYER2_NONCE, 0);
    Move bestMove = new Move(new Position(1,5), new Position(0,4));
    Move playerMove = ((MinMaxPlayer) cut).miniMax(board, COLOR.WHITE);
    assertEquals(bestMove, playerMove);
  }

  @Test
  void testEvaluation(){
    double cost = ((MinMaxPlayer) cut).evaluateBoard(board, COLOR.WHITE);
    assertEquals(10, cost);
  }

  @Test
  void testMinCost(){
    double cost = ((MinMaxPlayer) cut).minCostMove(board, COLOR.WHITE, 0);
    assertEquals(10, cost);
  }

  @Test
  void testMaxCost(){
    double cost = ((MinMaxPlayer) cut).maxCostMove(board, COLOR.WHITE, 0);
    assertEquals(10, cost);
  }

  @Override
  @Test
  void testGetNextMove(){
    Move nextMove = cut.getNextMove(game);
    assertNotNull(nextMove);
  }
}