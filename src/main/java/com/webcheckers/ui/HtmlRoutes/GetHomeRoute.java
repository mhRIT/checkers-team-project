package com.webcheckers.ui.HtmlRoutes;

import static com.webcheckers.ui.HtmlRoutes.PostBuildConfig.INIT_CONFIG;
import static spark.Spark.halt;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Board.InitConfig;
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
  public static final String OPP_MESSAGE = "oppMessage";
  public static final String GAME_MESSAGE = "gameMessage";
  private static final String CONFIG_TYPE = "configType";
  private static final String CONFIG_NUM_RED = "configNumRed";
  private static final String CONFIG_NUM_WHITE = "configNumWhite";
  private static final String CONFIG_PRESET = "configPreset";

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
    String oppMessage = session.attribute(OPP_MESSAGE);
    InitConfig config = session.attribute(INIT_CONFIG);

    if(config == null){
      config = new InitConfig();
      session.attribute(INIT_CONFIG, config);
    }

    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");
    if(oppMessage != null){
      vm.put(OPP_MESSAGE, oppMessage);
    }

    if(currPlayer == null){
      LOG.finer("GetHomeRoute is invoked: no player attached to the current session");
      vm.put(NUM_PLAYERS, playerLobby.getNumPlayers());
      vm.put("signedIn", false);
    } else {
      vm.put(AI_PLAYER_NAMES, playerLobby.aiNames());
      vm.put(ALL_PLAYER_NAMES, playerLobby.playerNames(currPlayer.getName()));
      vm.put(PLAYER, currPlayer);
      
      vm.put(CONFIG_TYPE, config.getType());
      vm.put(CONFIG_NUM_RED, config.getNumRedPieces());
      vm.put(CONFIG_NUM_WHITE, config.getNumWhitePieces());
      vm.put(CONFIG_PRESET, config.getPreset());
      
      vm.put("signedIn", true);

      GameContext game = gameCenter.getGame(currPlayer);
      if(game != null) {
        if(game.isGameOver()){
          vm.put(GAME_MESSAGE, game.endMessage());
          LOG.finer("GetHomeRoute game is ended");
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