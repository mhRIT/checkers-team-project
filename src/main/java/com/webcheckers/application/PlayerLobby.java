package com.webcheckers.application;

import com.webcheckers.model.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* PlayerLobby.java
 * Represents the lobby in which all players inhabit
 * and from which they log in and out of
 */

public class PlayerLobby {

  private HashMap<String, Player> playerList;
  private Object gameCenter;

  public PlayerLobby(Object gameCenter) {
    playerList = new HashMap<String, Player>();
    this.gameCenter = gameCenter;
  }

  //Checks if the username is not taken, and adds
  // them to the player list if so
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

  private boolean isAvailable(String name) {
    for (String eachName : playerList.keySet()) {
      if (name.equals(eachName)) {
        return false;
      }
    }
    return true;
  }

  private boolean nameContainsIllegalChars(String name) {
    name = name.trim();
    return name.matches("[ a-zA-Z0-9]+");
  }

  public boolean validateName(String name) {
    return isAvailable(name) && nameContainsIllegalChars(name);
  }

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

  public enum FAILED_VALIDATION_CAUSE {TAKEN, ILL_CHARS}
}
