package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

/**
 * The UI Controller to GET the Home page.
 * This is the page where the user starts.
 *
 * @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 */
public class GetHomeRoute implements Route {

  //
  //Constants
  //

  private static final String VIEW_NAME = "home.ftl";
  private static final String PLAYER = "player";
  private static final String ALL_PLAYER_NAMES = "allPlayers";
  private static final String NUM_PLAYERS = "numPlayers";

  //
  // Attributes
  //

  private final PlayerLobby playerLobby;
  private final TemplateEngine templateEngine;
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  //
  // Constructor
  //

  /**
   * Create the Spark Route (UI controller) for the
   * {@code GET /} HTTP request.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
    // validation
    Objects.requireNonNull(playerLobby,"playerLobby must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.playerLobby = playerLobby;
    this.templateEngine = templateEngine;
    //
    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetHomeRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");

    // retrieve the session, and the player
    final Session session = request.session();
    Player player = session.attribute(PLAYER);

    LOG.setLevel(Level.FINER);

    // if the player is signed-in show list of all signed-in players
    if(player != null){
      LOG.finer("The player is not null");
      String[] list = playerLobby.playerNames();
      vm.put(ALL_PLAYER_NAMES, list);
    }
    // if the player is not signed-in show how many players are signed-in
    else{
      LOG.finer("The player is null");
      String numberOfPlayers = "" + playerLobby.numberOfPlayers();
      vm.put(NUM_PLAYERS, numberOfPlayers);
    }

    return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
  }

}