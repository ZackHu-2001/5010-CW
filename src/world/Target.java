package world;

public class Target {
  int currentRoom;
  int health;
  String name;


  public Target(int health, String name) {
    if (health <= 0) {
      throw new IllegalArgumentException("Target's health should be positive.");
    }

    currentRoom = 0;
    this.health = health;
    this.name = name;
  }

  public String toString() {
    return String.format(
        "%s: \n\thealth: %d\n\tcurrent room: %d",
        name,
        health,
        currentRoom);
  }
}
