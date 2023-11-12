package model;

import java.awt.image.BufferedImage;

/**
 * The model in MVC pattern. Model provides a series of method
 * to let controller handle and control.
 */
public interface WorldModel {

  /**
   * Get the player for this turn.
   *
   * @return The player of this turn.
   */
  Player getTurn();

  /**
   * Update the turn, poll the first player to the end of the queue.
   */
  void updateTurn();

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
   * Show a list of available items in the room.
   *
   * @param player The player whose turn it is at the moment.
   * @return The string shows the detailed information of those items.
   */
  String showItemsInRoom(Player player);

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
   * Display information of this world.
   *
   * @return Information of this world.
   */
  String toString();

  /**
   * Return the information of a specific room.
   *
   * @param roomId The room that its information needed.
   * @return The information of the room.
   */
  String getRoomInfo(int roomId);

  /**
   * Return the total room count in the mansion, which is to help
   * handle user creating player.
   *
   * @return The total room count.
   */
  int getRoomCnt();

  /**
   * Return the remaining health of target.
   *
   * @return the remaining health of target.
   */
  int getTargetRemainingHealth();

  /**
   * Get doctor lucky's current position.
   *
   * @return Current position of doctor lucky.
   */
  int getTargetPosition();

  /**
   * Get doctor pet's current position.
   *
   * @return Current position of pet.
   */
  int getPetPosition();

  /**
   * Get doctor pet's name.
   *
   * @return Name of the pet.
   */
  String getPetName();

  /**
   * Return the command of computer player.
   *
   * @param player The computer player of this turn.
   * @return The command of computer player.
   */
  Readable computerPlayerAction(Player player);

  /**
   * Draws a map of the game world and saves it to an image file.
   *
   * @param outputFilePath The path and filename where the map image should be saved.
   * @return bufferedImage The buffered image that could be stored in file.
   */
  BufferedImage draw(String outputFilePath);
}
