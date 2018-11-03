package com.webcheckers.ui;

import static com.webcheckers.model.Game.COLOR.RED;
import static com.webcheckers.ui.GetHomeRoute.ALL_PLAYER_NAMES;
import static com.webcheckers.ui.GetHomeRoute.PLAYER;
import static com.webcheckers.ui.PostSelectOpponentRoute.MESSAGE;
import static com.webcheckers.ui.PostSelectOpponentRoute.OPP_PLAYER_NAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.ui.PostSelectOpponentRoute.VIEW_MODE;
import com.webcheckers.ui.boardView.BoardView;
import com.webcheckers.ui.boardView.Message;
import com.webcheckers.ui.boardView.Message.MESSAGE_TYPE;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

/**
 * The unit test suite for the {@link PostSelectOpponentRouteTest} component.
 *
 * @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
@Tag("UI-tier")
class PostSelectOpponentRouteTest {

  //
  // Constants
  //

  private static final String TEST_PLAYER_NAME = "testName";
  private static final String TEST_OPP_NAME = "oppName";

  /**
   * The component-under-test (CuT).
   */
  private PostSelectOpponentRoute CuT;

  //
  // Required HTTP objects
  //

  private Request request;
  private Session session;
  private Response response;

  //
  // Application specific friendlies
  //

  private PlayerLobby playerLobby;
  private GameCenter gameCenter;
  private TemplateEngine engine;

  /**
   * Setup new mock objects for each test.
   */
  @BeforeEach
  public void setup() {
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    response = mock(Response.class);

    gameCenter = new GameCenter();
    playerLobby = new PlayerLobby(gameCenter);
    engine = mock(TemplateEngine.class);

    // create a unique CuT for each test
    CuT = new PostSelectOpponentRoute(gameCenter, playerLobby, engine);
  }

  /**
   *  Test the construction of the SelectOpponent Route.
   */
  @Test
  public void testRouteCreation() {
    assertNotNull(CuT);
  }

  /**
   *  Test the presence of the ModelView used for rendering the HTML when no player
   *  is stored in the current session.
   */
  @Test
  public void testViewModel() {
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    CuT.handle(request,response);
    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();

    Player testPlayer = playerLobby.signin(TEST_PLAYER_NAME);
    when(session.attribute("player")).thenReturn(testPlayer);
    CuT.handle(request,response);
    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();
  }

  /**
   *  Test the attribute values in the ModelView map for correct values when a player is not
   *  stored in the current session.
   */
  @Test
  public void testNoSessionPlayer() {
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    CuT.handle(request,response);
    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();
    testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
    testHelper.assertViewModelAttribute(GetHomeRoute.NUM_PLAYERS, playerLobby.getNumPlayers());
    testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
  }

  /**
   *  Test the attribute values in the ModelView map for correct values when a player is
   *  stored in the current session and is not signed in.
   */
  @Test
  public void testNotSignedIn() {
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    when(session.attribute("player")).thenReturn(new Player(TEST_PLAYER_NAME));
    CuT.handle(request,response);
    testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
    testHelper.assertViewModelAttribute(GetHomeRoute.NUM_PLAYERS, playerLobby.getNumPlayers());
    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();
    testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
  }

  /**
   *  Test the attribute values in the ModelView map for correct values when a player is
   *  stored in the current session, signed in, but is not in a game.
   */
  @Test
  public void testInGame() {
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    Player testPlayer = playerLobby.signin(TEST_PLAYER_NAME);
    when(session.attribute("player")).thenReturn(testPlayer);

    Player testOppPlayer = playerLobby.signin(TEST_OPP_NAME);
    when(request.queryParams(OPP_PLAYER_NAME)).thenReturn(TEST_OPP_NAME);

    Game game = new Game(testPlayer, testOppPlayer);
    gameCenter.addGame(game);

    String[] testPlayerNames = playerLobby.playerNames(TEST_PLAYER_NAME);

    String message = String.format("The selected opponent, %s, is already in a game", TEST_OPP_NAME);

    CuT.handle(request,response);
    testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
//    testHelper.assertViewModelAttribute(ALL_PLAYER_NAMES, testPlayerNames);
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
  public void testNotInGame() {
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    Player testPlayer = playerLobby.signin(TEST_PLAYER_NAME);
    when(session.attribute("player")).thenReturn(testPlayer);

    Player testOppPlayer = playerLobby.signin(TEST_OPP_NAME);
    when(request.queryParams(OPP_PLAYER_NAME)).thenReturn(TEST_OPP_NAME);

//    Game game = new Game(testPlayer, testOppPlayer);
//    gameCenter.addGame(game);

    CuT.handle(request,response);
    testHelper.assertViewModelAttribute(GetGameRoute.TITLE_ATTR, GetGameRoute.TITLE);
    testHelper.assertViewModelAttribute("currentPlayer", testPlayer);
    testHelper.assertViewModelAttribute("viewMode", VIEW_MODE.PLAY);
    testHelper.assertViewModelAttribute("redPlayer", testPlayer);
    testHelper.assertViewModelAttribute("whitePlayer", testOppPlayer);
    testHelper.assertViewModelAttribute("activeColor", RED);
//    testHelper.assertViewModelAttribute("board", new BoardView(game.getState(testPlayer)));
//    testHelper.assertViewModelAttribute("message", new Message("This is a triumph", MESSAGE_TYPE.info));
    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();
    testHelper.assertViewName(GetGameRoute.VIEW_NAME);
  }
}