package controller;

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
   * Handle map click.
   */
  void handleMapClick(int x, int y);

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

}
