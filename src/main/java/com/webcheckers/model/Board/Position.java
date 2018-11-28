package com.webcheckers.model.Board;

public class Position {
  //
  // Attributes
  //
  private int row;
  private int cell;

  public Position(int cell, int row){
    this.cell = cell;
    this.row = row;
  }

  public int getRow() {
    return row;
  }

  public int getCell() {
    return cell;
  }

  @Override
  public String toString() {
    return String.format("(%d, %d)", cell, row);
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
    if (! (obj instanceof Position)) return false;
    final Position that = (Position) obj;
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