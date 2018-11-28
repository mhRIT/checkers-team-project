package com.webcheckers.ui.HtmlRoutes;

import static com.webcheckers.ui.HtmlRoutes.GetHomeRoute.ALL_PLAYER_NAMES;
import static com.webcheckers.ui.HtmlRoutes.GetHomeRoute.NUM_PLAYERS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Player.Player;
import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.ModelAndView;

/**
 * The unit test suite for the {@link GetHomeRoute} component.
 *
 * @author <a href='mailto:sjk7867@rit.edu'>Simon Kirkwood</a>
 */
@Tag("UI-tier")
public class GetHomeRouteTest extends HtmlRouteTest {
  //
  // Attributes
  //
  private Player player1;
  private Player player2;

  /**
   * Setup new mock objects for each test.
   */
  @BeforeEach
  public void setup() {
    super.setUp();
    cut = new GetHomeRoute(gameCenter, playerLobby, engine);
    player1 = new Player(TEST_PLAYER_NAME, 0);
    player1 = new Player(TEST_OPP_NAME, 1);
  }

  /**
   *
   * @throws Exception
   */
  @Test
  public void testBehavior () throws Exception {
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    when(session.attribute("player")).thenReturn(null);
    cut.handle(request,response);

    testHelper.assertViewModelAttributeIsAbsent(ALL_PLAYER_NAMES);
    testHelper.assertViewModelAttribute(NUM_PLAYERS,0);

    when(session.attribute("player")).thenReturn(player1.getName());
    cut.handle(request,response);
  }
}
