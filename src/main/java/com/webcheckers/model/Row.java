package com.webcheckers.model;

import java.util.Iterator;

public class Row {
  private int index = -1;

  public int getIndex(){
    return this.index;
  }

  public Iterator<Space> iterator(){
    Iterator<Space> it = new Iterator<Space>() {
      @Override
      public boolean hasNext() {
        return false;
      }

      @Override
      public Space next() {
        return null;
      }
    }
  }
}
