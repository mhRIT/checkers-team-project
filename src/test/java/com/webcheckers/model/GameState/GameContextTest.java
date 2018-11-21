package com.webcheckers.model.GameState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.webcheckers.model.Game;
import com.webcheckers.model.GameState.GameContext;
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

  private GameContext CuT;
  private Player wPlayer;
  private Player rPlayer;

  /**
   * TODO
   */
  @BeforeEach
  public void setup(){
    wPlayer = mock(Player.class);
    rPlayer = mock(Player.class);
    CuT = new GameContext(rPlayer,wPlayer);
  }

  @Test
  public void testIsOver(){
    assertFalse(CuT.isGameOver());
  }
}
