package com.webcheckers.ui.AjaxRoutes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Board.InitConfig;
import com.webcheckers.model.Board.Move;
import com.webcheckers.model.Board.Position;
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
public class PostSubmitTurnRouteTest {

  private GameCenter gameCenter;
  private PlayerLobby playerLobby;
  private Gson gson;
  private GameContext game;
  int gameNonce = 0;
  private Player player1;
  private Player player2;
  private InitConfig initConfig;
  int playerNonce = 0;
  private Request request;
  private Response response;
  private Session session;

  private PostSubmitTurnRoute cut;

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

    cut = new PostSubmitTurnRoute(gameCenter, playerLobby, gson);
  }

  @Test
  public void testRoute(){
    Message info = new Message("true", MESSAGE_TYPE.info);
    Message unableError = new Message("Your turn is not yet complete", MESSAGE_TYPE.error);

    when(session.attribute("player")).thenReturn("Test1");
    Message msg = (Message) cut.handle(request,response);
    assertEquals(unableError, msg);

//    when(session.attribute("player")).thenReturn("empty");
//    msg = (Message) cut.handle(request,response);
//    assertEquals(unableError, msg);
  }
}
