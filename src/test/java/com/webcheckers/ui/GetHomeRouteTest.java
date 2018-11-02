package com.webcheckers.ui;

import static com.webcheckers.ui.GetHomeRoute.ALL_PLAYER_NAMES;
import static com.webcheckers.ui.GetHomeRoute.NUM_PLAYERS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

@Tag("UI-tier")
public class GetHomeRouteTest {

  /**
   * The unit test suite for the {@link GetHomeRoute} component.
   *
   * @author <a href='mailto:sjk7867@rit.edu'>Simon Kirkwood</a>
   */

    private GetHomeRoute CuT;

    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private GameCenter gameCenter;
    private PlayerLobby playerLobby;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
      request = mock(Request.class);
      session = mock(Session.class);
      when(request.session()).thenReturn(session);
      response = mock(Response.class);
      engine = mock(TemplateEngine.class);

      gameCenter = mock(GameCenter.class);
      playerLobby = mock(PlayerLobby.class);

      CuT = new GetHomeRoute(gameCenter,playerLobby,engine);
    }

    /**
     *  Test that you can construct a new Home Route.
     */
    @Test
    public void new_home_route(){
      new GetHomeRoute(gameCenter,playerLobby,engine);
    }

    /**
     *  Test that the model exists in GetHomeRoute
     */
    @Test
    public void test_view_model(){
      final TemplateEngineTester testHelper = new TemplateEngineTester();
      when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

      CuT.handle(request,response);

      testHelper.assertViewModelExists();
      testHelper.assertViewModelIsaMap();
    }

    /**
     *  Test that the model attributes exists in GetHomeRoute
     */
    @Test
    public void test_view_attributes(){
      final TemplateEngineTester testHelper = new TemplateEngineTester();
      when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

      CuT.handle(request,response);
    }

    @Test
    public void test_behavior(){
      final TemplateEngineTester testHelper = new TemplateEngineTester();
      when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

      when(session.attribute("player")).thenReturn(null);
      when(playerLobby.getNumPlayers()).thenReturn(0);
      CuT.handle(request,response);

      testHelper.assertViewModelAttributeIsAbsent(ALL_PLAYER_NAMES);
      testHelper.assertViewModelAttribute(NUM_PLAYERS,0);

      Player mock = mock(Player.class);
      String[] names = new String[0];
      when(session.attribute("player")).thenReturn(mock);
      when(playerLobby.playerNames("")).thenReturn(names);
      when(gameCenter.isPlayerInGame(mock)).thenReturn(false);
      CuT.handle(request,response);

      testHelper.assertViewModelAttributeIsAbsent(NUM_PLAYERS);
      testHelper.assertViewModelAttribute(ALL_PLAYER_NAMES,names);
      assertNotEquals(CuT.handle(request,response),"nothing");

      when(gameCenter.isPlayerInGame(mock)).thenReturn(true);
      assertEquals(CuT.handle(request,response),"nothing");




    }
  }
