package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

class PostValidateMoveRouteTest {

  /**
   * The component-under-test (CuT).
   */
  private PostValidateMoveRoute CuT;

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
  private Gson gson;
  private GameCenter gameCenter;
  private TemplateEngine engine;

  @BeforeEach
  void setUp() {
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    response = mock(Response.class);

    gameCenter = new GameCenter();
    gson = new Gson();
    playerLobby = new PlayerLobby(gameCenter);
    engine = mock(TemplateEngine.class);

    // create a unique CuT for each test
    CuT = new PostValidateMoveRoute(gameCenter, gson, playerLobby, engine);
  }

  /**
   *  Test the construction of the ValidateMove Route.
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
  }
}
