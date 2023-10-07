package world;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Objects;
import java.util.Random;
import org.junit.Test;

/**
 * This class contains JUnit test cases for the {@link Item} class.
 * It covers various scenarios to test the behavior of the Item class methods.
 */
public class ItemTest {
  private Item item;

  /**
   * Test case for invalid values provided to the {@link Item} constructor.
   * It ensures that the constructor correctly throws exceptions when
   * invalid parameters are given.
   */
  @Test
  public void testInvalidValueForConstructor() {
    try {
      new Item("Foo", 3, -1, 15);
      fail("Unable to catch exception.");
    } catch (IllegalArgumentException e) {
      System.out.println("Successfully caught the exception.");
    }

    try {
      new Item("Foo", -3, 10, 15);
      fail("Unable to catch exception.");
    } catch (IllegalArgumentException e) {
      System.out.println("Successfully caught the exception.");
    }

    try {
      new Item("Foo", 0, 10, 15);
      fail("Unable to catch exception.");
    } catch (IllegalArgumentException e) {
      System.out.println("Successfully caught the exception.");
    }

    try {
      new Item("Foo", 1, 10, 10);
      fail("Unable to catch exception.");
    } catch (IllegalArgumentException e) {
      System.out.println("Successfully caught the exception.");
    }
  }

  /**
   * Test case for valid values provided to the {@link Item} constructor.
   * It ensures that the constructor accepts valid parameters without exceptions.
   */
  @Test
  public void testValidValueForConstructor() {
    try {
      new Item("Foo", 1, 1, 15);
      new Item("Foo", 3, 10, 15);
      new Item("Foo", 4, 5, 15);
      new Item("Foo", 2, 6, 15);
      new Item("Foo", 6, 12, 15);
    } catch (IllegalArgumentException e) {
      fail("No exception should be thrown.");
    }
  }

  /**
   * Test the {@link Item#getValue()} method to ensure it returns the correct value.
   */
  @Test
  public void testGetValue() {
    // Fuzz test
    Random random = new Random();
    for (int i = 0; i < 20; i++) {
      int value = random.nextInt(100) + 1;
      int roomWithin = random.nextInt(100);
      Item item = new Item("Foo" + String.valueOf(value), value,
          roomWithin, roomWithin + 1);
      assertEquals(value, item.getValue());
    }
  }

  /**
   * Test the {@link Item#getName()} method to ensure it returns the correct name.
   */
  @Test
  public void testGetName() {
    // Fuzz test
    Random random = new Random();
    for (int i = 0; i < 20; i++) {
      int value = random.nextInt(100) + 1;
      int roomWithin = random.nextInt(100);
      Item item = new Item("Foo" + String.valueOf(value), value,
          roomWithin, roomWithin + 1);
      assertEquals("Foo" + String.valueOf(value), item.getName());
    }
  }

  /**
   * Test the {@link Item#getRoomWithin()} method to ensure it returns the correct room within.
   */
  @Test
  public void testGetRoomWithin() {
    // Fuzz test
    Random random = new Random();
    for (int i = 0; i < 20; i++) {
      int value = random.nextInt(100) + 1;
      int roomWithin = random.nextInt(100);
      Item item = new Item("Foo" + String.valueOf(value), value,
          roomWithin, roomWithin + 1);
      assertEquals(roomWithin, item.getRoomWithin());
    }
  }

  /**
   * Test the {@link Item#toString()} method to ensure it generates
   * the expected string representation.
   */
  @Test
  public void testToString() {
    // Fuzz test
    Random random = new Random();
    for (int i = 0; i < 20; i++) {
      int value = random.nextInt(100) + 1;
      int roomWithin = random.nextInt(100);
      Item item = new Item("Foo" + String.valueOf(value), value,
          roomWithin, roomWithin + 1);
      String expected = String.format("[%s]: %d, in %d# room; ",
          "Foo" + String.valueOf(value), value, roomWithin);
      assertEquals(expected, item.toString());
    }
  }

  /**
   * Test the {@link Item#equals(Object)} method to ensure it correctly
   * compares Item objects for equality.
   */
  @Test
  public void testEquals() {
    Item a = new Item("A", 3, 15, 20);
    assertTrue(a.equals(a));

    Item b = new Item("B", 3, 15, 20);
    assertFalse(a.equals(b));

    Item c = new Item("A", 1, 15, 20);
    assertFalse(a.equals(c));

    Item d = new Item("A", 3, 12, 20);
    assertFalse(a.equals(d));

    Item e = new Item("A", 3, 15, 18);
    assertFalse(a.equals(e));

    Item f = new Item("A", 3, 15, 20);
    assertTrue(a.equals(f));
  }

  /**
   * Test the {@link Item#hashCode()} method to ensure it generates the
   * correct hash code for Item objects.
   */
  @Test
  public void testHashCode() {
    Item a = new Item("A", 3, 15, 20);
    assertEquals(Objects.hash("A", 3, 15, 20), a.hashCode());
  }
}