package com.webcheckers.ui.HtmlRoutes;

import static com.webcheckers.ui.HtmlRoutes.GetHomeRoute.ALL_PLAYER_NAMES;
import static com.webcheckers.ui.HtmlRoutes.GetHomeRoute.NUM_PLAYERS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Player;
import com.webcheckers.ui.HtmlRoutes.GetHomeRoute;
import com.webcheckers.ui.RouteTest;
import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.ModelAndView;

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
      Player p1 = mock;
      Player p2 = mock(Player.class);
      GameCenter gameCenter = mock(GameCenter.class);
      String[] names = new String[0];
      when(session.attribute("player")).thenReturn(mock);

      CuT.handle(request,response);
    }
  }
