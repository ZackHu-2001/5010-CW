package world;

/**
 * The {@code Target} class represents a target object that can move between rooms in a game.
 * Each target has a name, health, and can move between rooms within a specified range.
 * The target's health must be positive when created.
 */
public class Target {
  int currentRoom;
  int roomNum;
  int health;
  String name;

  /**
   * Constructs a new target with the specified health, name, and room range.
   *
   * @param health   The initial health of the target. Must be a positive integer.
   * @param name     The name of the target.
   * @param roomNum  The total number of rooms that the target can move between.
   * @throws IllegalArgumentException if the health is not positive.
   */
  public Target(int health, String name, int roomNum) {
    if (health <= 0) {
      throw new IllegalArgumentException("Target's health should be positive.");
    }

    currentRoom = 0;
    this.health = health;
    this.name = name;
    this.roomNum = roomNum;
  }

  /**
   * Moves the target to the next room in its range. If the target reaches the last room,
   * it wraps around to the first room.
   */
  public void move() {
    if (currentRoom == roomNum) {
      currentRoom = 0;
    } else {
      currentRoom += 1;
    }
  }

  /**
   * Returns a string representation of the target, including its name, health, and current room.
   *
   * @return A string containing target information.
   */
  public String toString() {
    return String.format(
        "%s: \n\thealth: %d\n\tcurrent room: %d",
        name,
        health,
        currentRoom);
  }
}
