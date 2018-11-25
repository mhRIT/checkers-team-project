package com.webcheckers.ui.AjaxRoutes;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import com.webcheckers.ui.HtmlRoutes.HtmlRouteTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

/**
 * The unit test suite for the {@link PostValidateMoveRouteTest} component.
 *
 * @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
@Tag("UI-tier")
class PostValidateMoveRouteTest extends HtmlRouteTest {
  @BeforeEach
  protected void setUp() {
    super.setUp();

    // create a unique CuT for each test
    CuT = new PostValidateMoveRoute(gameCenter, playerLobby, gson);
  }
}
