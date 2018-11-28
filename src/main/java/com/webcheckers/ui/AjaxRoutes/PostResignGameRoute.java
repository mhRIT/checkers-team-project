package com.webcheckers.ui.AjaxRoutes;

import static com.webcheckers.ui.HtmlRoutes.GetHomeRoute.PLAYER;
import static spark.Spark.halt;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.ui.HtmlRoutes.PostSigninRoute;
import com.webcheckers.ui.boardView.Message;
import com.webcheckers.ui.boardView.Message.MESSAGE_TYPE;
import com.webcheckers.model.Player.Player;

import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

/**
 * The {@code POST /resignGame} route handler.
 * This is the page where the user starts.
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
public class PostResignGameRoute extends AjaxRoute {

  /**
   * Create the Spark Route (UI controller) for the {@code POST /resignGame} HTTP request.
   * {@inheritDoc}
   */
  public PostResignGameRoute(GameCenter gameCenter, PlayerLobby playerLobby, Gson gson) {
    super(gameCenter, playerLobby, gson);
  }

  /**
   * Redirects to the WebCheckers Home page.
   * {@inheritDoc}
   */
  @Override
  public Object handle(Request request, Response response) {
    final Session session = request.session();
    String currPlayerName = session.attribute(PLAYER);
    Player currPlayer = playerLobby.getPlayer(currPlayerName);

    if(currPlayer != null){
      if(gameCenter.resignAll(currPlayer)){
        return new Message("", MESSAGE_TYPE.info);
      }
      else{
        return new Message("", MESSAGE_TYPE.error);
      }
    } else {
      return new Message("", MESSAGE_TYPE.error);
    }
  }
}
