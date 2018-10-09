package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import game.ftl;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetGameRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) for the
   * {@code GET /game} HTTP request.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetGameRoute(final TemplateEngine templateEngine) {
    // validation
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.templateEngine = templateEngine;
    //
    LOG.config("GetGameRoute is initialized.");
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
    LOG.finer("GetGameRoute is invoked.");
    
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Game!");

    if(checkGameCenter(gameCenter, currentPlayer)){

      vm.put("whitePlayer", );
    }

    return templateEngine.render(new ModelAndView(vm , "game.ftl"));
  }

  public Boolean checkGameCenter(GameCenter gameCenter, Player player){
    return gameCenter.isPlayerInGame(player);
  }

}