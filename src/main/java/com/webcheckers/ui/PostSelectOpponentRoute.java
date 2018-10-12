package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Message.MESSAGE_TYPE;
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
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class PostSelectOpponentRoute implements Route {

  private static final Logger LOG = Logger.getLogger(PostSelectOpponentRoute.class.getName());

  private final GameCenter gameCenter;
  private final PlayerLobby playerLobby;
  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) for the {@code GET /selectOpponent} HTTP request.
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
   * Render the WebCheckers Game page.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    final Session session = request.session();
    Player currPlayer = session.attribute("player");
    Player opponent = playerLobby.getPlayer(request.queryParams("opponent"));
    LOG.finer("PostSelectOpponentRoute is invoked: " + currPlayer.getName());

    Map<String, Object> vm = new HashMap<>();

    if (gameCenter.isPlayerInGame(opponent)) {
      String message = String.format("The selected opponent, %s, is already in a game",
          opponent.getName());

      LOG.config(message);

      vm.put("title", "Welcome!");
      vm.put("message", message);
      vm.put("players", playerLobby.getPlayerNames());
      vm.put("currentPlayer", currPlayer);

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
    vm.put("board", game.getState(currPlayer));
    vm.put("message", new Message("This is a triumph", MESSAGE_TYPE.info));

    return templateEngine.render(new ModelAndView(vm, "game.ftl"));
  }

  public enum VIEW_MODE {PLAY, SPECTATOR, REPLAY}
}