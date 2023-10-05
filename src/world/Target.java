package world;

public class Target {
  int currentRoom;
  int roomNum;
  int health;
  String name;


  public Target(int health, String name, int roomNum) {
    if (health <= 0) {
      throw new IllegalArgumentException("Target's health should be positive.");
    }

    currentRoom = 0;
    this.health = health;
    this.name = name;
    this.roomNum = roomNum;
  }

  public void move() {
    if (currentRoom == roomNum) {
      currentRoom = 0;
    } else {
      currentRoom += 1;
    }
  }

  public String toString() {
    return String.format(
        "%s: \n\thealth: %d\n\tcurrent room: %d",
        name,
        health,
        currentRoom);
  }
}
