package com.webcheckers.ui.HtmlRoutes;

import static com.webcheckers.ui.HtmlRoutes.GetHomeRoute.OPP_MESSAGE;
import static com.webcheckers.ui.HtmlRoutes.GetHomeRoute.PLAYER;
import static com.webcheckers.ui.HtmlRoutes.PostBuildConfig.INIT_CONFIG;
import static spark.Spark.halt;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Board.InitConfig;
import com.webcheckers.model.Board.InitConfig.START_TYPE;
import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Player.Player;
import com.webcheckers.ui.WebServer;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

/**
 * The {@code POST /selectOpponent} route handler.
 * This is the page where the user starts.
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mvm7902@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
public class PostSelectOpponentRoute extends HtmlRoute {
  //
  //Constants
  //
  public static final String TITLE_ATTR = "title";
  public static final String TITLE = "GameState!";
  public static final String OPP_PLAYER_NAME = "opponent";

  //
  // Enums
  //
  public enum VIEW_MODE {PLAY, SPECTATOR, REPLAY}

  /**
   * Create the Spark Route (UI controller) for the {@code POST /selectOpponent} HTTP request.
   * {@inheritDoc}
   */
  public PostSelectOpponentRoute(final GameCenter gameCenter, final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
    super(gameCenter, playerLobby, templateEngine);
  }

  /**
   * Render the WebCheckers GameState page or the Home page, depending on whether
   * the selected opponent is already in a game or not.
   * {@inheritDoc}
   */
  @Override
  public Object handle(Request request, Response response) {
    final Session session = request.session();
    String currPlayerName = session.attribute(PLAYER);
    Player currPlayer = playerLobby.getPlayer(currPlayerName);

    if(!checkValidPlayerName(currPlayerName)){
      response.redirect(WebServer.HOME_URL);
      halt();
      return "nothing";
    }

    String oppPlayerName = request.queryParams(OPP_PLAYER_NAME);
    Player opponent = playerLobby.getPlayer(oppPlayerName);
    GameContext game = gameCenter.getGame(opponent);

    if (game != null && !opponent.isAi() && !game.isGameOver()) {
      String message = String.format("The selected opponent, '%s', is already in a game",
          opponent.getName());
      LOG.finer(message);
      session.attribute(OPP_MESSAGE, message);
      response.redirect(WebServer.HOME_URL);
      halt();
      return "nothing";
    }

    InitConfig gameConfig = session.attribute(INIT_CONFIG);
    gameCenter.createGame(currPlayer, opponent, gameConfig);
    response.redirect(WebServer.GAME_URL);
    halt();
    return "nothing";
  }
}