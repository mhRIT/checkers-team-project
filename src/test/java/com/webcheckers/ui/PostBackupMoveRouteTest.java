package com.webcheckers.ui;

import static com.webcheckers.model.Game.COLOR.RED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.ui.boardView.Message;
import com.webcheckers.ui.boardView.Message.MESSAGE_TYPE;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

@Tag("UI-tier")
public class PostBackupMoveRouteTest {
  private PostBackupMoveRoute CuT;
  private GameCenter gameCenter;
  private Game game;
  private Game[] games;
  private Player player;
  private Request request;
  private Response response;
  private Session session;

  @BeforeEach
  public void setup(){
    session = mock(Session.class);
    gameCenter = mock(GameCenter.class);
    game = mock(Game.class);
    games = new Game[1];
    games[0] = game;
    player = mock(Player.class);
    request = mock(Request.class);
    response = mock(Response.class);
    when(request.session()).thenReturn(session);
    CuT = new PostBackupMoveRoute(gameCenter);
  }
  @Test
  public void testRoute(){
    when(session.attribute("player")).thenReturn(player);
    when(gameCenter.getGames(player)).thenReturn(games);

    when(game.getActiveColor()).thenReturn(RED);
    when(game.getRedPlayer()).thenReturn(null);
    when(game.hasPlayer(player)).thenReturn(false);

    Message msg = (Message)CuT.handle(request,response);
    Message info = new Message("Move undone",MESSAGE_TYPE.info);
    Message error = new Message("Could not undo move",MESSAGE_TYPE.error);

    assertTrue(msg.equals(error));

    when(game.hasPlayer(player)).thenReturn(true);
    when(game.getRedPlayer()).thenReturn(player);

    msg = (Message)CuT.handle(request,response);
    assertTrue(msg.equals(info));
  }
}