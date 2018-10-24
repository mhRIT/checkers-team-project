package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.*;

public class PostSigninRouteTest {

  private PostSigninRoute CuT;

  private Request request;
  private Session session;
  private Response response;
  private TemplateEngine engine;

  private PlayerLobby playerLobby;
  private PlayerLobby testLobby;

  @BeforeEach
  public void setup(){
    request = mock(Request.class);
    session = mock(Session.class);
    response = mock(Response.class);
    engine = mock(TemplateEngine.class);
    testLobby = new PlayerLobby(mock(GameCenter.class));
    playerLobby = mock(PlayerLobby.class);

    when(request.queryParams("username")).thenReturn("test");
    when(playerLobby.signin(anyString())).thenReturn(testLobby.signin("test"));
    when(request.session()).thenReturn(session);

    CuT = new PostSigninRoute(playerLobby, engine);
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
    testHelper.assertViewModelAttribute(PostSigninRoute.MESSAGE_TYPE_ATTR,
        PostSigninRoute.ERROR_TYPE);

    testHelper.assertViewName(GetSigninRoute.VIEW_NAME);

  }

  @Test
  public void testHandle(){
    when(request.queryParams("username")).thenReturn("test");
    CuT.handle(request, response);

    assertNotNull(testLobby.getPlayer("test"));
    assertFalse(testLobby.validateName("test"));

    when(session.attribute("player")).thenReturn(testLobby.getPlayer("test"));
    CuT.handle(request, response);

    when(request.queryParams("username")).thenReturn("");
    CuT.handle(request, response);

    assertNull(testLobby.getPlayer(""));
    assertFalse(testLobby.validateName(""));


  }
}
