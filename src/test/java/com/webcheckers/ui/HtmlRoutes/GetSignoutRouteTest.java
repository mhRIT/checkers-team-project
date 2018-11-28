package com.webcheckers.ui.HtmlRoutes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Board.InitConfig;
import com.webcheckers.model.Player.Player;
import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import spark.*;

/**
 * The unit test suite for the {@link GetSignoutRoute} component.
 *
 * @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
@Tag("UI-tier")
class GetSignoutRouteTest {

  /**
   * The component-under-test (cut).
   */
  private GetSignoutRoute cut;

  //
  // Constants
  //
  protected static final String TEST_PLAYER_NAME = "testName";
  public static final String TEST_OPP_NAME = "oppName";

  //
  // Required HTTP objects
  //
  protected Request request;
  protected Session session;
  protected Response response;

  //
  // Application specific friendlies
  //
  protected PlayerLobby playerLobby;
  private Gson gson;
  protected GameCenter gameCenter;
  private TemplateEngine engine;
  private int playerNonce = 0;
  private InitConfig initConfig;

  @BeforeEach
  void setUp() {
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    response = mock(Response.class);

    gameCenter = new GameCenter();
    gson = new Gson();
    playerLobby = new PlayerLobby();
    initConfig = new InitConfig("testName");
    engine = mock(TemplateEngine.class);

    cut = new GetSignoutRoute(gameCenter, playerLobby, engine);
  }

  /**
   *  Test that the component was successfully created.
   */
  @Test
  void testInstantiation() {
    assertNotNull(cut);
  }

  /**
   *  Test that the route correctly redirects to the home page.
   */
  @Test
  void testViewModel() {
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    assertThrows(HaltException.class,  () -> {
      cut.handle(request,response);
    });

    assertThrows(AssertionFailedError.class, testHelper::assertViewModelExists);
    assertThrows(AssertionFailedError.class, testHelper::assertViewModelIsaMap);
  }

  /**
   *  Test that the route redirects to the home page when no player is stored in the
   *  current session.
   */
  @Test
  public void testSessionEmpty(){
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    // no player is stored in session
    assertThrows(HaltException.class,  () -> {
      cut.handle(request,response);
    });
  }

  /**
   *  Test that the route redirects to the home page when a player is stored in the session,
   *  but that player is not known by playerLobby.
   */
  @Test
  public void testNoPlayer(){
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    // player is stored in session but not in playerLobby
    when(session.attribute("player")).thenReturn(TEST_PLAYER_NAME);
    assertThrows(HaltException.class,  () -> {cut.handle(request,response);});
  }

  /**
   *  Test that the current player successfully signed out.
   */
  @Test
  public void testSuccessfulSignout(){
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    // player is stored in session and is signed in
    Player testPlayer = playerLobby.signin(TEST_PLAYER_NAME);
    when(session.attribute("player")).thenReturn(testPlayer.getName());

    assertThrows(HaltException.class,  () -> {cut.handle(request,response);});
  }
}