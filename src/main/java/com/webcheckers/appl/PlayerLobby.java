package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.HashMap;
import java.util.Iterator;

/** PlayerLobby.java
 * Represents the lobby in which all players inhabit
 * and from which they log in and out of.
 *
 * @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 */

public class PlayerLobby {

    //
    // Attributes
    //

    private HashMap<String, Player> playerList;
    private Object gameCenter;

    /**
     * Construct a new PlayerLobby to store signed-in players.
     *
     * @param gameCenter
     *     currently unused-saved for later story updates
     */
    public PlayerLobby(Object gameCenter){
        playerList = new HashMap<String,Player>();
        this.gameCenter = gameCenter;
    }

    /**
     * Given that a user name isAvailable,
     * store player name in the player hash map
     * --thus player is signed-in.
     *
     * @param name
     *   the player's username
     * @return
     *   true if player was signed-in successfully, else false
     */
    public Boolean signin(String name){
        if(isAvailable(name)){
          Player newPlayer = new Player(name);
          playerList.put(name,newPlayer);
          return true;
        }
        return false;
    }

    //TODO finish this
    public Boolean signout(String name){
        playerList.remove(name);
        return true;
    }

    /**
     * Checks that a player's desired username is
     * not already in use by another player, and that
     * the username consists of at least one alphanumeric
     * (and optionally one or more spaces) character other than a space.
     *
     * @param name
     *   the player's username
     * @return
     *   true if player's username is valid, else false
     */
    public Boolean isAvailable(String name){
      for (String eachName : playerList.keySet()) {
        if(name.equals(eachName)) {
          return false;
        }
      }
      if(name.matches("[a-zA-Z\\s\\d]+") && name.trim().length() > 0)
        return true;

      return false;
    }

    /**
     * Generates a list of all the names of
     * players who are signed-in.
     *
     * @return
     *   a list of all player's names who are signed-in
     */
    public String playerNames(){
        String list = "";
        Iterator i = playerList.keySet().iterator();
        while(i.hasNext()){
            String name = (String)i.next();
            list = list + (name + "\n");
            i.remove();
        }
        return list;
    }

    /**
     * Retrieves the number of players that are
     * currently signed-in.
     *
     * @return
     *   how many players are currently signed-in
     */
    public String numberOfPlayers(){
        return "" + playerList.keySet().size();
    }
}
