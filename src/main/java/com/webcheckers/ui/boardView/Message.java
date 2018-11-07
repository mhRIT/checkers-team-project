package com.webcheckers.ui.boardView;

/**
 *  {@code Message}
 *  <p>
 *  Represents a message passed to the view to determine flow of execution
 *  of the game.
 *  </p>
 *
 *  @author <a href='mailto:mlh1964@rit.edu'>Meaghan Hoitt</a>
 *  @author <a href='mailto:sjk7867@rit.edu'>Simon Kirwkwood</a>
 *  @author <a href='mailto:mvm7902@rit.edu'>Matthew Milone</a>
 *  @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
public class Message {
  //
  // Enums
  //

  public enum MESSAGE_TYPE {info, error}

  //
  // Attributes
  //

  private final String text;
  private final MESSAGE_TYPE type;

  /**
   * Constructs a message of the specified type using the specified text.
   *
   * @param text  the text of the message
   * @param type  the type of the message
   */
  public Message(String text, MESSAGE_TYPE type) {
    this.text = text;
    this.type = type;
  }

  /**
   * Retrieves the text stored in the message.
   *
   * @return the text of the message
   */
  public String getText() {
    return text;
  }

  /**
   * Retrieves the type of the message.
   *
   * @return the type of the message
   */
  public MESSAGE_TYPE getType() {
    return type;
  }

  public boolean equals(Message msg){
    return this.toString().equals(msg.toString());
  }

  public String toString(){
    return "" + text + ", " + type.toString();
  }
}
