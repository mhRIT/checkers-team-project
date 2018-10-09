package com.webcheckers.model;

public class Piece {

  public enum type{SINGLE, KING}
  public enum color{RED, WHITE}

  public Boolean move() {
    return null;
  }

  public Piece(String type, String color){

  }

  public Boolean validate() {
    // -selected piece must be of correct color
    // -destination must not be occupied
    // -destination must be a red square
    // -must be player's turn?
    return null;
  }

}
