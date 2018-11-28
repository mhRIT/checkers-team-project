package com.webcheckers.ui.HtmlRoutes;

import static com.webcheckers.ui.HtmlRoutes.PostBuildConfig.INIT_CONFIG;
import static com.webcheckers.ui.HtmlRoutes.PostSelectOpponentRoute.OPP_PLAYER_NAME;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.webcheckers.model.Board.InitConfig;
import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Player.Player;
import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.HaltException;
import spark.ModelAndView;

/**
 * The unit test suite for the {@link PostSelectOpponentRouteTest} component.
 *
 * @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
@Tag("UI-tier")
class PostSelectOpponentRouteTest extends HtmlRouteTest {

  /**
   * Setup new mock objects for each test.
   */
  @BeforeEach
  public void setup() {
    super.setUp();
    // create a unique cut for each test
    cut = new PostSelectOpponentRoute(gameCenter, playerLobby, engine);
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
    when(session.attribute("player")).thenReturn(testPlayer.getName());

    Player testOppPlayer = playerLobby.signin(TEST_OPP_NAME);
    when(request.queryParams(OPP_PLAYER_NAME)).thenReturn(TEST_OPP_NAME);

    InitConfig initConfig = new InitConfig(TEST_OPP_NAME);
    GameContext game = gameCenter.createGame(testPlayer, testOppPlayer, initConfig);

    String message = String.format("The selected opponent, %s, is already in a game", TEST_OPP_NAME);

    assertThrows(HaltException.class, () -> {cut.handle(request,response);});
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
    when(session.attribute("player")).thenReturn(testPlayer.getName());

    Player testOppPlayer = playerLobby.signin(TEST_OPP_NAME);
    when(request.queryParams(OPP_PLAYER_NAME)).thenReturn(TEST_OPP_NAME);

    when(session.attribute(INIT_CONFIG)).thenReturn(new InitConfig(TEST_OPP_NAME));

    assertThrows(HaltException.class, () -> {cut.handle(request,response);});
  }

  /**
   *  Test the attribute values in the ModelView map for correct values when a player is not
   *  stored in the current session.
   */
  @Override
  @Test
  public void testNoSessionPlayer() throws Exception {
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
    when(session.attribute("player")).thenReturn(null);
    assertThrows(HaltException.class,  () -> {cut.handle(request,response);});
  }

  /**
   *  Test the attribute values in the ModelView map for correct values when a player is
   *  stored in the current session and is not signed in.
   */
  @Override
  @Test
  public void testNotSignedIn() throws Exception {
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
    when(session.attribute("player")).thenReturn(TEST_PLAYER_NAME);
    assertThrows(HaltException.class,  () -> {cut.handle(request,response);});
  }
}