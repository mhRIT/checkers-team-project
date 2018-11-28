package com.webcheckers.ui.HtmlRoutes;

import static com.webcheckers.ui.HtmlRoutes.GetHomeRoute.PLAYER;
import static spark.Spark.halt;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player.Player;
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
public class GetSignoutRoute extends HtmlRoute {
  //
  // Constants
  //
  static final String TITLE_ATTR = "title";
  static final String TITLE = "Home";
  static final String VIEW_NAME = "home.ftl";

  /**
   * Create the Spark Route (UI controller) for the {@code GET /signout} HTTP request.
   * {@inheritDoc}
   */
  public GetSignoutRoute(final GameCenter gameCenter, final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
    super(gameCenter, playerLobby, templateEngine);
  }

  /**
   * Render the WebCheckers Sign-out page.
   * {@inheritDoc}
   *
   */
  @Override
  public Object handle(Request request, Response response) {
    final Session session = request.session();
    String currPlayerName = session.attribute(PLAYER);
    Player currPlayer = playerLobby.getPlayer(currPlayerName);

    if(currPlayer == null){
      response.redirect(WebServer.HOME_URL);
      halt();
      return "nothing";
    }

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
