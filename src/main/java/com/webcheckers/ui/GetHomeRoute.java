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
 * The {@code GET /} route handler.
 * This is the page where the user starts.
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mvm7902@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
public class GetHomeRoute implements Route {
  //
  //Constants
  //

  public static final String VIEW_NAME = "home.ftl";
  public static final String PLAYER = "player";
  public static final String ALL_PLAYER_NAMES = "allPlayers";
  public static final String NUM_PLAYERS = "numPlayers";

  //
  // Attributes
  //

  private final GameCenter gameCenter;
  private final PlayerLobby playerLobby;
  private final TemplateEngine templateEngine;
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  //
  // Constructor
  //

  /**
   * Create the Spark Route (UI controller) for the {@code GET /signin} HTTP request.
   *
   * @param gameCenter  the {@link GameCenter} for tracking all ongoing games
   * @param playerLobby the {@link PlayerLobby} for tracking all signed in players
   * @param templateEngine the {@link TemplateEngine} used for rendering page HTML.
   * @throws NullPointerException when the {@code gameCenter}, {@code playerLobby}, or {@code
   * templateEngine} parameter is null
   */
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
   * {@inheritDoc}
   * Render the WebCheckers Home page or the Game page, depending on whether the current
   * player is in a game or not.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    final Session session = request.session();
    Player currPlayer = session.attribute(PLAYER);

    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");

    if(currPlayer == null){
      LOG.finer("GetHomeRoute is invoked: no player attached to the current session");
      vm.put(NUM_PLAYERS, playerLobby.getNumPlayers());
    } else {
      LOG.finer(String.format("Player \'%s\' is %sin a game",
                              currPlayer.getName(),
                              gameCenter.isPlayerInGame(currPlayer) ? "" : "not "));
      vm.put(ALL_PLAYER_NAMES, playerLobby.playerNames(currPlayer.getName()));
      vm.put(PLAYER, currPlayer);

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