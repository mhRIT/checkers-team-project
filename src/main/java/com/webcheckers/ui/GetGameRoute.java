package com.webcheckers.ui;

import static com.webcheckers.ui.GetHomeRoute.PLAYER;
import static spark.Spark.halt;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.ui.boardView.BoardView;
import com.webcheckers.ui.boardView.Message;
import com.webcheckers.ui.boardView.Message.MESSAGE_TYPE;
import com.webcheckers.model.Player;
import com.webcheckers.ui.PostSelectOpponentRoute.VIEW_MODE;
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

public class GetGameRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

  private static int count = 0;

  private final GameCenter gameCenter;
  private final TemplateEngine templateEngine;

  public GetGameRoute(GameCenter gameCenter, final TemplateEngine templateEngine) {
    LOG.setLevel(Level.ALL);
    // validation
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");

    //
    // Attributes
    //
    this.gameCenter = gameCenter;
    this.templateEngine = templateEngine;
  }

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
