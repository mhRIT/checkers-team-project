package com.webcheckers.ui.HtmlRoutes;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.*;

/**
 * The unit test suite for the {@link GetSigninRoute} component.
 *
 * @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 */
@Tag("UI-tier")
public class GetSigninRouteTest extends HtmlRouteTest {


  /**
   * The component-under-test (cut).
   */
//    private GetSigninRoute CuT;

  private Request request;
  private Session session;
  private Response response;
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
      engine = mock(TemplateEngine.class);

      // create a unique cut for each test
      cut = new GetSigninRoute(gameCenter, playerLobby, engine);
  }

  /**
   *  Test that the model attributes exists in GetSigninRoute
   */
  @Test
  public void testViewAttributes() throws Exception {
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    cut.handle(request,response);

    testHelper.assertViewModelAttribute(GetSigninRoute.TITLE_ATTR, GetSigninRoute.TITLE);
    testHelper.assertViewName(GetSigninRoute.VIEW_NAME);
  }
}
