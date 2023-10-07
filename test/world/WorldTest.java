package world;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileReader;
import java.io.IOException;
import org.junit.Test;

/**
 * This class contains JUnit test cases for the {@link World} class, focusing on various aspects
 * such as mansion parsing, neighbor relationships, and target information retrieval.
 */
public class WorldTest {

  @Test
  public void testGetMansion() throws IOException {
    World world = new World(new FileReader("res/mansion.txt"));
    assertEquals(36, world.getMansion().getRow());
    assertEquals(30, world.getMansion().getColumn());
    assertEquals("Doctor Lucky's world.Mansion", world.getMansion().getName());
  }

  @Test
  public void testParseString() throws IOException {
    World world = new World(new FileReader("res/mansion.txt"));
    assertEquals(36, world.getMansion().getRow());
    assertEquals(30, world.getMansion().getColumn());
    assertEquals("Doctor Lucky's world.Mansion", world.getMansion().getName());
    assertEquals(21, world.getMansion().getRoomList().size());
    assertEquals(2, world.getMansion().getRoomList().get(8).getItemList().size());
    assertEquals(8, world.getMansion().getRoomList().get(3).getNeightborList().size());
  }

  @Test
  public void testIsNeighbor() throws IOException {
    Room a = new Room("Dining Hall", new int[] { 12, 11, 21, 20 });
    Room b = new Room("Armory", new int[] { 22, 19, 23, 26 });

    assertTrue(World.isNeighbor(a, b));
  }

  @Test
  public void testGetTarget() throws IOException {
    World world = new World(new FileReader("res/mansion.txt"));
    assertEquals(50, world.getTarget().getHealth());
    assertEquals("Doctor Lucky", world.getTarget().getName());
    assertEquals(0, world.getTarget().getCurrentRoom());
  }
}