package com.webcheckers.model.Player.Heuristic;

import com.webcheckers.model.GameState.GameContext;

public abstract class Heuristic {

  private String name;
  private int weight;

  public Heuristic(String id){
    this.name = id;
  }

  public String getName(){
    return name;
  }

  public int getWeight(){
    return weight;
  }

  public abstract int calculate(GameContext game);
}
