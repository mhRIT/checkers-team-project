package com.webcheckers.ui.HtmlRoutes;

import static spark.Spark.halt;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player.Player;
import com.webcheckers.ui.WebServer;
import java.util.Objects;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

/**
 * Abstract class for HtmlRoutes.
 *
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
public abstract class HtmlRoute implements Route {
  //
  // Attributes
  //
  final GameCenter gameCenter;
  final PlayerLobby playerLobby;
  final TemplateEngine templateEngine;
  final Logger LOG = Logger.getLogger(this.getClass().getName());

  /**
   * Create the Spark Route (UI controller) for the HTTP request.
   *
   * @param gameCenter  the {@link GameCenter} for tracking all ongoing games
   * @param playerLobby the default {@link PlayerLobby} for tracking all players
   * @param templateEngine the {@link TemplateEngine} used for rendering page HTML.
   * @throws NullPointerException when the {@code gameCenter}, {@code playerLobby}, or {@code
   * templateEngine} parameter is null
   */
  public HtmlRoute(GameCenter gameCenter, PlayerLobby playerLobby, final TemplateEngine templateEngine)
      throws NullPointerException {
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");

    this.playerLobby = playerLobby;
    this.gameCenter = gameCenter;
    this.templateEngine = templateEngine;
  }

  /**
   * Abstract method for handling HTTP requests.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return a message indicating whether the request was successful
   */
  public abstract Object handle(Request request, Response response);

  boolean checkValidPlayerName(String playerName){
    boolean playerStored = playerName != null;
    Player currPlayer = playerLobby.getPlayer(playerName);
    boolean playerExits = currPlayer != null;

    return  playerStored && playerExits;
  }
}
