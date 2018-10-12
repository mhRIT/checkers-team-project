package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.ui.boardView.Message;
import com.webcheckers.ui.boardView.Message.MESSAGE_TYPE;
import com.webcheckers.model.Player;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

public class PostCheckTurnRoute implements Route {

  private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());

  private final GameCenter gameCenter;
  private final Gson gson;
  private final PlayerLobby playerLobby;
  private final TemplateEngine templateEngine;

  public PostCheckTurnRoute(GameCenter gameCenter, Gson gson, PlayerLobby playerLobby,
      final TemplateEngine templateEngine) {
    LOG.setLevel(Level.ALL);
    // validation
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(gson, "gson must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");

    //
    // Attributes
    //
    this.gameCenter = gameCenter;
    this.playerLobby = playerLobby;
    this.gson = gson;
    this.templateEngine = templateEngine;
  }

  @Override
  public Object handle(Request request, Response response) {
    final Session session = request.session();
    Player player = session.attribute("player");
    LOG.finer("PostCheckTurnRoute is invoked: " + player.getName());

    Message respMessage;
    if(!gameCenter.isPlayerInGame(player)){
      respMessage = new Message("In game?", MESSAGE_TYPE.error);
    } else {
      respMessage = new Message("true", MESSAGE_TYPE.info);
    }
    return respMessage;
  }
}
