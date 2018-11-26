package com.webcheckers.ui.AjaxRoutes;


import static com.webcheckers.ui.HtmlRoutes.GetHomeRoute.PLAYER;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Player.Player;
import com.webcheckers.ui.boardView.Message;
import com.webcheckers.ui.boardView.Message.MESSAGE_TYPE;
import spark.Request;
import spark.Response;
import spark.Session;

/**
 * The {@code POST /backupMove} route handler.
 * Handles a player undoing their turn.
 *
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirkwood</a>
 */
public class PostBackupMoveRoute extends AjaxRoute {

  /**
   * Create the Spark Route (UI controller) for the {@code POST /backupMove} HTTP request.
   * {@inheritDoc}
   */
  public PostBackupMoveRoute(GameCenter gameCenter, PlayerLobby playerLobby, Gson gson) {
    super(gameCenter, playerLobby, gson);
  }

  /**
   * Handles a player undoing their turn.
   * {@inheritDoc}
   */
  public Object handle(Request request, Response response) {
    final Session session = request.session();
    String currPlayerName = session.attribute(PLAYER);
    Player currPlayer = playerLobby.getPlayer(currPlayerName);
    GameContext game = gameCenter.getGame(currPlayer);
    if(game.revert()){
      return new Message("Move undone", MESSAGE_TYPE.info);
    } else {
      if (game.isGameOver()){
        return new Message("Your opponent has resigned", MESSAGE_TYPE.info);
      } else {
        return new Message("Could not undo move", MESSAGE_TYPE.error);
      }
    }
  }
}
