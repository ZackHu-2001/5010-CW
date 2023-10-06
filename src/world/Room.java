package world;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Room} class represents a room within a game world, characterized by its name, location, items, and neighbors.
 * Each room has a name, a rectangular location within the world grid, a list of items found in the room, and neighboring rooms.
 */
public class Room {
  private final String name;
  private final int[] location;
  private List<Item> itemList;
  private final List<Room> neighbors;

  /**
   * Constructs a new room with the specified name and location.
   *
   * @param name     The name of the room.
   * @param location An array of four integers specifying the room's location within the world grid.
   *                 The array should contain four elements in the order [rowStart, colStart, rowEnd, colEnd].
   * @throws IllegalArgumentException if the location array does not contain exactly four elements.
   * @throws IllegalStateException    if the location coordinates are invalid (e.g., end coordinates are less than start coordinates).
   */
  public Room(String name, int[] location) {
    // room location check
    if ((location).length != 4) {
      throw new IllegalArgumentException("Location should contain exactly four elements.");
    }

    if (location[0] > location[2] || location[1] > location[3]) {
      throw new IllegalStateException("Incorrect format for room position.");
    }

    if (location[0] < 0 || location[2] < 0 || location[1] < 0 || location[3] < 0) {
      throw new IllegalStateException("Room position should be possitive.");
    }
    this.location = location;
    this.name = name.trim();
    this.itemList = new ArrayList<Item>();
    this.neighbors = new ArrayList<Room>();
  }

  /**
   * Adds an item to the list of items found in the room.
   *
   * @param item The item to be added to the room.
   */
  public void addItem(Item item) {
    itemList.add(item);
  }

  /**
   * Adds a neighboring room to the list of neighbors for this room.
   *
   * @param room The neighboring room to be added.
   */
  public void addNeighbor(Room room) {
    neighbors.add(room);
  }

  /**
   * Gets the name of the room.
   *
   * @return The name of the room.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the location of the room as an array of four integers representing [rowStart, colStart, rowEnd, colEnd].
   *
   * @return An array of four integers representing the room's location.
   */
  public int[] getLocation() {
    return location;
  }

  /**
   * Gets the list of items found in the room.
   *
   * @return The list of items in the room.
   */
  public List<Item> getItemList() {
    return itemList;
  }

  /**
   * Returns a string representation of the room, including its name, location, items, and neighboring rooms.
   *
   * @return A string containing room information.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder()
        .append("[" + name + "]")
        .append(": ")
        .append(location[0])
        .append(" ")
        .append(location[1])
        .append(", ")
        .append(location[2])
        .append(" ")
        .append(location[3])
        .append("\n\t\tItems within: ");
    for (Item item: itemList) {
      sb.append(item.toString());
    }
    sb.append("\n\t\tNeighbors: ");
    for (Room room: neighbors) {
      sb.append(room.getName());
      sb.append(", ");
    }
    sb.append("\n\n");
    return new String(sb);
  }

  @Override
  public boolean equals(Object o) {
    return false;
  }

  @Override
  public int hashCode() {
    return 0;
  }
}
