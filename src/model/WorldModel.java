package model;

import java.util.Map;

/**
 * The model in MVC pattern. Model provides a series of method
 * to let controller handle and control.
 */
public interface WorldModel extends ReadOnlyModel {

  /**
   * Initialize the world based on the input world configuration.
   *
   * @param pathToFile The path to the file that contains the world configuration.
   */
  public void initializeWorld(String pathToFile);

  /**
   * Update the turn, poll the first player to the end of the queue.
   */
  void updateTurn();

  /**
   * Set the max turn of the game.
   *
   * @param maxTurn The max turn number of this game.
   */
  void setMaxTurn(int maxTurn);

  /**
   * Add a new player to this game.
   *
   * @param name Name of the player.
   * @param currentRoom Room that the player stay at the beginning of the game.
   * @param isHuman Flag shows whether the player is human.
   * @return The created player.
   */
  Player addPlayer(String name, int currentRoom, boolean isHuman);

  /**
   * Moves the player to the target room.
   *
   * @param player The player to be moved to the target room.
   * @param targetRoomId The target room's id.
   * @return Return whether the command executed correctly.
   */
  boolean movePlayer(Player player, int targetRoomId);

  /**
   * Moves the pet to the target room.
   *
   * @param targetRoomId  The target room's id.
   * @return              return whether room id is valid.
   */
  boolean movePet(int targetRoomId);

  /**
   * Allow the player to pick up item from the room they currently stay in.
   *
   * @param player  The player that choose to pick up item.
   * @param index    Index of the item to take.
   * @return Whether this command successfully executed.
   */
  boolean pickUpItem(Player player, int index);

  /**
   * Displaying information about where a specific player is in the world including
   * what spaces that can be seen from where they are.
   *
   * @param player The player whose turn it is.
   * @return The information to display.
   */
  String lookAround(Player player);

  /**
   * Show a list of item that player holds.
   *
   * @param player The player whose turn it is at the moment.
   * @return The string shows the detailed information of items holds.
   */
  String showItemsHold(Player player);

  /**
   * Attack the target with bare hand.
   *
   * @return whether the attack attempt success.
   */
  public boolean attackWithHand();

  /**
   * Attack the target with chosen item.
   *
   * @param player    The player that choose to attack.
   * @param index     Index of the item to use.
   * @return Whether index out of bound and whether seen by others.
   */
  boolean[] attackWithItem(Player player, int index);

  /**
   * Return the command of computer player.
   *
   * @param player The computer player of this turn.
   * @return The command of computer player.
   */
  Readable computerPlayerAction(Player player);

  /**
   * Start the game.
   */
  void startGame();

  /**
   * Find the player using name, and return the description of that player.
   *
   * @param name  name of the player
   * @return      description of the player
   */
  String getPlayerDescription(String name);

  /**
   * Return the mansion that contains rooms.
   *
   * @return the mansion
   */
  Mansion getMansion();

  /**
   * Get the item with the highest damage.
   *
   * @return the String, Integer pair that represents the name
   *          and index of the item with the highest damage.
   */
  Map<String, Integer> getHighestDamageItem();

  /**
   * Ends the game, set isGameOver flag to false.
   */
  void gameOver();

}
