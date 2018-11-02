package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
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

/**
 * The {@code POST /checkTurn} route handler.
 * Check if the opponent has submitted their turn.
 *
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 */
public class PostCheckTurnRoute implements Route {

  private final GameCenter gameCenter;
  private final Gson gson;
  private final TemplateEngine templateEngine;
  private static final Logger LOG = Logger.getLogger(PostSigninRoute.class.getName());

  public PostCheckTurnRoute(GameCenter gameCenter, Gson gson, TemplateEngine templateEngine){
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    Objects.requireNonNull(gson, "gson must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");

    this.gameCenter = gameCenter;
    this.gson = gson;
    this.templateEngine = templateEngine;
  }

  public Object handle(Request request, Response response){
    final Session session = request.session();
    Player player = session.attribute("player");
    Game game = gameCenter.getGames(player)[0];

    if(game.getTurnSwitch()){
      game.setTurnSwitch(false);
      LOG.finer("CheckTurn is true for player: " + player.getName());
      return new Message("true", MESSAGE_TYPE.info);
    }
    else{
      LOG.finer("CheckTurn is false for player: " + player.getName());
      return new Message("false",MESSAGE_TYPE.error);
    }
  }
}
