package com.webcheckers.model;

public class Move {
  //
  // Attributes
  //
  private Position start;
  private Position end;

  public Move(Position startPos, Position endPos){
    this.start = startPos;
    this.end = endPos;
  }

  public Position getStart(){
    return start;
  }

  public Position getEnd(){
    return end;
  }

  @Override
  public String toString() {
    return String.format("%s -> %s", start, end);
  }

  /**
   * Compares if other object is a player
   * and has the same name as this player.
   *
   * @return true if the object is a player with the same player name
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (! (obj instanceof Move)) return false;
    final Move that = (Move) obj;
    return this.toString().equals(that.toString());
  }

  /**
   * Generates a hashCode for the player, based on the name.
   *
   * @return  the hashCode
   */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
