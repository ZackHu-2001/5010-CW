package model;

/**
 * Pet class represents the pet of the target, which could make the room invisible.
 * Pet can be moved by player or follow the depth first traversal.
 */
public class Pet implements Movable{
  private final String name;
  private int currentRoom;
  private final int[] routine;

  /**
   * Constructor of Pet class.
   *
   * @param name          name of the pet.
   * @param currentRoom   current room that pet stay inside.
   */
  public Pet(String name, int currentRoom, int[] routine) {
    this.name = name;
    this.currentRoom = currentRoom;
    this.routine = routine;
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

  /**
   * Get the name of the pet.
   *
   * @return pet's name
   */
  public String getName() {
    return name;
  }

}
