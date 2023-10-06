package world;

/**
 * The {@code Item} class represents an item that can be found within a specific room in a game world.
 * Each item has a name, a value, and is located within a particular room.
 */
public class Item {
  private String name;
  private int value;
  private int roomWithin;

  /**
   * Constructs a new item with the specified name, value, and room location.
   *
   * @param name       The name of the item.
   * @param value      The value of the item.
   * @param roomWithin The room number where the item is located.
   */
  Item(String name, int value, int roomWithin) {
    this.name = name;
    this.value = value;
    this.roomWithin = roomWithin;
  }

  /**
   * Gets the value of the item.
   *
   * @return The value of the item.
   */
  public int getValue() {
    return value;
  }

  /**
   * Gets the room number where the item is located.
   *
   * @return The room number where the item is located.
   */
  public int getRoomWithin() {
    return roomWithin;
  }

  /**
   * Returns a string representation of the item, including its name, value, and room location.
   *
   * @return A string containing item information.
   */
  public String toString() {
    return String.format("[%s]: %d, in %d# room; ", name, value, roomWithin);
  }
}
