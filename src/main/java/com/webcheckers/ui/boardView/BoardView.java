package com.webcheckers.ui.boardView;

import static com.webcheckers.model.Board.Y_BOARD_SIZE;

import com.webcheckers.model.Board;
import com.webcheckers.model.Board.SPACE_TYPE;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

  private List<Row> rows;

  /**
   * Constructs the view of the board, as needed by the game.ftl
   * Freemarker template.
   *
   * @param  boardState  the current state of the board
   */
  public BoardView(Game game, Player player) {
    this.rows = new ArrayList<>();
    Board boardState = game.getBoardState();
    if(game.getRedPlayer().equals(player)){
      for (int i = Y_BOARD_SIZE-1; i >= 0; i--) {
        Row eachRow = new Row(i, boardState.getRow(i));
        rows.add(eachRow);
      }
    } else {
      for (int i = 0; i < Y_BOARD_SIZE; i++) {
        SPACE_TYPE[] spacesRev = boardState.getRowReverse(i);
        Row eachRow = new Row(i, spacesRev);
        rows.add(eachRow);
      }
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
