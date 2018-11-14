package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import spark.Route;

public abstract class AjaxRoute implements Route {
  private static final Logger LOG = Logger.getLogger(AjaxRoute.class.getName());

  final GameCenter gameCenter;
  Gson gson = null;

  public AjaxRoute(GameCenter gameCenter){
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    this.gameCenter = gameCenter;
  }

  public AjaxRoute(GameCenter gameCenter, Gson gson){
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    Objects.requireNonNull(gson, "gson must not be null");

    this.gameCenter = gameCenter;
    this.gson = gson;
  }

  public abstract Object handle(Request request, Response response);
}
