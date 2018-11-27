package com.webcheckers.ui.boardView;

import com.webcheckers.model.Board.Board;
import com.webcheckers.model.Board.Board.SPACE_TYPE;
import com.webcheckers.ui.boardView.Piece.COLOR;
import com.webcheckers.ui.boardView.Piece.TYPE;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  {@code Row}
 *  Represents a single Row on the view of the board.
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mvm7902@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
public class Row implements Iterable {
  //
  // Attributes
  //
  private final int index;
  private List<Space> spaces;

  /**
   * Constructs a single row, as would be seen on a board.
   *
   * @param idx the index (0 to 7, inclusive) of this row
   * @param spaceList the list of spaces to place in this row
   */
  public Row(int idx, List<SPACE_TYPE> spaceList) {
    this.spaces = new ArrayList<>();
    for(int i = 0; i < spaceList.size(); i++){
      SPACE_TYPE eachSpace = spaceList.get(i);
      boolean validPos = (idx * Board.BOARD_SIZE + i) % 2 == (idx % 2);

      switch (eachSpace){
        case SINGLE_RED:
          spaces.add(new Space(i, new Piece(COLOR.RED, TYPE.SINGLE, idx, i), validPos));
          break;
        case KING_RED:
          spaces.add(new Space(i, new Piece(COLOR.RED, TYPE.KING, idx, i), validPos));
          break;
        case SINGLE_WHITE:
          spaces.add(new Space(i, new Piece(COLOR.WHITE, TYPE.SINGLE, idx, i), validPos));
          break;
        case KING_WHITE:
          spaces.add(new Space(i, new Piece(COLOR.WHITE, TYPE.KING, idx, i), validPos));
          break;
        default:
          spaces.add(new Space(i, null, validPos));
          break;
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
