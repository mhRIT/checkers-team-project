package com.webcheckers.model.Player.Heuristic;

import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Player.Player;

public abstract class Heuristic {

  private String name;
  private double weight;

  public Heuristic(double weight){
    this.name = this.getClass().getName();
    this.weight = weight;
  }

  public Heuristic(){
    this(1);
  }

  public String getName(){
    return name;
  }

  public double getWeight(){
    return weight;
  }

  public abstract int calculate(GameContext game, Player player);

  /**
   * Builds a user-friendly string representation
   * for this heuristic.
   *
   * @return  the string representation for this heuristic
   */
  @Override
  public String toString(){
    return String.format("%s: %f", name, weight);
  }

  /**
   * Compares if other heuristic is a heuristic
   * and has the same name as this heuristic.
   *
   * @return true if the object is a heuristic with the same heuristic name
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (! (obj instanceof Heuristic)) return false;
    final Heuristic that = (Heuristic) obj;
    return this.name.equals(that.getName());
  }

  /**
   * Generates a hashCode for the heuristic, based on the string
   * representation of this heuristic.
   *
   * @return  an int value representing this heuristic
   */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
