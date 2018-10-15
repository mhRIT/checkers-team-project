package com.webcheckers.ui;

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
 *  @author <a href='mailto:mlh1964@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mlh1964@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:mlh1964@rit.edu'>Andrew Festa</a>
 */
public class GetSigninRoute implements Route {
  //
  // Constants
  //
  static final String TITLE_ATTR = "title";
  static final String TITLE = "Sign-in";
  static final String VIEW_NAME = "signin.ftl";

  //
  // Attributes
  //
  private final TemplateEngine templateEngine;
  private static final Logger LOG = Logger.getLogger(GetSigninRoute.class.getName());

  /**
   * Create the Spark Route (UI controller) for the {@code GET /signin} HTTP request.
   *
   * @param templateEngine the {@link TemplateEngine} used for rendering page HTML.
   * @throws NullPointerException when the {@code gameCenter}, {@code playerLobby}, or {@code
   * templateEngine} parameter is null
   */
  public GetSigninRoute(final TemplateEngine templateEngine) {
    LOG.setLevel(Level.ALL);
    // validation
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.templateEngine = templateEngine;
    //
    LOG.config("GetSigninRoute is initialized.");
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
    LOG.finer("GetSigninRoute is invoked");

    //start building the View-Model
    final Map<String, Object> vm = new HashMap<>();
    vm.put(TITLE_ATTR, TITLE);

    return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
  }
}
