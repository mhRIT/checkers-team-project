package com.webcheckers.ui;

import static spark.Spark.halt;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

/**
 * The {@code GET /signout} route handler.
 *
 * @author <a href='mailto:sjk7867@rit.edu'>Simon Kirkwood</a>
 * @author <a href='mailto:mlh1964@.rit.edu'>Meaghan Hoitt</a>
 */
public class GetSignoutRoute implements Route {

  // Values used in the view-model map for rendering the home view.
  static final String TITLE_ATTR = "title";
  static final String TITLE = "Home";
  static final String VIEW_NAME = "home.ftl";
  private static final Logger LOG = Logger.getLogger(GetSignoutRoute.class.getName());
  private final TemplateEngine templateEngine;
  private PlayerLobby playerLobby;

  /**
   * The constructor for the {@code GET /signin} route handler.
   *
   * @param templateEngine The {@link TemplateEngine} used for rendering page HTML.
   */
  public GetSignoutRoute(PlayerLobby playerLobby, final TemplateEngine templateEngine) {
    LOG.setLevel(Level.ALL);
    // validation
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.playerLobby = playerLobby;
    this.templateEngine = templateEngine;
    //
    LOG.config("GetSignoutRoute is initialized.");
  }

  /**
   * Render the WebCheckers Sign-out page.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return the rendered HTML for the Sign-in page
   */
  @Override
  public Object handle(Request request, Response response) {
    final Session session = request.session();
    Player currPlayer = session.attribute("player");

    LOG.finer("GetSignoutRoute is invoked: " + currPlayer.getName());

    if (currPlayer != null) {
      playerLobby.signout(currPlayer.getName());
      session.removeAttribute("player");
    }
    response.redirect(WebServer.HOME_URL);
    halt();
    return "nothing";
  }
}
