package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.GameState.GameContext;
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
public class PostSubmitTurnRouteTest {

    private PostSubmitTurnRoute CuT;
    private GameCenter gameCenter;
    private GameContext game;
    private Player player1;
    private Player player2;
    private Request request;
    private Response response;
    private Session session;

    @BeforeEach
    public void setup(){
      gameCenter = new GameCenter();
      player1 = new Player("Test1");
      player2 = new Player("Test2");
      game = gameCenter.createGame(player1, player2);

      session = mock(Session.class);
      request = mock(Request.class);
      response = mock(Response.class);

      when(request.session()).thenReturn(session);

      CuT = new PostSubmitTurnRoute(gameCenter);
    }

    @Test
    public void testRoute(){
      when(session.attribute("player")).thenReturn(player1);

      Message info = new Message("true", MESSAGE_TYPE.info);
      Message error = new Message("false",MESSAGE_TYPE.error);

      Message msg = (Message)CuT.handle(request,response);

      assertTrue(msg.equals(info));

      msg = (Message)CuT.handle(request,response);
      assertTrue(msg.equals(error));
    }

}
