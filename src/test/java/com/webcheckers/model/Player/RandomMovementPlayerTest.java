package com.webcheckers.model.Player;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.webcheckers.model.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RandomMovementPlayerTest extends AiPlayerTest {
  //
  // Attributes
  //

  @Override
  @BeforeEach
  void setUp(){
    super.setUp();
    cut = new RandomMovementPlayer(PLAYER2_NAME, PLAYER2_NONCE);
  }

  @Override
  @Test
  void testGetNextMove(){
    Move nextMove = cut.getNextMove(game);
    assertNotNull(nextMove);
  }
}