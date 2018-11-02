package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.ui.boardView.Message;
import com.webcheckers.ui.boardView.Message.MESSAGE_TYPE;
import java.util.Objects;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

public class PostSubmitTurnRoute implements Route {

  private final GameCenter gameCenter;
  private final Gson gson;
  private final PlayerLobby playerLobby;
  private final TemplateEngine templateEngine;
  private static final Logger LOG = Logger.getLogger(PostSigninRoute.class.getName());

  public PostSubmitTurnRoute(GameCenter gameCenter, Gson gson, PlayerLobby playerLobby, TemplateEngine templateEngine){
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    Objects.requireNonNull(gson, "gson must not be null");
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");

    this.gameCenter = gameCenter;
    this.gson = gson;
    this.playerLobby = playerLobby;
    this.templateEngine = templateEngine;
  }

  public Object handle(Request request, Response response) {
    final Session session = request.session();
    Player player = session.attribute("player");

    Game game = gameCenter.getGames(player)[0];

    if(game.isLastTurnValid()){
      return new Message("true", MESSAGE_TYPE.info);
    }
    else{
      return new Message("false", MESSAGE_TYPE.error);
    }
  }


}
