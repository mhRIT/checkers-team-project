package com.webcheckers.ui.boardView;

import com.webcheckers.model.Board;
import com.webcheckers.model.Board.SPACE_TYPE;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  {@code BoardView}
 *  <p>
 *  Represents the view of the board, as expected by the game.ftl
 *  Freemarker template.
 *  </p>
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mvm7902@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
public class BoardView implements Iterable {
  //
  // Attributes
  //

  private ArrayList<Row> rows = new ArrayList<>();

  /**
   * Constructs the view of the board, as needed by the game.ftl
   * Freemarker template.
   *
   * @param boardState  the current state of the board
   */
  public BoardView(Board boardState) {
    for (int i = 0; i < Board.Y_BOARD_SIZE; i++) {
      SPACE_TYPE[] rowSpaces = new SPACE_TYPE[Board.X_BOARD_SIZE];
      for(int j = 0; j < rowSpaces.length; j++){
        rowSpaces[j] = boardState.getPieceAtLocation(j,i);
      }
      Row eachRow = new Row(i, rowSpaces);
      rows.add(eachRow);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator iterator() {
    return rows.iterator();
  }
}
