package com.webcheckers.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

/**
 * The unit test suite for the {@link PostValidateMoveRouteTest} component.
 *
 * @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
@Tag("UI-tier")
class PostValidateMoveRouteTest extends RouteTest {
  @BeforeEach
  void setUp() {
    super.setUp();

    // create a unique CuT for each test
    CuT = new PostValidateMoveRoute(gameCenter, gson, playerLobby, engine);
  }
}
