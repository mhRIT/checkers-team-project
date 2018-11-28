package com.webcheckers.ui.HtmlRoutes;

import static com.webcheckers.ui.HtmlRoutes.GetHomeRoute.PLAYER;
import static spark.Spark.halt;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Board.InitConfig;
import com.webcheckers.ui.WebServer;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

/**
 * The {@code POST /buildConfig} route handler.
 * This is page where a user is able to build the configuration
 * settings for starting a game.
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mvm7902@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
public class PostBuildConfig extends HtmlRoute {
  //
  // Constants
  //
  public static final String INIT_CONFIG = "initConfig";

  /**
   * Create the Spark Route (UI controller) for the {@code POST /buildConfig} HTTP request.
   * {@inheritDoc}
   */
  public PostBuildConfig(GameCenter gameCenter,
      PlayerLobby playerLobby, TemplateEngine templateEngine)
      throws NullPointerException {
    super(gameCenter, playerLobby, templateEngine);
  }

  @Override
  public Object handle(Request request, Response response) {
    final Session session = request.session();
    String currPlayerName = session.attribute(PLAYER);

    if(checkValidPlayerName(currPlayerName)){
      Gson gson = new Gson();
      JsonObject jObj = gson.fromJson(request.body(), JsonObject.class);
      InitConfig config = gson.fromJson(request.body(), InitConfig.class);
      session.attribute(INIT_CONFIG, config);
    }

    response.redirect(WebServer.HOME_URL);
    halt();
    return "nothing";
  }
}
