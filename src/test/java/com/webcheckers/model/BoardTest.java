package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.webcheckers.model.Board.SPACE_TYPE;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {

  //
  // Constants
  //

  //
  // Components under test
  //
  private Board cut;

  /**
   * Setup the state of the instance for each test.
   */
  @BeforeEach
  void setUp() {
    cut = new Board();
  }

  /**
   * Tests that the Board was successfully created.
   */
  @Test
  void testNotNull() {
    assertNotNull(cut);
  }

  @Test
  void TestInitStart() {
    cut.initStart();

    int modVal = 0;
    for(int i = 0; i < Board.Y_BOARD_SIZE; i++){
      for(int j = 0; j < Board.X_BOARD_SIZE; j++){
        SPACE_TYPE eachSpace = cut.getPieceAtLocation(j,i);
        assertNotNull(eachSpace);
        if(modVal % 2 == 0){
          if(i < 3){
            assertEquals(SPACE_TYPE.SINGLE_RED, eachSpace, String.format("(%d, %d): %s", j, i, eachSpace.toString()));
          } else if(i > 4) {
            assertEquals(SPACE_TYPE.SINGLE_WHITE, eachSpace, String.format("(%d, %d): %s", j, i, eachSpace.toString()));
          } else {
            assertEquals(SPACE_TYPE.EMPTY, eachSpace, String.format("(%d, %d): %s", j, i, eachSpace.toString()));
          }
        } else {
          assertEquals(SPACE_TYPE.EMPTY, eachSpace, String.format("(%d, %d): %s", j, i, eachSpace.toString()));
        }
        modVal ^= 1;
      }
      modVal ^= 1;
    }
  }

  @Test
  void testValidateMove() {
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
      cut.initStart();
      assertEquals(validMove, cut.validateMove(eachMove.getStart(), eachMove.getEnd()),
          String.format("Start: %s -> End: %s", eachMove.getStart(), eachMove.getEnd()));
    }
  }

  @Test
  void testMovePiece() {
    Position testStart = new Position(0,0);
    Position testEnd = new Position(0,0);

    cut.initStart();
    assertFalse(cut.movePiece(testStart, testEnd));
  }

  @Test
  void testCheckEnd() {
    cut.initStart();
    assertFalse(cut.checkEnd());
  }

  @Test
  void testCartesianToIndex() {
    int[] indices = {
        0,  -1, 1,  -1, 2,  -1, 3,  -1,
        -1, 4,  -1, 5,  -1, 6,  -1, 7,
        8,  -1, 9,  -1, 10, -1, 11, -1,
        -1, 12, -1, 13, -1, 14, -1, 15,
        16, -1, 17, -1, 18, -1, 19, -1,
        -1, 20, -1, 21, -1, 22, -1, 23,
        24, -1, 25, -1, 26, -1, 27, -1,
        -1, 28, -1, 29, -1, 30, -1, 31
    };
    int counter = 0;

    for(int i = 0; i < Board.Y_BOARD_SIZE; i++){
      for(int j = 0; j < Board.X_BOARD_SIZE; j++){
        assertEquals(indices[counter++], cut.cartesianToIndex(j,i),
            String.format("(%d, %d), %d", j, i, counter));
      }
    }
  }

  @Test
  void testPlacePiece() {
    for(SPACE_TYPE eachSpaceType : SPACE_TYPE.values()){
      cut.initStart();
      testPlacePieceType(eachSpaceType);
    }
  }

  private void testPlacePieceType(SPACE_TYPE pieceToPlace){
    boolean piecePlaced;
    SPACE_TYPE pieceAtLoc;

    for(int yCoord = 0; yCoord < Board.Y_BOARD_SIZE; yCoord++){
      for(int xCoord = 0; xCoord < Board.X_BOARD_SIZE; xCoord++){
        pieceAtLoc = cut.getPieceAtLocation(xCoord, yCoord);
        piecePlaced = cut.placePiece(xCoord, yCoord, pieceToPlace);

        if(pieceAtLoc.equals(SPACE_TYPE.EMPTY)
            && cut.isValidLocation(xCoord, yCoord)
            && !pieceToPlace.equals(SPACE_TYPE.EMPTY)){
          assertTrue(piecePlaced,
              String.format("(%d, %d): %s", xCoord, yCoord, piecePlaced));
          pieceAtLoc = cut.getPieceAtLocation(xCoord, yCoord);
          assertEquals(pieceAtLoc, pieceToPlace,
              String.format("(%d, %d): exp: %s, act: %s", xCoord, yCoord, pieceToPlace, pieceAtLoc));
        } else {
          assertFalse(piecePlaced,
              String.format("(%d, %d): %s", xCoord, yCoord, piecePlaced));
        }
      }
    }
  }

  @Test
  void testRemovePiece() {
    cut.initStart();

    for(int yCoord = 0; yCoord < Board.Y_BOARD_SIZE; yCoord++){
      for(int xCoord = 0; xCoord < Board.X_BOARD_SIZE; xCoord++){
        SPACE_TYPE pieceAtLoc = cut.getPieceAtLocation(xCoord, yCoord);
        SPACE_TYPE remPiece = cut.removePiece(xCoord, yCoord);
        assertEquals(pieceAtLoc, remPiece,
            String.format("(%d, %d): exp: %s, act: %s", xCoord, yCoord, pieceAtLoc, remPiece));

        pieceAtLoc = cut.getPieceAtLocation(xCoord, yCoord);
        assertEquals(pieceAtLoc, SPACE_TYPE.EMPTY,
            String.format("(%d, %d): exp: %s, act: %s", xCoord, yCoord, pieceAtLoc, remPiece));
      }
    }
  }

  @Test
  void testIsValidLocation(){
    cut.initStart();
    int modVal = 0;

    for(int yCoord = 0; yCoord < Board.Y_BOARD_SIZE; yCoord++){
      for(int xCoord = 0; xCoord < Board.X_BOARD_SIZE; xCoord++){
        boolean isValid = cut.isValidLocation(xCoord, yCoord);
        if(modVal % 2 == 0){
          assertTrue(isValid, String.format("(%d, %d)", xCoord, yCoord));
        } else {
          assertFalse(isValid, String.format("(%d, %d)", xCoord, yCoord));
        }
        modVal ^= 1;
      }
      modVal ^= 1;
    }
  }

  @Test
  void testGetRowReverse(){
    cut.initStart();

    SPACE_TYPE[][] expectedRows = new SPACE_TYPE[][]{
        {SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_RED, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_RED,
            SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_RED, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_RED},

        {SPACE_TYPE.SINGLE_RED, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_RED, SPACE_TYPE.EMPTY,
            SPACE_TYPE.SINGLE_RED, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_RED, SPACE_TYPE.EMPTY},

        {SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_RED, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_RED,
            SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_RED, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_RED},

        {SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY,
            SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY},

        {SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY,
            SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY},

        {SPACE_TYPE.SINGLE_WHITE, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_WHITE, SPACE_TYPE.EMPTY,
            SPACE_TYPE.SINGLE_WHITE, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_WHITE, SPACE_TYPE.EMPTY},

        {SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_WHITE, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_WHITE,
            SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_WHITE, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_WHITE},

        {SPACE_TYPE.SINGLE_WHITE, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_WHITE, SPACE_TYPE.EMPTY,
            SPACE_TYPE.SINGLE_WHITE, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_WHITE, SPACE_TYPE.EMPTY},
    };

    for(int i = 0; i < expectedRows.length; i++){
      testEachRowReverse(i, expectedRows[i]);
    }
  }

  private void testEachRowReverse(int idx, SPACE_TYPE[] expectedRow){
    SPACE_TYPE[] actualRow = cut.getRowReverse(idx);
    assertEquals(expectedRow.length, actualRow.length,
        String.format("exp len: %d, act len: %d", expectedRow.length, actualRow.length));
    for(int i = 0; i < expectedRow.length; i++){
      assertEquals(expectedRow[i], actualRow[i],
          String.format("row idx: %d, col idx: %d", idx, i));
    }
  }

  @Test
  void testGetRow() {
    cut.initStart();

    SPACE_TYPE[][] expectedRows = new SPACE_TYPE[][]{
        {SPACE_TYPE.SINGLE_RED, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_RED, SPACE_TYPE.EMPTY,
         SPACE_TYPE.SINGLE_RED, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_RED, SPACE_TYPE.EMPTY},

        {SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_RED, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_RED,
         SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_RED, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_RED},

        {SPACE_TYPE.SINGLE_RED, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_RED, SPACE_TYPE.EMPTY,
         SPACE_TYPE.SINGLE_RED, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_RED, SPACE_TYPE.EMPTY},

        {SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY,
         SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY},

        {SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY,
         SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY, SPACE_TYPE.EMPTY},

        {SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_WHITE, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_WHITE,
         SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_WHITE, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_WHITE},

        {SPACE_TYPE.SINGLE_WHITE, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_WHITE, SPACE_TYPE.EMPTY,
         SPACE_TYPE.SINGLE_WHITE, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_WHITE, SPACE_TYPE.EMPTY},

        {SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_WHITE, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_WHITE,
         SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_WHITE, SPACE_TYPE.EMPTY, SPACE_TYPE.SINGLE_WHITE},
    };

    for(int i = 0; i < expectedRows.length; i++){
      testEachRow(i, expectedRows[i]);
    }
  }

  private void testEachRow(int idx, SPACE_TYPE[] expectedRow){
    SPACE_TYPE[] actualRow = cut.getRow(idx);
    assertEquals(expectedRow.length, actualRow.length,
        String.format("exp len: %d, act len: %d", expectedRow.length, actualRow.length));
    for(int i = 0; i < expectedRow.length; i++){
      assertEquals(expectedRow[i], actualRow[i],
          String.format("row idx: %d, col idx: %d", idx, i));
    }
  }

  @Test
  void testGetPieceAtLocation() {
    cut.initStart();

    // TODO create unit tests
  }

  @Test
  void testGetRedLocations() {
    cut.initStart();
    // 0b0000_0000_0000_0000_0000_1111_1111_1111
    int locVal = 0b1111_1111_1111;
    assertEquals(locVal, cut.getRedLocations());
  }

  @Test
  void testGetNumRedPieces() {
    cut.initStart();
    int numPieces = 12;
    assertEquals(numPieces, cut.getNumRedPieces());
  }

  @Test
  void testGetWhiteLocations() {
    cut.initStart();
    // 0b1111_1111_1111_0000_0000_0000_0000_0000
    int locVal = 0b1111_1111_1111 << 20;
    assertEquals(locVal, cut.getWhiteLocations());
  }

  @Test
  void testGetNumWhitePieces() {
    cut.initStart();
    int numPieces = 12;
    assertEquals(numPieces, cut.getNumWhitePieces());
  }

  @Test
  void testGetNumPieces() {
    cut.initStart();
    int numPieces = 24;
    assertEquals(numPieces, cut.getNumPieces());
  }

  //@Test: try to move a non-king backwards.

  //@Test: try to make a piece jump a piece of its own color.
}