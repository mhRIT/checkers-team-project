package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
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

public class PostResignGameRoute implements Route {

  private static final Logger LOG = Logger.getLogger(PostSigninRoute.class.getName());

  private final GameCenter gameCenter;
  private final TemplateEngine templateEngine;

  PostResignGameRoute(GameCenter gameCenter, TemplateEngine templateEngine) {
    LOG.setLevel(Level.ALL);
    // validation
    Objects.requireNonNull(gameCenter, "playerLobby must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");

    //
    // Attributes
    //
    this.gameCenter = gameCenter;
    this.templateEngine = templateEngine;
  }

  @Override
  public Object handle(Request request, Response response) {
    final Session session = request.session();
    Player player = session.attribute("player");
    LOG.finer("PostResignGameRoute is invoked: " + player.getName());

    gameCenter.removeGame(gameCenter.getGames(player)[0]);

    return new Message("Resigning from game: You lost", MESSAGE_TYPE.info);
  }
}
