package com.webcheckers.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webcheckers.model.Player.Player;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


/**
 * The unit test suite for the {@link PlayerLobby} component.
 *
 * @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 */
@Tag("Application-tier")
public class PlayerLobbyTest {

  private static final int INIT_NUM_PLAYERS = 0;
  private static final String VALID_NAME = "vaLid nAme 432";
  private static final String TEST_NAME = "testName";
  private static final String INVALID_NAME = " in _va!id  :)";
  private static final String EMPTY_NAME = "    ";

  private PlayerLobby cut;

  @BeforeEach
  public void testSetup(){
      cut = new PlayerLobby();
  }

  /**
   * Test that you can construct a new Player Lobby.
   */
  @Test
  public void testCreate() {
      new PlayerLobby();
  }

  /**
   * Test the initial state of Player Lobby.
   */
  @Test
  void testInit(){
      assertEquals(INIT_NUM_PLAYERS, cut.getNumPlayers());
  }

  @Test
  void testPlayerNamesEmpty(){
    Player player = cut.signin(VALID_NAME);
    List<String> playerNames = cut.playerNames(player.getName());
    assertTrue(playerNames.isEmpty());
  }

  @Test
  void testPlayerNamesNotEmpty(){
    cut.signin(VALID_NAME);
    List<String> playerNames = cut.playerNames(TEST_NAME);
    assertFalse(playerNames.isEmpty());
  }

  @Test
  void testGetPlayer(){
    Player validPlayer = cut.getPlayer(VALID_NAME);
    assertNull(validPlayer);

    cut.signin(VALID_NAME);
    validPlayer = cut.getPlayer(VALID_NAME);
    assertNotNull(validPlayer);

    validPlayer = cut.getPlayer(TEST_NAME);
    assertNull(validPlayer);
  }

  @Test
  void testAiNames(){
    List<String> aiNameList = cut.aiNames();
    assertFalse(aiNameList.isEmpty());
  }

  @Test
  void testGetAiPlayer(){
    Player easyAi = cut.getPlayer("Easy AI");
    Player medAi = cut.getPlayer("Medium AI");
    Player hardAi = cut.getPlayer("Hard AI");
    Player extAi = cut.getPlayer("Extreme AI");

    assertNotNull(easyAi);
    assertNotNull(medAi);
    assertNotNull(hardAi);
    assertNotNull(extAi);
  }

  /**
   * Test the creation of a new Player aka Player Sign-in.
   */
  @Test
  public void testSignInSuccess(){
      Player player = cut.signin(VALID_NAME);
      assertNotNull(player);
      assertEquals(player, cut.getPlayer(VALID_NAME));
      assertEquals(cut.getNumPlayers(), INIT_NUM_PLAYERS+1);
      assertEquals(cut.playerNames(VALID_NAME).size(), 0);
  }

  /**
   * Test the creation of a new Player aka Player Sign-in.
   */
  @Test
  public void testSignInFail(){
    Player player = cut.signin(INVALID_NAME);
    assertNull(player);
  }

  /**
   * Test the creation of a new Player aka Player Sign-in.
   */
  @Test
  public void testSignOut(){
    cut.signin(VALID_NAME);
    boolean successSignout = cut.signout(VALID_NAME);
    assertTrue(successSignout);

    successSignout = cut.signout(VALID_NAME);
    assertFalse(successSignout);
  }

  /**
   * Test the creation of many new Players.
   */
  @Test
  public void testMultiSigning(){
      int rand = (int)(Math.random()*15 + 1);

      for(int i= 0; i < rand; i++){
          Player player = cut.signin(VALID_NAME + i);
          assertNotNull(player);
      }
      assertEquals(cut.getNumPlayers(), INIT_NUM_PLAYERS + rand);
  }

  @Test
  public void testContainsSuccess(){
    Player player1 = cut.signin(VALID_NAME);
    boolean containsPlayer = cut.containsPlayers(player1);
    assertTrue(containsPlayer);
  }

  @Test
  public void testContainsFail(){
    Player player1 = new Player(VALID_NAME, 0);
    boolean containsPlayer = cut.containsPlayers(player1);
    assertFalse(containsPlayer);
  }

  /**
   * Test that the player's name isn't already taken.
   * Test that the player's name is legal.
   */
  @Test
  public void testValidateName(){
      assertTrue(cut.validateName(VALID_NAME));
      cut.signin(VALID_NAME);
      assertFalse(cut.validateName(VALID_NAME));

      assertFalse(cut.validateName(INVALID_NAME));
      assertFalse(cut.validateName(EMPTY_NAME));
  }
}
