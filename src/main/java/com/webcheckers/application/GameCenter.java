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
  private static final Logger LOG = Logger.getLogger(GameCenter.class.getName());

  /**
   * The default constructor for the GameCenter class.
   */
  public GameCenter() {
    gameList = new ArrayList<>();
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
//    Game game = new Game(player1, player2);
//    gameList.add(game);
    GameContext game = new GameContext(player1, player2);
    gameList.add(game);
    return game;
  }

  /**
   * Checks if the specified player is currently playing a game.
   *
   * @param   player  the player to check
   * @return  true    if the player is currently playing a game
   *          false   otherwise
   */
  public boolean isPlayerInGame(Player player) {
    return getGames(player).size() > 0;
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
      if(eachGame.resignPlayer(player)){
          resignCount++;
      }
    }
    return resignCount;
  }

  /**
   * Retrieves a list of games currently being played by the
   * specified player.
   *
   * @param   player  the player whose games are to be retrieved
   * @return          an array of GameState objects that contain
   *                  the specified player as one of the players
   */
  public List<GameContext> getGames(Player player) {
    ArrayList<GameContext> playerGameList = new ArrayList<>();

    for (GameContext eachGame : gameList) {
      if (eachGame.getRedPlayer().equals(player) || eachGame.getWhitePlayer().equals(player)){
        playerGameList.add(eachGame);
      }
    }

    return playerGameList;
  }

  public boolean removeGame(GameContext game) {
    if(gameList.contains(game)){
      gameList.remove(game);
      return true;
    }
    return false;
  }
}
