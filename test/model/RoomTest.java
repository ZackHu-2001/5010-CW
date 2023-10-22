package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Objects;
import java.util.Random;
import org.junit.Test;

/**
 * This class contains JUnit test cases for the {@link Room} class.
 * It covers various scenarios to test the behavior of the Room class methods.
 */
public class RoomTest {

  @Test
  public void testInvalidInputForConstructor() {
    try {
      new Room("FOO", new int[] {1, 2, 3}, 1);
    } catch (IllegalArgumentException e) {
      System.out.println("Successfully caught exception.");
    }

    try {
      new Room("FOO", new int[] {4, 2, 3, 3}, 1);
    } catch (IllegalArgumentException e) {
      System.out.println("Successfully caught exception.");
    }

    try {
      new Room("FOO", new int[] {4, 4, 5, 3}, 1);
    } catch (IllegalArgumentException e) {
      System.out.println("Successfully caught exception.");
    }

    try {
      new Room("FOO", new int[] {-1, 2, 3, 3}, 1);
    } catch (IllegalArgumentException e) {
      System.out.println("Successfully caught exception.");
    }

    try {
      new Room("FOO", new int[] {1, -2, 3, 3}, 1);
    } catch (IllegalArgumentException e) {
      System.out.println("Successfully caught exception.");
    }

    try {
      new Room("FOO", new int[] {1, 2, -3, 3}, 1);
    } catch (IllegalArgumentException e) {
      System.out.println("Successfully caught exception.");
    }

    try {
      new Room("FOO", new int[] {1, 2, 3, -3}, 1);
    } catch (IllegalArgumentException e) {
      System.out.println("Successfully caught exception.");
    }
  }

  @Test
  public void testValidInputForConstructor() {
    Random random = new Random();

    for (int i = 0; i < 20; i++) {
      int a = random.nextInt(10);
      int b = a + random.nextInt(10);
      int c = b + random.nextInt(10);
      int d = c + random.nextInt(10);
      try {
        new Room("FOO" + String.valueOf(random.nextInt()), new int[]{ a, b, c, d}, i);
      } catch (IllegalArgumentException e) {
        fail("No exception should be caught for legal input.");
      }
    }
  }

  @Test
  public void addItem() {
    Random random = new Random();

    Room room = new Room("Foo", new int[] { 1, 2, 3, 4}, 1);
    for (int i = 0; i < 20; i++) {
      room.addItem(new Item("Pen", random.nextInt(10) + 1,
          31));
      assertEquals(i + 1, room.getItemList().size());
    }
  }

  @Test
  public void addNeighbor() {
    Random random = new Random();

    Room room = new Room("Foo", new int[] {1, 2, 3, 4}, 1);
    for (int i = 0; i < 4; i++) {
      int a = random.nextInt(10);
      int b = a + random.nextInt(10);
      int c = b + random.nextInt(10);
      int d = c + random.nextInt(10);
      room.addNeighbor(new Room("Pen", new int[] {a, b, c, d}, i));
      assertEquals(i + 1, room.getNeightborList().size());
    }
  }

  @Test
  public void getName() {
    Random random = new Random();

    for (int i = 0; i < 20; i++) {
      int randomInt = random.nextInt();
      Room room = new Room("FOO" + String.valueOf(randomInt), new int[]{1, 2, 3, 4}, i);
      assertEquals(randomInt, room.getName());
    }
  }

  @Test
  public void getLocation() {
    Random random = new Random();

    for (int i = 0; i < 20; i++) {
      int a = random.nextInt(10);
      int b = a + random.nextInt(10);
      int c = b + random.nextInt(10);
      int d = c + random.nextInt(10);
      Room room = new Room("FOO" + String.valueOf(random.nextInt()), new int[]{a, b, c, d}, i);

      assertEquals(a, room.getLocation()[0]);
      assertEquals(b, room.getLocation()[1]);
      assertEquals(c, room.getLocation()[2]);
      assertEquals(d, room.getLocation()[3]);
    }
  }

  @Test
  public void getItemList() {
    Random random = new Random();

    Room room = new Room("Foo", new int[] {1, 2, 3, 4}, 1);
    for (int i = 0; i < 20; i++) {
      Item item = new Item("Pen", random.nextInt(10) + 1,
          31);
      room.addItem(item);
      assertEquals(i + 1, room.getItemList().size());
    }
  }

  @Test
  public void testToString() {
    Random random = new Random();
    String name = "Foo";
    int roomId = 1;
    int[] location = new int[] {1, 2, 3, 4};
    
    Room room = new Room(name, location, roomId);
    for (int i = 0; i < 20; i++) {
      Item item = new Item("Pen", random.nextInt(10) + 1,
          31);
      room.addItem(item);
      assertEquals(i + 1, room.getItemList().size());
    }
    
    Room neighbor = new Room("bar", new int[] {2, 3, 4, 5}, 1);
    room.addNeighbor(neighbor);
    
    StringBuilder expectedString = new StringBuilder()
        .append("[")
        .append(name)
        .append("]")
        .append(": ")
        .append(location[0])
        .append(" ")
        .append(location[1])
        .append(", ")
        .append(location[2])
        .append(" ")
        .append(location[3])
        .append("\n\t\tItems within: ");
    for (Item item : room.getItemList()) {
      expectedString.append(item.toString());
    }
    expectedString.append("\n\t\tNeighbors: ");
    for (Room neighborRoom : room.getNeightborList()) {
      expectedString.append(neighborRoom.getName());
      expectedString.append(", ");
    }
    expectedString.append("\n\n");
    assertEquals(expectedString, room.toString());
  }

  @Test
  public void testEquals() {
    Random random = new Random();

    Room a = new Room("Kitchen", new int[] {1, 2, 3, 4}, 1);
    Room b = new Room("Washroom", new int[] {2, 3, 4, 5}, 2);
    Room c = new Room("Living Room", new int[] {2, 3, 4, 5}, 3);
    Room d = new Room("Kitchen", new int[] {1, 2, 3, 4}, 4);

    for (int i = 0; i < 3; i++) {
      Item item = new Item("Pen", random.nextInt(10) + 1,
          31);
      a.addItem(item);
      b.addItem(item);
      c.addItem(item);
      d.addItem(item);
    }

    assertTrue(a.equals(a));
    assertFalse(a.equals(b));
    assertFalse(a.equals(c));
    assertTrue(a.equals(d));
  }

  @Test
  public void testHashCode() {
    Random random = new Random();
    String name = "Foo";
    int[] location = new int[] {1, 2, 3, 4};

    Room room = new Room(name, location, 1);
    for (int i = 0; i < 20; i++) {
      Item item = new Item("Pen", random.nextInt(10) + 1,
          31);
      room.addItem(item);
      assertEquals(i + 1, room.getItemList().size());
    }

    Room neighbor = new Room("bar", new int[] {2, 3, 4, 5}, 2);
    room.addNeighbor(neighbor);
    assertEquals(Objects.hash(name, location, room.getItemList(),
        room.getNeightborList()), room.hashCode());
  }
}