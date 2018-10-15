package com.webcheckers.ui.boardView;

import com.webcheckers.model.Board.SPACE_TYPE;
import com.webcheckers.ui.boardView.Piece.COLOR;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  {@code Row}
 *  <p>
 *  Represents a single Row on the view of the board.
 *  </p>
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:mlh1964@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mlh1964@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:mlh1964@rit.edu'>Andrew Festa</a>
 */
public class Row implements Iterable {
  //
  // Attributes
  //

  private final int index;
  private ArrayList<Space> spaces;

  /**
   * Constructs a single row, as would be seen on a board.
   *
   * @param idx the index (0 to 7, inclusive) of this row
   * @param spaceList the list of spaces to place in this row
   */
  public Row(int idx, SPACE_TYPE[] spaceList) {
    this.spaces = new ArrayList<>();
    for(int i = 0; i < spaceList.length; i++){
      SPACE_TYPE eachSpace = spaceList[i];
      boolean validPos = (idx*8 + i) % 2 != idx % 2;

      if(eachSpace.equals(SPACE_TYPE.SINGLE_RED)){
        spaces.add(new Space(i, new Piece(COLOR.RED, idx, i), validPos));
      } else if(eachSpace.equals(SPACE_TYPE.SINGLE_WHITE)) {
        spaces.add(new Space(i, new Piece(COLOR.WHITE, idx, i), validPos));
      } else {
        spaces.add(new Space(i, null, validPos));
      }
    }
    this.index = idx;
  }

  /**
   * Retrieves the index (0 to 7, inclusive) of this row on the board.
   *
   * @return  the index of this row
   */
  public int getIndex() {

    return index;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator iterator() {
    return spaces.iterator();
  }
}
