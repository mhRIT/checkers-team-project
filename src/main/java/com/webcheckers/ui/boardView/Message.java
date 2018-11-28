package com.webcheckers.ui.boardView;

/**
 *  {@code Message}
 *  Represents a message passed to the view to determine flow of execution
 *  of the game.
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

  /**
   * Builds a user-friendly string representation
   * for this Message.
   *
   * @return  the string representation for this Message
   */
  @Override
  public String toString(){
    return String.format("%s, %s", text, getType().toString());
  }

  /**
   * Compares if other object is a message
   * and has the same string representation as this message.
   *
   * @return true if the object is a message with the same
   *              string representation
   */
  @Override
  public boolean equals(Object obj){
    if (obj == this) return true;
    if (! (obj instanceof Message)) return false;
    final Message that = (Message) obj;
    return this.toString().equals(that.toString());
  }

  /**
   * Generates a hashCode for the message, based on the string
   * representation of this message.
   *
   * @return  an int value representing this message
   */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
