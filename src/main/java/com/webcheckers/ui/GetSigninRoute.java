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
 *
 * @author <a href='mailto:sjk7867@rit.edu'>Simon Kirkwood</a>
 * @author <a href='mailto:mlh1964@.rit.edu'>Meaghan Hoitt</a>
 */
public class GetSigninRoute implements Route {

  // Values used in the view-model map for rendering the home view.
  static final String TITLE_ATTR = "title";
  static final String TITLE = "Sign-in";
  static final String VIEW_NAME = "signin.ftl";
  private static final Logger LOG = Logger.getLogger(GetSigninRoute.class.getName());
  private final TemplateEngine templateEngine;

  /**
   * The constructor for the {@code GET /signin} route handler.
   *
   * @param templateEngine The {@link TemplateEngine} used for rendering page HTML.
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
