package com.webcheckers.Model;

import static com.webcheckers.model.Game.COLOR.*;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

import static org.mockito.Mockito.mock;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class GameTest {

  private Game CuT;
  private Player wPlayer;
  private Player rPlayer;

  @BeforeEach
  public void setup(){
    wPlayer = mock(Player.class);
    rPlayer = mock(Player.class);
    CuT = new Game(rPlayer,wPlayer);
  }

  @Test
  public void testSwitchTurn(){
    CuT.switchTurn();
    assertSame(CuT.getActiveColor(), WHITE);
    CuT.switchTurn();
    assertSame(CuT.getActiveColor(), RED);
    CuT.makeMove(0,1,0,1);
    assertSame(CuT.getActiveColor(),WHITE);
    CuT.makeMove(1,2,1,2);
    assertSame(CuT.getActiveColor(),RED);
  }

  @Test
  public void testPlayers(){
    assertTrue(CuT.hasPlayer(wPlayer));
    assertTrue(CuT.hasPlayer(rPlayer));

    assertSame(CuT.getWhitePlayer(),wPlayer);
    assertSame(CuT.getRedPlayer(),rPlayer);
  }
}
