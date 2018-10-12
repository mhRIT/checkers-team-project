package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Message.MESSAGE_TYPE;
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

public class PostMoveRoute implements Route {

  private static final Logger LOG = Logger.getLogger(PostSigninRoute.class.getName());

  private final GameCenter gameCenter;
  private final PlayerLobby playerLobby;
  private final TemplateEngine templateEngine;

  public PostMoveRoute(GameCenter gameCenter, PlayerLobby playerLobby,
      final TemplateEngine templateEngine) {
    LOG.setLevel(Level.ALL);
    // validation
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");

    //
    // Attributes
    //
    this.gameCenter = gameCenter;
    this.playerLobby = playerLobby;
    this.templateEngine = templateEngine;
  }

  @Override
  public Object handle(Request request, Response response) {
    final Session session = request.session();
    Player player = session.attribute("player");
    Game game = gameCenter.getGames(player)[0];
    LOG.finer("PostMoveRoute is invoked: " + player.getName());

    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Game!");
    vm.put("currentPlayer", player);
    vm.put("viewMode", VIEW_MODE.PLAY);
    vm.put("redPlayer", game.getRedPlayer());
    vm.put("whitePlayer", game.getWhitePlayer());
    vm.put("activeColor", game.getActiveColor());
    vm.put("board", game.getState(player));
    vm.put("message", new Message("PostMove", MESSAGE_TYPE.info));

    return templateEngine.render(new ModelAndView(vm, "game.ftl"));
  }
}
