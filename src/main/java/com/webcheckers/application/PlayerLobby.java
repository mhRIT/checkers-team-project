package com.webcheckers.application;

import com.webcheckers.model.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *  PlayerLobby.java
 *  Represents the lobby in which all players inhabit and from which they log in and out of.
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:mlh1964@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mlh1964@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:mlh1964@rit.edu'>Andrew Festa</a>
 *
 */

public class PlayerLobby {

  public enum FAILED_VALIDATION_CAUSE {TAKEN, ILL_CHARS}

  private HashMap<String, Player> playerList;
  private GameCenter gameCenter;

  /**
   * Construct a new PlayerLobby to store signed-in players.
   *
   * @param gameCenter
   */
  public PlayerLobby(GameCenter gameCenter) {
    playerList = new HashMap<String, Player>();
    this.gameCenter = gameCenter;
  }

  /**
   * Given that a user name isAvailable, store player name in the player hash map --thus player is
   * signed-in.
   *
   * @param name the player's username
   * @return true if player was signed-in successfully, else false
   */
  public Player signin(String name) {
    if (validateName(name)) {
      Player newPlayer = new Player(name);
      playerList.put(name, newPlayer);
      return newPlayer;
    }
    return null;
  }

  public boolean signout(String name) {
    return playerList.remove(name) == null;
  }

  /**
   * Checks that a player's desired username is not already in use by another player, and that the
   * username consists of at least one alphanumeric (and optionally one or more spaces) character
   * other than a space.
   *
   * @param name the player's username
   * @return true if player's username is valid, else false
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

  private boolean nameContainsIllegalChars(String name) {
    name = name.trim();
    return name.matches("[ a-zA-Z0-9]+");
  }

  public boolean validateName(String name) {
    return isAvailable(name) && nameContainsIllegalChars(name);
  }

  /**
   * Retrieves a player given the username.
   *
   * @param name the username of the current player
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
   * @return how many players are currently signed-in
   */
  public int getNumPlayers() {
    return playerList.size();
  }

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
   * @return a list of all player's names who are signed-in
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
