package world;

import java.util.Objects;
import java.util.Random;
import org.junit.Test;

import static org.junit.Assert.*;

public class TargetTest {

  @Test
  public void testInvalidValueForConstructor() {
    try {
      new Target(0, "Bonny", 11);
    } catch (IllegalArgumentException e) {
      System.out.println("Successfully caught exception.");
    }

    try {
      new Target(-2, "Bonny", 11);
    } catch (IllegalArgumentException e) {
      System.out.println("Successfully caught exception.");
    }

    try {
      new Target(3, "Bonny", 0);
    } catch (IllegalArgumentException e) {
      System.out.println("Successfully caught exception.");
    }

    try {
      new Target(3, "Bonny", -1);
    } catch (IllegalArgumentException e) {
      System.out.println("Successfully caught exception.");
    }
  }

  @Test
  public void testValidValueForConstructor() {
    Random random = new Random();
    Target target;
    int health;
    int maxRoomNum;

    for (int i = 0; i < 20; i++) {
      try {
        health = random.nextInt(100)+1;
        maxRoomNum = random.nextInt(100)+1;
        target = new Target(health,
            "Bob" + String.valueOf(health + maxRoomNum),
            maxRoomNum);
      } catch (IllegalArgumentException e) {
        fail("No exception should be caught for valid input.");
      }
    }
  }

  @Test
  public void testMove() {
    Target target = new Target(10, "Foo", 20);
    for (int i=0; i<20; i++) {
      assertEquals(i, target.getCurrentRoom());
      target.move();
    }
    assertEquals(0, target.getCurrentRoom());
  }

  @Test
  public void testGetCurrentRoom() {
    Target target = new Target(10, "Foo", 20);
    for (int i=0; i<20; i++) {
      assertEquals(i, target.getCurrentRoom());
      target.move();
    }
    assertEquals(0, target.getCurrentRoom());
  }

  @Test
  public void testToString() {
    Random random = new Random();
    Target target;
    int health;
    int maxRoomNum;

    for (int i = 0; i < 20; i++) {
      health = random.nextInt(100)+1;
      maxRoomNum = random.nextInt(100)+1;
      target = new Target(health,
          "Bob" + String.valueOf(health + maxRoomNum),
          maxRoomNum);

      String expected = String.format("%s: \n\thealth: %d\n\tcurrent room: %d",
          "Bob" + String.valueOf(health + maxRoomNum),
          health,
          target.getCurrentRoom());

      assertEquals(expected, target.toString());
    }
  }

  @Test
  public void testEquals() {
    Target a = new Target(10, "Bob", 10);
    Target b = new Target(10, "Zack", 10);
    Target c = new Target(10, "BAR", 11);
    Target d = new Target(10, "Bob", 10);
    assertTrue(a.equals(a));
    assertFalse(a.equals(b));
    assertFalse(a.equals(c));
    assertTrue(a.equals(d));
  }

  @Test
  public void testHashCode() {
    Target a = new Target(10, "Bob", 10);
    assertEquals(Objects.hash(a.getName(), a.getCurrentRoom(), 10, a.getHealth()), a.hashCode());
  }
}