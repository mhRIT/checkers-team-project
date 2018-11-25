package com.webcheckers.application;

import com.webcheckers.model.Player.AiPlayer;
import com.webcheckers.model.Player.MinMaxPlayer;
import com.webcheckers.model.Player.Player;
import com.webcheckers.model.Player.RandomMovementPlayer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * {@code PlayerLobby}
 *  Represents the lobby in which all players inhabit and from which they log in and out of.
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mvm7902@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 *
 */
public class PlayerLobby {
  //
  // Attributes
  //
  private HashMap<String, Player> playerList;
  private HashMap<String, Player> AiList;
  private int playerNonce = 0;

  /**
   * Constructs a new PlayerLobby to store signed-in players.
   */
  public PlayerLobby() {
    playerList = new HashMap<>();
    AiList = new HashMap<>();

    AiPlayer randomPlayer = new RandomMovementPlayer("Easy AI", getPlayerNonce());
    AiPlayer medMinMaxPlayer = new MinMaxPlayer("Medium AI", getPlayerNonce());
    AiPlayer hardMinMaxPlayer = new MinMaxPlayer("Hard AI", getPlayerNonce());

    AiList.put(randomPlayer.getName(), randomPlayer);
    AiList.put(medMinMaxPlayer.getName(), medMinMaxPlayer);
    AiList.put(hardMinMaxPlayer.getName(), hardMinMaxPlayer);
  }

  /**
   *
   * @return
   */
  private int getPlayerNonce(){
    return playerNonce++;
  }

  /**
   * Given that a user name isAvailable, store player name in the player hash map -- thus player is
   * signed-in.
   *
   * @param   name  the player's username
   * @return  true  if player was signed-in successfully, else false
   */
  public Player signin(String name) {
    name = name.trim();
    if (validateName(name)) {
      Player newPlayer = new Player(name, getPlayerNonce());
      playerList.put(name, newPlayer);
      return newPlayer;
    }
    return null;
  }

  /**
   * Attempts to remove the Player whose name is 'name' from the list
   * of signed in players.
   *
   * @param   name  the player's username
   * @return  true  if the player exists and was successfully removed
   */
  public boolean signout(String name) {
    boolean toReturn = false;
    if(playerList.containsKey(name)){
      playerList.remove(name);
      toReturn = true;
    }
    return toReturn;
  }

  /**
   * Checks that a player's desired username is not already in use by another player.
   *
   * @param   name  the player's username
   * @return  true  if player's username is valid, else false
   */
  public boolean isAvailable(String name) {
    for (String eachName : playerList.keySet()) {
      if (name.equals(eachName)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks that the desired username consists of at least one alphanumeric
   * character other than a single space.
   *
   * @param   name  the player's username
   * @return  true  if the 'name' only contains valid characters
   *          false otherwise
   */
  private boolean nameContainsLegalChars(String name) {
    return name.matches("[a-zA-Z\\s\\d]+") && name.trim().length() > 0;
  }

  /**
   * Checks if a specified 'name' is a valid username for a player.
   *
   * @param   name  the player's username
   * @return  true  if the 'name' is valid
   *          false otherwise
   */
  public boolean validateName(String name) {
    return isAvailable(name) && nameContainsLegalChars(name);
  }

  /**
   * Retrieves a player given the username.
   *
   * @param   name  the username of the current player
   * @return        the player of the specified 'name'
   */
  public Player getPlayer(String name) {
    Player toReturn = playerList.getOrDefault(name, null);
    if(toReturn == null){
      toReturn = AiList.getOrDefault(name, null);
    }
    return toReturn;
  }


  /**
   * Checks if the playerLobby knows about the specified player.
   *
   * @param   player  the player to check the existence of
   * @return  true    if the player is known by the playerLobby
   *          false   otherwise
   */
  public boolean containsPlayers(Player player) {
    return playerList.containsValue(player);
  }

  /**
   * Retrieves the number of players that are currently signed-in.
   *
   * @return  number of players currently signed in
   */
  public int getNumPlayers() {
    return playerList.size();
  }

  /**
   * Generates a list of all the names of players who are signed-in.
   *
   * @param   exclude the name of a player to exclude from the returned list of names
   * @return          a list of all player's names who are signed-in, excluding the
   *                  specified 'exclude' name
   */
  public List<String> playerNames(String exclude) {
    List<String> players = new ArrayList<String>(playerList.keySet());
    players.remove(exclude);
    Collections.sort(players);

    return players;
  }

  /**
   * Generates a list of all the names of players who are signed-in.
   *
   * @return          a list of all player's names who are signed-in, excluding the
   *                  specified 'exclude' name
   */
  public List<String> aiNames() {
    return new ArrayList<String>(AiList.keySet());
  }
}
