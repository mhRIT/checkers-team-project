package com.webcheckers.ui;

import static com.webcheckers.ui.GetHomeRoute.ALL_PLAYER_NAMES;
import static com.webcheckers.ui.GetHomeRoute.NUM_PLAYERS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
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
      Game mockGame = mock(Game.class);
      Game[] games = new Game[1];
      games[0] = mockGame;
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

      when(gameCenter.isPlayerInGame(mock)).thenReturn(true);
//      when(gameCenter.getGames(mock)).thenReturn(games);
      when(mockGame.checkEnd()).thenReturn(true);

      CuT.handle(request,response);

      testHelper.assertViewModelAttribute("message",mockGame.endMessage());
      assertFalse(GetHomeRoute.outOfGame.contains(p1) && GetHomeRoute.outOfGame.contains(p2));

      GetHomeRoute.outOfGame.add(mock);
      CuT.handle(request,response);
      assertFalse(GetHomeRoute.outOfGame.contains(p1) && GetHomeRoute.outOfGame.contains(p2));

      GetHomeRoute.outOfGame.add(p2);
      CuT.handle(request,response);
      assertTrue(GetHomeRoute.outOfGame.contains(p1) && GetHomeRoute.outOfGame.contains(p2));
    }
  }
