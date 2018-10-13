package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

public class PostGameRoute implements Route {

  private final GameCenter gameCenter;
  private final TemplateEngine templateEngine;

  PostGameRoute(GameCenter gameCenter, TemplateEngine templateEngine){

    //
    // Attributes
    //

    this.gameCenter = gameCenter;
    this.templateEngine = templateEngine;
  }

  @Override
  public String handle(Request request, Response response){
    return null;
  }
}
