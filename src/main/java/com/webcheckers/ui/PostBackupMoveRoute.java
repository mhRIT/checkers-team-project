package com.webcheckers.ui;

import static com.webcheckers.model.Game.COLOR.RED;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Game.COLOR;
import com.webcheckers.model.Player;
import com.webcheckers.ui.boardView.Message;
import com.webcheckers.ui.boardView.Message.MESSAGE_TYPE;
import java.util.Objects;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostBackupMoveRoute implements Route {

  private GameCenter gameCenter;

  public PostBackupMoveRoute(GameCenter gameCenter) {
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");

    this.gameCenter = gameCenter;
  }

  public Object handle(Request request, Response response) {
    final Session session = request.session();
    Player player = session.attribute("player");
    Game game = gameCenter.getGames(player)[0];

    COLOR c = game.getActiveColor();
    Player compare;
    switch(c){
      case RED:
        compare = game.getRedPlayer();
        break;
      default:
        compare = game.getWhitePlayer();
        break;
    }

    if (game.hasPlayer(player) && player.equals(compare)) {
      return new Message("Move undone", MESSAGE_TYPE.info);
    } else {
      return new Message("Could not undo move", MESSAGE_TYPE.error);
    }
  }
}
