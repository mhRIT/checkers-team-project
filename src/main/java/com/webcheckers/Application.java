package com.webcheckers;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import java.io.InputStream;
import java.util.Objects;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.webcheckers.ui.WebServer;

import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;


/**
 * The entry point for the WebCheckers web application.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
public final class Application {
  public enum DEMO_STATE { START, MID, END, DEBUG, PRODUCTION;
    /**
     * TODO
     *
     * @param stateString
     */
    public static DEMO_STATE getState(String stateString) {
      switch (stateString.toLowerCase()){
        case "start":
          return DEMO_STATE.START;
        case "mid":
          return DEMO_STATE.MID;
        case "end":
          return DEMO_STATE.END;
        case "debug":
          return DEMO_STATE.DEBUG;
        case "production":
        default:
          return DEMO_STATE.PRODUCTION;
      }
    }
  }

  private static final Logger LOG = Logger.getLogger(Application.class.getName());

  //
  // Application Launch method
  //

  /**
   * Entry point for the WebCheckers web application.
   *
   * <p>
   * It wires the application components together.  This is an example
   * of <a href='https://en.wikipedia.org/wiki/Dependency_injection'>Dependency Injection</a>
   * </p>
   *
   * @param args
   *    Command line arguments; none expected.
   */
  public static void main(String[] args) {
    String demoStateString = System.getProperty("demoState", "false");
//    System.out.printf("Demo state: %s\n", demoStateString);
    DEMO_STATE demoState = DEMO_STATE.getState(demoStateString);

    // initialize Logging
    try {
      ClassLoader classLoader = Application.class.getClassLoader();
      final InputStream logConfig = classLoader.getResourceAsStream("log.properties");
      LogManager.getLogManager().readConfiguration(logConfig);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Could not initialize log manager because: " + e.getMessage());
    }

    // The application uses FreeMarker templates to generate the HTML
    // responses sent back to the client. This will be the engine processing
    // the templates and associated data.
    final TemplateEngine templateEngine = new FreeMarkerEngine();

    // The application uses Gson to generate JSON representations of Java objects.
    // This should be used by your Ajax Routes to generate JSON for the HTTP
    // response to Ajax requests.
    final Gson gson = new Gson();

    // create the one and only game center
    final GameCenter gameCenter = new GameCenter();

    // create the one and only PlayerLobby
    final PlayerLobby playerLobby = new PlayerLobby(gameCenter);

    switch (demoState){
      case START:
        playerLobby.signin("Player1");
        playerLobby.signin("Player2");

        Player player1 = playerLobby.getPlayer("Player1");
        Player player2 = playerLobby.getPlayer("Player2");

        Game newGame = gameCenter.createGame(player1, player2);
        newGame.initializeStart();
      case MID:
        break;
      case END:
        break;
      case DEBUG:
        break;
      case PRODUCTION:
        break;
    }

    // inject the game center and freemarker engine into web server
    final WebServer webServer = new WebServer(templateEngine, gson, gameCenter, playerLobby);

    // inject web server into application
    final Application app = new Application(webServer);

    // start the application up
    app.initialize();
  }

  //
  // Attributes
  //

  private final WebServer webServer;

  //
  // Constructor
  //

  private Application(final WebServer webServer) {
    // validation
    Objects.requireNonNull(webServer, "webServer must not be null");
    //
    this.webServer = webServer;
  }

  //
  // Private methods
  //

  private void initialize() {
    LOG.config("WebCheckers is initializing.");

    // configure Spark and startup the Jetty web server
    webServer.initialize();

    // other applications might have additional services to configure
    LOG.config("WebCheckers initialization complete.");
  }
}