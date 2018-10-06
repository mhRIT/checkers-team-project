package com.webcheckers.ui;

import com.webcheckers.model.PlayerLobby;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;


public class PostSigninRoute implements Route {

  private final PlayerLobby playerLobby;
  private final TemplateEngine templateEngine;

  private final String USERNAME = "username";

  public PostSigninRoute(PlayerLobby playerLobby, TemplateEngine templateEngine){
    // validation
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.playerLobby = playerLobby;
    this.templateEngine = templateEngine;
  }

  public Object handle(Request request, Response response){
    final Map<String, Object> vm = new HashMap<>();
    final String username = request.queryParams(USERNAME);

    final Session session = request.session();

    if(playerLobby.isAvailable(username)){
      playerLobby.signin(username);
      templateEngine.render(new ModelAndView(vm , "home.ftl"));
    }

    return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
  }

}
