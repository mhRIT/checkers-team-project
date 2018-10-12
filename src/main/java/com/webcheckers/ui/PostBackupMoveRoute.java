package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
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

public class PostBackupMoveRoute implements Route {

  private static final Logger LOG = Logger.getLogger(PostBackupMoveRoute.class.getName());

  private final GameCenter gameCenter;
  private final Gson gson;
  private final PlayerLobby playerLobby;
  private final TemplateEngine templateEngine;

  public PostBackupMoveRoute(GameCenter gameCenter, Gson gson, PlayerLobby playerLobby,
      final TemplateEngine templateEngine) {
    LOG.setLevel(Level.ALL);
    // validation
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    Objects.requireNonNull(gson, "gson must not be null");
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");

    //
    // Attributes
    //
    this.gameCenter = gameCenter;
    this.gson = gson;
    this.playerLobby = playerLobby;
    this.templateEngine = templateEngine;
  }

  @Override
  public Object handle(Request request, Response response) {

    final Session session = request.session();
    Player player = session.attribute("player");
    Game game = gameCenter.getGames(player)[0];
    LOG.finer("PostBackupMoveRoute is invoked: " + player.getName());

    //
    Map<String, String> map = gson.fromJson(request.body(), Map.class);
    Map<String, Object> vm = new HashMap<>();

    vm.put("title", "Game!");
    vm.put("currentPlayer", player);
    vm.put("viewMode", VIEW_MODE.PLAY);
    vm.put("redPlayer", game.getRedPlayer());
    vm.put("whitePlayer", game.getWhitePlayer());
    vm.put("activeColor", game.getActiveColor());
    vm.put("board", game.getState(player));
    vm.put("message", new Message("Backup?", MESSAGE_TYPE.info));

    return templateEngine.render(new ModelAndView(vm, "game.ftl"));
  }
}
