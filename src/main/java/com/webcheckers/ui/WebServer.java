package com.webcheckers.ui;

import static spark.Spark.get;
import static spark.Spark.ipAddress;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.redirect;
import static spark.Spark.staticFiles;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.ui.AjaxRoutes.PostBackupMoveRoute;
import com.webcheckers.ui.AjaxRoutes.PostCheckTurnRoute;
import com.webcheckers.ui.AjaxRoutes.PostResignGameRoute;
import com.webcheckers.ui.AjaxRoutes.PostSubmitTurnRoute;
import com.webcheckers.ui.AjaxRoutes.PostValidateMoveRoute;
import com.webcheckers.ui.HtmlRoutes.GetGameRoute;
import com.webcheckers.ui.HtmlRoutes.GetHomeRoute;
import com.webcheckers.ui.HtmlRoutes.GetSigninRoute;
import com.webcheckers.ui.HtmlRoutes.GetSignoutRoute;
import com.webcheckers.ui.HtmlRoutes.PostSelectOpponentRoute;
import com.webcheckers.ui.HtmlRoutes.PostSigninRoute;
import java.util.Objects;
import java.util.logging.Logger;
import spark.TemplateEngine;


/**
 * The server that initializes the set of HTTP request handlers. This defines the <em>web
 * application interface</em> for this WebCheckers application.
 *
 *
 * There are multiple ways in which you can have the client issue a request and the application
 * generate responses to requests. If your team is not careful when designing your approach, you can
 * quickly create a mess where no one can remember how a particular request is issued or the
 * response gets generated. Aim for consistency in your approach for similar activities or
 * requests.
 * <br>
 * <br>
 * Design choices for how the client makes a request include:
 * <ul>
 * <li>Request URL</li>
 * <li>HTTP verb for request (GET, POST, PUT, DELETE and so on)</li>
 * <li><em>Optional:</em> Inclusion of request parameters</li>
 * </ul>
 * <br>
 * Design choices for generating a response to a request include:
 * <ul>
 * <li>View templates with conditional elements</li>
 * <li>Use different view templates based on results of executing the client request</li>
 * <li>Redirecting to a different application URL</li>
 * </ul>
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class WebServer {
  private static final Logger LOG = Logger.getLogger(WebServer.class.getName());

  //
  // Constants
  //
  public static final String HOME_URL = "/";
  public static final String INDEX_URL = "/index";
  public static final String SIGNIN_URL = "/signin";
  public static final String SIGNOUT_URL = "/signout";
  public static final String SELECT_OPPONENT_URL = "/selectOpponent";
  public static final String GAME_URL = "/game";
  public static final String CHECK_TURN_URL = "/checkTurn";
  public static final String VALIDATE_MOVE_URL = "/validateMove";
  public static final String SUBMIT_TURN_URL = "/submitTurn";
  public static final String BACKUP_MOVE_URL = "/backupMove";
  public static final String RESIGN_URL = "/resignGame";

  //
  // Attributes
  //
  private final GameCenter gameCenter;
  private final PlayerLobby playerLobby;
  private final Gson gson;
  private final TemplateEngine templateEngine;

  //
  // Constructors
  //
  /**
   * The constructor for the Web Server.
   *
   * @param templateEngine The default {@link TemplateEngine} to render page-level HTML views.
   * @param gson The default {@link Gson} parser for Route handlers.
   * @param gameCenter the default {@link GameCenter} for tracking all ongoing games
   * @param playerLobby the default {@link PlayerLobby} for tracking all players
   * @throws NullPointerException If any of the parameters are {@code null}.
   */
  public WebServer(final TemplateEngine templateEngine,
                  final Gson gson,
                  final GameCenter gameCenter,
                  final PlayerLobby playerLobby) {

    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    Objects.requireNonNull(gson, "gson must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");

    this.templateEngine = templateEngine;
    this.gson = gson;
    this.gameCenter = gameCenter;
    this.playerLobby = playerLobby;
  }

  //
  // Public methods
  //
  /**
   * Initialize all of the HTTP routes that make up this web application.
   *
   * <p>
   * GameInit of the web server includes defining the location for static files, and defining
   * all routes for processing client requests. The method returns after the web server finishes its
   * initialization.
   * </p>
   */
  public void initialize() {

    // Configuration to serve static files
    staticFiles.location("/public");

    //// Setting any route (or filter) in Spark triggers initialization of the
    //// embedded Jetty web server.

    // Configure the Jetty server to run on any available IP at port 10000
    port(4567);
    ipAddress("0.0.0.0");

    {
      // Shows the Checkers game Home page.
      get(HOME_URL,
          new GetHomeRoute(gameCenter, playerLobby, templateEngine));
      redirect.get(INDEX_URL, HOME_URL);

      // Shows the sign-in page
      get(SIGNIN_URL,
          new GetSigninRoute(gameCenter, playerLobby, templateEngine));

      // Signs out the current player and shows the Home page.
      get(SIGNOUT_URL,
          new GetSignoutRoute(gameCenter, playerLobby, templateEngine));

      // Input username
      post(SIGNIN_URL,
          new PostSigninRoute(gameCenter, playerLobby, templateEngine));

      // Starts a game against the selected opponent if the player is not in a game and shows
      // the game page
      post(SELECT_OPPONENT_URL,
          new PostSelectOpponentRoute(gameCenter, playerLobby, templateEngine));

      // Displays the GameState page
      get(GAME_URL,
          new GetGameRoute(gameCenter, playerLobby, templateEngine));
    }

    {
      post(VALIDATE_MOVE_URL,
          new PostValidateMoveRoute(gameCenter, playerLobby, gson),
          gson::toJson);

      post(BACKUP_MOVE_URL,
          new PostBackupMoveRoute(gameCenter, playerLobby, gson),
          gson::toJson);

      post(RESIGN_URL,
          new PostResignGameRoute(gameCenter, playerLobby, gson),
          gson::toJson);

      post(SUBMIT_TURN_URL,
          new PostSubmitTurnRoute(gameCenter, playerLobby, gson),
          gson::toJson);

      post(CHECK_TURN_URL,
          new PostCheckTurnRoute(gameCenter, playerLobby, gson),
          gson::toJson);
    }

    get("*",
        new GetHomeRoute(gameCenter, playerLobby, templateEngine));

    LOG.config("WebServer is initialized.");
  }
}