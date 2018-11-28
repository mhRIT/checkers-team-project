package com.webcheckers.model.Board;

import java.util.ArrayList;
import java.util.List;

public class InitConfig {

  public enum START_TYPE {
    NORMAL,
    RANDOM,
    PRESET,
    CUSTOM
  }

  public enum PRE_SET_BOARD {
    START,
    MIDDLE,
    END,
    NONE
  }

  private String type;
  private String opponent;

  private int numRedPieces = 0;
  private int numWhitePieces = 0;

  private String preset;

  private List<Position> custom = new ArrayList<>();

  public InitConfig(String opponentName, int redPieces, int whitePieces){
    this.opponent = opponentName;
    this.numRedPieces = redPieces;
    this.numWhitePieces = whitePieces;
  }

  public InitConfig(String opponentName, String presetSelected){
    this.opponent = opponentName;
    this.preset = presetSelected;
  }

  public InitConfig(String opponentName, List<Position> positions){
    this.opponent = opponentName;
    custom.addAll(positions);
  }

  public InitConfig(String opponentName){
    this.opponent = opponentName;
  }

  public InitConfig(){
    type = "normal";
  }

  public START_TYPE getStartType(){
    START_TYPE toReturn;
    try{
      toReturn = START_TYPE.valueOf(type);
    } catch (IllegalArgumentException iae){
      try{
        toReturn = START_TYPE.valueOf(type.toUpperCase());
      } catch (IllegalArgumentException iae_nested){
        toReturn = START_TYPE.NORMAL;
      }
    }
    return toReturn;
  }

  public String getType(){
    return type;
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

  public PRE_SET_BOARD getPreSetBoard(){
    PRE_SET_BOARD toReturn;
    try{
      toReturn = PRE_SET_BOARD.valueOf(preset);
    } catch (IllegalArgumentException iae){
      try{
        toReturn = PRE_SET_BOARD.valueOf(preset.toUpperCase());
      } catch (IllegalArgumentException iae_nested){
        toReturn = PRE_SET_BOARD.START;
      }
    }
    return toReturn;
  }

  public String getPreset(){
    return preset;
  }

  public void setPreset(String setVal){
    preset = setVal;
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
    return String.format("%s | Red pieces: %d, white pieces: %d, pre-set: %s",
        opponent,
        numRedPieces,
        numWhitePieces,
        preset);
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
