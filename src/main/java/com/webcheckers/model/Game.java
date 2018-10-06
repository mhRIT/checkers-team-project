package com.webcheckers.model;

import java.util.HashMap;

public class Game {

  private HashMap<Color, Player> players = new HashMap<Color, Player>();
  private Board board;

  public enum Color{RED, WHITE}

  public void endGame(){

  }
}
