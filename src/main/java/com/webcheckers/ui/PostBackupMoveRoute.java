package com.webcheckers.ui;


import com.webcheckers.application.GameCenter;
import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.GameState.GameContext.COLOR;
import com.webcheckers.model.Player;
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

  public PostBackupMoveRoute(GameCenter gameCenter) {
    super(gameCenter);
  }

  public Object handle(Request request, Response response) {
    final Session session = request.session();
    Player player = session.attribute("player");
    GameContext game = gameCenter.getGames(player).get(0);

    COLOR c = game.getActiveColor();
    Player compare;
    //Get the active player based on the active color
    switch(c){
      case RED:
        compare = game.getRedPlayer();
        break;
      default:
        compare = game.getWhitePlayer();
        break;
    }

    //If the current player is the active player, return a successful undo message
//    if (game.hasPlayer(player) && player.equals(compare)) {
//      game.undoLastMove();
//      return new Message("Move undone", MESSAGE_TYPE.info);
//    } else {
//      return new Message("Could not undo move", MESSAGE_TYPE.error);
//    }
    return new Message("Move undone", MESSAGE_TYPE.info);
  }
}
