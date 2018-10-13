package com.webcheckers.ui;

import static spark.Spark.halt;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

/**
 * The UI Controller to GET the Home page.
 * This is the page where the user starts.
 *
 * @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  //
  //Constants
  //

  private static final String VIEW_NAME = "home.ftl";
  private static final String PLAYER = "player";
  private static final String ALL_PLAYER_NAMES = "allPlayers";
  private static final String NUM_PLAYERS = "numPlayers";

  //
  // Attributes
  //

  private final GameCenter gameCenter;
  private final PlayerLobby playerLobby;
  private final TemplateEngine templateEngine;

  //
  // Constructor
  //

  /**
   * Create the Spark Route (UI controller) for the {@code GET /} HTTP request.
   *
   * @param templateEngine the HTML template rendering engine
   * @throws NullPointerException when the {@code gameCenter}, {@code playerLobby}, or {@code
   * templateEngine} parameter is null
   */
<<<<<<< HEAD
  public GetHomeRoute(final GameCenter gameCenter,
                      final PlayerLobby playerLobby,
                      final TemplateEngine templateEngine) {
    LOG.setLevel(Level.ALL);
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");

    //
    this.playerLobby = playerLobby;
    this.gameCenter = gameCenter;
    this.templateEngine = templateEngine;
    //
    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    final Session session = request.session();
    Player player = session.attribute(PLAYER);
    if(currPlayer == null){
      LOG.finer("GetHomeRoute is invoked: no player attached to the current session");
    } else {
      LOG.finer(String.format("Player \'%s\' is %sin a game",
                              currPlayer.getName(),
                              gameCenter.isPlayerInGame(currPlayer) ? "" : "not "));
    }

    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");
<<<<<<< HEAD

    if (currPlayer == null) {
      vm.put(NUM_PLAYERS, playerLobby.getNumPlayers());
    } else {
      vm.put(ALL_PLAYER_NAMES, playerLobby.playerNames(player.getName()));
      vm.put("currentPlayer", currPlayer);

      // if player is in game, go to game page
      // else go to home page
      if(gameCenter.isPlayerInGame(currPlayer)) {
        response.redirect(WebServer.GAME_URL);
        halt();
        return "nothing";
      }
    }

    return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
  }
}