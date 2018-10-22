package com.webcheckers.ui.boardView;

/**
 *  {@code Space}
 *  <p>
 *  Represents a single space in the view of the board.
 *  </p>
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mvm7902@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
public class Space {
  //
  // Attributes
  //

  private final int cellIdx;
  private Piece piece = null;
  private boolean validPos = false;

  /**
   * Constructs a Space, which can either be empty or contain a piece.
   *
   * @param cellIdx the index in the Row of this space
   * @param piece   the piece to be stored in this space. A value of null
   *                means the space is empty
   * @param validPos  whether this space is a valid position to place a piece
   */
  public Space(int cellIdx, Piece piece, boolean validPos) {
    this.cellIdx = cellIdx;
    this.piece = piece;
    this.validPos = validPos;
  }

  /**
   * Retrieves the cell index in the row of this space.
   *
   * @return  the index of this space
   */
  public int getCellIdx() {

    return cellIdx;
  }

  /**
   * Checks whether this space is a valid place on which a piece can be placed.
   *
   * @return  whether this is a valid position for a piece
   */
  public boolean isValid() {
    return piece == null && validPos;
  }

  /**
   * Retrieves the piece from this space.
   * A value of null indicates that this space is empty.
   *
   * @return  the piece stored in this space
   */
  public Piece getPiece() {
    return piece;
  }
}
