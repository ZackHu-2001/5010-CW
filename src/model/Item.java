package model;

import java.util.Objects;

/**
 * The {@code Item} class represents an item that can be found within a
 * specific room in a game world. Each item has a name, a value, and is
 * located within a particular room.
 */
public class Item {
  private final String name;
  private final int value;
  private int roomWithin;
  private final int maxRoomNum;
  /**
   * Constructs a new item with the specified name, value, and room location.
   *
   * @param name       The name of the item.
   * @param value      The value of the item.
   * @param roomWithin The room number where the item is located.
   * @param maxRoomNum The maximum number of room in the mansion.
   */

  public Item(String name, int value, int roomWithin, int maxRoomNum) {
    if (value <= 0) {
      throw new IllegalArgumentException("Item should have positive attack.");
    }
    if (roomWithin < 0 || roomWithin >= maxRoomNum) {
      throw new IllegalArgumentException("Illegal room for item.");
    }

    this.name = name;
    this.value = value;
    this.roomWithin = roomWithin;
    this.maxRoomNum = maxRoomNum;
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
   * Gets the name of the item.
   * @return The name of the item.
   */
  public String getName() {
    return name;
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
    return String.format("[%s]: attack %d, in #%d room; ", name, value, roomWithin + 1);
  }

  /**
   * Compares the input object with this object and
   * returns true if they are equal.
   *
   * @param o Object to compare.
   * @return  Whether two objects equals.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (! (o instanceof Item)) {
      return false;
    }

    Item item = (Item) o;
    return this.name.equals(item.name)
        && this.value == item.value
        && this.roomWithin == item.roomWithin
        && this.maxRoomNum == item.maxRoomNum;
  }

  /**
   * Return the hashcode for item object based on
   * its name, value, room within, and max room number.
   *
   * @return Hashcode of this item object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, value, roomWithin, maxRoomNum);
  }
}
