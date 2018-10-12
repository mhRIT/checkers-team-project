package com.webcheckers.ui;

import static com.webcheckers.ui.GetSigninRoute.TITLE_ATTR;
import static spark.Spark.halt;

import com.webcheckers.application.PlayerLobby;
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

public class PostSigninRoute implements Route {
  private static final Logger LOG = Logger.getLogger(PostSigninRoute.class.getName());
  private static final String USERNAME = "username";
  private final TemplateEngine templateEngine;
  private PlayerLobby playerLobby;

  public PostSigninRoute(PlayerLobby playerLobby, final TemplateEngine templateEngine) {
    LOG.setLevel(Level.ALL);
    // validation
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.playerLobby = playerLobby;
    this.templateEngine = templateEngine;
  }

  @Override
  public Object handle(Request request, Response response) {
    final Session session = request.session();
    String username = request.queryParams(USERNAME);
    LOG.finer("PostSigninRoute is invoked: " + username);

    final Map<String, Object> vm = new HashMap<String, Object>();
    vm.put(TITLE_ATTR, "Welcome!");

    if (playerLobby.validateName(username)) {
      session.attribute("player", playerLobby.signin(username));
      response.redirect(WebServer.HOME_URL);
      halt();
      return "nothing";
    } else {
      vm.put("message", "The username you entered is invalid");
      return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
    }
  }
}
