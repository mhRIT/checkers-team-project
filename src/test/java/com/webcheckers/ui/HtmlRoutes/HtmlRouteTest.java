package com.webcheckers.ui.HtmlRoutes;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Board.InitConfig;
import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

//import com.sun.org.apache.xml.internal.security.Init;

/**
 * An abstract class to define unit test classes for Routes.
 *
 * @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
@Tag("UI-tier")
public abstract class HtmlRouteTest {
  /**
   * The component-under-test (cut).
   */
  protected Route cut;

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
  protected Gson gson;
  protected GameCenter gameCenter;
  protected TemplateEngine engine;
  int playerNonce = 0;
  protected InitConfig initConfig;

  @BeforeEach
  protected void setUp() {
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    response = mock(Response.class);

    gameCenter = new GameCenter();
    gson = new Gson();
    playerLobby = new PlayerLobby();
    initConfig = new InitConfig("testName");
    engine = mock(TemplateEngine.class);
  }

  /**
   *  Test the construction of the SelectOpponent Route.
   */
  @Test
  public void testRouteCreation() {
    assertNotNull(cut);
  }

  /**
   *  Test the attribute values in the ModelView map for correct values when a player is not
   *  stored in the current session.
   */
  @Test
  public void testNoSessionPlayer() throws Exception {
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
    when(session.attribute("player")).thenReturn(null);
    cut.handle(request,response);
  }

    /**
   *  Test the attribute values in the ModelView map for correct values when a player is
   *  stored in the current session and is not signed in.
   */
  @Test
  public void testNotSignedIn() throws Exception {
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
    when(session.attribute("player")).thenReturn(TEST_PLAYER_NAME);
    cut.handle(request,response);
  }
}
