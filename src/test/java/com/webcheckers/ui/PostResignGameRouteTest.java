package com.webcheckers.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.ui.boardView.Message;
import com.webcheckers.ui.boardView.Message.MESSAGE_TYPE;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

/**
 * The unit test suite for the {@link PostResignGameRoute} component.
 *
 * @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 */
@Tag("UI-tier")
class PostResignGameRouteTest extends RouteTest {

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        super.setUp();
        // create a unique CuT for each test
        CuT = new PostResignGameRoute(gameCenter, engine);
    }

    /**
     * Test the resignation of a player
     */
    @Test
    public void testResignation() throws Exception{
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        Player testPlayer = playerLobby.signin(TEST_PLAYER_NAME);
        when(session.attribute("player")).thenReturn(testPlayer);

        Player testOpponent = playerLobby.signin(TEST_OPP_NAME);

        Game game = gameCenter.createGame(testPlayer, testOpponent);

        Message message = (Message) CuT.handle(request,response);

        assertEquals(MESSAGE_TYPE.info, message.getType());
    }

    /**
     * Test the failed resignation of a player
     */
    @Test
    public void testNoResignation() throws Exception{
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        Player testPlayer = playerLobby.signin(TEST_PLAYER_NAME);
        when(session.attribute("player")).thenReturn(testPlayer);

        Message message = (Message) CuT.handle(request,response);

        assertEquals(MESSAGE_TYPE.error, message.getType());
    }

}
