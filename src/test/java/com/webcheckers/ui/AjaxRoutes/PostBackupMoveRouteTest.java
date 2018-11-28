package com.webcheckers.ui.AjaxRoutes;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Board.InitConfig;
import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Player.Player;
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
  private PostBackupMoveRoute cut;

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
    playerLobby = new PlayerLobby();
    playerLobby.signin("Test1");
    playerLobby.signin("Test2");
    player1 = playerLobby.getPlayer("Test1");
    player2 = playerLobby.getPlayer("Test2");
    gameCenter = new GameCenter();
    game = gameCenter.createGame(player1,
                                  player2,
                                  new InitConfig(player2.getName()));
    gson = new Gson();

    session = mock(Session.class);
    request = mock(Request.class);
    response = mock(Response.class);

    when(request.session()).thenReturn(session);

    cut = new PostBackupMoveRoute(gameCenter, playerLobby, gson);
  }

  @Test
  public void testRoute(){
    Message info = new Message("Move undone", MESSAGE_TYPE.info);
    Message unableError = new Message("Unable to locate game", MESSAGE_TYPE.error);
    Message nullError = new Message("Unable to locate game", MESSAGE_TYPE.error);

    when(session.attribute("player")).thenReturn("Test1");
    Message msg = (Message) cut.handle(request,response);
    assertEquals(info, msg);

    when(session.attribute("player")).thenReturn("empty");
    msg = (Message) cut.handle(request,response);
    assertEquals(nullError, msg);
  }
}