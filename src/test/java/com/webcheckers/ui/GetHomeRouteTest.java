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

/**
 * The unit test suite for the {@link GetHomeRoute} component.
 *
 * @author <a href='mailto:sjk7867@rit.edu'>Simon Kirkwood</a>
 */
@Tag("UI-tier")
public class GetHomeRouteTest extends RouteTest {

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
      super.setUp();
      CuT = new GetHomeRoute(gameCenter,playerLobby,engine);
    }

    @Test
    public void testBehavior () throws Exception {
      final TemplateEngineTester testHelper = new TemplateEngineTester();
      when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

      when(session.attribute("player")).thenReturn(null);
//      when(playerLobby.getNumPlayers()).thenReturn(0);
      CuT.handle(request,response);

      testHelper.assertViewModelAttributeIsAbsent(ALL_PLAYER_NAMES);
      testHelper.assertViewModelAttribute(NUM_PLAYERS,0);

      Player mock = mock(Player.class);
      String[] names = new String[0];
      when(session.attribute("player")).thenReturn(mock);
//      when(playerLobby.playerNames("")).thenReturn(names);
//      when(gameCenter.isPlayerInGame(mock)).thenReturn(false);
//      CuT.handle(request,response);

//      testHelper.assertViewModelAttributeIsAbsent(NUM_PLAYERS);
//      testHelper.assertViewModelAttribute(ALL_PLAYER_NAMES,names);
//      assertNotEquals("nothing", CuT.handle(request,response));
//
//      when(gameCenter.isPlayerInGame(mock)).thenReturn(true);
//      assertEquals("nothing", CuT.handle(request,response));
    }
  }
