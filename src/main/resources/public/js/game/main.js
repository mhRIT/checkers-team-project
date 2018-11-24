/**
 * This file contains the launch code for the GameState view.
 */
(function () {
  // not strict to get the GameState state object from the HTML response
  //'use strict';

  // This 'this' variable here holds the browser's window object
  // and this is where the 'game.ftl' (FreeMarker) template holds
  // some global GameState state data such as the names and piece colors
  // for both players.
  var gameState = this.gameState;

  define(function (require) {
    'use strict';

    // imports
    const GameView = require('./GameView');

    // create the View object
    $(document).ready(function () {

      // configure global exception handler
      window.addEventListener('error', function (error) {
        alert(error.message);
        console.log(error);
      });

      // create and startup the GameState View component
      var view = new GameView(gameState);
      // create a globally-accessible variable for debugging purposes
      window._gameView = view;
      // the GameState View startup uses the viewMode to determine the View's behavior state model
      view.startup();

    });
  });

})();
