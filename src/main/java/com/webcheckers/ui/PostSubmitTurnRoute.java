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
 * The {@code POST /submitTurn} route handler.
 * Handles a player submitting their turn.
 *
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirkwood</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
public class PostSubmitTurnRoute extends AjaxRoute {

  private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());

  public PostSubmitTurnRoute(GameCenter gameCenter){
    super(gameCenter);
  }

  public Object handle(Request request, Response response) {
    final Session session = request.session();
    Player player = session.attribute("player");
    GameContext game = gameCenter.getGames(player).get(0);

    if(game.isTurnOver() && game.proceed()){
      return new Message("true", MESSAGE_TYPE.info);
    } else {
      return new Message("Your turn is not yet complete", MESSAGE_TYPE.error);
    }
    //If the move being attempted is valid, switch the turn and return a
    //Message of type info. Otherwise, return a Message of type error.
//    if(game.isLastTurnValid()){
//      game.switchTurn();
//      return new Message("true", MESSAGE_TYPE.info);
//    }
//    else{
//      return new Message("false", MESSAGE_TYPE.error);
//    }
  }
}