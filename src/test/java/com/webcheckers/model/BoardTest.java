package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webcheckers.model.Board.SPACE_TYPE;
import java.util.ArrayList;
import java.util.List;
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
    for(int i = 0; i < Board.BOARD_SIZE; i++){
      for(int j = 0; j < Board.BOARD_SIZE; j++){
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

    for(int i = 0; i < Board.BOARD_SIZE; i++){
      for(int j = 0; j < Board.BOARD_SIZE; j++){
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

    for(int yCoord = 0; yCoord < Board.BOARD_SIZE; yCoord++){
      for(int xCoord = 0; xCoord < Board.BOARD_SIZE; xCoord++){
        pieceAtLoc = cut.getPieceAtLocation(xCoord, yCoord);
        piecePlaced = cut.placePiece(xCoord, yCoord, pieceToPlace);

        if(pieceAtLoc.equals(SPACE_TYPE.EMPTY)
            && cut.isValidLocation(new Position(xCoord, yCoord))
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

    for(int yCoord = 0; yCoord < Board.BOARD_SIZE; yCoord++){
      for(int xCoord = 0; xCoord < Board.BOARD_SIZE; xCoord++){
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

    for(int yCoord = 0; yCoord < Board.BOARD_SIZE; yCoord++){
      for(int xCoord = 0; xCoord < Board.BOARD_SIZE; xCoord++){
        boolean isValid = cut.isValidLocation(new Position(xCoord, yCoord));
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
  void testGetAllSimpleMoves(){
    cut.initStart();
    testRedSimpleMoves(7);
    testWhiteSimpleMoves(7);

    List<Move> testMoves = new ArrayList<>();
    testMoves.add(new Move(new Position(0,2), new Position(1,3)));
    testMoves.add(new Move(new Position(2,2), new Position(3,3)));
    testMoves.add(new Move(new Position(4,2), new Position(5,3)));
    testMoves.add(new Move(new Position(6,2), new Position(7,3)));

    for(Move eachMove : testMoves){
      boolean success = cut.movePiece(eachMove);
      assertTrue(success);
    }

    testRedSimpleMoves(14);

    cut.initStart();
    testMoves = new ArrayList<>();
    testMoves.add(new Move(new Position(1,5), new Position(0,4)));
    testMoves.add(new Move(new Position(3,5), new Position(2,4)));
    testMoves.add(new Move(new Position(5,5), new Position(4,4)));
    testMoves.add(new Move(new Position(7,5), new Position(6,4)));

    for(Move eachMove : testMoves){
      boolean success = cut.movePiece(eachMove);
      assertTrue(success);
    }

    testWhiteSimpleMoves(14);
  }

  private void testRedSimpleMoves(int numMoves){
    List<Move> allRedMoves = cut.getAllRedSimpleMoves();
    assertNotNull(allRedMoves);
    assertEquals(numMoves, allRedMoves.size());
  }

  private void testWhiteSimpleMoves(int numMoves){
    List<Move> allWhiteMoves = cut.getAllWhiteSimpleMoves();
    assertNotNull(allWhiteMoves);
    assertEquals(numMoves, allWhiteMoves.size());
  }

  @Test
  void testGetAllJumpMoves(){
    cut.initStart();
    testRedJumpMoves(0);
    testWhiteJumpMoves(0);

    List<Move> testMoves = new ArrayList<>();
    testMoves.add(new Move(new Position(0,2), new Position(1,3)));
    testMoves.add(new Move(new Position(2,2), new Position(3,3)));
    testMoves.add(new Move(new Position(4,2), new Position(5,3)));
    testMoves.add(new Move(new Position(6,2), new Position(7,3)));

    testMoves.add(new Move(new Position(1,5), new Position(0,4)));
    testMoves.add(new Move(new Position(3,5), new Position(2,4)));
    testMoves.add(new Move(new Position(5,5), new Position(4,4)));
    testMoves.add(new Move(new Position(7,5), new Position(6,4)));

    for(Move eachMove : testMoves){
      boolean success = cut.movePiece(eachMove);
      assertTrue(success);
    }

    testRedJumpMoves(6);
    testWhiteJumpMoves(6);
  }

  private void testRedJumpMoves(int numMoves){
    List<Move> allRedMoves = cut.getAllRedJumpMoves();
    assertNotNull(allRedMoves);
    assertEquals(numMoves, allRedMoves.size());
  }

  private void testWhiteJumpMoves(int numMoves){
    List<Move> allWhiteMoves = cut.getAllWhiteJumpMoves();
    assertNotNull(allWhiteMoves);
    assertEquals(numMoves, allWhiteMoves.size());
  }

  @Test
  void testMovePiece(){
    cut.initStart();

    List<Move> testMoves = new ArrayList<>();
    testMoves.add(new Move(new Position(0,2), new Position(1,3)));
    testMoves.add(new Move(new Position(2,2), new Position(3,3)));
    testMoves.add(new Move(new Position(4,2), new Position(5,3)));
    testMoves.add(new Move(new Position(6,2), new Position(7,3)));

    for(Move eachMove : testMoves){
      boolean success = cut.movePiece(eachMove);
      assertTrue(success);
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