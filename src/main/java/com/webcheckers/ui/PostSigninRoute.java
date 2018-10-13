package com.webcheckers.ui;

import static com.webcheckers.ui.GetSigninRoute.TITLE_ATTR;
import static spark.Spark.halt;

import com.webcheckers.application.PlayerLobby;
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

public class PostSigninRoute implements Route {

  //
  // Attributes
  //

  private final PlayerLobby playerLobby;
  private final TemplateEngine templateEngine;
  private static final Logger LOG = Logger.getLogger(PostSigninRoute.class.getName());

  //
  // Constants
  //

  private static final String USERNAME = "username";
  private static final String TITLE = "Welcome";
  private static final String VIEW_NAME = "home.ftl";
  private static final String PLAYER = "player";

  static final String MESSAGE_ATTR = "message";
  static final String MESSAGE_TYPE_ATTR = "messageType";
  static final String ERROR_TYPE = "error";
  static final String ERROR_VIEW_NAME = "signin.ftl";
  static final String BAD_USERNAME =
          "Username must consists of only letters " +
          "or numbers and be at least one character long.";
  static final String TAKEN_USERNAME = "Username is already in use by another player";

  public PostSigninRoute(PlayerLobby playerLobby, final TemplateEngine templateEngine) {
    LOG.setLevel(Level.ALL);
    // validation
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.playerLobby = playerLobby;
    this.templateEngine = templateEngine;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object handle(Request request, Response response){
    final Session session = request.session();
    String username = request.queryParams(USERNAME);
    LOG.finer("PostSigninRoute is invoked: " + username);

    // start building View-Model
    final Map<String, Object> vm = new HashMap<String,Object>();
    vm.put(TITLE_ATTR, TITLE);
    ModelAndView mv;

    // store player in httpSession
    if(session.attribute(PLAYER) == null){
        if(playerLobby.signin(username)){
          Player player = playerLobby.getPlayer(username);
          session.attribute(PLAYER, player);
      } else {
        mv = error(vm,TAKEN_USERNAME);
        return templateEngine.render(mv);
      }
    }

    // the player signs-in correctly, redirect to the homepage
    if(playerLobby.getPlayer(username) != null){
      response.redirect(WebServer.HOME_URL);
      halt();
      return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
    else {
      // the player did not sign in correctly, redirect to sign-in-page
      mv = error(vm,BAD_USERNAME);
      return templateEngine.render(mv);
    }
  }

  /**
   * Creates an error message and stores it in the View_Model.
   */
  private ModelAndView error(final Map<String, Object> vm, final String message) {
    vm.put(MESSAGE_ATTR, message);
    vm.put(MESSAGE_TYPE_ATTR, ERROR_TYPE);
    return new ModelAndView(vm, ERROR_VIEW_NAME);
  }
}
