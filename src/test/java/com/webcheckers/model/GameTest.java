package com.webcheckers.model;

import static com.webcheckers.model.Game.COLOR.*;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.webcheckers.model.Game.EndState;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.beans.binding.When;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the {@link Game} component.
 *
 * @author <a href='mailto:sjk7867@rit.edu'>Simon Kirkwood</a>
 * @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
@Tag("model-tier")
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
  @Test
  public void testSwitchTurn(){
    Move testMove0 = new Move(new Position(2,2), new Position(3,3));
    Move testMove1 = new Move(new Position(4,2), new Position(5,3));

    //attempt to switch turn before it should be possible
    CuT.switchTurn();
    //expected: turn does not change
    assertSame(CuT.getActiveColor(), RED);

    //red makes a move
    CuT.makeMove(testMove0);
    CuT.switchTurn();
    //expected: turn changes to wplayer
    assertSame(CuT.getActiveColor(), WHITE);

    //white makes a move
    CuT.makeMove(testMove1);
    CuT.switchTurn();
    //expected: turn changes to rplayer
    assertSame(CuT.getActiveColor(), RED);
  }


  @Test
  public void testGetOpponent(){
    Player sPlayer = mock(Player.class);
    assertNull(CuT.getOpponent(sPlayer));
    assertEquals(CuT.getOpponent(rPlayer), wPlayer);
    assertEquals(CuT.getOpponent(wPlayer), rPlayer);
  }


  @Test
  public void test1ResignPlayer(){
    assertFalse(CuT.hasResigned(rPlayer));
    assertFalse(CuT.hasResigned(wPlayer));

    //rplayer resigns successfully
    assertTrue(CuT.resignPlayer(rPlayer));
    assertTrue(CuT.hasResigned(rPlayer));

    //expected: turn changes to wplayer
    assertSame(CuT.getActiveColor(), WHITE);

    //wplayer resigns unsuccessfully
    assertFalse(CuT.resignPlayer(wPlayer));
    assertFalse(CuT.hasResigned(wPlayer));

    //expected: still wplayer turn
    assertSame(CuT.getActiveColor(), WHITE);
  }

  @Test
  public void test2ResignPlayer(){
    assertFalse(CuT.hasResigned(rPlayer));
    assertFalse(CuT.hasResigned(wPlayer));

    //rplayer turn, but wplayer resigns successfully
    assertTrue(CuT.resignPlayer(wPlayer));
    assertTrue(CuT.hasResigned(wPlayer));

    //expected: rplayer turn
    assertSame(CuT.getActiveColor(), RED);

    //rplayer resigns unsuccessfully
    assertFalse(CuT.resignPlayer(rPlayer));
    assertFalse(CuT.hasResigned(rPlayer));

    //expected: still rplayer turn
    assertSame(CuT.getActiveColor(), RED);
  }

  @Test
  public void test3ResignPlayer(){
    //rplayer moves
    Move testMove0 = new Move(new Position(2,2), new Position(3,3));
    CuT.makeMove(testMove0);
    CuT.switchTurn();

    //wplayer resigns successfully
    assertTrue(CuT.resignPlayer(wPlayer));
    //expected: turn changes to rplayer
    assertSame(CuT.getActiveColor(), RED);

    //rplayer resigns unsuccessfully
    assertFalse(CuT.resignPlayer(rPlayer));
    //expected: still rplayer turn
    assertSame(CuT.getActiveColor(), RED);
  }

  @Test
  public void test4ResignPlayer(){
    //rplayer makes a move
    Move testMove1 = new Move(new Position(2,2), new Position(3,3));
    CuT.makeMove(testMove1);
    CuT.switchTurn();

    //wplayer turn, but rplayer resigns successfully
    assertTrue(CuT.resignPlayer(rPlayer));
    //expected: wplayer turn
    assertSame(CuT.getActiveColor(), WHITE);

    //wplayer resigns unsuccessfully
    assertFalse(CuT.resignPlayer(wPlayer));
    //expected: still wplayer turn
    assertSame(CuT.getActiveColor(), WHITE);
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
  @Test
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
  @Test
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
