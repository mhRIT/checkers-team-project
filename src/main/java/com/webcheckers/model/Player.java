package com.webcheckers.model;

/**
 *  {@code Player}
 *  <p>
 *  Represents a single user.
 *  </p>
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mvm7902@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 *
 */
public class Player {
  //
  // Attributes
  //

  private String name;

  /**
   * The default constructor for the Player class.
   *
   * @param name  the name of this player
   */
  public Player(String name) {
    this.name = name;
  }

  /**
   * Retrieves the name of the player.
   *
   * @return  the name of the player
   */
  public String getName() {
    return name;
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
    if (! (obj instanceof Player)) return false;
    final Player that = (Player) obj;
    return this.name.equals(that.name);
  }

  /**
   * Generates a hashCode for the player, based on the name.
   *
   * @return  the hashCode
   */
  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
