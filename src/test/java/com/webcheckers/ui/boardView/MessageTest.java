package com.webcheckers.ui.boardView;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.ui.boardView.Message.MESSAGE_TYPE;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MessageTest {

  //
  // Constants
  //
  private static final String TEST_INFO_MESSAGE = "infoText";
  private static final String TEST_ERROR_MESSAGE = "errorText";

  //
  // Components under test
  //
  private Message infoCut;
  private Message errorCut;

  @BeforeEach
  void setUp() {
    infoCut = new Message(TEST_INFO_MESSAGE, MESSAGE_TYPE.info);
    errorCut = new Message(TEST_ERROR_MESSAGE, MESSAGE_TYPE.error);
  }

  @Test
  void testCreation(){
    assertNotNull(infoCut);
    assertNotNull(errorCut);
  }

  @Test
  void testGetText() {
    assertEquals(TEST_INFO_MESSAGE, infoCut.getText());
    assertEquals(TEST_ERROR_MESSAGE, errorCut.getText());
  }

  @Test
  void getType() {
    assertEquals(MESSAGE_TYPE.info, infoCut.getType());
    assertEquals(MESSAGE_TYPE.error, errorCut.getType());
  }
}