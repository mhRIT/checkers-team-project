package com.webcheckers.ui.HtmlRoutes;

import static com.webcheckers.ui.HtmlRoutes.GetHomeRoute.PLAYER;
import static spark.Spark.halt;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Player.Player;
import com.webcheckers.ui.HtmlRoutes.PostSelectOpponentRoute.VIEW_MODE;
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.boardView.BoardView;
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
 * The {@code GET /game} route handler.
 * This is the page that displays the state of the game and allows
 * the user to make a move.
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mvm7902@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
public class GetGameRoute implements Route {
  //
  // Constants
  //
  public static final String TITLE_ATTR = "title";
  public static final String TITLE = "GameState!";
  public static final String VIEW_NAME = "game.ftl";
  public static final String GAME_ID = "gameId";

  //
  // Attributes
  //
  private final GameCenter gameCenter;
  private final PlayerLobby playerLobby;
  private final TemplateEngine templateEngine;
  private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

  /**
   * Create the Spark Route (UI controller) for the {@code GET /game} HTTP request.
   *
   * @param gameCenter  the {@link GameCenter} for tracking all ongoing games
   * @param templateEngine the {@link TemplateEngine} used for rendering page HTML.
   * @throws NullPointerException when the {@code gameCenter}, {@code playerLobby}, or {@code
   * templateEngine} parameter is null
   */
  public GetGameRoute(final GameCenter gameCenter,
      final PlayerLobby playerLobby,
      final TemplateEngine templateEngine) {
    LOG.setLevel(Level.ALL);
    // validation
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");

    this.gameCenter = gameCenter;
    this.playerLobby = playerLobby;
    this.templateEngine = templateEngine;
  }

  /**
   * {@inheritDoc}
   * Render the WebCheckers GameState page.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return the rendered HTML for the GameState page
   */
  @Override
  public Object handle(Request request, Response response) {
    final Session session = request.session();
    String currPlayerName = session.attribute(PLAYER);
    Player currPlayer = playerLobby.getPlayer(currPlayerName);
    GameContext game = gameCenter.getGame(currPlayer);

    if(currPlayer == null || game == null){
      response.redirect(WebServer.HOME_URL);
      halt();
      return "nothing";
    }

    LOG.finer(String.format("GetGameRoute is invoked: %s -> %s",
        currPlayer.getName(),
        game.toString()));

    if(game.isGameOver()){
      response.redirect(WebServer.HOME_URL);
      halt();
      return "nothing";
    }

    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Game!");
    vm.put("currentPlayer", currPlayer);
    vm.put("redPlayer", game.getRedPlayer());
    vm.put("whitePlayer", game.getWhitePlayer());
    vm.put("activeColor", game.getActiveColor());
    vm.put("board", new BoardView(game, currPlayer));
    vm.put("viewMode", VIEW_MODE.PLAY);

    return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
  }
}
