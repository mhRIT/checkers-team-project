package com.webcheckers.ui.boardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public class Row implements Iterable {

  private final int index;
  private ArrayList<Space> spaces;

  public Row(int idx, Space[] spaceList) {
    this.spaces = new ArrayList<>(Arrays.asList(spaceList));
    this.index = idx;
  }

  public int getIndex() {

    return index;
  }

  public void reverse(){
    Collections.reverse(spaces);
  }

  @Override
  public Iterator iterator() {
    return spaces.iterator();
  }
}
