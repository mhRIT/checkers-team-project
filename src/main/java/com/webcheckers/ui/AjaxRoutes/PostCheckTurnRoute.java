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
 * The {@code POST /checkTurn} route handler.
 * Check if the opponent has submitted their turn.
 *
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 */
public class PostCheckTurnRoute extends AjaxRoute {

  /**
   * Create the Spark Route (UI controller) for the {@code POST /checkTurn} HTTP request.
   * {@inheritDoc}
   */
  public PostCheckTurnRoute(GameCenter gameCenter, PlayerLobby playerLobby, Gson gson) {
    super(gameCenter, playerLobby, gson);
  }

  /**
   * Check if the opponent has submitted their turn.
   * {@inheritDoc}
   */
  public Object handle(Request request, Response response){
    final Session session = request.session();
    String currPlayerName = session.attribute(PLAYER);
    Player currPlayer = playerLobby.getPlayer(currPlayerName);
    GameContext game = gameCenter.getGame(currPlayer);

    if(game.isGameOver() || currPlayer.equals(game.getActivePlayer())){
      return new Message("true", MESSAGE_TYPE.info);
    }
    else{
      return new Message("false",MESSAGE_TYPE.error);
    }
  }
}
