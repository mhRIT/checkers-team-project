package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
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
 * The {@code POST /validateMove} route handler.
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mvm7902@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
public class PostValidateMoveRoute implements Route {
  //
  // Attributes
  //

  private final GameCenter gameCenter;
  private final Gson gson;
  private final PlayerLobby playerLobby;
  private final TemplateEngine templateEngine;
  private static final Logger LOG = Logger.getLogger(PostSigninRoute.class.getName());

  /**
   * Create the Spark Route (UI controller) for the {@code POST /validateMove} HTTP request.
   *
   * @param gameCenter  the {@link GameCenter} for tracking all ongoing games
   * @param playerLobby the {@link PlayerLobby} for tracking all signed in players
   * @param templateEngine the {@link TemplateEngine} used for rendering page HTML.
   * @throws NullPointerException when the {@code gameCenter}, {@code playerLobby}, or {@code
   * templateEngine} parameter is null
   */
  public PostValidateMoveRoute(GameCenter gameCenter, Gson gson, PlayerLobby playerLobby,
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

  /**
   * {@inheritDoc}
   * Render the WebCheckers Game page.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return JSON response of whether the move is valid
   */
  @Override
  public Object handle(Request request, Response response) {
    final Session session = request.session();
    Player player = session.attribute("player");

    if(player == null || playerLobby.getPlayer(player.getName()) == null) {
      return new Message("Invalid move", MESSAGE_TYPE.error);
    }

    Game game = gameCenter.getGames(player)[0];
    LOG.finer("PostValidateMoveRoute is invoked: " + player.getName());

    Move requestMove = gson.fromJson(request.body(), Move.class);
    if(game.validateMove(requestMove)){
      return new Message("Valid move", MESSAGE_TYPE.info);
    } else {
      return new Message("Invalid move", MESSAGE_TYPE.error);
    }
  }
}
