package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.HashMap;
import java.util.Iterator;

/* PlayerLobby.java
 * Represents the lobby in which all players inhabit
 * and from which they log in and out of
 */

public class PlayerLobby {

    private HashMap<String, Player> playerList;
    private Object gameCenter;

    public PlayerLobby(Object gameCenter){
        playerList = new HashMap<String,Player>();
        this.gameCenter = gameCenter;
    }

    //Checks if the username is not taken, and adds
    // them to the player list if so
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

    public Boolean isAvailable(String name){
      for (String eachName : playerList.keySet()) {
        if(name.equals(eachName)) {
          return false;
        }
      }
      return true;
    }

    public String toString(){
//      String list = "";
//      Iterator i = playerList.keySet().iterator();
//      while(i.hasNext()){
//        String name = (String)i.next();
//        list = list + (name + "\n");
//        i.remove();
//    }
    return "" + playerList.keySet().size();
    }
}
