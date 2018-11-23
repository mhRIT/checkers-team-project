package com.webcheckers.ui.HtmlRoutes;

import static com.webcheckers.ui.HtmlRoutes.GetHomeRoute.PLAYER;
import static spark.Spark.halt;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.ui.WebServer;
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
 * This is the page that signs out the current player and allows
 * for their name to be used by another player.
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mvm7902@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
public class GetSignoutRoute implements Route {
  //
  // Constants
  //
  static final String TITLE_ATTR = "title";
  static final String TITLE = "Home";
  static final String VIEW_NAME = "home.ftl";

  //
  // Attributes
  //
  private final TemplateEngine templateEngine;
  private final PlayerLobby playerLobby;
  private final GameCenter gameCenter;
  private static final Logger LOG = Logger.getLogger(GetSignoutRoute.class.getName());

  /**
   * Create the Spark Route (UI controller) for the {@code GET /signout} HTTP request.
   *
   * @param playerLobby the {@link PlayerLobby} for tracking all signed in players
   * @param gameCenter the {@link GameCenter} for tracking all ongoing games
   * @param templateEngine the {@link TemplateEngine} used for rendering page HTML.
   * @throws NullPointerException when the {@code gameCenter}, {@code playerLobby}, or {@code
   * templateEngine} parameter is null
   */
  public GetSignoutRoute(final PlayerLobby playerLobby, final GameCenter gameCenter, final TemplateEngine templateEngine) {
    LOG.setLevel(Level.ALL);
    // validation
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.playerLobby = playerLobby;
    this.gameCenter = gameCenter;
    this.templateEngine = templateEngine;
    //
    LOG.config("GetSignoutRoute is initialized.");
  }

  /**
   * {@inheritDoc}
   * Render the WebCheckers Sign-out page.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return the rendered HTML for the Sign-in page
   */
  @Override
  public Object handle(Request request, Response response) {
    final Session session = request.session();
    String currPlayerName = session.attribute(PLAYER);
    Player currPlayer = playerLobby.getPlayer(currPlayerName);

    if(currPlayer == null){
      LOG.finer("GetSignoutRoute is invoked with no stored player");
      response.redirect(WebServer.HOME_URL);
      halt();
      return "nothing";
    }

    LOG.finer("GetSignoutRoute is invoked: " + currPlayer.getName());

    if (playerLobby.containsPlayers(currPlayer)) {
      gameCenter.resignAll(currPlayer);
      playerLobby.signout(currPlayer.getName());
      session.removeAttribute("player");
    }
    response.redirect(WebServer.HOME_URL);
    halt();
    return "nothing";
  }
}
