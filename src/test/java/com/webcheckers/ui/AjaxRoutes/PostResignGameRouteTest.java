package com.webcheckers.ui.AjaxRoutes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.webcheckers.model.GameState.GameContext;
import com.webcheckers.model.Player.Player;
import com.webcheckers.ui.HtmlRoutes.HtmlRouteTest;
import com.webcheckers.ui.TemplateEngineTester;
import com.webcheckers.ui.boardView.Message;
import com.webcheckers.ui.boardView.Message.MESSAGE_TYPE;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.ModelAndView;

/**
 * The unit test suite for the {@link PostResignGameRoute} component.
 *
 * @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 */
@Tag("UI-tier")
class PostResignGameRouteTest extends HtmlRouteTest {

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        super.setUp();
        // create a unique CuT for each test
        CuT = new PostResignGameRoute(gameCenter, playerLobby, gson);
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

        GameContext game = gameCenter.createGame(testPlayer, testOpponent, initConfig);

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
