package com.webcheckers.model.GameState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webcheckers.model.Board.Board;
import com.webcheckers.model.Board.Move;
import com.webcheckers.model.Player.Player;
import com.webcheckers.model.Board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the {@link GameContext} component.
 *
 * @author <a href='mailto:sjk7867@rit.edu'>Simon Kirkwood</a>
 * @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
@Tag("Model-tier")
public class GameContextTest {

  //
  // Constants
  //
  private static final String PLAYER1_NAME = "player1";
  private static final String PLAYER2_NAME = "player2";
  int RED_PLAYER_NONCE = 0;
  int WHITE_PLAYER_NONCE = 1;
  int INVALID_PLAYER_NONCE = 2;

  //
  // Attributes
  //
  private Player player1;
  private Player player2;
  int gameNonce = 0;

  //
  // Components under test
  //
  private GameContext cut;

  /**
   * TODO
   */
  @BeforeEach
  public void setup(){
    player1 = new Player(PLAYER1_NAME, RED_PLAYER_NONCE);
    player2 = new Player(PLAYER2_NAME, WHITE_PLAYER_NONCE);
    cut = new GameContext(player1, player2, gameNonce++);
  }

  @Test
  public void testProceed(){
    Move illegalMove = new Move(new Position(0,1), new Position(0,2));
    player1.putNextMove(cut, illegalMove);
    GameState startState = cut.getState();
    boolean success = cut.proceed();
    GameState nextState = cut.getState();

    assertNotNull(startState);
    assertNotNull(nextState);
    assertEquals(startState.getClass(), nextState.getClass());
    assertFalse(success);
  }

  @Test
  public void testGetBoard(){
    Board currentBoard = cut.getCurrentBoard();
    cut.revert();
    Board oldBoard = cut.getCurrentBoard();

    assertNotNull(currentBoard);
    assertNull(oldBoard);
  }

  @Test
  public void testGetPlayer(){
    Player rPlayer = cut.getRedPlayer();
    Player wPlayer = cut.getWhitePlayer();

    assertEquals(player1, rPlayer);
    assertEquals(player2, wPlayer);
  }

  @Test
  public void testSwitchTurn(){
    Player player = cut.getActivePlayer();
    cut.setState(new EndTurnState());
    cut.switchTurn();
    Player switchedPlayer = cut.getActivePlayer();
    assertNotEquals(player, switchedPlayer);

    cut.setState(new EndTurnState());
    cut.switchTurn();
    switchedPlayer = cut.getActivePlayer();
    assertEquals(player, switchedPlayer);

    cut.switchTurn();
    switchedPlayer = cut.getActivePlayer();
    assertNotEquals(player, switchedPlayer);
  }

  @Test
  public void testIsOver(){
    assertFalse(cut.isGameOver());
    cut.setState(new GameOverState());
    assertTrue(cut.isGameOver());
  }

  @Test
  public void testEndMessage(){
    String message = cut.endMessage();
    assertEquals("Game is not over.", message);

    cut.setState(new GameOverState());
    message = cut.endMessage();
    assertNotEquals("Game is not over.", message);
  }

  @Test
  public void testRevert(){
    boolean success = cut.revert();
    GameState state = cut.getState();
    assertTrue(success);
    assertEquals(WaitTurnState.class, state.getClass());

    success = cut.revert();
    assertFalse(success);
  }

  @Test
  public void testAddBoard(){
    Board currentBoard = cut.getCurrentBoard();
    Board board = new Board();
    cut.addNextBoard(board);
    Board newBoard = cut.getCurrentBoard();

    assertNotEquals(currentBoard, newBoard);
    assertEquals(board, newBoard);
  }

  @Test
  public void testResignRedSuccess(){
    boolean success = cut.resignPlayer(player1);
    GameState state = cut.getState();

    assertEquals(GameOverState.class, state.getClass());
    assertTrue(success);
  }

  @Test
  public void testResignWhiteSuccess(){
    boolean success = cut.resignPlayer(player2);
    GameState state = cut.getState();

    assertEquals(GameOverState.class, state.getClass());
    assertTrue(success);
  }

  @Test
  public void testResignFail(){
    Player notPlayer = new Player("notInGame", INVALID_PLAYER_NONCE);
    boolean success = cut.resignPlayer(notPlayer);
    assertFalse(success);
  }

  @Test
  void testToString(){
    assertNotNull(cut);
    assertEquals(String.format("%d | Red player: %s | White player: %s",
                  cut.getId(),
                  PLAYER1_NAME,
                  PLAYER2_NAME),
        cut.toString());
  }

  @Test
  void testEquals(){
    GameContext game2 = new GameContext(player1, player2, 0);
    boolean posEqual = cut.equals(game2);
    assertTrue(posEqual);

    posEqual = cut.equals(new Object());
    assertFalse(posEqual);

    posEqual = cut.equals(cut);
    assertTrue(posEqual);
  }

  @Test
  void testHash(){
    GameContext game2 = new GameContext(player1, player2, gameNonce);
    GameContext game3 = new GameContext(player1, new Player("p3", INVALID_PLAYER_NONCE), gameNonce);

    int hash0 = cut.hashCode();
    int hash2 = game2.hashCode();
    int hash3 = game3.hashCode();
    assertNotEquals(hash0, hash2);
    assertNotEquals(hash0, hash3);
  }
}
