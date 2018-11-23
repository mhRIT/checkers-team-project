package com.webcheckers.application;

import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *  {@code GameCenter}
 *  <p>
 *  Stores a collection of Games across all current sessions and provides various
 *  methods for interacting with the current Games.
 *  </p>
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mvm7902@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 *
 */
public class GameCenter {
  //
  // Attributes
  //
//  private ArrayList<Game> gameList;
  private ArrayList<GameContext> gameList;
  private ArrayList<GameContext> archivedGames;

  /**
   * The default constructor for the GameCenter class.
   */
  public GameCenter() {
    gameList = new ArrayList<>();
    archivedGames = new ArrayList<>();
  }

  /**
   * Creates a new game to store in the list of ongoing games using
   * the specified Player objects as the players in the game.
   *
   * @param   player1 the player that will play as Red
   * @param   player2 the player that will play as white
   * @return          the newly created GameState
   */
  public GameContext createGame(Player player1, Player player2) {
    GameContext game = new GameContext(player1, player2);
    gameList.add(game);
    return game;
  }

  /**
   * Resigns the specified player from any games they may be playing in.
   *
   * @param player  the player to resign
   * @return        the number of games the player resigned from
   */
  public int resignAll(Player player){
    int resignCount = 0;
    List<GameContext> games = getGames(player);

    for (GameContext eachGame: games) {
      if(resign(eachGame, player)){
          resignCount++;
      }
    }
    return resignCount;
  }

  /**
   * Resigns the specified player from the specified game.
   *
   * @param game    the game from which the player resigned
   * @param player  the player to resign
   * @return        if the player was successfully resigned
   */
  public boolean resign(GameContext game, Player player){
    boolean toReturn = false;

    if(game.resignPlayer(player)){
      toReturn = true;
    }

    return toReturn;
  }

  /**
   * Retrieves a list of games currently being played by the
   * specified player.
   *
   * @param   player  the player whose games are to be retrieved
   * @return          an array of GameState objects that contain
   *                  the specified player as one of the players
   */
  List<GameContext> getGames(Player player) {
    ArrayList<GameContext> playerGameList = new ArrayList<>();

    for (GameContext eachGame : gameList) {
      if (eachGame.getRedPlayer().equals(player) || eachGame.getWhitePlayer().equals(player)){
        playerGameList.add(eachGame);
      }
    }

    return playerGameList;
  }

  public GameContext getGame(Player player){
    GameContext toReturn = null;

    for (GameContext eachGame : gameList) {
      if (eachGame.getRedPlayer().equals(player) || eachGame.getWhitePlayer().equals(player)){
        toReturn = eachGame;
      }
    }

    return toReturn;
  }

  public boolean archiveGame(GameContext gameContext){
    boolean toReturn = false;

    if(gameList.contains(gameContext) && gameContext.isGameOver()){
      gameList.remove(gameContext);
      archivedGames.add(gameContext);
      toReturn = true;
    }

    return toReturn;
  }
}
