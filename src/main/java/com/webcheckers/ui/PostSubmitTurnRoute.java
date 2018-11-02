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
 * The {@code POST /submitTurn} route handler.
 * Handles a player submitting their turn.
 *
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 */
public class PostSubmitTurnRoute implements Route {

  private final GameCenter gameCenter;
  private final Gson gson;
  private final TemplateEngine templateEngine;
  private static final Logger LOG = Logger.getLogger(PostSigninRoute.class.getName());

  public PostSubmitTurnRoute(GameCenter gameCenter, Gson gson, TemplateEngine templateEngine){
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    Objects.requireNonNull(gson, "gson must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");

    this.gameCenter = gameCenter;
    this.gson = gson;
    this.templateEngine = templateEngine;
  }

  public Object handle(Request request, Response response) {
    final Session session = request.session();
    Player player = session.attribute("player");

    Game game = gameCenter.getGames(player)[0];

    //If the move being attempted is valid, switch the turn and return a
    //Message of type info. Otherwise, return a Message of type error.
    if(game.isLastTurnValid()){
      game.setTurnSwitch(true);
      game.switchTurn();
      return new Message("true", MESSAGE_TYPE.info);
    }
    else{
      return new Message("false", MESSAGE_TYPE.error);
    }
  }


}
