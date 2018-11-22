package com.webcheckers.model;

import static com.webcheckers.model.GameState.GameContext.COLOR;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.webcheckers.model.Game.EndState;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the {@link Game} component.
 *
 * @author <a href='mailto:sjk7867@rit.edu'>Simon Kirkwood</a>
 * @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
@Tag("Model-tier")
public class GameTest {

  private Game CuT;
  private Player wPlayer;
  private Player rPlayer;

  /**
   * TODO
   */
  @BeforeEach
  public void setup(){
    wPlayer = mock(Player.class);
    rPlayer = mock(Player.class);
    CuT = new Game(rPlayer,wPlayer);
  }

  /**
   * TODO
   */
  public void testSwitchTurn(){
    Move testMove0 = new Move(new Position(0,1), new Position(0,1));
    Move testMove1 = new Move(new Position(1,2), new Position(1,2));

    CuT.switchTurn();
    assertSame(CuT.getActiveColor(), COLOR.WHITE);
    CuT.switchTurn();
    assertSame(CuT.getActiveColor(), COLOR.RED);
//    CuT.makeMove(testMove0);
//    assertSame(CuT.getActiveColor(), WHITE);
//    CuT.makeMove(testMove1);
//    assertSame(CuT.getActiveColor(), RED);
  }

  /**
   * TODO
   */
  @Test
  public void testPlayers(){
    assertTrue(CuT.hasPlayer(wPlayer));
    assertTrue(CuT.hasPlayer(rPlayer));

    assertSame(CuT.getWhitePlayer(),wPlayer);
    assertSame(CuT.getRedPlayer(),rPlayer);
  }

  /**
   * TODO
   */
  public void testMoveValidation(){
    Map<Move, Boolean> testMoves = new HashMap<>();

    testMoves.put(new Move(new Position(0, 0), new Position(0, 0)), false);
    testMoves.put(new Move(new Position(0, 0), new Position(0, 1)), false);
    testMoves.put(new Move(new Position(0, 0), new Position(1, 1)), false);
    testMoves.put(new Move(new Position(0, 0), new Position(1, 0)), false);
    testMoves.put(new Move(new Position(0, 0), new Position(1, -1)), false);
    testMoves.put(new Move(new Position(0, 0), new Position(0, -1)), false);
    testMoves.put(new Move(new Position(0, 0), new Position(-1, -1)), false);
    testMoves.put(new Move(new Position(0, 0), new Position(-1, 0)), false);
    testMoves.put(new Move(new Position(0, 0), new Position(-1, 1)), false);

    testMoves.put(new Move(new Position(0, 2), new Position(0, 2)), false);
    testMoves.put(new Move(new Position(0, 2), new Position(0, 3)), false);
    testMoves.put(new Move(new Position(0, 2), new Position(1, 3)), true);
    testMoves.put(new Move(new Position(0, 2), new Position(1, 2)), false);
    testMoves.put(new Move(new Position(0, 2), new Position(1, 1)), false);
    testMoves.put(new Move(new Position(0, 2), new Position(0, 1)), false);
    testMoves.put(new Move(new Position(0, 2), new Position(-1, 1)), false);
    testMoves.put(new Move(new Position(0, 2), new Position(-1, 2)), false);
    testMoves.put(new Move(new Position(0, 2), new Position(-1, 3)), false);

    testMoves.put(new Move(new Position(6, 2), new Position(6, 2)), false);
    testMoves.put(new Move(new Position(6, 2), new Position(6, 3)), false);
    testMoves.put(new Move(new Position(6, 2), new Position(7, 3)), true);
    testMoves.put(new Move(new Position(6, 2), new Position(7, 2)), false);
    testMoves.put(new Move(new Position(6, 2), new Position(7, 1)), false);
    testMoves.put(new Move(new Position(6, 2), new Position(6, 1)), false);
    testMoves.put(new Move(new Position(6, 2), new Position(5, 1)), false);
    testMoves.put(new Move(new Position(6, 2), new Position(5, 2)), false);
    testMoves.put(new Move(new Position(6, 2), new Position(5, 3)), true);

    testMoves.put(new Move(new Position(1, 5), new Position(1, 5)), false);
    testMoves.put(new Move(new Position(1, 5), new Position(1, 6)), false);
    testMoves.put(new Move(new Position(1, 5), new Position(2, 6)), false);
    testMoves.put(new Move(new Position(1, 5), new Position(3, 5)), false);
    testMoves.put(new Move(new Position(1, 5), new Position(2, 4)), true);
    testMoves.put(new Move(new Position(1, 5), new Position(1, 4)), false);
    testMoves.put(new Move(new Position(1, 5), new Position(0, 4)), true);
    testMoves.put(new Move(new Position(1, 5), new Position(0, 5)), false);
    testMoves.put(new Move(new Position(1, 5), new Position(0, 6)), false);

    testMoves.put(new Move(new Position(7, 5), new Position(7, 5)), false);
    testMoves.put(new Move(new Position(7, 5), new Position(7, 6)), false);
    testMoves.put(new Move(new Position(7, 5), new Position(8, 6)), false);
    testMoves.put(new Move(new Position(7, 5), new Position(8, 5)), false);
    testMoves.put(new Move(new Position(7, 5), new Position(8, 4)), false);
    testMoves.put(new Move(new Position(7, 5), new Position(7, 4)), false);
    testMoves.put(new Move(new Position(7, 5), new Position(6, 4)), true);
    testMoves.put(new Move(new Position(7, 5), new Position(6, 5)), false);
    testMoves.put(new Move(new Position(7, 5), new Position(6, 6)), false);

    for(Entry eachEntry : testMoves.entrySet()){
      Move eachMove = (Move) eachEntry.getKey();
      Boolean validMove = (Boolean) eachEntry.getValue();
      assertEquals(validMove, CuT.validateMove(eachMove),
          String.format("Start: %s -> End: %s", eachMove.getStart(), eachMove.getEnd()));
    }
  }

  /**
   * TODO
   */
  @Test
  void testMovePiece() {
    Map<Move, Boolean> testMoves = new HashMap<>();

    testMoves.put(new Move(new Position(0, 0), new Position(0, 0)), false);
    testMoves.put(new Move(new Position(0, 0), new Position(0, 1)), false);
    testMoves.put(new Move(new Position(0, 0), new Position(1, 1)), false);
    testMoves.put(new Move(new Position(0, 0), new Position(1, 0)), false);
    testMoves.put(new Move(new Position(0, 0), new Position(1, -1)), false);
    testMoves.put(new Move(new Position(0, 0), new Position(0, -1)), false);
    testMoves.put(new Move(new Position(0, 0), new Position(-1, -1)), false);
    testMoves.put(new Move(new Position(0, 0), new Position(-1, 0)), false);
    testMoves.put(new Move(new Position(0, 0), new Position(-1, 1)), false);

    for(Entry eachEntry : testMoves.entrySet()){
      Move eachMove = (Move) eachEntry.getKey();
      Boolean validMove = (Boolean) eachEntry.getValue();
      assertEquals(validMove, CuT.makeMove(eachMove),
          String.format("Start: %s -> End: %s", eachMove.getStart(), eachMove.getEnd()));
    }
  }

  /**
   * TODO
   */
  void testCheckEnd() {
    Board board = mock(Board.class);
    CuT = new Game(wPlayer,rPlayer);

    when(CuT.getBoardState()).thenReturn(board);
    when(board.getNumRedPieces()).thenReturn(0);
    assertTrue(CuT.checkEnd());
    assertEquals(CuT.getWhitePlayer(),CuT.getWinner());
    assertEquals(EndState.ALL_PIECES,CuT.getEndState());

    CuT = new Game(wPlayer,rPlayer);
    when(board.getNumRedPieces()).thenReturn(1);
    when(board.getNumWhitePieces()).thenReturn(0);
    assertTrue(CuT.checkEnd());
    assertEquals(CuT.getRedPlayer(),CuT.getWinner());
    assertEquals(EndState.ALL_PIECES,CuT.getEndState());

    CuT = new Game(wPlayer,rPlayer);
    when(board.getNumWhitePieces()).thenReturn(1);;
    assertFalse(CuT.checkEnd());
    assertNull(CuT.getWinner());
    assertEquals(EndState.NOT_OVER,CuT.getEndState());
  }
}
