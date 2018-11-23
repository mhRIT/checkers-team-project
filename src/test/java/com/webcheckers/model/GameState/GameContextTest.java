package com.webcheckers.model.GameState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the {@link Game} component.
 *
 * @author <a href='mailto:sjk7867@rit.edu'>Simon Kirkwood</a>
 * @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
@Tag("Model-tier")
public class GameContextTest {

  //
  // Constants
  //
  private static final String PLAYER1_NAME = "player1";
  private static final String PLAYER2_NAME = "player2";

  //
  // Attributes
  //
  private Player player1;
  private Player player2;

  //
  // Components under test
  //
  private GameContext cut;

  /**
   * TODO
   */
  @BeforeEach
  public void setup(){
    Player player1 = new Player(PLAYER1_NAME);
    Player player2 = new Player(PLAYER2_NAME);
    cut = new GameContext(player1, player2);
  }

  @Test
  public void testIsOver(){
    assertFalse(cut.isGameOver());
    cut.setState(new GameOverState());
    assertTrue(cut.isGameOver());
  }
}
