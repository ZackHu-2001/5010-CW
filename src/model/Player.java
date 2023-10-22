package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Player class represents the player. It maintains fields
 * like name, currentRoom, and list of holding items. It
 * also provides some basic method to let controller control.
 */
public class Player {
  private final String name;
  private List<Item> itemList;
  private int currentRoom;
  private final boolean isHuman;

  /**
   * Constructor of Player class.
   *
   * @param name        Name of the player.
   * @param currentRoom Room that player start with.
   * @param isHuman     Whether is a human player.
   */
  public Player(String name, int currentRoom, boolean isHuman) {
    this.name = name;
    this.currentRoom = currentRoom;
    this.itemList = new ArrayList<>();
    this.isHuman = isHuman;
  }

  /**
   * Return whether it is a human player.
   *
   * @return Whether it is a human player.
   */
  public boolean isHuman() {
    return isHuman;
  }

  /**
   * Get current room that player is in.
   *
   * @return Current room's id.
   */
  public int getCurrentRoom() {
    return currentRoom;
  }

  /**
   * Move the player from current to target room,
   * this method actually acts like setter.
   *
   * @param targetRoom The target room to move to.
   */
  public void move(int targetRoom) {
    currentRoom = targetRoom;
  }

  /**
   * Add item to the player, if already have 4 items then replace the first item with the new item.
   *
   * @param item Item to be added.
   */
  public void addItem(Item item) {
    if (itemList.size() >= 4) {
      itemList.set(0, item);
    } else {
      itemList.add(item);
    }
  }

  /**
   * Return a description of a specific player including where they are
   * in the world and what they are carrying.
   *
   * @return A description of a specific player including where they are
   *        in the world and what they are carrying.
   */
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("Name: ")
        .append(name)
        .append("    Holds: ");
    if (itemList.isEmpty()) {
      stringBuilder.append("[Empty]\n");
    } else {
      for (Item i : itemList) {
        stringBuilder.append(i.toString());
      }
      stringBuilder.append("\n");
    }

    return stringBuilder.toString();
  }
}
