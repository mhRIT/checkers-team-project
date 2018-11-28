package com.webcheckers.model.Board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webcheckers.model.Board.InitConfig.PRE_SET_BOARD;
import com.webcheckers.model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the {@link Board} component.
 *
 * @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
@Tag("Model-tier")
class InitConfigTest {
  //
  // Constants
  //
  public static String OPP_PLAYER_NAME = "opponentName";

  //
  // Attributes
  //
  Player oppPlayer;
  int playerNone = 0;

  //
  // Components under test
  //
  private InitConfig cut;

  @BeforeEach
  void setUp(){
    oppPlayer = new Player("opponentName", playerNone);
    cut = new InitConfig(oppPlayer.getName());
  }

  /**
   *
   */
  @Test
  void testConstructors(){
    testEmptyConstructor();
    testOppNameConstructor();
    testPresetConstructor();
    testRandomConstructor();
  }

  /**
   *
   */
  private void testEmptyConstructor(){
    cut = new InitConfig();
    assertNotNull(cut);
  }

  /**
   *
   */
  private void testOppNameConstructor(){
    cut = new InitConfig(oppPlayer.getName());
    assertNotNull(cut);
    assertEquals(OPP_PLAYER_NAME, cut.getOpponent());
  }

  /**
   *
   */
  private void testRandomConstructor(){
    int numRed = 5;
    int numWhite = 6;
    cut = new InitConfig(oppPlayer.getName(), numRed, numWhite);
    assertNotNull(cut);
    assertEquals(OPP_PLAYER_NAME, cut.getOpponent());
    assertEquals(numRed, cut.getNumRedPieces());
    assertEquals(numWhite, cut.getNumWhitePieces());
  }

  /**
   *
   */
  private void testPresetConstructor(){
    String preset = "start";
    cut = new InitConfig(oppPlayer.getName(), preset);
    assertNotNull(cut);
    assertEquals(OPP_PLAYER_NAME, cut.getOpponent());
    assertEquals(preset, cut.getPreset());
    assertEquals(PRE_SET_BOARD.START, cut.getPreSetBoard());
  }

  @Test
  void testToString(){
    String correctString = "opponentName | Red pieces: 0, white pieces: 0, pre-set: null";
    String cutStr = cut.toString();
    assertEquals(correctString, cutStr);
  }

  @Test
  void testEquals(){
    InitConfig testConfig = new InitConfig(OPP_PLAYER_NAME);
    boolean posEqual = cut.equals(testConfig);
    assertTrue(posEqual);

    posEqual = cut.equals(new Object());
    assertFalse(posEqual);
//
    posEqual = cut.equals(cut);
    assertTrue(posEqual);
  }

  @Test
  void testHash(){
//    cut = new Position(0,1);
//    Position pos2 = new Position(0, 1);
//    Position pos3 = new Position(0, 2);
//
//    int hash0 = cut.hashCode();
//    int hash2 = pos2.hashCode();
//    int hash3 = pos3.hashCode();
//    assertEquals(hash0, hash2);
//    assertNotEquals(hash0, hash3);
  }
}
