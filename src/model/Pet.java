package model;

/**
 * Pet class represents the pet of the target, which could make the room invisible.
 * Pet can be moved by player or follow the depth first traversal.
 */
public class Pet implements Movable{
  private final String name;
  private int currentRoom;

  /**
   * Constructor of Pet class.
   *
   * @param name          name of the pet.
   * @param currentRoom   current room that pet stay inside.
   */
  public Pet(String name, int currentRoom) {
    this.name = name;
    this.currentRoom = currentRoom;
  }

  @Override
  public void move(int targetRoom) {
    currentRoom = targetRoom;
  }

  /**
   * Get current room that pet stay inside.
   *
   * @return current room
   */
  public int getCurrentRoom() {
    return currentRoom;
  }
}
