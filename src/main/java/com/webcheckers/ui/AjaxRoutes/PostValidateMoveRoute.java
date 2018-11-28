package com.webcheckers.ui.AjaxRoutes;

import static com.webcheckers.ui.HtmlRoutes.GetHomeRoute.PLAYER;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Board.Board;
import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Board.Move;
import com.webcheckers.model.Player.Player;
import com.webcheckers.ui.boardView.Message;
import com.webcheckers.ui.boardView.Message.MESSAGE_TYPE;
import spark.Request;
import spark.Response;
import spark.Session;

/**
 * The {@code POST /validateMove} route handler.
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mvm7902@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
public class PostValidateMoveRoute extends AjaxRoute {

  /**
   * Create the Spark Route (UI controller) for the {@code POST /validateMove} HTTP request.
   * {@inheritDoc}
   */
  public PostValidateMoveRoute(GameCenter gameCenter, PlayerLobby playerLobby, Gson gson) {
    super(gameCenter, playerLobby, gson);
  }

  /**
   * Render the WebCheckers Game page.
   * {@inheritDoc}
   */
  @Override
  public Object handle(Request request, Response response) {
    final Session session = request.session();
    String currPlayerName = session.attribute(PLAYER);
    Player currPlayer = playerLobby.getPlayer(currPlayerName);
    GameContext game = gameCenter.getGame(currPlayer);

    Move requestMove = gson.fromJson(request.body(), Move.class);
    requestMove.setType();
    if(currPlayer.equals(game.getWhitePlayer())){
      requestMove = Board.invertMove(requestMove);
    }
    currPlayer.putNextMove(game, requestMove);

    if(!game.isTurnOver() && game.proceed()){
      return new Message("Valid move", MESSAGE_TYPE.info);
    } else {
      if (game.isGameOver()){
        return new Message("Your opponent has resigned", MESSAGE_TYPE.info);
      } else {
        return new Message("You are not allowed to make that move", MESSAGE_TYPE.error);
      }
    }
  }
}
