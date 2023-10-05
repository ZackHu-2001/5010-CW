package world;

import java.util.ArrayList;
import java.util.List;

public class Room {
  private String name;
  private int[] location;
  private List<Item> itemList;
  private List<Room> neighbors;

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

  public void addItem(Item item) {
    itemList.add(item);
  }

  public void addNeighbor(Room room) {
    neighbors.add(room);
  }
  public String getName() {
    return name;
  }
  public int[] getLocation() {
    return location;
  }

  public List<Item> getItemList() {
    return itemList;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder()
        .append(name)
        .append(" ")
        .append(location[0])
        .append(" ")
        .append(location[1])
        .append(" ")
        .append(location[2])
        .append(" ")
        .append(location[3])
        .append("\t\titems: ");
    for (Item item: itemList) {
      sb.append(item.toString());
    }
    sb.append("neighbors: ");
    for (Room room: neighbors) {
      sb.append(room.getName());
      sb.append(", ");
    }
    sb.append("\n");
    return new String(sb);
  }
}
