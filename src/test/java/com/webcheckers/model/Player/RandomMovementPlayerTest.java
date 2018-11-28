package com.webcheckers.model.Player;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.webcheckers.model.Board.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the {@link Player} component.
 *
 * @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
@Tag("Model-tier")
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