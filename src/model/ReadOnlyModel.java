package model;

import java.awt.image.BufferedImage;

/**
 * Read only model for view to pull content.
 */
public interface ReadOnlyModel {

  /**
   * Get the player for this turn.
   *
   * @return The player of this turn.
   */
  Player getCurrentTurnPlayer();

  /**
   * Get the current turn.
   *
   * @return The current turn.
   */
  int getTurn();

  /**
   * Get the max turn.
   *
   * @return The max turn.
   */
  int getMaxTurn();

  /**
   * Get the clicked room's id.
   *
   * @return the room.
   */
  Room getRoom(int x, int y);

  /**
   * Check if the turn is used up.
   *
   * @return True if used up or else false.
   */
  boolean checkTurnUsedUp();

  /**
   * Show a list of available items in the room.
   *
   * @param player The player whose turn it is at the moment.
   * @return The string shows the detailed information of those items.
   */
  String showItemsInRoom(Player player);

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
   * Draws a map of the game world and saves it to an image file.
   *
   * @return bufferedImage The buffered image that could be stored in file.
   */
  BufferedImage drawMap();
}
