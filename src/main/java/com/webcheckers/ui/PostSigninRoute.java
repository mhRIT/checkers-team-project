package com.webcheckers.ui;

import static com.webcheckers.ui.GetSigninRoute.TITLE_ATTR;
import static spark.Spark.halt;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

/**
 * The {@code POST /signin} route handler.
 *
 * @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 */
public class PostSigninRoute implements Route {

  //
  // Constants
  //
  private static final String USERNAME = "username";
  private static final String TITLE = "Welcome";
  private static final String VIEW_NAME = "home.ftl";
  private static final String PLAYER_USERNAME = "playerUsername";

  static final String MESSAGE_ATTR = "message";
  static final String MESSAGE_TYPE_ATTR = "messageType";
  static final String ERROR_TYPE = "error";
  static final String BAD_USERNAME = "Username must consists of only letters" +
          "or numbers and be at least one character long.";

  //
  // Attributes
  //

  private final PlayerLobby playerLobby;
  private final TemplateEngine templateEngine;
  private static final Logger LOG = Logger.getLogger(PostSigninRoute.class.getName());

  //
  // Constructor
  //

  /**
   * The constructor for the {@code POST /signin} route handler.
   *
   * @param playerLobby
   *    {@Link PlayerLobby} that holds the username of each signed-in player
   * @param templateEngine
   *    template engine to use for rendering HTML page
   *
   * @throws NullPointerException
   *    when the {@code playerLobby} or {@code templateEngine} parameter is null
   */
  public PostSigninRoute(PlayerLobby playerLobby,
                         TemplateEngine templateEngine){
    // validation
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.playerLobby = playerLobby;
    this.templateEngine = templateEngine;
  }

  public Object handle(Request request, Response response){
    // start building View-Model
    final Map<String, Object> vm = new HashMap<String,Object>();
    String username = request.queryParams(USERNAME);

    vm.put(TITLE_ATTR, TITLE);
    ModelAndView mv;

    // retrieve the session
    final Session session = request.session();

    // store player in httpSession
    if(session.attribute(PLAYER_USERNAME) == null){
      Player player = playerLobby.getPlayer(username);
      session.attribute(PLAYER_USERNAME, player);
    }
    // the player signs-in correctly, redirect to the homepage
    if(playerLobby.signin(username)){
      response.redirect(WebServer.HOME_URL);
      return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
    else {
      // the player did not sign in correctly, redirect to sign-in-page
      mv = error(vm,BAD_USERNAME);
      response.redirect(WebServer.SIGNIN_URL);
      halt();
      return templateEngine.render(mv);
    }
  }

  private ModelAndView error(final Map<String, Object> vm, final String message) {
    vm.put(MESSAGE_ATTR, message);
    vm.put(MESSAGE_TYPE_ATTR, ERROR_TYPE);
    return new ModelAndView(vm, VIEW_NAME);
  }
}
