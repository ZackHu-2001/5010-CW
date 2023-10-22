package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The {@code Room} class represents a room within a game world, characterized
 * by its name, location, items, and neighbors. Each room has a name, a rectangular
 * location within the world grid, a list of items found in the room, and neighboring rooms.
 */
public class Room {
  private final String name;
  private final int id;
  private final int[] location;
  private List<Item> itemList;
  private final List<Room> neighbors;
  private List<Player> playerList;

  /**
   * Constructs a new room with the specified name and location.
   *
   * @param name     The name of the room.
   * @param location An array of four integers specifying the room's location within the world grid.
   *                 The array should contain four elements in the order
   *                 [rowStart, colStart, rowEnd, colEnd].
   * @param id       The id of the room.
   * @throws IllegalArgumentException if the location array does not contain exactly four elements.
   * @throws IllegalStateException    if the location coordinates are invalid
   *                                  (e.g., end coordinates are less than start coordinates).
   */
  public Room(String name, int[] location, int id) {
    // room location check
    if ((location).length != 4) {
      throw new IllegalArgumentException("Location should contain exactly four elements.");
    }

    if (location[0] > location[2] || location[1] > location[3]) {
      throw new IllegalStateException("Incorrect format for room position.");
    }

    if (location[0] < 0 || location[1] < 0) {
      throw new IllegalStateException("Room position should be positive.");
    }
    this.location = location;
    this.name = name.trim();
    this.id = id;
    this.itemList = new ArrayList<>();
    this.neighbors = new ArrayList<>();
    this.playerList = new ArrayList<>();
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
   * Adds a player to the list of player in this room.
   *
   * @param player The player to be added.
   */
  public void addPlayer(Player player) {
    playerList.add(player);
  }


  /**
   * Deletes a player to the list of player in this room.
   *
   * @param player The player to be deleted.
   */
  public void deletePlayer(Player player) {
    if (playerList.contains(player)) {
      playerList.remove(player);
    } else {
      throw new IllegalStateException("Room" + id + "does not have this player.");
    }
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
   * Gets the location of the room as an array of four integers representing
   * [rowStart, colStart, rowEnd, colEnd].
   *
   * @return An array of four integers representing the room's location.
   */
  public int[] getLocation() {
    return location;
  }

  /**
   * Gets the id of the room.
   *
   * @return The id of the room.
   */
  public int getId() {
    return id;
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
   * Gets the list of neighbor room.
   *
   * @return The list of neighbor room.
   */
  public List<Room> getNeightborList() {
    return neighbors;
  }

  /**
   * Once the player picked up the item, it is removed from room.
   * @param index The item's index to be removed.
   */
  public void deleteItem(int index) {
    itemList.remove(index);
  }

  /**
   * Gets the list of visible room.
   *
   * @return The list of visible room.
   */
  public List<Room> getVisibleRoom() {
    return neighbors;
  }

  /**
   * Returns a string representation of the room, including its name,
   * location, items, and neighboring rooms.
   *
   * @return A string containing room information.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder()
        .append("[")
        .append(name)
        .append("]")
        .append(": ")
        .append((id + 1))
        .append("\n\t\tItems within: ");
    if (itemList.isEmpty()) {
      sb.append("[Empty]");
    } else {
      for (Item item : itemList) {
        sb.append(item.toString());
      }
    }

    sb.append("\n\t\tNeighbors: ");
    for (Room room : neighbors) {
      sb.append("#")
          .append(room.getId() + 1)
          .append(" ")
          .append(room.getName())
          .append(", ");
    }
    sb.deleteCharAt(sb.length() - 2);
    sb.append("\n\t\tPlayer inside: ");
    if (playerList.isEmpty()) {
      sb.append("[Empty]\n");
    } else {
      for (Player player : playerList) {
        sb.append(player.toString());
      }
    }
    sb.append("\n");
    return new String(sb);
  }

  /**
   * Indicates whether some other object is "equal to" this one. The equality is
   * determined based on comparing the attributes of two Room objects.
   *
   * @param o The object to compare with this Room.
   * @return {@code true} if the specified object is equal to this Room; {@code false}
   *         otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof Room)) {
      return false;
    }

    Room tmp = (Room) o;
    if (!(tmp.name.equals(this.name))) {
      return false;
    }

    for (int i = 0; i < 4; i++) {
      if (tmp.location[i] != location[i]) {
        return false;
      }
    }

    if (tmp.itemList.size() != itemList.size()) {
      return false;
    }

    for (int i = 0; i < itemList.size(); i++) {
      if (!(tmp.itemList.get(i).equals(itemList.get(i)))) {
        return false;
      }
    }

    return true;
  }

  /**
   * Return the hashcode for room object.
   *
   * @return Hashcode for room object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, location, itemList, neighbors);
  }
}
