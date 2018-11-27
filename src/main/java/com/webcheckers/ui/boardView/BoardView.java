package com.webcheckers.ui.boardView;

import static com.webcheckers.model.Board.Board.BOARD_SIZE;

import com.webcheckers.model.Board.Board;
import com.webcheckers.model.Board.Board.SPACE_TYPE;
import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Player.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *  {@code BoardView}
 *  Represents the view of the board, as expected by the game.ftl
 *  Freemarker template.
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
   * @param game    the game to be viewed
   * @param player  the player who is viewing the board
   */
  public BoardView(GameContext game, Player player) {
    this.rows = new ArrayList<>();
    Board boardState = game.getCurrentBoard();
    if(game.getRedPlayer().equals(player)){
      for (int i = BOARD_SIZE - 1; i >= 0; i--) {
        Row eachRow = new Row(i, boardState.getRow(i));
        rows.add(eachRow);
      }
    } else {
      for (int i = 0; i < BOARD_SIZE; i++) {
        List<SPACE_TYPE> spacesRev = boardState.getRow(i);
        Collections.reverse(spacesRev);
        Row eachRow = new Row(BOARD_SIZE - (i+1), spacesRev);
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
