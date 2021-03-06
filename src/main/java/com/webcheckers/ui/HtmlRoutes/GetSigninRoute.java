package com.webcheckers.ui.HtmlRoutes;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

/**
 * The {@code GET /signin} route handler.
 * This is the page where a user selects their desired username.
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mvm7902@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
public class GetSigninRoute extends HtmlRoute {
  //
  // Constants
  //
  static final String TITLE_ATTR = "title";
  static final String TITLE = "Sign-in";
  static final String VIEW_NAME = "signin.ftl";

  /**
   * Create the Spark Route (UI controller) for the {@code GET /signin} HTTP request.
   * {@inheritDoc}
   *
   */
  public GetSigninRoute(final GameCenter gameCenter, final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
    super(gameCenter, playerLobby, templateEngine);
  }

  /**
   * {@inheritDoc}
   * Render the WebCheckers Sign-in page.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return the rendered HTML for the Sign-in page
   */
  @Override
  public Object handle(Request request, Response response) {
    final Map<String, Object> vm = new HashMap<>();
    vm.put(TITLE_ATTR, TITLE);

    return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
  }
}
