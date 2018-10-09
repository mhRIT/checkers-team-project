package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

public class Board {

  private ArrayList<Piece> redPieces = new ArrayList<Piece>();
  private ArrayList<Piece> whitePieces = new ArrayList<Piece>();
  private ArrayList<Row> rows = new ArrayList<Row>();

  public Board(){
    for(int i=0; i<8; i++){
      rows.add(new Row(i));
    }
  }

  public void initialize() {

  }

  public Boolean checkEnd() {
    return null;
  }

  public ArrayList<Row> getState() {
    return rows;
  }
}
