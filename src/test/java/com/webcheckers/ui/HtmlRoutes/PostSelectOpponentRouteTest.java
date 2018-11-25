package com.webcheckers.ui.HtmlRoutes;

import static com.webcheckers.model.Board.COLOR.RED;
import static com.webcheckers.ui.HtmlRoutes.GetHomeRoute.PLAYER;
import static com.webcheckers.ui.HtmlRoutes.PostSelectOpponentRoute.MESSAGE;
import static com.webcheckers.ui.HtmlRoutes.PostSelectOpponentRoute.OPP_PLAYER_NAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Player.Player;
import com.webcheckers.ui.HtmlRoutes.PostSelectOpponentRoute.VIEW_MODE;
import com.webcheckers.ui.RouteTest;
import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.ModelAndView;

/**
 * The unit test suite for the {@link PostSelectOpponentRouteTest} component.
 *
 * @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
@Tag("UI-tier")
class PostSelectOpponentRouteTest extends RouteTest {

  /**
   * Setup new mock objects for each test.
   */
  @BeforeEach
  public void setup() {
    super.setUp();
    // create a unique CuT for each test
    CuT = new PostSelectOpponentRoute(gameCenter, playerLobby, engine);
  }

  /**
   *  Test the presence of the ModelView used for rendering the HTML when no player
   *  is stored in the current session.
   */
  @Test
  public void testViewModel() throws Exception {
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    Player testPlayer = playerLobby.signin(TEST_PLAYER_NAME);
    when(session.attribute("player")).thenReturn(testPlayer);

    Player testOppPlayer = playerLobby.signin(TEST_OPP_NAME);
    when(request.queryParams(OPP_PLAYER_NAME)).thenReturn(TEST_OPP_NAME);

    GameContext game = gameCenter.createGame(testPlayer, testOppPlayer);

    CuT.handle(request,response);

    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();

    testPlayer = playerLobby.signin(TEST_PLAYER_NAME);
    when(session.attribute("player")).thenReturn(testPlayer);

    CuT.handle(request,response);

    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();
  }

  /**
   *  Test the attribute values in the ModelView map for correct values when a player is
   *  stored in the current session, signed in, but is not in a game.
   */
  @Test
  public void testInGame() throws Exception {
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    Player testPlayer = playerLobby.signin(TEST_PLAYER_NAME);
    when(session.attribute("player")).thenReturn(testPlayer);

    Player testOppPlayer = playerLobby.signin(TEST_OPP_NAME);
    when(request.queryParams(OPP_PLAYER_NAME)).thenReturn(TEST_OPP_NAME);

    GameContext game = gameCenter.createGame(testPlayer, testOppPlayer);

    String message = String.format("The selected opponent, %s, is already in a game", TEST_OPP_NAME);

    CuT.handle(request,response);

    testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
    testHelper.assertViewModelAttribute(PLAYER, testPlayer);
    testHelper.assertViewModelAttribute(MESSAGE, message);
    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();
    testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
  }

  /**
   *  Test the attribute values in the ModelView map for correct values when a player is
   *  stored in the current session, signed in, and is in a game.
   */
  @Test
  public void testNotInGame() throws Exception {
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    Player testPlayer = playerLobby.signin(TEST_PLAYER_NAME);
    when(session.attribute("player")).thenReturn(testPlayer);

    Player testOppPlayer = playerLobby.signin(TEST_OPP_NAME);
    when(request.queryParams(OPP_PLAYER_NAME)).thenReturn(TEST_OPP_NAME);

    CuT.handle(request,response);

    testHelper.assertViewModelAttribute(GetGameRoute.TITLE_ATTR, GetGameRoute.TITLE);
    testHelper.assertViewModelAttribute("currentPlayer", testPlayer);
    testHelper.assertViewModelAttribute("viewMode", VIEW_MODE.PLAY);
    testHelper.assertViewModelAttribute("redPlayer", testPlayer);
    testHelper.assertViewModelAttribute("whitePlayer", testOppPlayer);
    testHelper.assertViewModelAttribute("activeColor", RED);
    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();
    testHelper.assertViewName(GetGameRoute.VIEW_NAME);
  }
}