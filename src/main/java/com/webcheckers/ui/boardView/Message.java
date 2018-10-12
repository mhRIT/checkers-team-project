package com.webcheckers.ui.boardView;

public class Message {

  public enum MESSAGE_TYPE {info, error}

  private final String text;
  private final MESSAGE_TYPE type;

  public Message(String text, MESSAGE_TYPE type) {
    this.text = text;
    this.type = type;
  }

  public String getText() {
    return text;
  }

  public MESSAGE_TYPE getType() {
    return type;
  }
}
