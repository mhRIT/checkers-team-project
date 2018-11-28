package com.webcheckers.ui.AjaxRoutes;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import java.util.Objects;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Abstract class for AjaxRoutes.
 *
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirkwood</a>
 */
public abstract class AjaxRoute implements Route {
  final GameCenter gameCenter;
  final PlayerLobby playerLobby;
  final Gson gson;

  /**
   * Create the Spark Route (UI controller) for the AJAX request.
   *
   * @param gameCenter  the {@link GameCenter} for tracking all ongoing games
   * @param playerLobby the default {@link PlayerLobby} for tracking all players
   * @param gson The default {@link Gson} parser for handling Ajax responses
   * @throws NullPointerException when the {@code gameCenter}, {@code playerLobby}, or {@code
   * templateEngine} parameter is null
   */
  public AjaxRoute(GameCenter gameCenter, PlayerLobby playerLobby, Gson gson) throws NullPointerException {
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(gson, "gson must not be null");

    this.playerLobby = playerLobby;
    this.gameCenter = gameCenter;
    this.gson = gson;
  }

  /**
   * Abstract method for handling HTTP requests.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return a message indicating whether the request was successful
   */
  public abstract Object handle(Request request, Response response);
}
