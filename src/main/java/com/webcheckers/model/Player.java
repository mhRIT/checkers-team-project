package com.webcheckers.model;

/**
 *  {@code Player}
 *  <p>
 *  Represents a single user.
 *  </p>
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:mlh1964@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mlh1964@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:mlh1964@rit.edu'>Andrew Festa</a>
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
}
