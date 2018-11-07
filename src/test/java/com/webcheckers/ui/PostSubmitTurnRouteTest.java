package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.ui.boardView.Message;
import com.webcheckers.ui.boardView.Message.MESSAGE_TYPE;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

public class PostSubmitTurnRouteTest {

    private PostSubmitTurnRoute CuT;
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

      CuT = new PostSubmitTurnRoute(gameCenter);
    }

    @Test
    public void testRoute(){
      when(session.attribute("player")).thenReturn(player);
      when(gameCenter.getGames(player)).thenReturn(games);

      when(game.isLastTurnValid()).thenReturn(true);

      Message info = new Message("true", MESSAGE_TYPE.info);
      Message error = new Message("false",MESSAGE_TYPE.error);

      Message msg = (Message)CuT.handle(request,response);

      assertTrue(msg.equals(info));

      when(game.isLastTurnValid()).thenReturn(false);

      msg = (Message)CuT.handle(request,response);
      assertTrue(msg.equals(error));
    }

}
