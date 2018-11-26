package com.webcheckers.model.Player;

import com.webcheckers.model.Board;
import com.webcheckers.model.Board.COLOR;
import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player.Heuristic.*;
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

    heuristicList.add(new ForwardDefenseHeuristic());
    heuristicList.add(new BackwardsDefenseHeuristic());
    heuristicList.add(new PiecesOnSideHeuristic());
    heuristicList.add(new KingCountHeuristic());
    heuristicList.add(new OffenseHeuristic());
    heuristicList.add(new PieceCountHeuristic());
    heuristicList.add(new PositionHeuristic());
    heuristicList.add(new BasePiecesHeuristic());

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
      double eachCost = eachHeuristic.calculate(board, activeColor);
      toReturn += eachCost;
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
          difficulty);
    } catch (CloneNotSupportedException e) {
      nextMove = null;
    }
    return nextMove;
  }

  /**
   *
   * @param board
   * @param color
   * @param depth
   * @return
   */
  Move minCostMove(Board board, COLOR color, int depth)
      throws CloneNotSupportedException {
    Move toReturn = new Move(new Position(0,0), new Position(1,1));

    if(depth > 0){
      List<Move> validJumpList = board.getAllJumpMoves(color.opposite());
      List<Move> validSimpleList = board.getAllSimpleMoves(color.opposite());
      double minCost  = evaluateBoard(board, color.opposite());

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
   * @param depth
   * @return
   * @throws CloneNotSupportedException
   */
  Move maxCostMove(Board board, COLOR color, int depth)
      throws CloneNotSupportedException {
    List<Move> validJumpList = board.getAllJumpMoves(color);
    List<Move> validSimpleList = board.getAllSimpleMoves(color);

    List<Move> testMoves = validJumpList.isEmpty() ? validSimpleList : validJumpList;
    Move toReturn = validJumpList.isEmpty() ? validSimpleList.get(0) : validJumpList.get(0);

    Board testBoard = (Board)board.clone();
    testBoard.makeMove(toReturn);
    double maxCost = evaluateBoard(testBoard, color);

    for (Move eachMove : testMoves) {
      testBoard.makeMove(eachMove);
      double testCost = evaluateBoard(testBoard, color);
      if(testCost > maxCost){
        toReturn = eachMove;
        maxCost = testCost;
      }
    }
    return toReturn;
  }
}
