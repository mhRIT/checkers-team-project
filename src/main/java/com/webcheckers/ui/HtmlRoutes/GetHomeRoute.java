package com.webcheckers.ui.HtmlRoutes;

import static spark.Spark.halt;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Player.Player;
import com.webcheckers.ui.WebServer;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

/**
 * The {@code GET /} route handler.
 * This is the page where the user starts.
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mvm7902@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
public class GetHomeRoute extends HtmlRoute {
  //
  //Constants
  //

  public static final String TITLE_ATTR = "title";
  public static final String TITLE = "Welcome!";
  public static final String VIEW_NAME = "home.ftl";
  public static final String PLAYER = "player";
  public static final String AI_PLAYER_NAMES = "aiPlayers";
  public static final String ALL_PLAYER_NAMES = "allPlayers";
  public static final String NUM_PLAYERS = "numPlayers";
  public static final String MESSAGE = "message";

  //
  // Constructor
  //
  /**
   * Create the Spark Route (UI controller) for the {@code GET /signin} HTTP request.
   * {@inheritDoc}
   */
  public GetHomeRoute(final GameCenter gameCenter, final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
    super(gameCenter, playerLobby, templateEngine);
  }

  /**
   * Render the WebCheckers Home page or the GameState page, depending on whether the current
   * player is in a game or not.
   * {@inheritDoc}
   *
   */
  @Override
  public Object handle(Request request, Response response) {
    final Session session = request.session();
    String currPlayerName = session.attribute(PLAYER);
    Player currPlayer = playerLobby.getPlayer(currPlayerName);

    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");

    if(currPlayer == null){
      LOG.finer("GetHomeRoute is invoked: no player attached to the current session");
      vm.put(NUM_PLAYERS, playerLobby.getNumPlayers());
    } else {
      vm.put(AI_PLAYER_NAMES, playerLobby.aiNames());
      vm.put(ALL_PLAYER_NAMES, playerLobby.playerNames(currPlayer.getName()));
      vm.put(PLAYER, currPlayer);

      GameContext game = gameCenter.getGame(currPlayer);
      if(game != null) {
        if(game.isGameOver()){
          vm.put(MESSAGE, game.endMessage());
          LOG.finer("GetHomeRoute game is ended");
          vm.put(MESSAGE, game.endMessage());
        } else {
          response.redirect(WebServer.GAME_URL);
          halt();
          return "nothing";
        }
      }
    }

    return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
  }
}