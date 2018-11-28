package com.webcheckers.model.Board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webcheckers.model.Board.Board;
import com.webcheckers.model.Board.Board.COLOR;
import com.webcheckers.model.Board.Board.SPACE_TYPE;
import com.webcheckers.model.Board.Move;
import com.webcheckers.model.Board.Position;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the {@link Board} component.
 *
 * @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
@Tag("Model-tier")
class BoardTest {
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

  /**
   * Tests the string representation of the board.
   */
  @Test
  void testToString(){
    String correctStartView =
        "-----------------\n" +
        "| |w| |w| |w| |w|\n" +
        "|w| |w| |w| |w| |\n" +
        "| |w| |w| |w| |w|\n" +
        "| | | | | | | | |\n" +
        "| | | | | | | | |\n" +
        "|r| |r| |r| |r| |\n" +
        "| |r| |r| |r| |r|\n" +
        "|r| |r| |r| |r| |\n" +
        "-----------------";
    cut.initStart();
    String cutString = cut.toString();
    System.out.println(cutString);
    assertEquals(correctStartView, cutString);
  }

  /**
   * TODO
   */
  @Test
  void testInitStart() {
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

  /**
   * Tests that the board recognizes pieces along its front and back
   */
  @Test
  void testPiecesAlongBase(){
    cut.initStart();

    int count = cut.getNumPiecesAlongBase(COLOR.RED);
    assertEquals(4, count);

    count = cut.getNumPiecesAlongBase(COLOR.WHITE);
    assertEquals(4, count);
  }

  /**
   * Tests that the board recognizes pieces along its sides
   */
  @Test
  void testPiecesAlongEdge(){
    cut.initStart();

    int count = cut.getNumPiecesAlongSide(COLOR.RED);
    assertEquals(3, count);

    count = cut.getNumPiecesAlongSide(COLOR.WHITE);
    assertEquals(3, count);
  }

  /**
   * Tests that the board's indices are correctly mapped to positions based on row and column
   */
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
        assertEquals(indices[counter++], Board.cartesianToIndex(j,i),
            String.format("(%d, %d), %d", j, i, counter));
      }
    }
  }

  /**
   * Tests that different types of pieces can be placed on the board
   */
  @Test
  void testPlacePiece() {
    for(SPACE_TYPE eachSpaceType : SPACE_TYPE.values()){
      cut.initStart();
      testPlacePieceType(eachSpaceType);
    }
  }

  /**
   * Tests that a piece of a specific type can be placed on an empty square
   *
   * @param pieceToPlace
   */
  private void testPlacePieceType(SPACE_TYPE pieceToPlace){
    boolean piecePlaced;
    SPACE_TYPE pieceAtLoc;

    for(int yCoord = 0; yCoord < Board.BOARD_SIZE; yCoord++){
      for(int xCoord = 0; xCoord < Board.BOARD_SIZE; xCoord++){
        pieceAtLoc = cut.getPieceAtLocation(xCoord, yCoord);
        piecePlaced = cut.placePiece(xCoord, yCoord, pieceToPlace);

        if(pieceAtLoc.equals(SPACE_TYPE.EMPTY)
            && Board.isValidLocation(new Position(xCoord, yCoord))
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

  /**
   * Tests that a piece can be removed from the board
   */
  @Test
  void testRemovePiece() {
    cut.initStart();

    for(int yCoord = 0; yCoord < Board.BOARD_SIZE; yCoord++){
      for(int xCoord = 0; xCoord < Board.BOARD_SIZE; xCoord++){
        SPACE_TYPE pieceAtLoc = cut.getPieceAtLocation(xCoord, yCoord);
        boolean pieceRemoved = cut.removePiece(xCoord, yCoord);
        if(pieceAtLoc.isEmpty()){
          assertFalse(pieceRemoved, String.format("(%d, %d)", xCoord, yCoord));
        } else {
          assertTrue(pieceRemoved, String.format("(%d, %d)", xCoord, yCoord));
        }

        pieceAtLoc = cut.getPieceAtLocation(xCoord, yCoord);
        assertEquals(SPACE_TYPE.EMPTY, pieceAtLoc);
      }
    }
  }

  /**
   * Tests that squares will be correctly deemed valid/invalid as positions on the board
   */
  @Test
  void testIsValidLocation(){
    cut.initStart();
    int modVal = 0;

    for(int yCoord = 0; yCoord < Board.BOARD_SIZE; yCoord++){
      for(int xCoord = 0; xCoord < Board.BOARD_SIZE; xCoord++){
        boolean isValid = Board.isValidLocation(new Position(xCoord, yCoord));
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

  /**
   * Tests that a list of all possible simple moves can be obtained given a board state
   */
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
      boolean success = cut.makeMove(eachMove);
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
      boolean success = cut.makeMove(eachMove);
      assertTrue(success);
    }

    testWhiteSimpleMoves(14);
  }

  /**
   * Tests that a list of the red player's possible simple moves can be obtained
   */
  private void testRedSimpleMoves(int numMoves){
    List<Move> allRedMoves = cut.getAllSimpleMoves(COLOR.RED);
    assertNotNull(allRedMoves);
    assertEquals(numMoves, allRedMoves.size());
  }

  /**
   * Tests that a list of the white player's possible simple moves can be obtained
   */
  private void testWhiteSimpleMoves(int numMoves){
    List<Move> allWhiteMoves = cut.getAllSimpleMoves(COLOR.WHITE);
    assertNotNull(allWhiteMoves);
    assertEquals(numMoves, allWhiteMoves.size());
  }

  /**
   * Tests that a list of all possible jump moves can be correctly obtained
   */
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
      boolean success = cut.makeMove(eachMove);
      assertTrue(success);
    }

    testRedJumpMoves(6);
    testWhiteJumpMoves(6);
  }

  /**
   * Tests that a list of all red's possible jump moves can be obtained
   *
   * @param numMoves
   */
  private void testRedJumpMoves(int numMoves){
    List<Move> allRedMoves = cut.getAllJumpMoves(COLOR.RED);
    assertNotNull(allRedMoves);
    assertEquals(numMoves, allRedMoves.size());
  }

  /**
   * Tests that a list of all white's possible jump moves can be obtained
   *
   * @param numMoves
   */
  private void testWhiteJumpMoves(int numMoves){
    List<Move> allWhiteMoves = cut.getAllJumpMoves(COLOR.WHITE);
    assertNotNull(allWhiteMoves);
    assertEquals(numMoves, allWhiteMoves.size());
  }

  /**
   * Tests that a piece can be correctly moved from one position to another
   */
  @Test
  void testMovePiece(){
    cut.initStart();

    List<Move> testMoves = new ArrayList<>();
    testMoves.add(new Move(new Position(0,2), new Position(1,3)));
    testMoves.add(new Move(new Position(2,2), new Position(3,3)));
    testMoves.add(new Move(new Position(4,2), new Position(5,3)));
    testMoves.add(new Move(new Position(6,2), new Position(7,3)));

    for(Move eachMove : testMoves){
      boolean success = cut.makeMove(eachMove);
      assertTrue(success);
    }
  }

  /**
   * Tests that the initial board configuration is correct from the perspective of the opposing player
   */
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

  /**
   * Tests that a row has the correct starting configuration for the opposite player
   *
   * @param idx
   * @param expectedRow
   */
  private void testEachRowReverse(int idx, SPACE_TYPE[] expectedRow){
    List<SPACE_TYPE> actualRow = cut.getRow(idx);
    Collections.reverse(actualRow);
    assertEquals(expectedRow.length, actualRow.size(),
        String.format("exp len: %d, act len: %d", expectedRow.length, actualRow.size()));
    for(int i = 0; i < expectedRow.length; i++){
      assertEquals(expectedRow[i], actualRow.get(i),
          String.format("row idx: %d, col idx: %d", idx, i));
    }
  }

  /**
   * Tests that the board configuration is correct from the perspective of the primary player
   */
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
      SPACE_TYPE[] eachRow = expectedRows[i];
      testEachRow(i, Arrays.asList(eachRow));
    }
  }

  /**
   * Tests that a row is correctly configured for the primary player
   *
   * @param idx
   * @param expectedRow
   */
  private void testEachRow(int idx, List<SPACE_TYPE> expectedRow){
    List<SPACE_TYPE> actualRow = cut.getRow(idx);
    assertEquals(expectedRow.size(), actualRow.size(),
        String.format("exp len: %d, act len: %d", expectedRow.size(), actualRow.size()));
    for(int i = 0; i < expectedRow.size(); i++){
      assertEquals(expectedRow.get(i), actualRow.get(i),
          String.format("row idx: %d, col idx: %d", idx, i));
    }
  }

  /**
   * Tests that a piece can be correctly returned based on a location
   */
  @Test
  void testGetPieceAtLocation() {
    cut.initStart();

    // TODO create unit tests
  }

  /**
   * Tests that a list of all positions of red pieces can be correctly obtained
   */
  @Test
  void testGetRedLocations() {
    cut.initStart();
    // 0b0000_0000_0000_0000_0000_1111_1111_1111
    int locVal = 0b1111_1111_1111;
    assertEquals(locVal, cut.getRedLocations());
  }

  /**
   * Tests that the number of red pieces can be obtained
   */
  @Test
  void testGetNumRedPieces() {
    cut.initStart();
    int numPieces = 12;
    assertEquals(numPieces, cut.getNumPieces(COLOR.RED));
  }

  /**
   * Tests that a list of all positions of white pieces can be correctly obtained
   */
  @Test
  void testGetWhiteLocations() {
    cut.initStart();
    // 0b1111_1111_1111_0000_0000_0000_0000_0000
    int locVal = 0b1111_1111_1111 << 20;
    assertEquals(locVal, cut.getWhiteLocations());
  }

  /**
   * Tests that the number of white pieces can be obtained
   */
  @Test
  void testGetNumWhitePieces() {
    cut.initStart();
    int numPieces = 12;
    assertEquals(numPieces, cut.getNumPieces(COLOR.WHITE));
  }

  /**
   * Tests that the number of pieces can be obtained
   */
  @Test
  void testGetNumPieces() {
    cut.initStart();
    int numPieces = 12;
    assertEquals(numPieces, cut.getNumPieces(COLOR.RED));
    assertEquals(numPieces, cut.getNumPieces(COLOR.WHITE));
  }
}