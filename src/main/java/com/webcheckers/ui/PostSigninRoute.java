package com.webcheckers.ui;

import com.webcheckers.model.PlayerLobby;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

public class PostSigninRoute implements Route {

  public PostSigninRoute(PlayerLobby playerLobby, TemplateEngine templateEngine){

  }

  public Object handle(Request request, Response response){
    return null;
  }

}
