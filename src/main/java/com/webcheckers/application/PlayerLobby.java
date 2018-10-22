package com.webcheckers.application;

import com.webcheckers.model.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *  {@code PlayerLobby}
 *  <p>
 *    Represents the lobby in which all players inhabit and from which they log in and out of.
 *  </p>
 *
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mvm7902@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 *
 */
public class PlayerLobby {

  public enum FAILED_VALIDATION_CAUSE {TAKEN, ILL_CHARS}

  private HashMap<String, Player> playerList;
  private GameCenter gameCenter;

  /**
   * Constructs a new PlayerLobby to store signed-in players.
   *
   * @param gameCenter  instance of GameCenter to associate with
   *                    this PlayerLobby
   */
  public PlayerLobby(GameCenter gameCenter) {
    playerList = new HashMap<String, Player>();
    this.gameCenter = gameCenter;
  }

  /**
   * Given that a user name isAvailable, store player name in the player hash map --thus player is
   * signed-in.
   *
   * @param   name  the player's username
   * @return  true  if player was signed-in successfully, else false
   */
  public Player signin(String name) {
    name = name.trim();
    if (validateName(name)) {
      Player newPlayer = new Player(name);
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
    return playerList.remove(name) == null;
  }

  /**
   * Checks that a player's desired username is not already in use by another player, and that the
   * username consists of at least one alphanumeric (and optionally one or more spaces) character
   * other than a space.
   *
   * @param   name  the player's username
   * @return  true  if player's username is valid, else false
   */
  public Boolean isAvailable(String name) {
    for (String eachName : playerList.keySet()) {
      if (name.equals(eachName)) {
        return false;
      }
    }
    if (name.matches("[a-zA-Z\\s\\d]+") && name.trim().length() > 0) {
      return true;
    }

    return false;
  }

  /**
   * Checks if a specified 'name' contains illegal characters.
   *
   * @param   name  the player's username
   *
   * @return  true  if the 'name' only contains valid characters
   *          false otherwise
   */
  private boolean nameContainsIllegalChars(String name) {
    name = name.trim();
    return name.matches("[ a-zA-Z0-9]+");
  }

  /**
   * Checks if a specified 'name' is a valid username for a player.
   *
   * @param   name  the player's username
   *
   * @return  true  if the 'name' is valid
   *          false otherwise
   */
  public boolean validateName(String name) {
    return isAvailable(name);
//    return isAvailable(name) && nameContainsIllegalChars(name);
  }

  /**
   * Retrieves a player given the username.
   *
   * @param   name  the username of the current player
   * @return        the player of the specified 'name'
   */
  public Player getPlayer(String name) {
    return playerList.getOrDefault(name, null);
  }

  public List<String> getPlayerNames() {
    List<String> toReturn = new ArrayList<String>();

    for (String eachName : playerList.keySet()) {
      toReturn.add(eachName);
    }

    return toReturn;
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
   * {@inheritDoc}
   * Returns a String representation of the current PlayerLobby.
   *
   * @return  a String containing the number and names of signed
   *          in players
   */
  @Override
  public String toString() {
    String toReturn = "" + playerList.keySet().size() + ": ";
    String separator = "";
    for (String eachName : playerList.keySet()) {
      toReturn += separator + eachName;
      separator = ", ";
    }
    return toReturn;
  }

  /**
   * Generates a list of all the names of players who are signed-in.
   *
   * @param   exclude the name of a player to exclude from the returned list of names
   * @return          a list of all player's names who are signed-in, excluding the
   *                  specified 'exclude' name
   */
  public String[] playerNames(String exclude) {
    String[] list;
    Set<String> players = playerList.keySet();
    ArrayList<String> arrayList = new ArrayList<String>(players);
    arrayList.remove(exclude);
    Collections.sort(arrayList);
    list = arrayList.toArray(new String[0]);
    return list;
  }
}
