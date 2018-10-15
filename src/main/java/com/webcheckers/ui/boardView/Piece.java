package com.webcheckers.ui.boardView;

/**
 *  {@code Piece}
 *  <p>
 *  Represents a single red or white Piece, as seen on the view of the board.
 *  </p>
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:mlh1964@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mlh1964@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:mlh1964@rit.edu'>Andrew Festa</a>
 */
public class Piece {
  //
  // Enums
  //

  public enum COLOR {RED, WHITE}
  public enum TYPE {SINGLE, KING}

  //
  // Attributes
  //

  private int row;
  private int col;
  private COLOR color = COLOR.RED;
  private TYPE type = TYPE.SINGLE;

  /**
   * Constructs an instance of a {@code COLOR} Piece at the specified
   * coordinates.
   *
   * @param color the color of the piece
   * @param row   the y-coordinate of the piece on the board
   * @param col   the x-coordinate of the piece on the board
   */
  public Piece(COLOR color, int row, int col) {
    this.color = color;
    this.row = row;
    this.col = col;
  }

  /**
   * Retrieves the COLOR of the piece.
   *
   * @return  the COLOR of the piece
   */
  public COLOR getColor() {
    return color;
  }

  /**
   * Retrieves the TYPE of the piece.
   *
   * @return  the TYPE of the piece
   */
  public TYPE getType() {
    return type;
  }
}
