package com.webcheckers.model;

public class Piece {

  private int x;
  private int y;

  public enum Color{RED, WHITE}

  public Boolean move() {
    return null;
  }

  public Boolean validate() {
    // -selected piece must be of correct color
    // -destination must not be occupied
    // -destination must be a red square
    // -must be player's turn?
    return null;
  }

}
