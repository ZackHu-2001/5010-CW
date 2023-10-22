package controller;

import static java.lang.System.exit;
import static java.lang.System.out;
import static org.junit.Assert.assertEquals;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import model.World;
import model.WorldModel;
import org.junit.Before;
import org.junit.Test;


/**
 * This is the test for the game controller. Mainly focus
 * on testing the user input handling, and the internal
 * state updates after user's request.
 */
public class GameControllerTest {
  StringBuilder log = new StringBuilder();
  private WorldModel worldModel;
  private Appendable output = new StringBuffer();
//  private Appendable output = System.out;


  /**
   * Read in the model before each test.
   */
  @Before
  public void setUp() {
    Reader fileReader;
    String pathToFile = "res/mansion.txt";

    try {
      fileReader = new FileReader(pathToFile);
      this.worldModel = new World(fileReader);
    } catch (IOException e) {
      System.out.println("There are problems with path to file, exit now.");
      exit(1);
    }

  }

  /**
   * Test invalid turn: String Input.
   */
  @Test
  public void testInvalidTurn_StringInput() {
    StringReader command = new StringReader("jkl\n10\nq\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "Invalid max turn, one positive integer expected.\n" +
        "Please enter again:\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "Exit game, have a nice day~\n";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Test invalid turn: Nonconsecutive String.
   */
  @Test
  public void testInvalidTurn_NonconsecutiveString() {
    StringReader command = new StringReader("jkl uio\n10\nq\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "Invalid max turn, one positive integer expected.\n" +
        "Please enter again:\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "Exit game, have a nice day~\n";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Test invalid turn: Multiple Line Of String.
   */
  @Test
  public void testInvalidTurn_MultipleLineOfString() {
    StringReader command = new StringReader("jkl\nuio\nabc\n10\nq\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "Invalid max turn, one positive integer expected.\n" +
        "Please enter again:Invalid max turn, one positive integer expected.\n" +
        "Please enter again:Invalid max turn, one positive integer expected.\n" +
        "Please enter again:\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "Exit game, have a nice day~\n";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Test invalid turn: Zero.
   */
  @Test
  public void testInvalidTurn_Zero() {
    StringReader command = new StringReader("0\n10\nq\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "Invalid max turn, one positive integer expected.\n" +
        "\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "Exit game, have a nice day~\n";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Test invalid turn: Negative value.
   */
  @Test
  public void testInvalidTurn_NegativeValue() {
    StringReader command = new StringReader("-5\n10\nq\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "Invalid max turn, one positive integer expected.\n" +
        "\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "Exit game, have a nice day~\n";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Test invalid turn: Negative value.
   */
  @Test
  public void testInvalidTurn_QuitDirectly() {
    StringReader command = new StringReader("q\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "Exit game, have a nice day~\n";
    assertEquals(expectedOutput, output.toString());
  }


  /**
   * Test add player: direct quit.
   */
  @Test
  public void testAddPlayer_Quit() {
    StringReader command = new StringReader("20\nq\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "Exit game, have a nice day~\n";
    assertEquals(expectedOutput, output.toString());
  }


  /**
   * Test add player: indirect quit.
   */
  @Test
  public void testAddPlayer_IndirectQuit() {
    StringReader command = new StringReader("20\nno I don't want to start\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "Exit game, have a nice day~\n";
    assertEquals(expectedOutput, output.toString());
  }


  /**
   * Test add player: quit half way, scenario 1.
   */
  @Test
  public void testAddPlayer_QuitHalfWay_1() {
    StringReader command = new StringReader("20\nyes\nquit\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "\n" +
        "Let's add players.\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Exit game, have a nice day~";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Test add player: quit half way, scenario 2.
   */
  @Test
  public void testAddPlayer_QuitHalfWay_2() {
    StringReader command = new StringReader("20\nyes\nhuman\nzack\nquit\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "\n" +
        "Let's add players.\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "Exit game, have a nice day~\n";
    assertEquals(expectedOutput, output.toString());
  }


  /**
   * Test add player: quit half way, scenario 3.
   */
  @Test
  public void testAddPlayer_QuitHalfWay_3() {
    StringReader command = new StringReader("20\nyes\nhuman\nzack\n5\nquit\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "\n" +
        "Let's add players.\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "\n" +
        "One player added successfully!\n" +
        "\n" +
        "A summary of the human player added:\n" +
        "Name: zack\n" +
        "Starting position: 5\n" +
        "\n" +
        "Do you want to add one more player? (Maximum 7, now 1) [Y/N]\n" +
        "Exit game, have a nice day~\n";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Test add player: add invalid type of player: AI.
   */
  @Test
  public void testAddPlayer_InvalidTypeOfPlayer_1() {
    StringReader command = new StringReader("20\ny\nAI\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "\n" +
        "Let's add players.\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Undetectable input: AI\n" +
        "Please enter again: Enter the name of the player: \n";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Test add player: add invalid type of player: computer AI.
   */
  @Test
  public void testAddPlayer_InvalidTypeOfPlayer_2() {
    StringReader command = new StringReader("20\ny\ncomputer AI\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "\n" +
        "Let's add players.\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Undetectable input: computer AI\n" +
        "Please enter again: Enter the name of the player: \n";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Test add player: use 'quit' as player name.
   */
  @Test
  public void testAddPlayer_QuitAsName() {
    StringReader command = new StringReader("20\nq\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "Exit game, have a nice day~\n";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Test add player: invalid start position: 0.
   */
  @Test
  public void testAddPlayer_InvalidPosition_Zero() {
    StringReader command = new StringReader("20\nyes\nhuman\nzack\n0\n1\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "\n" +
        "Let's add players.\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "Invalid position: 0, should be in range 1 to 21. \n" +
        "Please enter again: \n" +
        "One player added successfully!\n" +
        "\n" +
        "A summary of the human player added:\n" +
        "Name: zack\n" +
        "Starting position: 1\n" +
        "\n" +
        "Do you want to add one more player? (Maximum 7, now 1) [Y/N]\n" +
        "\n" +
        "Stop adding player.\n" +
        "All players loaded, game starts now!\n" +
        "\n" +
        "Turn 1, Doctor Lucky at room 0\n" +
        "Information of the current turn's human player: \n" +
        "Name: zack    Holds: [Empty]\n" +
        "In room [Armory]: 22 19, 23 26\n" +
        "\t\tItems within: [Revolver]: attack 3, in #1 room; \n" +
        "\t\tNeighbors: #2 Billiard world.Room, #4 Dining Hall, #5 Drawing world.Room \n" +
        "\t\tPlayer inside: Name: zack    Holds: [Empty]\n" +
        "\n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "3. pick item (pick up item in the room)\n";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Test add player: invalid start position: -5.
   */
  @Test
  public void testAddPlayer_InvalidPosition_NegativeValue() {
    StringReader command = new StringReader("20\nyes\nhuman\nzack\n-5\n1\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "\n" +
        "Let's add players.\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "Invalid position: -5, should be in range 1 to 21. \n" +
        "Please enter again: \n" +
        "One player added successfully!\n" +
        "\n" +
        "A summary of the human player added:\n" +
        "Name: zack\n" +
        "Starting position: 1\n" +
        "\n" +
        "Do you want to add one more player? (Maximum 7, now 1) [Y/N]\n" +
        "\n" +
        "Stop adding player.\n" +
        "All players loaded, game starts now!\n" +
        "\n" +
        "Turn 1, Doctor Lucky at room 0\n" +
        "Information of the current turn's human player: \n" +
        "Name: zack    Holds: [Empty]\n" +
        "In room [Armory]: 22 19, 23 26\n" +
        "\t\tItems within: [Revolver]: attack 3, in #1 room; \n" +
        "\t\tNeighbors: #2 Billiard world.Room, #4 Dining Hall, #5 Drawing world.Room \n" +
        "\t\tPlayer inside: Name: zack    Holds: [Empty]\n" +
        "\n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "3. pick item (pick up item in the room)\n";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Test add player: invalid start position: 22.
   */
  @Test
  public void testAddPlayer_InvalidPosition_OutOfBound() {
    StringReader command = new StringReader("20\nyes\nhuman\nzack\n22\n1\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "\n" +
        "Let's add players.\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "Invalid position: 22, should be in range 1 to 21. \n" +
        "Please enter again: \n" +
        "One player added successfully!\n" +
        "\n" +
        "A summary of the human player added:\n" +
        "Name: zack\n" +
        "Starting position: 1\n" +
        "\n" +
        "Do you want to add one more player? (Maximum 7, now 1) [Y/N]\n" +
        "\n" +
        "Stop adding player.\n" +
        "All players loaded, game starts now!\n" +
        "\n" +
        "Turn 1, Doctor Lucky at room 0\n" +
        "Information of the current turn's human player: \n" +
        "Name: zack    Holds: [Empty]\n" +
        "In room [Armory]: 22 19, 23 26\n" +
        "\t\tItems within: [Revolver]: attack 3, in #1 room; \n" +
        "\t\tNeighbors: #2 Billiard world.Room, #4 Dining Hall, #5 Drawing world.Room \n" +
        "\t\tPlayer inside: Name: zack    Holds: [Empty]\n" +
        "\n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "3. pick item (pick up item in the room)\n";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Test add player: one human player
   */
  @Test
  public void testAddPlayer_OneHumanPlayer() {
    StringReader command = new StringReader("20\nyes\nh\nzack\n5\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "\n" +
        "Let's add players.\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "\n" +
        "One player added successfully!\n" +
        "\n" +
        "A summary of the human player added:\n" +
        "Name: zack\n" +
        "Starting position: 5\n" +
        "\n" +
        "Do you want to add one more player? (Maximum 7, now 1) [Y/N]\n" +
        "\n" +
        "Stop adding player.\n" +
        "All players loaded, game starts now!\n" +
        "\n" +
        "Turn 1, Doctor Lucky at room 0\n" +
        "Information of the current turn's human player: \n" +
        "Name: zack    Holds: [Empty]\n" +
        "In room [Drawing world.Room]: 22 13, 25 18\n" +
        "\t\tItems within: [Letter Opener]: attack 2, in #5 room; \n" +
        "\t\tNeighbors: #1 Armory, #4 Dining Hall, #6 Foyer, #20 Wine Cellar \n" +
        "\t\tPlayer inside: Name: zack    Holds: [Empty]\n" +
        "\n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "3. pick item (pick up item in the room)\n";
    assertEquals(expectedOutput, output.toString());
  }


  /**
   * Test add player: one computer player
   */
  @Test
  public void testAddPlayer_OneComputerPlayer() {
    StringReader command = new StringReader("20\nyes\nc\nzack\n5\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "\n" +
        "Let's add players.\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "\n" +
        "One player added successfully!\n" +
        "\n" +
        "A summary of the computer player added:\n" +
        "Name: zack\n" +
        "Starting position: 5\n" +
        "\n" +
        "Do you want to add one more player? (Maximum 7, now 1) [Y/N]\n" +
        "\n" +
        "Stop adding player.\n" +
        "All players loaded, game starts now!\n" +
        "\n" +
        "Turn 1, Doctor Lucky at room 0\n" +
        "Information of the current turn's computer player: \n" +
        "Name: zack    Holds: [Empty]\n" +
        "In room [Drawing world.Room]: 22 13, 25 18\n" +
        "\t\tItems within: [Letter Opener]: attack 2, in #5 room; \n" +
        "\t\tNeighbors: #1 Armory, #4 Dining Hall, #6 Foyer, #20 Wine Cellar \n" +
        "\t\tPlayer inside: Name: zack    Holds: [Empty]\n" +
        "\n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "3. pick item (pick up item in the room)\n";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Test add player: one computer player and one human.
   */
  @Test
  public void testAddPlayer_OneComputerOneHuman() {
    StringReader command = new StringReader("20\nyes\nc\nai\n5\ny\nh\nzack\n2");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "\n" +
        "Let's add players.\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "\n" +
        "One player added successfully!\n" +
        "\n" +
        "A summary of the computer player added:\n" +
        "Name: ai\n" +
        "Starting position: 5\n" +
        "\n" +
        "Do you want to add one more player? (Maximum 7, now 1) [Y/N]\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "\n" +
        "One player added successfully!\n" +
        "\n" +
        "A summary of the human player added:\n" +
        "Name: zack\n" +
        "Starting position: 2\n" +
        "\n" +
        "Do you want to add one more player? (Maximum 7, now 2) [Y/N]\n" +
        "\n" +
        "Stop adding player.\n" +
        "All players loaded, game starts now!\n" +
        "\n" +
        "Turn 1, Doctor Lucky at room 1\n" +
        "Information of the current turn's computer player: \n" +
        "Name: ai    Holds: [Empty]\n" +
        "In room [Drawing world.Room]: 4\n" +
        "\t\tItems within: [Letter Opener]: attack 2, in #5 room; \n" +
        "\t\tNeighbors: #1 Armory, #4 Dining Hall, #6 Foyer, #20 Wine Cellar \n" +
        "\t\tPlayer inside: Name: ai    Holds: [Empty]\n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "3. pick item (pick up item in the room)\n" +
        "Neighbor rooms' information: \n" +
        "[Armory]: 0\n" +
        "\t\tItems within: [Revolver]: attack 3, in #1 room; \n" +
        "\t\tNeighbors: #2 Billiard world.Room, #4 Dining Hall, #5 Drawing world.Room \n" +
        "\t\tPlayer inside: [Empty]\n" +
        "\n" +
        "[Dining Hall]: 3\n" +
        "\t\tItems within: [Empty]\n" +
        "\t\tNeighbors: #1 Armory, #2 Billiard world.Room, #5 Drawing world.Room, #9 Kitchen, #15 Parlor, #18 Tennessee world.Room, #19 Trophy world.Room, #20 Wine Cellar \n" +
        "\t\tPlayer inside: [Empty]\n" +
        "\n" +
        "[Foyer]: 5\n" +
        "\t\tItems within: [Empty]\n" +
        "\t\tNeighbors: #5 Drawing world.Room, #16 Piazza \n" +
        "\t\tPlayer inside: [Empty]\n" +
        "\n" +
        "[Wine Cellar]: 19\n" +
        "\t\tItems within: [Rat Poison]: attack 2, in #20 room; [Piece of Rope]: attack 2, in #20 room; \n" +
        "\t\tNeighbors: #4 Dining Hall, #5 Drawing world.Room, #9 Kitchen \n" +
        "\t\tPlayer inside: [Empty]\n" +
        "\n" +
        "\n" +
        "Turn 2, Doctor Lucky at room 2\n" +
        "Information of the current turn's human player: \n" +
        "Name: zack    Holds: [Empty]\n" +
        "In room [Billiard world.Room]: 1\n" +
        "\t\tItems within: [Billiard Cue]: attack 2, in #2 room; \n" +
        "\t\tNeighbors: #1 Armory, #4 Dining Hall, #19 Trophy world.Room \n" +
        "\t\tPlayer inside: Name: zack    Holds: [Empty]\n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "3. pick item (pick up item in the room)\n";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Test add player: maximum number of player
   */
  @Test
  public void testAddPlayer_MaxNum() {
    StringReader command = new StringReader("20\nyes\nc\nzack\n5\ny\nh\nfoo\n2\ny\nh\n" +
        "bar\n3\ny\nh\nqua\n4\ny\nh\nufo\n6\ny\nh\nhhh\n12\ny\nh\nabc\n20\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "\n" +
        "Let's add players.\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "\n" +
        "One player added successfully!\n" +
        "\n" +
        "A summary of the computer player added:\n" +
        "Name: zack\n" +
        "Starting position: 5\n" +
        "\n" +
        "Do you want to add one more player? (Maximum 7, now 1) [Y/N]\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "\n" +
        "One player added successfully!\n" +
        "\n" +
        "A summary of the human player added:\n" +
        "Name: foo\n" +
        "Starting position: 2\n" +
        "\n" +
        "Do you want to add one more player? (Maximum 7, now 2) [Y/N]\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "\n" +
        "One player added successfully!\n" +
        "\n" +
        "A summary of the human player added:\n" +
        "Name: bar\n" +
        "Starting position: 3\n" +
        "\n" +
        "Do you want to add one more player? (Maximum 7, now 3) [Y/N]\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "\n" +
        "One player added successfully!\n" +
        "\n" +
        "A summary of the human player added:\n" +
        "Name: qua\n" +
        "Starting position: 4\n" +
        "\n" +
        "Do you want to add one more player? (Maximum 7, now 4) [Y/N]\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "\n" +
        "One player added successfully!\n" +
        "\n" +
        "A summary of the human player added:\n" +
        "Name: ufo\n" +
        "Starting position: 6\n" +
        "\n" +
        "Do you want to add one more player? (Maximum 7, now 5) [Y/N]\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "\n" +
        "One player added successfully!\n" +
        "\n" +
        "A summary of the human player added:\n" +
        "Name: hhh\n" +
        "Starting position: 12\n" +
        "\n" +
        "Do you want to add one more player? (Maximum 7, now 6) [Y/N]\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "\n" +
        "One player added successfully!\n" +
        "\n" +
        "A summary of the human player added:\n" +
        "Name: abc\n" +
        "Starting position: 20\n" +
        "\n" +
        "All players loaded, game starts now!\n" +
        "\n" +
        "Turn 1, Doctor Lucky at room 0\n" +
        "Information of the current turn's computer player: \n" +
        "Name: zack    Holds: [Empty]\n" +
        "In room [Drawing world.Room]: 22 13, 25 18\n" +
        "\t\tItems within: [Letter Opener]: attack 2, in #5 room; \n" +
        "\t\tNeighbors: #1 Armory, #4 Dining Hall, #6 Foyer, #20 Wine Cellar \n" +
        "\t\tPlayer inside: Name: zack    Holds: [Empty]\n" +
        "\n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "3. pick item (pick up item in the room)\n";
    assertEquals(expectedOutput, output.toString());
  }


  /**
   * Test move: move to invalid space & move to same space.
   */
  @Test
  public void testMove_InvalidMove() {
    StringReader command = new StringReader("20\nyes\nh\nbob\n1\ny\nh\nzack\n2\nN\nmove\n1\n2\nquit\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "\n" +
        "Let's add players.\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "\n" +
        "One player added successfully!\n" +
        "\n" +
        "A summary of the human player added:\n" +
        "Name: bob\n" +
        "Starting position: 1\n" +
        "\n" +
        "Do you want to add one more player? (Maximum 7, now 1) [Y/N]\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "\n" +
        "One player added successfully!\n" +
        "\n" +
        "A summary of the human player added:\n" +
        "Name: zack\n" +
        "Starting position: 2\n" +
        "\n" +
        "Do you want to add one more player? (Maximum 7, now 2) [Y/N]\n" +
        "\n" +
        "Stop adding player.\n" +
        "All players loaded, game starts now!\n" +
        "\n" +
        "Turn 1, Doctor Lucky at room 1\n" +
        "Information of the current turn's human player: \n" +
        "Name: bob    Holds: [Empty]\n" +
        "In room [Armory]: 0\n" +
        "\t\tItems within: [Revolver]: attack 3, in #1 room; \n" +
        "\t\tNeighbors: #2 Billiard world.Room, #4 Dining Hall, #5 Drawing world.Room \n" +
        "\t\tPlayer inside: Name: bob    Holds: [Empty]\n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "3. pick item (pick up item in the room)\n" +
        "Please enter the index of the room you want to move to: \n" +
        "Can not move to room 1, not neighbor of current room.\n" +
        "Please enter again: \n" +
        "Successfully moved to room 2.\n" +
        "\n" +
        "Turn 2, Doctor Lucky at room 2\n" +
        "Information of the current turn's human player: \n" +
        "Name: zack    Holds: [Empty]\n" +
        "In room [Billiard world.Room]: 1\n" +
        "\t\tItems within: [Billiard Cue]: attack 2, in #2 room; \n" +
        "\t\tNeighbors: #1 Armory, #4 Dining Hall, #19 Trophy world.Room \n" +
        "\t\tPlayer inside: Name: zack    Holds: [Empty]\n" +
        "Name: bob    Holds: [Empty]\n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "3. pick item (pick up item in the room)\n" +
        "Exit game, have a nice day~\n";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Test move: move to invalid space & one leave one come in, check if they can see each other
   */
  @Test
  public void testMove_InvalidMove_1() {
    StringReader command = new StringReader("20\nyes\nh\nbob\n1\ny\nh\nzack\n2\nN\nmove\n1\n2\nmove\n2\n1\nquit\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "\n" +
        "Let's add players.\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "\n" +
        "One player added successfully!\n" +
        "\n" +
        "A summary of the human player added:\n" +
        "Name: bob\n" +
        "Starting position: 1\n" +
        "\n" +
        "Do you want to add one more player? (Maximum 7, now 1) [Y/N]\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "\n" +
        "One player added successfully!\n" +
        "\n" +
        "A summary of the human player added:\n" +
        "Name: zack\n" +
        "Starting position: 2\n" +
        "\n" +
        "Do you want to add one more player? (Maximum 7, now 2) [Y/N]\n" +
        "\n" +
        "Stop adding player.\n" +
        "All players loaded, game starts now!\n" +
        "\n" +
        "Turn 1, Doctor Lucky at room 1\n" +
        "Information of the current turn's human player: \n" +
        "Name: bob    Holds: [Empty]\n" +
        "In room [Armory]: 0\n" +
        "\t\tItems within: [Revolver]: attack 3, in #1 room; \n" +
        "\t\tNeighbors: #2 Billiard world.Room, #4 Dining Hall, #5 Drawing world.Room \n" +
        "\t\tPlayer inside: Name: bob    Holds: [Empty]\n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "3. pick item (pick up item in the room)\n" +
        "Please enter the index of the room you want to move to: \n" +
        "Can not move to room 1, not neighbor of current room.\n" +
        "Please enter again: \n" +
        "Successfully moved to room 2.\n" +
        "\n" +
        "Turn 2, Doctor Lucky at room 2\n" +
        "Information of the current turn's human player: \n" +
        "Name: zack    Holds: [Empty]\n" +
        "In room [Billiard world.Room]: 1\n" +
        "\t\tItems within: [Billiard Cue]: attack 2, in #2 room; \n" +
        "\t\tNeighbors: #1 Armory, #4 Dining Hall, #19 Trophy world.Room \n" +
        "\t\tPlayer inside: Name: zack    Holds: [Empty]\n" +
        "Name: bob    Holds: [Empty]\n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "3. pick item (pick up item in the room)\n" +
        "Please enter the index of the room you want to move to: \n" +
        "Can not move to room 2, not neighbor of current room.\n" +
        "Please enter again: \n" +
        "Successfully moved to room 1.\n" +
        "\n" +
        "Turn 3, Doctor Lucky at room 3\n" +
        "Information of the current turn's human player: \n" +
        "Name: bob    Holds: [Empty]\n" +
        "In room [Billiard world.Room]: 1\n" +
        "\t\tItems within: [Billiard Cue]: attack 2, in #2 room; \n" +
        "\t\tNeighbors: #1 Armory, #4 Dining Hall, #19 Trophy world.Room \n" +
        "\t\tPlayer inside: Name: bob    Holds: [Empty]\n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "3. pick item (pick up item in the room)\n" +
        "Exit game, have a nice day~\n";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Test look around.
   */
  @Test
  public void testLookAround() {
    StringReader command = new StringReader("20\nyes\nh\nbob\n1\ny\nh\nzack\n2\nN\nlook around\nlook around\nquit\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "\n" +
        "Let's add players.\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "\n" +
        "One player added successfully!\n" +
        "\n" +
        "A summary of the human player added:\n" +
        "Name: bob\n" +
        "Starting position: 1\n" +
        "\n" +
        "Do you want to add one more player? (Maximum 7, now 1) [Y/N]\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "\n" +
        "One player added successfully!\n" +
        "\n" +
        "A summary of the human player added:\n" +
        "Name: zack\n" +
        "Starting position: 2\n" +
        "\n" +
        "Do you want to add one more player? (Maximum 7, now 2) [Y/N]\n" +
        "\n" +
        "Stop adding player.\n" +
        "All players loaded, game starts now!\n" +
        "\n" +
        "Turn 1, Doctor Lucky at room 1\n" +
        "Information of the current turn's human player: \n" +
        "Name: bob    Holds: [Empty]\n" +
        "In room [Armory]: 1\n" +
        "\t\tItems within: [Revolver]: attack 3, in #1 room; \n" +
        "\t\tNeighbors: #2 Billiard world.Room, #4 Dining Hall, #5 Drawing world.Room \n" +
        "\t\tPlayer inside: Name: bob    Holds: [Empty]\n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "3. pick item (pick up item in the room)\n" +
        "Neighbor rooms' information: \n" +
        "[Billiard world.Room]: 2\n" +
        "\t\tItems within: [Billiard Cue]: attack 2, in #2 room; \n" +
        "\t\tNeighbors: #1 Armory, #4 Dining Hall, #19 Trophy world.Room \n" +
        "\t\tPlayer inside: Name: zack    Holds: [Empty]\n" +
        "\n" +
        "[Dining Hall]: 4\n" +
        "\t\tItems within: [Empty]\n" +
        "\t\tNeighbors: #1 Armory, #2 Billiard world.Room, #5 Drawing world.Room, #9 Kitchen, #15 Parlor, #18 Tennessee world.Room, #19 Trophy world.Room, #20 Wine Cellar \n" +
        "\t\tPlayer inside: [Empty]\n" +
        "\n" +
        "[Drawing world.Room]: 5\n" +
        "\t\tItems within: [Letter Opener]: attack 2, in #5 room; \n" +
        "\t\tNeighbors: #1 Armory, #4 Dining Hall, #6 Foyer, #20 Wine Cellar \n" +
        "\t\tPlayer inside: [Empty]\n" +
        "\n" +
        "\n" +
        "Turn 2, Doctor Lucky at room 2\n" +
        "Information of the current turn's human player: \n" +
        "Name: zack    Holds: [Empty]\n" +
        "In room [Billiard world.Room]: 2\n" +
        "\t\tItems within: [Billiard Cue]: attack 2, in #2 room; \n" +
        "\t\tNeighbors: #1 Armory, #4 Dining Hall, #19 Trophy world.Room \n" +
        "\t\tPlayer inside: Name: zack    Holds: [Empty]\n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "3. pick item (pick up item in the room)\n" +
        "Neighbor rooms' information: \n" +
        "[Armory]: 1\n" +
        "\t\tItems within: [Revolver]: attack 3, in #1 room; \n" +
        "\t\tNeighbors: #2 Billiard world.Room, #4 Dining Hall, #5 Drawing world.Room \n" +
        "\t\tPlayer inside: Name: bob    Holds: [Empty]\n" +
        "\n" +
        "[Dining Hall]: 4\n" +
        "\t\tItems within: [Empty]\n" +
        "\t\tNeighbors: #1 Armory, #2 Billiard world.Room, #5 Drawing world.Room, #9 Kitchen, #15 Parlor, #18 Tennessee world.Room, #19 Trophy world.Room, #20 Wine Cellar \n" +
        "\t\tPlayer inside: [Empty]\n" +
        "\n" +
        "[Trophy world.Room]: 19\n" +
        "\t\tItems within: [Duck Decoy]: attack 3, in #19 room; [Monkey Hand]: attack 2, in #19 room; \n" +
        "\t\tNeighbors: #2 Billiard world.Room, #4 Dining Hall, #11 Library, #18 Tennessee world.Room \n" +
        "\t\tPlayer inside: [Empty]\n" +
        "\n" +
        "\n" +
        "Turn 3, Doctor Lucky at room 3\n" +
        "Information of the current turn's human player: \n" +
        "Name: bob    Holds: [Empty]\n" +
        "In room [Armory]: 1\n" +
        "\t\tItems within: [Revolver]: attack 3, in #1 room; \n" +
        "\t\tNeighbors: #2 Billiard world.Room, #4 Dining Hall, #5 Drawing world.Room \n" +
        "\t\tPlayer inside: Name: bob    Holds: [Empty]\n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "3. pick item (pick up item in the room)\n" +
        "Exit game, have a nice day~\n";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Test pick item: whether show item list properly before and after picking, whether drops item
   * when item count reach maximum.
   */
  @Test
  public void testPickItem() {
    StringReader command = new StringReader("20\nyes\nh\nzack\n1\nn\npick item\n1\nmove" +
        "\n5\npick item\n1\nmove\n6\nmove\n16\npick item\n1\nmove\n8\npick item\n1\n" +
        "move\n7\npick item\n1\nquit\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "What is the max turn of the game?\n" +
        "\n" +
        "Start game by adding a player? Enter y to add player and start, anything else to quit.\n" +
        "\n" +
        "Let's add players.\n" +
        "Do you want to add a human player or a computer player? [H/C] \n" +
        "Enter the name of the player: \n" +
        "Enter the starting position (range from 1 to 21) of the player: \n" +
        "\n" +
        "One player added successfully!\n" +
        "\n" +
        "A summary of the human player added:\n" +
        "Name: zack\n" +
        "Starting position: 1\n" +
        "\n" +
        "Do you want to add one more player? (Maximum 7, now 1) [Y/N]\n" +
        "\n" +
        "Stop adding player.\n" +
        "All players loaded, game starts now!\n" +
        "\n" +
        "Turn 1, Doctor Lucky at room 1\n" +
        "Information of the current turn's human player: \n" +
        "Name: zack    Holds: [Empty]\n" +
        "In room [Armory]: 1\n" +
        "\t\tItems within: [Revolver]: attack 3; \n" +
        "\t\tNeighbors: #2 Billiard world.Room, #4 Dining Hall, #5 Drawing world.Room \n" +
        "\t\tPlayer inside: Name: zack    Holds: [Empty]\n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "3. pick item (pick up item in the room)\n" +
        "Here are available items: 1. [Revolver]: attack 3; \n" +
        "Which one you want to pick, enter index of item: Successfully picked up item.\n" +
        "\n" +
        "Turn 2, Doctor Lucky at room 2\n" +
        "Information of the current turn's human player: \n" +
        "Name: zack    Holds: [Revolver]: attack 3; \n" +
        "In room [Armory]: 1\n" +
        "\t\tItems within: [Empty]\n" +
        "\t\tNeighbors: #2 Billiard world.Room, #4 Dining Hall, #5 Drawing world.Room \n" +
        "\t\tPlayer inside: Name: zack    Holds: [Revolver]: attack 3; \n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "Please enter the index of the room you want to move to: \n" +
        "Successfully moved to room 5.\n" +
        "\n" +
        "Turn 3, Doctor Lucky at room 3\n" +
        "Information of the current turn's human player: \n" +
        "Name: zack    Holds: [Revolver]: attack 3; \n" +
        "In room [Drawing world.Room]: 5\n" +
        "\t\tItems within: [Letter Opener]: attack 2; \n" +
        "\t\tNeighbors: #1 Armory, #4 Dining Hall, #6 Foyer, #20 Wine Cellar \n" +
        "\t\tPlayer inside: Name: zack    Holds: [Revolver]: attack 3; \n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "3. pick item (pick up item in the room)\n" +
        "Here are available items: 1. [Letter Opener]: attack 2; \n" +
        "Which one you want to pick, enter index of item: Successfully picked up item.\n" +
        "\n" +
        "Turn 4, Doctor Lucky at room 4\n" +
        "Information of the current turn's human player: \n" +
        "Name: zack    Holds: [Revolver]: attack 3; [Letter Opener]: attack 2; \n" +
        "In room [Drawing world.Room]: 5\n" +
        "\t\tItems within: [Empty]\n" +
        "\t\tNeighbors: #1 Armory, #4 Dining Hall, #6 Foyer, #20 Wine Cellar \n" +
        "\t\tPlayer inside: Name: zack    Holds: [Revolver]: attack 3; [Letter Opener]: attack 2; \n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "Please enter the index of the room you want to move to: \n" +
        "Successfully moved to room 6.\n" +
        "\n" +
        "Turn 5, Doctor Lucky at room 5\n" +
        "Information of the current turn's human player: \n" +
        "Name: zack    Holds: [Revolver]: attack 3; [Letter Opener]: attack 2; \n" +
        "In room [Foyer]: 6\n" +
        "\t\tItems within: [Empty]\n" +
        "\t\tNeighbors: #5 Drawing world.Room, #16 Piazza \n" +
        "\t\tPlayer inside: Name: zack    Holds: [Revolver]: attack 3; [Letter Opener]: attack 2; \n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "Please enter the index of the room you want to move to: \n" +
        "Successfully moved to room 16.\n" +
        "\n" +
        "Turn 6, Doctor Lucky at room 6\n" +
        "Information of the current turn's human player: \n" +
        "Name: zack    Holds: [Revolver]: attack 3; [Letter Opener]: attack 2; \n" +
        "In room [Piazza]: 16\n" +
        "\t\tItems within: [Civil War Cannon]: attack 3; \n" +
        "\t\tNeighbors: #6 Foyer, #8 Hedge Maze, #21 Winter Garden \n" +
        "\t\tPlayer inside: Name: zack    Holds: [Revolver]: attack 3; [Letter Opener]: attack 2; \n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "3. pick item (pick up item in the room)\n" +
        "Here are available items: 1. [Civil War Cannon]: attack 3; \n" +
        "Which one you want to pick, enter index of item: Successfully picked up item.\n" +
        "\n" +
        "Turn 7, Doctor Lucky at room 7\n" +
        "Information of the current turn's human player: \n" +
        "Name: zack    Holds: [Revolver]: attack 3; [Letter Opener]: attack 2; [Civil War Cannon]: attack 3; \n" +
        "In room [Piazza]: 16\n" +
        "\t\tItems within: [Empty]\n" +
        "\t\tNeighbors: #6 Foyer, #8 Hedge Maze, #21 Winter Garden \n" +
        "\t\tPlayer inside: Name: zack    Holds: [Revolver]: attack 3; [Letter Opener]: attack 2; [Civil War Cannon]: attack 3; \n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "Please enter the index of the room you want to move to: \n" +
        "Successfully moved to room 8.\n" +
        "\n" +
        "Turn 8, Doctor Lucky at room 8\n" +
        "Information of the current turn's human player: \n" +
        "Name: zack    Holds: [Revolver]: attack 3; [Letter Opener]: attack 2; [Civil War Cannon]: attack 3; \n" +
        "In room [Hedge Maze]: 8\n" +
        "\t\tItems within: [Loud Noise]: attack 2; \n" +
        "\t\tNeighbors: #7 Green House, #16 Piazza \n" +
        "\t\tPlayer inside: Name: zack    Holds: [Revolver]: attack 3; [Letter Opener]: attack 2; [Civil War Cannon]: attack 3; \n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "3. pick item (pick up item in the room)\n" +
        "Here are available items: 1. [Loud Noise]: attack 2; \n" +
        "Which one you want to pick, enter index of item: Successfully picked up item.\n" +
        "\n" +
        "Turn 9, Doctor Lucky at room 9\n" +
        "Information of the current turn's human player: \n" +
        "Name: zack    Holds: [Revolver]: attack 3; [Letter Opener]: attack 2; [Civil War Cannon]: attack 3; [Loud Noise]: attack 2; \n" +
        "In room [Hedge Maze]: 8\n" +
        "\t\tItems within: [Empty]\n" +
        "\t\tNeighbors: #7 Green House, #16 Piazza \n" +
        "\t\tPlayer inside: Name: zack    Holds: [Revolver]: attack 3; [Letter Opener]: attack 2; [Civil War Cannon]: attack 3; [Loud Noise]: attack 2; \n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "Please enter the index of the room you want to move to: \n" +
        "Successfully moved to room 7.\n" +
        "\n" +
        "Turn 10, Doctor Lucky at room 10\n" +
        "Information of the current turn's human player: \n" +
        "Name: zack    Holds: [Revolver]: attack 3; [Letter Opener]: attack 2; [Civil War Cannon]: attack 3; [Loud Noise]: attack 2; \n" +
        "In room [Green House]: 7\n" +
        "\t\tItems within: [Trowel]: attack 2; [Pinking Shears]: attack 2; \n" +
        "\t\tNeighbors: #8 Hedge Maze \n" +
        "\t\tPlayer inside: Name: zack    Holds: [Revolver]: attack 3; [Letter Opener]: attack 2; [Civil War Cannon]: attack 3; [Loud Noise]: attack 2; \n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "3. pick item (pick up item in the room)\n" +
        "Here are available items: 1. [Trowel]: attack 2; 2. [Pinking Shears]: attack 2; \n" +
        "Which one you want to pick, enter index of item: Successfully picked up item.\n" +
        "\n" +
        "Turn 11, Doctor Lucky at room 11\n" +
        "Information of the current turn's human player: \n" +
        "Name: zack    Holds: [Trowel]: attack 2; [Letter Opener]: attack 2; [Civil War Cannon]: attack 3; [Loud Noise]: attack 2; \n" +
        "In room [Green House]: 7\n" +
        "\t\tItems within: [Pinking Shears]: attack 2; \n" +
        "\t\tNeighbors: #8 Hedge Maze \n" +
        "\t\tPlayer inside: Name: zack    Holds: [Trowel]: attack 2; [Letter Opener]: attack 2; [Civil War Cannon]: attack 3; [Loud Noise]: attack 2; \n" +
        "\n" +
        "Available options of this turn:\n" +
        "1. look around (show neighbor room information)\n" +
        "2. move (move to a neighbor room)\n" +
        "3. pick item (pick up item in the room)\n" +
        "Exit game, have a nice day~\n";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Test move: move to invalid space.
   */
  @Test
  public void testMove_InvalidMove() {
    StringReader command = new StringReader("20\nyes\nh\nbob\n1\ny\nh\nzack\n2\nN\nmove\n1\n2\nquit\n");
    GameController gameController = new GameController(command, output);
    gameController.startGame(worldModel);

    String expectedOutput = "";
    assertEquals(expectedOutput, output.toString());
  }
//
//  /**
//   * Test move: move to invalid space.
//   */
//  @Test
//  public void testMove_InvalidMove() {
//    StringReader command = new StringReader("20\nyes\nh\nbob\n1\ny\nh\nzack\n2\nN\nmove\n1\n2\nquit\n");
//    GameController gameController = new GameController(command, output);
//    gameController.startGame(worldModel);
//
//    String expectedOutput = "";
//    assertEquals(expectedOutput, output.toString());
//  }

}