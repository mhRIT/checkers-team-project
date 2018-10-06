package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

public class GetSigninRoute implements Route {
  private final TemplateEngine templateEngine;
  private static final Logger LOG = Logger.getLogger(GetSigninRoute.class.getName());

  public GetSigninRoute(TemplateEngine templateEngine){
    // validation
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.templateEngine = templateEngine;
    //
    LOG.config("GetSigninRoute is initialized.");
    }

  public Object handle(Request request, Response response){
    LOG.finer("GetHomeRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Sign in!");

    return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
  }
}
