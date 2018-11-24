package com.webcheckers.ui.HtmlRoutes;

import static com.webcheckers.ui.HtmlRoutes.GetHomeRoute.ALL_PLAYER_NAMES;
import static com.webcheckers.ui.HtmlRoutes.GetHomeRoute.PLAYER;
import static spark.Spark.halt;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Player;
import com.webcheckers.ui.WebServer;
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
 * The {@code POST /selectOpponent} route handler.
 * This is the page where the user starts.
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mvm7902@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
public class PostSelectOpponentRoute implements Route {

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

  public enum VIEW_MODE {PLAY, SPECTATOR, REPLAY;}

  //
  // Attributes
  //

  private final GameCenter gameCenter;
  private final PlayerLobby playerLobby;
  private final TemplateEngine templateEngine;
  private static final Logger LOG = Logger.getLogger(PostSelectOpponentRoute.class.getName());

  /**
   * Create the Spark Route (UI controller) for the {@code POST /selectOpponent} HTTP request.
   *
   * @param gameCenter  the {@link GameCenter} for tracking all ongoing games
   * @param playerLobby the {@link PlayerLobby} for tracking all signed in players
   * @param templateEngine the {@link TemplateEngine} used for rendering page HTML.
   * @throws NullPointerException when the {@code gameCenter}, {@code playerLobby}, or {@code
   * templateEngine} parameter is null
   */
  public PostSelectOpponentRoute(final GameCenter gameCenter, final PlayerLobby playerLobby,
      final TemplateEngine templateEngine) {
    LOG.setLevel(Level.ALL);
    // validation
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");

    //
    this.playerLobby = playerLobby;
    this.gameCenter = gameCenter;
    this.templateEngine = templateEngine;

    //
    LOG.config("PostSelectOpponentRoute is initialized.");
  }

  /**
   * {@inheritDoc}
   * Render the WebCheckers GameState page or the Home page, depending on whether
   * the selected opponent is already in a game or not.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return the rendered HTML for the game page
   */
  @Override
  public Object handle(Request request, Response response) {
    final Session session = request.session();
    String currPlayerName = session.attribute(PLAYER);
    Player currPlayer = playerLobby.getPlayer(currPlayerName);

    Player opponent = playerLobby.getPlayer(request.queryParams(OPP_PLAYER_NAME));
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
    if (game != null && !game.isGameOver()) {
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