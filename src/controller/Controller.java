package controller;

import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

/**
 * The interface for controller specify that the detailed
 * controller would provide a playGame method that take over
 * control and starts game.
 */
public interface Controller {
  /**
   * Play Game.
   */
  void playGame();

  /**
   * Handle right click on map.
   *
   * @param x x coordinate
   * @param y y coordinate
   */
  void handleRightClick(int x, int y);

  /**
   * Handle left click on map.
   *
   * @param x x coordinate
   * @param y y coordinate
   */
  void handleLeftClick(int x, int y);

  /**
   * Handle key pressed.
   *
   * @param key the key pressed
   */
  void handleKeyPress(char key);

  /**
   * Initialize the world through the given file.
   *
   * @param pathToFile path to the world configuration file
   */
  void initializeWorld(String pathToFile);

  /**
   * Add player to the GUI game version.
   *
   * @param name      name of the player
   * @param position  initial position of the player
   * @param capacity  the capacity of the player
   * @param isHuman   whether is human player
   * @return          whether player successfully added
   */
  boolean addPlayerGui(String name, int position, int capacity, boolean isHuman);

  /**
   * Start GUI game.
   */
  void playGameUnderGui();

  /**
   * Set max turn for the game.
   *
   * @param maxTurn maxTurn of the game.
   */
  void setMaxTurn(int maxTurn);

  /**
   * * Return the available command of current turn.
   *
   * @return the available command of current turn.
   */
  Map<String, Function<Scanner, Command>> getAvailableCommand();
}
