package com.webcheckers.ui;

import static com.webcheckers.ui.GetHomeRoute.PLAYER;
import static spark.Spark.halt;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.ui.PostSelectOpponentRoute.VIEW_MODE;
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
 * The {@code GET /game} route handler.
 * This is the page that displays the state of the game and allows
 * the user to make a move.
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:mlh1964@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mlh1964@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:mlh1964@rit.edu'>Andrew Festa</a>
 */
public class GetGameRoute implements Route {
  //
  // Statics
  //
  private static int count = 0; // count of times this Route handler has been invoked

  //
  // Attributes
  //
  private final GameCenter gameCenter;
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
  public GetGameRoute(GameCenter gameCenter, final TemplateEngine templateEngine) {
    LOG.setLevel(Level.ALL);
    // validation
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");


    this.gameCenter = gameCenter;
    this.templateEngine = templateEngine;
  }

  /**
   * Render the WebCheckers Game page.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return the rendered HTML for the Game page
   */
  @Override
  public Object handle(Request request, Response response) {
    final Session session = request.session();
    Player player = session.attribute(PLAYER);
    LOG.finer("GetGameRoute is invoked (" + count++ + "): " + player.getName());

    Game[] gameList = gameCenter.getGames(player);
    Game game;

    if(gameList.length > 0){
      game = gameList[0];
    } else {
      response.redirect(WebServer.HOME_URL);
      halt();
      return "nothing";
    }

    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Game!");
    vm.put("currentPlayer", player);
    vm.put("viewMode", VIEW_MODE.PLAY);
    vm.put("redPlayer", game.getRedPlayer());
    vm.put("whitePlayer", game.getWhitePlayer());
    vm.put("activeColor", game.getActiveColor());
    vm.put("board", new BoardView(game.getState(player)));
    vm.put("message", new Message("GetGameRoute", MESSAGE_TYPE.info));

    return templateEngine.render(new ModelAndView(vm, "game.ftl"));
  }
}
