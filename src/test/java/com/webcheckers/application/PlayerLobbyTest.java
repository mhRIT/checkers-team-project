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
        gameCenter = mock(GameCenter.class);
        CuT = new PlayerLobby(gameCenter);
    }

    /**
     * Test that you can construct a new Player Lobby.
     */
    @Test
    public void test_create_player_lobby() {
        new PlayerLobby(gameCenter);
    }

    /**
     * Test the initial state of Player Lobby.
     */
    @Test
    void init_state(){
        assertEquals(INIT_NUM_PLAYERS, CuT.getNumPlayers());
    }

    /**
     * Test the creation of a new Player aka Player Sign-in.
     */
    @Test
    public void test_signin(){
        final Player player = CuT.signin(VALID_NAME);
        assertNotNull(player);
        assertEquals(player, CuT.getPlayer(VALID_NAME));
        assertEquals(CuT.getNumPlayers(), INIT_NUM_PLAYERS+1);
        assertEquals(CuT.playerNames(VALID_NAME).length, 0);
    }

    /**
     * Test the creation of many new Players.
     */
    @Test
    public void test_many_signin(){
        int rand = (int)(Math.random()*15 + 1);

        for(int i= 0; i < rand; i++){
            final Player player = CuT.signin(VALID_NAME + i);
            assertNotNull(player);
        }
        assertEquals(CuT.getNumPlayers(), INIT_NUM_PLAYERS + rand);
    }

    /**
     * Test that the player's name isn't already taken.
     * Test that the player's name is legal.
     */
    @Test
    public void test_validateName(){
        assertTrue(CuT.validateName(VALID_NAME));
        CuT.signin(VALID_NAME);
        assertFalse(CuT.validateName(VALID_NAME));

        assertFalse(CuT.validateName(INVALID_NAME));
        assertFalse(CuT.validateName(EMPTY_NAME));
    }

}
