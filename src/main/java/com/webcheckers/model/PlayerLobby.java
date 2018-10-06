package com.webcheckers.model;

import java.util.HashMap;
import java.util.Iterator;

/* PlayerLobby.java
 * Represents the lobby in which all players inhabit
 * and from which they log in and out of
 */

public class PlayerLobby {
    private HashMap<String,Player> playerList;
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
      Iterator i = playerList.entrySet().iterator();
      while(i.hasNext()){
        HashMap.Entry otherName = (HashMap.Entry)i.next();
        if(otherName.getKey() == name){
          return false;
        }
        i.remove();
      }
      return true;
    }
}
