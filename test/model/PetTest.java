package model;

import static org.junit.Assert.assertTrue;

import controller.Controller;
import java.io.StringReader;
import org.junit.Test;
import view.View;

/**
 * The PetTest class tests the pet's movement.
 */
public class PetTest {
  StringBuilder log = new StringBuilder();
  private Appendable output = new StringBuffer();
  private WorldModel model;
  private View view;
  private Controller controller;


  /**
   * Test the starting position of pet and pet location display.
   */
  @Test
  public void testPetStartPoint() {
    StringReader command = new StringReader("yes\nh\nbob\n1\nn\nlook around\n");
    controller.playGame();

    String expectedOutput = "Turn 1: Doctor Lucky[50] at room 1, Fortune the Cat at room 1\n";
    assertTrue(output.toString().contains(expectedOutput));
  }

  /**
   * Test default move(DFT) of pet, and whether the information of pet's location
   * correctly updated.
   */
  @Test
  public void testPetDefaultMove() {
    StringReader command = new StringReader("yes\nh\nbob\n1\nn\n"
        + "look around\nlook around\nlook around\nlook around\nlook around\nlook around\n"
        + "look around\nlook around\nlook around\nlook around\nlook around\nlook around\n"
        + "look around\nlook around\nlook around\nlook around\nlook around\nlook around\n"
        + "look around\nlook around\nlook around\nlook around\nlook around\nlook around\n");
    controller.playGame();

    String expectedOutput = "Turn 10: Doctor Lucky[50] at room 10, Fortune the Cat at room 3\n";
    assertTrue(output.toString().contains(expectedOutput));

    expectedOutput = "Turn 15: Doctor Lucky[50] at room 15, Fortune the Cat at room 10\n";
    assertTrue(output.toString().contains(expectedOutput));

    expectedOutput = "Turn 22: Doctor Lucky[50] at room 1, Fortune the Cat at room 1";
    assertTrue(output.toString().contains(expectedOutput));
  }


  /**
   * Test move pet by player.
   */
  @Test
  public void testMovePet() {
    StringReader command = new StringReader("yes\nh\nbob\n1\nn\n"
        + "move pet\n22\n21\nmove pet\n0\n1\nlook around\nlook around\n");
    controller.playGame();

    String expectedOutput = "Turn 2: Doctor Lucky[50] at room 2, Fortune the Cat at room 21\n";
    assertTrue(output.toString().contains(expectedOutput));

    expectedOutput = "Turn 3: Doctor Lucky[50] at room 3, Fortune the Cat at room 1\n";
    assertTrue(output.toString().contains(expectedOutput));

    expectedOutput = "Turn 4: Doctor Lucky[50] at room 4, Fortune the Cat at room 2\n";
    assertTrue(output.toString().contains(expectedOutput));

    expectedOutput = "Turn 5: Doctor Lucky[50] at room 5, Fortune the Cat at room 4\n";
    assertTrue(output.toString().contains(expectedOutput));
  }
}
