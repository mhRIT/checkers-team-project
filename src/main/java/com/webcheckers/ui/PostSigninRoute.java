package com.webcheckers.ui;

import static com.webcheckers.ui.GetSigninRoute.TITLE_ATTR;
import static spark.Spark.halt;

import com.webcheckers.appl.PlayerLobby;
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


public class PostSigninRoute implements Route {

  private final PlayerLobby playerLobby;
  private final TemplateEngine templateEngine;
  private static final Logger LOG = Logger.getLogger(PostSigninRoute.class.getName());

  private static final String USERNAME = "username";

  public PostSigninRoute(PlayerLobby playerLobby, TemplateEngine templateEngine){
    // validation
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.playerLobby = playerLobby;
    this.templateEngine = templateEngine;
  }

  public Object handle(Request request, Response response){
    final Map<String, Object> vm = new HashMap<String,Object>();
    String username = request.queryParams(USERNAME);

    vm.put(TITLE_ATTR, "Welcome!");

    final Session session = request.session();

    if(playerLobby.isAvailable(username)){
      playerLobby.signin(username);
      LOG.config("Player list: " + playerLobby.toString());
      response.redirect(WebServer.HOME_URL);
      return templateEngine.render(new ModelAndView(vm , "home.ftl"));
    }else {
      response.redirect(WebServer.SIGNIN_URL);
      halt();
      return null;
    }
  }

}
