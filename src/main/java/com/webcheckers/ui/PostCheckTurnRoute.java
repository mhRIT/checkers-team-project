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
public class PostCheckTurnRoute extends AjaxRoute {

  private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());

  public PostCheckTurnRoute(GameCenter gameCenter){
    super(gameCenter);
  }

  public Object handle(Request request, Response response){
    final Session session = request.session();
    Player player = session.attribute("player");

    //If the opponent has resigned
    Game game = gameCenter.getGames(player)[0];
    if(game.hasResigned(game.getOpponent(player))){
      return new Message("true", MESSAGE_TYPE.info);
    }

    //If a game does exist it check if it is the current player's turn
    if(player.equals(game.getActivePlayer())){
      LOG.finer("CheckTurn is true for player: " + player.getName());
      return new Message("true", MESSAGE_TYPE.info);
    }
    else{
      LOG.finer("CheckTurn is false for player: " + player.getName());
      return new Message("false",MESSAGE_TYPE.error);
    }
  }
}
