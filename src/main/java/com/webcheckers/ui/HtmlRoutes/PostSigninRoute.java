package com.webcheckers.ui.HtmlRoutes;

import static com.webcheckers.ui.HtmlRoutes.GetHomeRoute.PLAYER;
import static com.webcheckers.ui.HtmlRoutes.GetSigninRoute.TITLE_ATTR;
import static spark.Spark.halt;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player.Player;
import com.webcheckers.ui.WebServer;
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
 * The {@code POST /signin} route handler.
 * This is the page where the user starts.
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mvm7902@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
public class PostSigninRoute implements Route {
  //
  // Constants
  //

  static final String USERNAME = "username";
  static final String TITLE = "Welcome";
  static final String VIEW_NAME = "home.ftl";

  static final String MESSAGE_ATTR = "message";
  static final String MESSAGE_TYPE_ATTR = "messageType";
  static final String ERROR_TYPE = "error";
  static final String ERROR_VIEW_NAME = "signin.ftl";
  static final String INVALID_USERNAME = "Invalid username.Selected username cannot already be "+
      "in use, be at least one character long, and consist of one alphanumeric characters";
  static final String ILL_CHARS_USERNAME =
      "Username must consist of only letters " +
      "or numbers and be at least one character long.";
  static final String TAKEN_USERNAME = "Username is already in use by another player";

  //
  // Attributes
  //

  private final PlayerLobby playerLobby;
  private final TemplateEngine templateEngine;
  private static final Logger LOG = Logger.getLogger(PostSigninRoute.class.getName());

  /**
   * Create the Spark Route (UI controller) for the {@code POST /signin} HTTP request.
   *
   * @param playerLobby the {@link PlayerLobby} for tracking all signed in players
   * @param templateEngine the {@link TemplateEngine} used for rendering page HTML.
   * @throws NullPointerException when the {@code gameCenter}, {@code playerLobby}, or {@code
   * templateEngine} parameter is null
   */
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
   * Render the WebCheckers Home page or the Sign-in page, depending
   * on if the user successfully signs in.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return the rendered HTML for the game page
   */
  @Override
  public Object handle(Request request, Response response){
    final Session session = request.session();
    String username = request.queryParams(USERNAME);
    username = username.trim();
    LOG.finer("PostSigninRoute is invoked: " + username);

    // start building View-model
    final Map<String, Object> vm = new HashMap<String,Object>();
    vm.put(TITLE_ATTR, TITLE);
    ModelAndView mv;

    // store player in httpSession
    if(session.attribute(PLAYER) == null){
        if(playerLobby.signin(username) != null) {
          Player player = playerLobby.getPlayer(username);
          session.attribute(PLAYER, player.getName());
        } else {
          mv = error(vm,INVALID_USERNAME);
          return templateEngine.render(mv);
        }
      } else {
          // the player did not sign in correctly, redirect to sign-in-page
        mv = error(vm,INVALID_USERNAME);
        return templateEngine.render(mv);
      }

    // the player signs-in correctly, redirect to the homepage
    if(playerLobby.getPlayer(username) != null){
      response.redirect(WebServer.HOME_URL);
      halt();
      return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
    else {
      // the player did not sign in correctly, redirect to sign-in-page
      mv = error(vm,INVALID_USERNAME);
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
