package com.webcheckers.ui.HtmlRoutes;

import static com.webcheckers.ui.HtmlRoutes.PostSigninRoute.ERROR_TYPE;
import static com.webcheckers.ui.HtmlRoutes.PostSigninRoute.MESSAGE_TYPE_ATTR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.ui.HtmlRoutes.GetSigninRoute;
import com.webcheckers.ui.HtmlRoutes.PostSigninRoute;

import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.*;

@Tag("UI-tier")
public class PostSigninRouteTest {

  private PostSigninRoute CuT;

  private Request request;
  private Session session;
  private Response response;
  private TemplateEngine engine;

  private GameCenter gameCenter;
  private PlayerLobby playerLobby;
  private PlayerLobby testLobby;

  @BeforeEach
  public void setup(){
    request = mock(Request.class);
    session = mock(Session.class);
    response = mock(Response.class);
    testLobby = new PlayerLobby();
    gameCenter = new GameCenter();
    playerLobby = new PlayerLobby();

    when(request.queryParams("username")).thenReturn("test");
    when(request.session()).thenReturn(session);

    CuT = new PostSigninRoute(gameCenter, playerLobby, engine);
  }

  @Test
  public void testCreation(){
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    assertNotNull(playerLobby);
    assertNotNull(engine);


  }

  @Test
  public void testVM() {
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    CuT.handle(request, response);

    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();
  }

  @Test
  public void testAttributes(){
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    CuT.handle(request, response);

    testHelper.assertViewModelAttribute(GetSigninRoute.TITLE_ATTR, PostSigninRoute.TITLE);
    testHelper.assertViewModelAttribute(MESSAGE_TYPE_ATTR,
        ERROR_TYPE);

    testHelper.assertViewName(GetSigninRoute.VIEW_NAME);

  }

  @Test
  public void testHandle(){
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    when(request.queryParams("username")).thenReturn("test");
    CuT.handle(request, response);

    assertNotNull(testLobby.getPlayer("test"));
    assertFalse(testLobby.validateName("test"));
    testHelper.assertViewModelAttributeIsAbsent(ERROR_TYPE);

    when(session.attribute("player")).thenReturn(testLobby.getPlayer("test"));
    CuT.handle(request, response);

    when(request.queryParams("username")).thenReturn("");
    CuT.handle(request, response);

    assertNull(testLobby.getPlayer(""));
    assertFalse(testLobby.validateName(""));
    testHelper.assertViewModelAttribute(MESSAGE_TYPE_ATTR,ERROR_TYPE);

    when(session.attribute("player")).thenReturn(null);
    CuT.handle(request,response);

  }
}