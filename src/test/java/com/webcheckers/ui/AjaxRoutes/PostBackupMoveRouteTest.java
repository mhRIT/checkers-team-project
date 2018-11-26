package com.webcheckers.ui.AjaxRoutes;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Player;
import com.webcheckers.ui.AjaxRoutes.PostBackupMoveRoute;
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
  private PlayerLobby playerLobby;
  private Gson gson;
  private GameContext game;
  private Player player1;
  private Player player2;
  int playerNonce = 0;
  private Request request;
  private Response response;
  private Session session;

  @BeforeEach
  public void setup(){
    gameCenter = new GameCenter();
    playerLobby = new PlayerLobby(gameCenter);
    gson = new Gson();
    player1 = new Player("Test1", playerNonce++);
    player2 = new Player("Test2", playerNonce++);
    game = gameCenter.createGame(player1, player2);

    session = mock(Session.class);
    request = mock(Request.class);
    response = mock(Response.class);

    when(request.session()).thenReturn(session);

    CuT = new PostBackupMoveRoute(gameCenter, playerLobby, gson);
  }
  @Test
  public void testRoute(){
    when(session.attribute("player")).thenReturn(player1);

//    when(game.getActiveColor()).thenReturn(RED);
//    when(game.getRedPlayer()).thenReturn(null);
//    when(game.hasPlayer(player)).thenReturn(false);

    Message msg = (Message)CuT.handle(request,response);
    Message info = new Message("Move undone",MESSAGE_TYPE.info);
    Message error = new Message("Could not undo move",MESSAGE_TYPE.error);

    assertTrue(msg.equals(error));

//    when(game.hasPlayer(player)).thenReturn(true);
//    when(game.getRedPlayer()).thenReturn(player);

    msg = (Message)CuT.handle(request,response);
    assertTrue(msg.equals(info));
  }
}