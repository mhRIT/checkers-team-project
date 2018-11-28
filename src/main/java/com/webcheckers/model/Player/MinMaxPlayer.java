package com.webcheckers.model.Player;

import com.webcheckers.model.Board.Board;
import com.webcheckers.model.Board.Board.COLOR;
import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Board.Move;
import com.webcheckers.model.Player.Heuristic.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

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

//    heuristicList.add(new ForwardDefenseHeuristic());
//    heuristicList.add(new BackwardsDefenseHeuristic());
    heuristicList.add(new PiecesOnSideHeuristic());
    heuristicList.add(new KingCountHeuristic());
    heuristicList.add(new OffenseHeuristic());
    heuristicList.add(new PieceCountHeuristic());
//    heuristicList.add(new PositionHeuristic());
//    heuristicList.add(new BasePiecesHeuristic());
    heuristicList.add(new SimpleMoveHeuristic());
    heuristicList.add(new JumpMoveHeuristic());

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
    nextMove = miniMax(currentBoard, game.getActiveColor());
    return nextMove;
  }

  /**
   *
   * @param rootBoard
   * @param rootColor
   * @return
   */
  Move miniMax(Board rootBoard, COLOR rootColor){
    List<Move> validJumpList = rootBoard.getAllJumpMoves(rootColor);
    List<Move> validSimpleList = rootBoard.getAllSimpleMoves(rootColor);

    List<Move> testMoves = validJumpList.isEmpty() ? validSimpleList : validJumpList;
    Move returnMove = testMoves.get(0);
    double maxCost = Double.MIN_VALUE;

    for(Move eachMove : testMoves){
      Board testBoard = (Board) rootBoard.clone();
      testBoard.makeMove(eachMove);
      double testCost = maxCostMove(testBoard, rootColor, difficulty);

      if(testCost > maxCost){
        maxCost = testCost;
        returnMove = eachMove;
      }
    }

    return returnMove;
  }

  /**
   *
   * @param board
   * @param color
   * @param depth
   * @return
   */
  double minCostMove(Board board, COLOR color, int depth) {
    if(depth == 0 || isTerminal(board)) {
      return evaluateBoard(board, color);
    }

    List<Move> validJumpList = board.getAllJumpMoves(color);
    List<Move> validSimpleList = board.getAllSimpleMoves(color);

    List<Move> testMoves = validJumpList.isEmpty() ? validSimpleList : validJumpList;
    double minCost = Double.MAX_VALUE;

    for (Move eachMove : testMoves) {
      Board currentBoard = (Board) board.clone();
      currentBoard.makeMove(eachMove);
      double testCost = maxCostMove(currentBoard, color.opposite(), depth - 1);
      if(testCost < minCost){
        minCost = testCost;
      }
    }

    return minCost;
  }

  /**
   *
   * @param board
   * @param color
   * @param depth
   * @return
   */
  double maxCostMove(Board board, COLOR color, int depth) {
    if(depth == 0 || isTerminal(board)) {
      return evaluateBoard(board, color);
    }

    List<Move> validJumpList = board.getAllJumpMoves(color);
    List<Move> validSimpleList = board.getAllSimpleMoves(color);

    List<Move> testMoves = validJumpList.isEmpty() ? validSimpleList : validJumpList;
    double maxCost = Double.MIN_VALUE;

    for (Move eachMove : testMoves) {
      Board currentBoard = (Board) board.clone();
      currentBoard.makeMove(eachMove);
      double testCost = minCostMove(currentBoard, color.opposite(), depth);
      if(testCost > maxCost){
        maxCost = testCost;
      }
    }

    return maxCost;
  }

  private boolean isTerminal(Board board){
    return isTerminal(board, COLOR.WHITE) && isTerminal(board, COLOR.RED);
  }

  private boolean isTerminal(Board board, COLOR color){
    boolean toReturn = false;

    List<Move> jumpList = board.getAllJumpMoves(color);
    List<Move> simpleList = board.getAllSimpleMoves(color);
    int numPieces = board.getNumPieces(color);
    if(jumpList.isEmpty() && simpleList.isEmpty() && numPieces == 0){
      toReturn = true;
    }

    return toReturn;
  }
}

//  function minimax(node, depth, maximizingPlayer) is
//    if depth = 0 or node is a terminal node then
//        return the heuristic value of node
//    if maximizingPlayer then
//    value := −∞
//    for each child of node do
//    value := max(value, minimax(child, depth − 1, FALSE))
//    return value
//    else (* minimizing player *)
//    value := +∞
//    for each child of node do
//    value := min(value, minimax(child, depth − 1, TRUE))
//    return value
//
//  (* Initial call *)
//  minimax(origin, depth, TRUE)
