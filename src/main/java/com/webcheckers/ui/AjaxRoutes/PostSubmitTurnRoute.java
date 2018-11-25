package com.webcheckers.ui.AjaxRoutes;

import static com.webcheckers.ui.HtmlRoutes.GetHomeRoute.PLAYER;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Player.Player;
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

  public PostSubmitTurnRoute(GameCenter gameCenter, PlayerLobby playerLobby, Gson gson) {
    super(gameCenter, playerLobby, gson);
  }

  public Object handle(Request request, Response response) {
    final Session session = request.session();
    String currPlayerName = session.attribute(PLAYER);
    Player currPlayer = playerLobby.getPlayer(currPlayerName);
    GameContext game = gameCenter.getGame(currPlayer);

    if(game.isTurnOver() && game.proceed()){
      return new Message("Your move has been submitted", MESSAGE_TYPE.info);
    } else {
      if (game.isGameOver()){
        return new Message("Your opponent has resigned", MESSAGE_TYPE.info);
      } else {
        return new Message("Your turn is not yet complete", MESSAGE_TYPE.error);
      }
    }
  }
}