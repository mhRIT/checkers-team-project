package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Player;
import com.webcheckers.ui.boardView.Message;
import com.webcheckers.ui.boardView.Message.MESSAGE_TYPE;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import spark.Session;

/**
 * The {@code POST /checkTurn} route handler.
 * Check if the opponent has submitted their turn.
 *
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 */
public class PostCheckTurnRoute extends AjaxRoute {

  private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());

  public PostCheckTurnRoute(GameCenter gameCenter){
    super(gameCenter);
  }

  public Object handle(Request request, Response response){
    final Session session = request.session();
    Player player = session.attribute("player");
    GameContext game = gameCenter.getGames(player).get(0);

    //If the game has ended OR
    //If it is the active player's turn
    if(game.checkEnd() || player.equals(game.getActivePlayer())){
      LOG.finer("CheckTurn is true for player: " + player.getName());
      return new Message("true", MESSAGE_TYPE.info);
    }
    else{
      LOG.finer("CheckTurn is false for player: " + player.getName());
      return new Message("false",MESSAGE_TYPE.error);
    }
  }
}
