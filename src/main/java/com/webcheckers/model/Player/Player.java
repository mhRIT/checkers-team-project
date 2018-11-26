package com.webcheckers.model.Player;

import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Move;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
public class Player implements PropertyChangeListener {

  //
  // Attributes
  //
  private String name;
  private int id;
  private Map<GameContext, Move> pendingMoveMap;

  /**
   * The default constructor for the Player class.
   *
   * @param name  the name of this player
   */
  public Player(String name, int idNum) {
    Objects.requireNonNull(name, "name must not be null");

    this.name = name;
    this.id = idNum;
    this.pendingMoveMap = new HashMap<>();
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
   * Returns if this player is an AI program.
   *
   * @return false if this player is a real player
   */
  public boolean isAi(){
    return false;
  }

  /**
   * Retrieves the id number for the current player.
   *
   * @return  the id of the player
   */
  public int getId() {
    return id;
  }

  /**
   * TODO
   * @param game
   * @param move
   */
  public void putNextMove(GameContext game, Move move){
    pendingMoveMap.put(game, move);
  }

  /**
   * TODO
   * @param game
   * @return
   */
  public Move getNextMove(GameContext game) {
    return pendingMoveMap.getOrDefault(game, null);
  }

  /**
   * Builds a user-friendly string representation
   * for this player.
   *
   * @return  the string representation for this player
   */
  @Override
  public String toString() {
    return String.format("%d: %s", id, name);
  }

  /**
   * Compares if other object is a player
   * and has the same id as this player.
   *
   * @return true if the object is a player with the same player name
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (! (obj instanceof Player)) return false;
    final Player that = (Player) obj;
    return this.id == that.getId();
  }

  /**
   * Generates a hashCode for the player, based on the string
   * representation of this player.
   *
   * @return  an int value representing this player
   */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    // do nothing
  }
}
