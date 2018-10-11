package com.webcheckers.application;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import java.util.ArrayList;
import java.util.logging.Logger;

public class GameCenter {
  private static final Logger LOG = Logger.getLogger(GameCenter.class.getName());

  private ArrayList<Game> gameList;

  public Game createGame(Player player1, Player player2){
    return null;
  }

  public Boolean isPlayerInGame(Player player){
    for(Game game: gameList){
      if(game.hasPlayer(player)){
        return Boolean.TRUE;
      }
    }
    return Boolean.FALSE;
  }
}
