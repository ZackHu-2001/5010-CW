package world;

import java.util.Random;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTest {
  private Item item;
  @Test
  public void testInvalidValueForConstructor() {
    try {
      new Item("Foo", 3, -1);
    } catch (IllegalArgumentException e) {
      System.out.println("Successfully caught the exception.");
    }

    try {
      new Item("Foo", -3, 10);
    } catch (IllegalArgumentException e) {
      System.out.println("Successfully caught the exception.");
    }

    try {
      new Item("Foo", 0, 10);
    } catch (IllegalArgumentException e) {
      System.out.println("Successfully caught the exception.");
    }
  }

  @Test
  public void testValidValueForConstructor() {
    try {
      new Item("Foo", 1, 1);
      new Item("Foo", 3, 10);
      new Item("Foo", 4, 5);
      new Item("Foo", 2, 6);
      new Item("Foo", 6, 12);
    } catch (IllegalArgumentException e) {
      fail("No exception should be thrown.");
    }
  }

  @Test
  public void testGetValue() {
    // Fuzz test
    Random random = new Random();
    for (int i=0; i<20; i++) {
      int value = random.nextInt(100)+1;
      int roomWithin = random.nextInt(100);
      Item item = new Item("Foo" + String.valueOf(value), value, roomWithin);
      assertEquals(value, item.getValue());
    }
  }

  @Test
  public void testGetName() {
    // Fuzz test
    Random random = new Random();
    for (int i=0; i<20; i++) {
      int value = random.nextInt(100)+1;
      int roomWithin = random.nextInt(100);
      Item item = new Item("Foo" + String.valueOf(value), value, roomWithin);
      assertEquals("Foo" + String.valueOf(value), item.getName());
    }
  }

  @Test
  public void testGetRoomWithin() {
    // Fuzz test
    Random random = new Random();
    for (int i=0; i<20; i++) {
      int value = random.nextInt(100)+1;
      int roomWithin = random.nextInt(100);
      Item item = new Item("Foo" + String.valueOf(value), value, roomWithin);
      assertEquals(roomWithin, item.getRoomWithin());
    }
  }

  @Test
  public void testToString() {
    // Fuzz test
    Random random = new Random();
    for (int i=0; i<20; i++) {
      int value = random.nextInt(100)+1;
      int roomWithin = random.nextInt(100);
      Item item = new Item("Foo" + String.valueOf(value), value, roomWithin);
      String expected = String.format("[%s]: %d, in %d# room; ",
          "Foo" + String.valueOf(value), value, roomWithin);
      assertEquals(expected, item.toString());
    }
  }
}