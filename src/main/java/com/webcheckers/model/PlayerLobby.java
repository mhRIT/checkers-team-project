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

    //Checks if the username is not taken, and adds
    // them to the player list if so
    public Boolean signin(String name){
        Iterator i = playerList.entrySet().iterator();
        while(i.hasNext()){
            HashMap.Entry otherName = (HashMap.Entry)i.next();
            if(otherName.getKey() == name){
                return false;
            }
            i.remove();
        }
        Player newPlayer = new Player(name);
        playerList.put(name,newPlayer);
        return true;
    }
    //TODO finish this
    public Boolean signout(String name){
        playerList.remove(name);
        return true;
    }
}
