package world;

public class Item {
  private String name;
  private int value;
  private int roomWithin;

  Item(String name, int value, int roomWithin) {
    this.name = name;
    this.value = value;
    this.roomWithin = roomWithin;
  }

  public int getValue() {
    return value;
  }

  public int getRoomWithin() {
    return roomWithin;
  }

  public String toString() {
    return String.format("%s: %d, %d# room ", name, value, roomWithin);
  }
}
