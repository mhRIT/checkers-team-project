import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Application-tier")
public class GameCenterTest {
  @Test
  public void test_createGame(){
    final GameCenter CuT = new GameCenter();
    final Game game = CuT.createGame();

    // Check that the game is real
    assertNotNull(game);
  }
}
