package world;

import java.util.Objects;

/**
 * The {@code Target} class represents a target object that can move between rooms in a game.
 * Each target has a name, health, and can move between rooms within a specified range.
 * The target's health must be positive when created.
 */
public class Target {
  private int currentRoom;
  private final int maxRoomNum;
  private int health;
  private final String name;

  /**
   * Constructs a new target with the specified health, name, and room range.
   *
   * @param health   The initial health of the target. Must be a positive integer.
   * @param name     The name of the target.
   * @param maxRoomNum  The total number of rooms that the target can move between.
   * @throws IllegalArgumentException if the health is not positive.
   */
  public Target(int health, String name, int maxRoomNum) {
    if (health <= 0) {
      throw new IllegalArgumentException("Target's health should be positive.");
    }
    if (maxRoomNum <= 0) {
      throw new IllegalArgumentException("Max room count should be positive.");
    }

    currentRoom = 0;
    this.health = health;
    this.name = name;
    this.maxRoomNum = maxRoomNum;
  }

  /**
   * Moves the target to the next room in its range. If the target reaches the last room,
   * it wraps around to the first room.
   */
  public void move() {
    if (currentRoom == maxRoomNum - 1) {
      currentRoom = 0;
    } else {
      currentRoom += 1;
    }
  }

  /**
   * Returns the current room that the target in.
   *
   * @return Number of room that the target in.
   */
  public int getCurrentRoom() {
    return currentRoom;
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

  /**
   * Indicates whether some other object is "equal to" this one. The equality is
   * determined based on comparing the attributes of two Target objects.
   *
   * @param o The object to compare with this Target.
   * @return {@code true} if the specified object is equal to this Target; {@code false}
   *         otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (! (o instanceof Target)) {
      return false;
    }

    Target tmp = (Target) o;
    return currentRoom == tmp.currentRoom
        && maxRoomNum == tmp.maxRoomNum
        && health == tmp.health
        && name.equals(tmp.name);
  }

  /**
   * Returns a hash code value for this Target object.
   *
   * @return A hash code value for this Target object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, currentRoom, maxRoomNum, health);
  }
}
