package com.webcheckers.application;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Application-tier")
public class GameCenterTest {
  @Test
  public void testCreateGame(){
    Player player1 = new Player("p1");
    Player player2 = new Player("p2");
    final GameCenter CuT = new GameCenter();
    final GameContext game = CuT.createGame(player1, player2);

    // Check that the game is real
    assertNotNull(game);
  }
}
