package com.webcheckers.model.Board;

public class InitConfig {

  private String opponent;

  private int numRedPieces;
  private int numWhitePieces;

  public InitConfig(String opponentName, int redPieces, int whitePieces){
    this.opponent = opponentName;
    this.numRedPieces = redPieces;
    this.numWhitePieces = whitePieces;
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

  /**
   * Builds a human-readable string representation of this configuration.
   *
   * @return  human-readable representation of this configuration
   */
  @Override
  public String toString() {
    return String.format("%s | Red pieces: %d, white pieces: %d", opponent, numRedPieces, numWhitePieces);
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
