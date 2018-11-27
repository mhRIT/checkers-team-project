package com.webcheckers.ui.HtmlRoutes;

import static com.webcheckers.ui.HtmlRoutes.GetHomeRoute.ALL_PLAYER_NAMES;
import static com.webcheckers.ui.HtmlRoutes.GetHomeRoute.PLAYER;
import static spark.Spark.halt;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Board.InitConfig;
import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Player.Player;
import com.webcheckers.ui.WebServer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
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
  public static final String MESSAGE = "message";

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

    String oppPlayerName = request.queryParams(OPP_PLAYER_NAME);
    Gson gson = new Gson();
    InitConfig gameConfig = gson.fromJson(request.body(), InitConfig.class);
    Player opponent = playerLobby.getPlayer(oppPlayerName);
    Map<String, Object> vm = new HashMap<>();


    if(currPlayer == null || playerLobby.getPlayer(currPlayer.getName()) == null) {
      String message = "PostSelectOpponentRoute is invoked with no player stored in the current " +
          "session or the player is not signed in.";
      LOG.finer(message);

      response.redirect(WebServer.HOME_URL);
      halt();
      return "nothing";
    }

    LOG.finer("PostSelectOpponentRoute is invoked: " + currPlayer.getName());
    GameContext game = gameCenter.getGame(opponent);
    if (game != null && !opponent.isAi() && !game.isGameOver()) {
      String message = String.format("The selected opponent, %s, is already in a game",
          opponent.getName());
      LOG.finer(message);
      vm.put(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
      vm.put(ALL_PLAYER_NAMES, playerLobby.playerNames(currPlayer.getName()));
      vm.put(PLAYER, currPlayer);
      vm.put(MESSAGE, message);

      return templateEngine.render(new ModelAndView(vm, "home.ftl"));
    }

    gameCenter.createGame(currPlayer, opponent);
    response.redirect(WebServer.GAME_URL);
    halt();
    return "nothing";
  }
}