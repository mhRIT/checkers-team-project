package com.webcheckers.ui;

import static com.webcheckers.ui.GetHomeRoute.ALL_PLAYER_NAMES;
import static com.webcheckers.ui.GetHomeRoute.PLAYER;
import static spark.Spark.halt;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.ui.boardView.BoardView;
import com.webcheckers.ui.boardView.Message;
import com.webcheckers.ui.boardView.Message.MESSAGE_TYPE;
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
   * Render the WebCheckers Game page or the Home page, depending on whether
   * the selected opponent is already in a game or not.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return the rendered HTML for the game page
   */
  @Override
  public Object handle(Request request, Response response) {
    final Session session = request.session();
    Player currPlayer = session.attribute(PLAYER);
    Player opponent = playerLobby.getPlayer(request.queryParams(OPP_PLAYER_NAME));
    LOG.finer("PostSelectOpponentRoute is invoked: " + currPlayer.getName());

    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");

    if (gameCenter.isPlayerInGame(opponent)) {
      String message = String.format("The selected opponent, %s, is already in a game",
          opponent.getName());
      LOG.finer(message);

      LOG.finer(String.format("Player \'%s\' is %sin a game",
          currPlayer.getName(),
          gameCenter.isPlayerInGame(currPlayer) ? "" : "not "));
      vm.put(ALL_PLAYER_NAMES, playerLobby.playerNames(currPlayer.getName()));
      vm.put(PLAYER, currPlayer);
      vm.put(MESSAGE, message);

//      response.redirect(WebServer.HOME_URL);
//      halt();
      return templateEngine.render(new ModelAndView(vm, "home.ftl"));
    }

    Game game = new Game(currPlayer, opponent);
    gameCenter.addGame(game);

    vm.put("title", "Game!");
    vm.put("currentPlayer", currPlayer);
    vm.put("viewMode", VIEW_MODE.PLAY);
    vm.put("redPlayer", game.getRedPlayer());
    vm.put("whitePlayer", game.getWhitePlayer());
    vm.put("activeColor", game.getActiveColor());
    vm.put("board", new BoardView(game.getState(currPlayer)));
    vm.put("message", new Message("This is a triumph", MESSAGE_TYPE.info));

    return templateEngine.render(new ModelAndView(vm, "game.ftl"));
  }
}