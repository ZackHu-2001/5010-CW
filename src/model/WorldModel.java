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
  public Player getTurn();

  /**
   * Update the turn, poll the first player to the end of the queue.
   */
  public void updateTurn();

  /**
   * Add a new player to this game.
   *
   * @param name Name of the player.
   * @param currentRoom Room that the player stay at the beginning of the game.
   * @param isHuman Flag shows whether the player is human.
   * @return The created player.
   */
  public Player addPlayer(String name, int currentRoom, boolean isHuman);

  /**
   * Moves the player to the target room.
   *
   * @param player The player to be moved to the target room.
   * @param targetRoomId The target room's id.
   * @return Return whether the command executed correctly.
   */
  public boolean movePlayer(Player player, int targetRoomId);

  /**
   * Moves the pet to the target room.
   *
   * @param targetRoomId The target room's id.
   */
  public boolean movePet(int targetRoomId);

  /**
   * Show a list of available items in the room.
   *
   * @param player The player whose turn it is at the moment.
   * @return The string shows the detailed information of those items.
   */
  public String showItems(Player player);

  /**
   * Allow the player to pick up item from the room they currently stay in.
   *
   * @param player  The player that choose to pick up item.
   * @param index    Index of the item to take.
   * @return Whether this command successfully executed.
   */
  public boolean pickUpItem(Player player, int index);

  /**
   * Displaying information about where a specific player is in the world including
   * what spaces that can be seen from where they are.
   *
   * @param player The player whose turn it is.
   * @return The information to display.
   */
  public String lookAround(Player player);

  /**
   * Display information of this world.
   *
   * @return Information of this world.
   */
  public String toString();

  /**
   * Return the information of a specific room.
   *
   * @param roomId The room that its information needed.
   * @return The information of the room.
   */
  public String getRoomInfo(int roomId);

  /**
   * Return the total room count in the mansion, which is to help
   * handle user creating player.
   *
   * @return The total room count.
   */
  public int getRoomCnt();

  /**
   * Get doctor lucky's current position.
   *
   * @return Current position of doctor lucky.
   */
  public int getTargetPosition();

  /**
   * Get doctor pet's current position.
   *
   * @return Current position of pet.
   */
  public int getPetPosition();

  /**
   * Return the command of computer player.
   *
   * @param player The computer player of this turn.
   * @return The command of computer player.
   */
  public Readable computerPlayerAction(Player player);

  /**
   * Draws a map of the game world and saves it to an image file.
   *
   * @param outputFilePath The path and filename where the map image should be saved.
   * @return bufferedImage The buffered image that could be stored in file.
   */
  public BufferedImage draw(String outputFilePath);
}
