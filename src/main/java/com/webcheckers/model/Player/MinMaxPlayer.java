package com.webcheckers.model.Player;

import com.webcheckers.model.Board;
import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player.Heuristic.DefenseAgainstKingsHeuristic;
import com.webcheckers.model.Player.Heuristic.DefenseHeuristic;
import com.webcheckers.model.Player.Heuristic.DefenseOnSidesHeuristic;
import com.webcheckers.model.Player.Heuristic.Heuristic;
import com.webcheckers.model.Player.Heuristic.KingCountHeuristic;
import com.webcheckers.model.Player.Heuristic.OffenseHeuristic;
import com.webcheckers.model.Player.Heuristic.PieceCountHeuristic;
import com.webcheckers.model.Player.Heuristic.PositionHeuristic;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MinMaxPlayer extends AiPlayer {

  //
  // Attributes
  //
  private List<Heuristic> heuristicList;
  private int difficulty = 1;

  /**
   * The default constructor for the Player class.
   *
   * @param name the name of this player
   */
  public MinMaxPlayer(String name, int idNum) {
    super(name, idNum);
    heuristicList = new ArrayList<>();

    heuristicList.add(new DefenseHeuristic());
    heuristicList.add(new DefenseAgainstKingsHeuristic());
    heuristicList.add(new DefenseOnSidesHeuristic());
    heuristicList.add(new KingCountHeuristic());
    heuristicList.add(new OffenseHeuristic());
    heuristicList.add(new PieceCountHeuristic());
    heuristicList.add(new PositionHeuristic());
  }

  public void setDifficulty(int difficultyLevel){
    this.difficulty = difficultyLevel;
  }
  public int evaluateBoard(GameContext gameContext, Player player) {
    int toReturn = 0;
    for (Heuristic eachHeuristic: heuristicList) {
      toReturn += eachHeuristic.calculate(gameContext, player);
    }
    return toReturn;
  }

  @Override
  public Move getNextMove(GameContext game) {
    Board currentBoard = game.getCurrentBoard();

    List<Move> validJumpList = currentBoard.getAllJumpMoves(game.getActiveColor());
    List<Move> validSimpleList = currentBoard.getAllSimpleMoves(game.getActiveColor());

    Move nextMove;
    if(!validJumpList.isEmpty()){
      nextMove = validJumpList.get(0);
    } else {
      nextMove = validSimpleList.get(0);
    }

    return nextMove;
  }
}
