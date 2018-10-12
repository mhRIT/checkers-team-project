package com.webcheckers.application;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import java.util.ArrayList;
import java.util.logging.Logger;

public class GameCenter {

  private static final Logger LOG = Logger.getLogger(GameCenter.class.getName());

  private ArrayList<Game> gameList;

  public GameCenter() {
    gameList = new ArrayList<>();
  }

  public Game createGame(Player player1, Player player2) {
    return new Game(player1, player2);
  }

  public boolean isPlayerInGame(Player player) {
    return getGames(player).length > 0;
  }

  public Game[] getGames(Player opponent) {
    ArrayList<Game> oppGameList = new ArrayList<>();

    for (Game eachGame : gameList) {
      if (eachGame.hasPlayer(opponent)) {
        oppGameList.add(eachGame);
      }
    }

    return oppGameList.toArray(new Game[0]);
  }

  public void addGame(Game game) {
    gameList.add(game);
  }

  public boolean removeGame(Game game) {
    return gameList.remove(game);
  }
}
