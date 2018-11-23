package com.webcheckers.ui.AjaxRoutes;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import java.util.Objects;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import spark.Route;

public abstract class AjaxRoute implements Route {
  private static final Logger LOG = Logger.getLogger(AjaxRoute.class.getName());

  final GameCenter gameCenter;
  final PlayerLobby playerLobby;
  final Gson gson;

  public AjaxRoute(GameCenter gameCenter, PlayerLobby playerLobby, Gson gson){
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(gson, "gson must not be null");

    this.playerLobby = playerLobby;
    this.gameCenter = gameCenter;
    this.gson = gson;
  }

  public abstract Object handle(Request request, Response response);
}
