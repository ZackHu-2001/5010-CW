package model;

public interface WorldModel {

  /**
   * Get the player for this turn.
   *
   * @return The player of this turn.
   */
  public Player getTurn();

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
   */
  public void movePlayer(Player player, int targetRoomId);

  /**
   * Show a list of available items in the room.
   *
   * @param player The player whose turn it is at the moment.
   */
  public String showItems(Player player);

  /**
   * Allow the player to pick up item from the room they currently stay in.
   *
   * @param player  The player that choose to pick up item.
   * @param itemName    Name of the item to take.
   */
  public void pickUpItem(Player player, String itemName);


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
   * Return whether game ends.
   *
   * @return Whether game ends.
   */
  public boolean isGameOver();

}
