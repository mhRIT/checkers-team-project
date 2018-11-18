package com.webcheckers.ui;

import static spark.Spark.halt;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import java.util.ArrayList;
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

  public static final String TITLE_ATTR = "title";
  public static final String TITLE = "Welcome!";
  public static final String VIEW_NAME = "home.ftl";
  public static final String PLAYER = "player";
  public static final String ALL_PLAYER_NAMES = "allPlayers";
  public static final String NUM_PLAYERS = "numPlayers";
  public static final String MESSAGE = "message";

  //
  // Attributes
  //

  private final GameCenter gameCenter;
  private final PlayerLobby playerLobby;
  private final TemplateEngine templateEngine;
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private ArrayList<Player> outOfGame = new ArrayList<Player>();

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
        Game game = gameCenter.getGames(currPlayer)[0];
        if(game.checkEnd()){
          LOG.finer("GetHomeRoute game is ended");
          vm.put(MESSAGE,game.endMessage());
          if(!outOfGame.contains(currPlayer)){
            LOG.finer("player," +currPlayer.getName()+" is added to game"+request.url());
            outOfGame.add(currPlayer);
          }
          Player p1 = game.getRedPlayer();
          Player p2 = game.getWhitePlayer();
          if(outOfGame.contains(p1) && outOfGame.contains(p2)){
            gameCenter.removeGame(game);
            outOfGame.remove(p1);
            outOfGame.remove(p2);
          }
        }else{
        response.redirect(WebServer.GAME_URL);
        halt();
        return "nothing";
        }
      }
    }

    return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
  }
}