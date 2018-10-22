package com.webcheckers.ui.boardView;

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
   * @param spaceMat  the current state of the board
   */
  public BoardView(SPACE_TYPE[][] spaceMat) {
    for (int i = 0; i < spaceMat.length; i++) {
      Row eachRow = new Row(i, spaceMat[i]);
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
