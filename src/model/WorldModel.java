package model;

import java.io.IOException;

public interface WorldModel {
  public void setUp(Readable readable) throws IOException;

  public Player getTurn();

  /**
   * Add a new human player to this game.
   *
   * @param name Name of the human player.
   * @param currentRoom Room that the player stay at the beginning of the game.
   * @return The created player.
   */
  public Player addHumanPlayer(String name, int currentRoom);

  /**
   * Add a new AI player to this game.
   *
   * @param name Name of the AI player.
   * @param currentRoom Room that the player stay at the beginning of the game.
   * @return The created player.
   */
  public Player addAIPlayer(String name, int currentRoom);

  /**
   * Moves the player to the target room.
   *
   * @param player The player to be moved to the target room.
   * @param targetRoomId The target room's id.
   */
  public void movePlayer(Player player, int targetRoomId);

  /**
   * Show a list of available items in the room.
   *
   * @param player The player whose turn it is at the moment.
   */
  public String showItems(Player player);

  public void pickUpItem(Player player, String itemName);
}
