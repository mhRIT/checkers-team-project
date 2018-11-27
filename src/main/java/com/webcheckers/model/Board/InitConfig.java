package com.webcheckers.model.Board;

import java.util.ArrayList;
import java.util.List;

public class InitConfig {

  public enum START_TYPE {
    NORMAL,
    RANDOM,
    PRE_SET,
    CUSTOM
  }

  public enum PRE_SET_BOARDS{
    START,
    MIDDLE,
    END,
    NONE
  }

  private String opponent;

  private int numRedPieces = 0;
  private int numWhitePieces = 0;

  private int boardIdx = 0;

  private List<Position> custom = new ArrayList<>();

  public InitConfig(String opponentName, int redPieces, int whitePieces){
    this.opponent = opponentName;
    this.numRedPieces = redPieces;
    this.numWhitePieces = whitePieces;
  }

  public InitConfig(String opponentName, int boardIdx){
    this.opponent = opponentName;
    this.boardIdx = boardIdx;
  }

  public InitConfig(String opponentName, List<Position> positions){
    this.opponent = opponentName;
    custom.addAll(positions);
  }

  public InitConfig(String opponentName){
    this.opponent = opponentName;
  }

  public START_TYPE getStartType(){
    if(numRedPieces != 0 && numWhitePieces != 0){
      return START_TYPE.RANDOM;
    } else if(boardIdx != 0){
      return START_TYPE.PRE_SET;
    } else if(custom != null && custom.size() != 0){
      return START_TYPE.CUSTOM;
    }
    return START_TYPE.NORMAL;
  }

  public String getOpponent(){
    return opponent;
  }

  public int getNumRedPieces(){
    return numRedPieces;
  }

  public int getNumWhitePieces(){
    return numWhitePieces;
  }

  public PRE_SET_BOARDS getPreSetBoard(){
    switch (boardIdx){
      case 1:
        return PRE_SET_BOARDS.START;
      case 2:
        return PRE_SET_BOARDS.MIDDLE;
      case 3:
        return PRE_SET_BOARDS.END;
      default:
        return PRE_SET_BOARDS.NONE;
    }
  }

  public List<Position> getCustom(){
    return custom;
  }

  /**
   * Builds a human-readable string representation of this configuration.
   *
   * @return  human-readable representation of this configuration
   */
  @Override
  public String toString() {
    return String.format("%s | Red pieces: %d, white pieces: %d, pre-set: %d",
        opponent,
        numRedPieces,
        numWhitePieces,
        boardIdx);
  }

  /**
   * Compares if other object is an InitConfig
   * and has the same settings as this InitConfig.
   *
   * @return true if the object is a InitConfig with the same settings
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (! (obj instanceof InitConfig)) return false;
    final InitConfig that = (InitConfig) obj;
    return this.toString().equals(that.toString());
  }

  /**
   * Generates a hashCode for the InitConfig, based on the toString.
   *
   * @return  the hashCode
   */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
