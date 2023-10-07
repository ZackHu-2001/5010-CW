package world;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.junit.Test;

import static org.junit.Assert.*;

public class MansionTest {

  @Test
  public void getRoomList() {
    Random random = new Random();
    List<Room> roomList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      int a = random.nextInt(10);
      int b = a + random.nextInt(10);
      int c = b + random.nextInt(10);
      int d = c + random.nextInt(10);
      Room room = new Room("Room " + String.valueOf(i), new int[] {a, b, c, d});
      roomList.add(room);
    }
    Mansion mansion = new Mansion(30, 40, "My Mansion", roomList);
    List<Room> tmpRoomList = mansion.getRoomList();
    assertTrue(tmpRoomList.equals(roomList));
  }

  @Test
  public void getRow() {
    Random random = new Random();
    List<Room> roomList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      int a = random.nextInt(10);
      int b = a + random.nextInt(10);
      int c = b + random.nextInt(10);
      int d = c + random.nextInt(10);
      Room room = new Room("Room " + String.valueOf(i), new int[] {a, b, c, d});
      roomList.add(room);
    }
    Mansion mansion = new Mansion(30, 40, "My Mansion", roomList);
    assertEquals(30, mansion.getRow());
  }

  @Test
  public void getColumn() {
    Random random = new Random();
    List<Room> roomList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      int a = random.nextInt(10);
      int b = a + random.nextInt(10);
      int c = b + random.nextInt(10);
      int d = c + random.nextInt(10);
      Room room = new Room("Room " + String.valueOf(i), new int[] {a, b, c, d});
      roomList.add(room);
    }
    Mansion mansion = new Mansion(30, 40, "My Mansion", roomList);
    assertEquals(40, mansion.getColumn());
  }

  @Test
  public void getName() {
    Random random = new Random();
    List<Room> roomList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      int a = random.nextInt(10);
      int b = a + random.nextInt(10);
      int c = b + random.nextInt(10);
      int d = c + random.nextInt(10);
      Room room = new Room("Room " + String.valueOf(i), new int[] {a, b, c, d});
      roomList.add(room);
    }
    Mansion mansion = new Mansion(30, 40, "My Mansion", roomList);
    assertEquals("My Mansion", mansion.getName());
  }

  @Test
  public void testToString() {
    Random random = new Random();
    List<Room> roomList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      int a = random.nextInt(10);
      int b = a + random.nextInt(10);
      int c = b + random.nextInt(10);
      int d = c + random.nextInt(10);
      Room room = new Room("Room " + String.valueOf(i), new int[] {a, b, c, d});
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

  @Test
  public void testEquals() {
    Random random = new Random();
    List<Room> roomList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      int a = random.nextInt(10);
      int b = a + random.nextInt(10);
      int c = b + random.nextInt(10);
      int d = c + random.nextInt(10);
      Room room = new Room("Room " + String.valueOf(i), new int[] {a, b, c, d});
      roomList.add(room);
    }
    Mansion a = new Mansion(30, 40, "My Mansion", roomList);
    Mansion b = new Mansion(20, 40, "My Mansion", roomList);
    Mansion c = new Mansion(30, 30, "My Mansion", roomList);
    Mansion d = new Mansion(30, 40, "Mansion", roomList);
    Mansion e = new Mansion(30, 40, "My Mansion", new ArrayList<>());
    Mansion f = new Mansion(30, 40, "My Mansion", roomList);

    assertTrue(a.equals(a));
    assertTrue(a.equals(f));
    assertFalse(a.equals(b));
    assertFalse(a.equals(c));
    assertFalse(a.equals(d));
    assertFalse(a.equals(e));
  }

  @Test
  public void testHashCode() {
    Random random = new Random();
    List<Room> roomList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      int a = random.nextInt(10);
      int b = a + random.nextInt(10);
      int c = b + random.nextInt(10);
      int d = c + random.nextInt(10);
      Room room = new Room("Room " + String.valueOf(i), new int[] {a, b, c, d});
      roomList.add(room);
    }
    Mansion mansion = new Mansion(30, 40, "My Mansion", roomList);

    assertEquals(Objects.hash("My Mansion", roomList, 30, 40), mansion.hashCode());
  }
}