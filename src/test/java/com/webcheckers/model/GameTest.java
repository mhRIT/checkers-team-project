package com.webcheckers.model;

import static com.webcheckers.model.Game.COLOR.*;

import static org.mockito.Mockito.mock;

import static org.junit.jupiter.api.Assertions.*;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class GameTest {

  private Game CuT;
  private Player wPlayer;
  private Player rPlayer;

  @BeforeEach
  public void setup(){
    wPlayer = mock(Player.class);
    rPlayer = mock(Player.class);
    CuT = new Game(rPlayer,wPlayer);
  }

  @Test
  public void testSwitchTurn(){
    Move testMove0 = new Move(new Position(0,1), new Position(0,1));
    Move testMove1 = new Move(new Position(1,2), new Position(1,2));

    CuT.switchTurn();
    assertSame(CuT.getActiveColor(), WHITE);
    CuT.switchTurn();
    assertSame(CuT.getActiveColor(), RED);
//    CuT.makeMove(testMove0);
//    assertSame(CuT.getActiveColor(), WHITE);
//    CuT.makeMove(testMove1);
//    assertSame(CuT.getActiveColor(), RED);
  }

  @Test
  public void testPlayers(){
    assertTrue(CuT.hasPlayer(wPlayer));
    assertTrue(CuT.hasPlayer(rPlayer));

    assertSame(CuT.getWhitePlayer(),wPlayer);
    assertSame(CuT.getRedPlayer(),rPlayer);
  }

  @Test
  public void testMoveValidation(){
    Map<Move, Boolean> testMoves = new HashMap();

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

  @Test
  void testMovePiece() {
    Map<Move, Boolean> testMoves = new HashMap();

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

  @Test
  void testCheckEnd() {
    assertFalse(CuT.checkEnd());
  }
}
