package com.webcheckers.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.webcheckers.model.Player;


/**
 * The unit test suite for the {@link PlayerLobby} component.
 *
 * @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 */
@Tag("Application-tier")
public class PlayerLobbyTest {

    private static final int INIT_NUM_PLAYERS = 0;
    private static final String VALID_NAME = "vaLid nAme 432";
    private static final String INVALID_NAME = " in _va!id  :)";
    private static final String EMPTY_NAME = "    ";

    private GameCenter gameCenter;
    private PlayerLobby CuT;

    @BeforeEach
    public void testSetup(){
        gameCenter = new GameCenter();
        CuT = new PlayerLobby(gameCenter);
    }

    /**
     * Test that you can construct a new Player Lobby.
     */
    @Test
    public void testCreate() {
        new PlayerLobby(gameCenter);
    }

    /**
     * Test the initial state of Player Lobby.
     */
    @Test
    void testInit(){
        assertEquals(INIT_NUM_PLAYERS, CuT.getNumPlayers());
    }

    /**
     * Test the creation of a new Player aka Player Sign-in.
     */
    @Test
    public void testSignInSuccess(){
        Player player = CuT.signin(VALID_NAME);
        assertNotNull(player);
        assertEquals(player, CuT.getPlayer(VALID_NAME));
        assertEquals(CuT.getNumPlayers(), INIT_NUM_PLAYERS+1);
        assertEquals(CuT.playerNames(VALID_NAME).length, 0);
    }

  /**
   * Test the creation of a new Player aka Player Sign-in.
   */
  @Test
  public void testSignInFail(){
    Player player = CuT.signin(INVALID_NAME);
    assertNull(player);
  }

  /**
   * Test the creation of a new Player aka Player Sign-in.
   */
  @Test
  public void testSignOut(){
    CuT.signin(VALID_NAME);
    boolean successSignout = CuT.signout(VALID_NAME);
    assertTrue(successSignout);

    successSignout = CuT.signout(VALID_NAME);
    assertFalse(successSignout);
  }

    /**
     * Test the creation of many new Players.
     */
    @Test
    public void testMultiSigning(){
        int rand = (int)(Math.random()*15 + 1);

        for(int i= 0; i < rand; i++){
            Player player = CuT.signin(VALID_NAME + i);
            assertNotNull(player);
        }
        assertEquals(CuT.getNumPlayers(), INIT_NUM_PLAYERS + rand);
    }

    @Test
    public void testContainsSuccess(){
      Player player1 = CuT.signin(VALID_NAME);
      boolean containsPlayer = CuT.containsPlayers(player1);
      assertTrue(containsPlayer);
    }

  @Test
  public void testContainsFail(){
    Player player1 = new Player(VALID_NAME);
    boolean containsPlayer = CuT.containsPlayers(player1);
    assertFalse(containsPlayer);
  }

    /**
     * Test that the player's name isn't already taken.
     * Test that the player's name is legal.
     */
    @Test
    public void testValidateName(){
        assertTrue(CuT.validateName(VALID_NAME));
        CuT.signin(VALID_NAME);
        assertFalse(CuT.validateName(VALID_NAME));

        assertFalse(CuT.validateName(INVALID_NAME));
        assertFalse(CuT.validateName(EMPTY_NAME));
    }
}
