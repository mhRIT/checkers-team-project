package com.webcheckers.model.Player;

import com.webcheckers.model.Board;
import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Move;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomMovementPlayer extends AiPlayer {

  /**
   * The default constructor for the Player class.
   *
   * @param name the name of this player
   */
  public RandomMovementPlayer(String name, int idNum) {
    super(name, idNum);
  }

  @Override
  public Move getNextMove(GameContext game) {
    Board currentBoard = game.getCurrentBoard();

    List<Move> validJumpList = currentBoard.getAllJumpMoves(game.getActiveColor());
    List<Move> validSimpleList = currentBoard.getAllSimpleMoves(game.getActiveColor());

    Move nextMove;
    int max = validJumpList.isEmpty() ? validSimpleList.size() : validJumpList.size();
    ThreadLocalRandom locRand = ThreadLocalRandom.current();
    int randIdx = locRand.nextInt(0, max);
    if(!validJumpList.isEmpty()){
      nextMove = validJumpList.get(randIdx);
    } else {
      nextMove = validSimpleList.get(randIdx);
    }

    return nextMove;
  }
}
