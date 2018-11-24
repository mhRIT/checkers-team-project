package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the {@link Player} component.
 *
 * @author <a href='mailto:axf5592@rit.edu'>Andrew Festa</a>
 */
@Tag("Model-tier")
class PositionTest {

  private Position cut;

  @Test
  void testRow(){
    cut = new Position(0,1);
    assertNotNull(cut);
    assertEquals(1, cut.getRow());
  }

  @Test
  void testCol(){
    cut = new Position(0,1);
    assertNotNull(cut);
    assertEquals(0, cut.getCell());
  }

  @Test
  void testToString(){
    cut = new Position(0,1);
    assertNotNull(cut);
    assertEquals("(0, 1)", cut.toString());
  }

  @Test
  void testEquals(){
    cut = new Position(0,1);
    Position pos2 = new Position(1,0);
    boolean posEqual = cut.equals(pos2);
    assertFalse(posEqual);

    posEqual = cut.equals(new Object());
    assertFalse(posEqual);

    posEqual = cut.equals(cut);
    assertTrue(posEqual);
  }

  @Test
  void testHash(){
    cut = new Position(0,1);
    Position pos2 = new Position(0, 1);
    Position pos3 = new Position(0, 2);

    int hash0 = cut.hashCode();
    int hash2 = pos2.hashCode();
    int hash3 = pos3.hashCode();
    assertEquals(hash0, hash2);
    assertNotEquals(hash0, hash3);
  }
}