package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.junit.Test;

/**
 * This class contains JUnit test cases for the {@link Mansion} class.
 * It covers various scenarios to test the behavior of the Mansion class methods.
 */
public class MansionTest {
  /**
   * Test the {@link Mansion#getRoomList()} method to ensure it returns the correct list of rooms.
   */
  @Test
  public void getRoomList() {
    Random random = new Random();
    List<Room> roomList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      int a = random.nextInt(10);
      int b = a + random.nextInt(10);
      int c = b + random.nextInt(10);
      int d = c + random.nextInt(10);
      Room room = new Room("Room " + String.valueOf(i), new int[] {a, b, c, d}, i);
      roomList.add(room);
    }
    Mansion mansion = new Mansion(30, 40, "My Mansion", roomList);
    List<Room> tmpRoomList = mansion.getRoomList();
    assertTrue(tmpRoomList.equals(roomList));
  }

  /**
   * Test the {@link Mansion#getRow()} method to ensure it returns the correct number of rows.
   */
  @Test
  public void getRow() {
    Random random = new Random();
    List<Room> roomList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      int a = random.nextInt(10);
      int b = a + random.nextInt(10);
      int c = b + random.nextInt(10);
      int d = c + random.nextInt(10);
      Room room = new Room("Room " + String.valueOf(i), new int[] {a, b, c, d}, i);
      roomList.add(room);
    }
    Mansion mansion = new Mansion(30, 40, "My Mansion", roomList);
    assertEquals(30, mansion.getRow());
  }

  /**
   * Test the {@link Mansion#getColumn()} method to ensure it returns the correct number of columns.
   */
  @Test
  public void getColumn() {
    Random random = new Random();
    List<Room> roomList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      int a = random.nextInt(10);
      int b = a + random.nextInt(10);
      int c = b + random.nextInt(10);
      int d = c + random.nextInt(10);
      Room room = new Room("Room " + String.valueOf(i), new int[] {a, b, c, d}, i);
      roomList.add(room);
    }
    Mansion mansion = new Mansion(30, 40, "My Mansion", roomList);
    assertEquals(40, mansion.getColumn());
  }

  /**
   * Test the {@link Mansion#getName()} method to ensure it returns the correct name.
   */
  @Test
  public void getName() {
    Random random = new Random();
    List<Room> roomList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      int a = random.nextInt(10);
      int b = a + random.nextInt(10);
      int c = b + random.nextInt(10);
      int d = c + random.nextInt(10);
      Room room = new Room("Room " + String.valueOf(i), new int[] {a, b, c, d}, i);
      roomList.add(room);
    }
    Mansion mansion = new Mansion(30, 40, "My Mansion", roomList);
    assertEquals("My Mansion", mansion.getName());
  }

  /**
   * Test the {@link Mansion#toString()} method to ensure it generates the
   * expected string representation.
   */
  @Test
  public void testToString() {
    Random random = new Random();
    List<Room> roomList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      int a = random.nextInt(10);
      int b = a + random.nextInt(10);
      int c = b + random.nextInt(10);
      int d = c + random.nextInt(10);
      Room room = new Room("Room " + String.valueOf(i), new int[] {a, b, c, d}, i);
      roomList.add(room);
    }
    Mansion mansion = new Mansion(30, 40, "My Mansion", roomList);
    StringBuilder expectedString = new StringBuilder()
        .append(new String("My Mansion"))
        .append("\nRow: ")
        .append(30)
        .append(" Column: ")
        .append(40)
        .append("\n")
        .append("Room List:\n");

    for (int i = 0; i < roomList.size(); i++) {
      expectedString.append("\t" + i + " ");
      expectedString.append(roomList.get(i).toString());
    }
    assertEquals(new String(expectedString), mansion.toString());
  }

  /**
   * Test the {@link Mansion#equals(Object)} method to ensure it correctly
   * compares Mansion objects for equality.
   */
  @Test
  public void testEquals() {
    Random random = new Random();
    List<Room> roomList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      int a = random.nextInt(10);
      int b = a + random.nextInt(10);
      int c = b + random.nextInt(10);
      int d = c + random.nextInt(10);
      Room room = new Room("Room " + String.valueOf(i), new int[] {a, b, c, d}, i);
      roomList.add(room);
    }
    Mansion a = new Mansion(30, 40, "My Mansion", roomList);
    assertTrue(a.equals(a));

    Mansion b = new Mansion(20, 40, "My Mansion", roomList);
    assertFalse(a.equals(b));

    Mansion c = new Mansion(30, 30, "My Mansion", roomList);
    assertFalse(a.equals(c));

    Mansion d = new Mansion(30, 40, "Mansion", roomList);
    assertFalse(a.equals(d));

    Mansion e = new Mansion(30, 40, "My Mansion", new ArrayList<>());
    assertFalse(a.equals(e));

    Mansion f = new Mansion(30, 40, "My Mansion", roomList);
    assertTrue(a.equals(f));
  }

  /**
   * Test the {@link Mansion#hashCode()} method to ensure it generates
   * the correct hash code for Mansion objects.
   */
  @Test
  public void testHashCode() {
    Random random = new Random();
    List<Room> roomList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      int a = random.nextInt(10);
      int b = a + random.nextInt(10);
      int c = b + random.nextInt(10);
      int d = c + random.nextInt(10);
      Room room = new Room("Room " + String.valueOf(i), new int[] {a, b, c, d}, i);
      roomList.add(room);
    }
    Mansion mansion = new Mansion(30, 40, "My Mansion", roomList);

    assertEquals(Objects.hash("My Mansion", roomList, 30, 40), mansion.hashCode());
  }
}