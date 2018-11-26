package com.webcheckers.model.Player;

import com.webcheckers.model.Board;
import com.webcheckers.model.Board.COLOR;
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
import com.webcheckers.model.Position;
import java.util.ArrayList;
import java.util.List;

public class MinMaxPlayer extends AiPlayer {
  //
  // Attributes
  //
  private List<Heuristic> heuristicList;
  private int difficulty;

  /**
   * The default constructor for the Player class.
   *
   * @param name the name of this player
   */
  public MinMaxPlayer(String name, int idNum, int difficultyLevel) {
    super(name, idNum);
    heuristicList = new ArrayList<>();

    heuristicList.add(new DefenseHeuristic());
    heuristicList.add(new DefenseAgainstKingsHeuristic());
    heuristicList.add(new DefenseOnSidesHeuristic());
    heuristicList.add(new KingCountHeuristic());
    heuristicList.add(new OffenseHeuristic());
    heuristicList.add(new PieceCountHeuristic());
    heuristicList.add(new PositionHeuristic());

    difficulty = difficultyLevel;
  }

  /**
   *
   * @param board
   * @param activeColor
   * @return
   */
  public int evaluateBoard(Board board, COLOR activeColor) {
    int toReturn = 0;

    for (Heuristic eachHeuristic: heuristicList) {
      toReturn += eachHeuristic.calculate(board, activeColor);
    }
    return toReturn;
  }

  /**
   *
   * @param game
   * @return
   */
  @Override
  public Move getNextMove(GameContext game) {
    Board currentBoard = game.getCurrentBoard();
    Move nextMove;
    try{
      nextMove = maxCostMove(currentBoard,
          game.getActiveColor(),
          game.getNonActiveColor(),
          difficulty);
    } catch (CloneNotSupportedException e) {
      nextMove = null;
    }
    return nextMove;
  }

  /**
   *
   * @param board
   * @param activeColor
   * @param opposingColor
   * @param depth
   * @return
   */
  private Move minCostMove(Board board, COLOR activeColor, COLOR opposingColor, int depth){
    Move toReturn = new Move(new Position(0,0), new Position(1,1));

    if(depth > 0){
      List<Move> validJumpList = board.getAllJumpMoves(opposingColor);
      List<Move> validSimpleList = board.getAllSimpleMoves(opposingColor);
      double minCost  = evaluateBoard(board, opposingColor);

      if(!validJumpList.isEmpty()){
        toReturn = validJumpList.get(0);
      } else {
        toReturn = validSimpleList.get(0);
      }
    } else {
      return toReturn;
    }
    return toReturn;
  }


  /**
   *
   * @param board
   * @param color
   * @param opposingColor
   * @param depth
   * @return
   * @throws CloneNotSupportedException
   */
  private Move maxCostMove(Board board, COLOR color,  COLOR opposingColor, int depth)
      throws CloneNotSupportedException {
    Move toReturn = new Move(new Position(0,0), new Position(1,1));

    List<Move> validJumpList = board.getAllJumpMoves(color);
    List<Move> validSimpleList = board.getAllSimpleMoves(color);
    double maxCost = 0;
    for (Move eachMove : validJumpList.isEmpty() ? validSimpleList : validJumpList) {
      double testCost = evaluateBoard(board, color);
      Board testBoard = (Board)board.clone();
      testBoard.movePiece(eachMove);
      if(testCost > maxCost){
        toReturn = eachMove;
        maxCost = testCost;
      }
    }
    return toReturn;
  }
}
