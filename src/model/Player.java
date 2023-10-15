package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
  private final String name;
  private List<Item> itemList;
  private int currentRoom;

  public Player(String name, int currentRoom) {
    this.name = name;
    this.currentRoom = currentRoom;
    this.itemList = new ArrayList<>();
  }

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

  public void addItem(Item item) {
    itemList.add(item);
  }

  public String toString() {
//    return a description of a specific player including where they are in the world and what they are carrying.
    StringBuilder stringBuilder = new StringBuilder("Player ").append(name);

    for (Item i: itemList) {
      stringBuilder.append(i.toString());
    }

    return stringBuilder.toString();
  }


}
