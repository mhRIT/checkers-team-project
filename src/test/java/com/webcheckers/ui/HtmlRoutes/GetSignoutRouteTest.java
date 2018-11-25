package com.webcheckers.ui.HtmlRoutes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
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

  private static final String PLAYER_NAME = "testName";

  private Session session;
  private Request request;
  private Response response;

  private TemplateEngine templateEngine;
  private PlayerLobby playerLobby;
  private GameCenter gameCenter;
  int playerNonce = 0;

  private GetSignoutRoute cut;

  @BeforeEach
  void setUp() {
    session = mock(Session.class);
    request = mock(Request.class);
    when(request.session()).thenReturn(session);
    response = mock(Response.class);

    templateEngine = mock(TemplateEngine.class);
    gameCenter = new GameCenter();
    playerLobby = new PlayerLobby();

    cut = new GetSignoutRoute(playerLobby, gameCenter, templateEngine);
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
    when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

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
    when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

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
    when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    // player is stored in session but not in playerLobby
    when(session.attribute("player")).thenReturn(new Player(PLAYER_NAME, playerNonce++));
    assertThrows(HaltException.class,  () -> {
      cut.handle(request,response);
    });

    assertThrows(AssertionFailedError.class, testHelper::assertViewModelExists);
    assertThrows(AssertionFailedError.class, testHelper::assertViewModelIsaMap);
  }

  /**
   *  Test that the current player successfully signed out.
   */
  @Test
  public void testSuccessfulSignout(){
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    // player is stored in session and is signed in
    Player testPlayer = playerLobby.signin(PLAYER_NAME);
    when(session.attribute("player")).thenReturn(testPlayer);

    assertThrows(HaltException.class,  () -> {
      cut.handle(request,response);
    });
  }
}