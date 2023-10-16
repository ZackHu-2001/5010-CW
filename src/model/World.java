package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * The {@code World} class represents a virtual game world containing a mansion, 
 * a target, and various rooms. It provides functionality to set up the world 
 * from input data, calculate neighbors between rooms, and draw a map of the world.
 */
public class World implements WorldModel{
  private Target target;
  private Mansion mansion;
  private final int maxTurn;
  private int currentTurn;
  private Queue<Player> playerQueue;


  /**
   * Sets up the world based on input data from a Readable source.
   *
   * @param input The Readable input source containing world configuration data.
   * @throws IOException if an I/O error occurs while reading the input.
   */
  public World(Readable input, int maxTurn) throws IOException {
    BufferedReader bufferedReader = new BufferedReader((Reader) input);
    StringBuffer stringBuffer = new StringBuffer();
    String line;

    while ((line = bufferedReader.readLine()) != null) {
      stringBuffer.append(line);
      stringBuffer.append('\n');
    }

    parseString(new String(stringBuffer));
    this.maxTurn = maxTurn;
    this.currentTurn = 0;
  }

  /**
   * Get the player for this turn.
   *
   * @return The player of this turn.
   */
  @Override
  public Player getTurn() {
    Player player = playerQueue.poll();
    playerQueue.offer(player);
    return player;
  }

  /**
   * Add a new human player to this game.
   *
   * @param name Name of the human player.
   * @param currentRoom Room that the player stay at the beginning of the game.
   * @return The created player.
   */
  @Override
  public Player addPlayer(String name, int currentRoom, boolean isHuman) {
    Player player = new Player(name, currentRoom, isHuman);
    playerQueue.add(player);
    mansion.getRoomList().get(currentRoom).addPlayer(player);
    return player;
  }


  /**
   * Moves the player to the target room.
   *
   * @param player The player to be moved to the target room.
   * @param targetRoomId The target room's id.
   */
  @Override
  public void movePlayer(Player player, int targetRoomId) {
    Room targetRoom = mansion.getRoomList().get(targetRoomId);
    List<Room> neightborList = targetRoom.getNeightborList();
    
    boolean isNeighbor = false;
    for (Room tmp: neightborList) {
      if (tmp.getId() == player.getCurrentRoom()) {
        isNeighbor = true;
        break;
      }
    }

    if (isNeighbor) {
      mansion.getRoomList().get(player.getCurrentRoom()).deletePlayer(player);
      mansion.getRoomList().get(targetRoomId).addPlayer(player);
      player.move(targetRoomId);
    } else {
      throw new IllegalStateException("Invalid move to room " + targetRoomId);
    }

    // turn + 1
    target.move();
  }

  /**
   * Show a list of available items in the room.
   *
   * @param player    The player whose turn it is at the moment.
   */
  public String showItems(Player player) {
    List<Item> itemList = mansion.getRoomList().get(player.getCurrentRoom()).getItemList();
    StringBuilder stringBuilder = new StringBuilder();
    for (Item item: itemList) {
      stringBuilder.append(item.toString());
    }
    return stringBuilder.toString();
  }

  /**
   * Allow the player to pick up item from the room they currently stay in.
   *
   * @param player  The player that choose to pick up item.
   * @param itemName    Name of the item to take.
   */
  public void pickUpItem(Player player, String itemName) {
    List<Item> itemList = mansion.getRoomList().get(player.getCurrentRoom()).getItemList();
    for (Item item: itemList) {
      if (item.getName().equals(itemName)) {
        player.addItem(item);
      }
    }

    // turn + 1
    target.move();
  }

  /**
   * Displaying information about where a specific player is in the world including
   * what spaces that can be seen from where they are.
   *
   * @param player The player whose turn it is.
   * @return The information to display.
   */
  public String lookAround(Player player) {
    // turn + 1
    target.move();

    return getMansion().getRoomList().get(player.getCurrentRoom()).toString();
//    StringBuilder stringBuilder = new StringBuilder();
//    stringBuilder.append("Currently player in room ")
//            .append(player.getCurrentRoom()).append("\n");
//    stringBuilder.append(getMansion().getRoomList().get())
  }

  /**
   * Gets the mansion included in the world.
   *
   * @return The mansion object.
   */
  public Mansion getMansion() {
    return mansion;
  }

  /**
   * Gets the target of the world.
   *
   * @return The target object.
   */
  public Target getTarget() {
    return target;
  }

  /**
   * Display information of this world.
   *
   * @return Information of this world.
   */
  public String toString() {
    return this.mansion.toString();
  }

  /**
   * Return whether game ends.
   *
   * @return Whether game ends.
   */
  public boolean isGameOver() {
    return currentTurn >= maxTurn;
  }

  /**
   * Parses the input information to create the mansion, target, rooms, and items in the world.
   *
   * @param information The input information containing world configuration data.
   */
  private void parseString(String information) {
    String[] parts = information.split("\n");
    // TODO: check if the input follow the format required

    String[] tmp;

    int roomNum = Integer.parseInt(parts[2]);

    tmp = (parts[1]).split(" ");
    target = new Target(Integer.parseInt(tmp[0]),
        parts[1].substring(parts[1].lastIndexOf(tmp[1])),
        roomNum);

    List<Room> roomList = new ArrayList<Room>();

    tmp = (parts[0]).split(" ");
    mansion = new Mansion(
        Integer.parseInt(parts[0].split(" ")[0]),
        Integer.parseInt(parts[0].split(" ")[1]),
        parts[0].substring(parts[0].lastIndexOf(tmp[2])),
        roomList);

    for (int i = 0; i < roomNum; i++) {
      tmp = parts[3 + i].trim().split("\\s+");
      int[] location = new int[4];

      location[0] = Integer.parseInt(tmp[0]);
      location[1] = Integer.parseInt(tmp[1]);
      location[2] = Integer.parseInt(tmp[2]);
      location[3] = Integer.parseInt(tmp[3]);
      roomList.add(new Room(
          parts[3 + i].substring(11),
          location, i));
    }

    calculateNeighbor(roomList);

    int itemNum = Integer.parseInt(parts[3 + roomNum]);
    int roomNumber;

    for (int i = 0; i < itemNum; i++) {
      tmp = parts[4 + roomNum + i].split("\\s+");

      roomNumber = Integer.parseInt(tmp[0]);

      Room tmpRoom = roomList.get(roomNumber);
      tmpRoom.addItem(new Item(
          parts[4 + roomNum + i].substring(parts[4 + roomNum + i].lastIndexOf(tmp[2])),
          Integer.parseInt(tmp[1]),
          roomNumber,
          roomNum));
    }
  }

  /**
   * Calculates neighbor relationships between rooms and updates their neighbor lists accordingly.
   *
   * @param roomList The list of rooms in the world.
   */
  private void calculateNeighbor(List<Room> roomList) {
    for (int i = 0; i < roomList.size(); i++) {
      for (int j = i + 1; j < roomList.size(); j++) {
        Room a = roomList.get(i);
        Room b = roomList.get(j);
        if (isNeighbor(a, b)) {
          a.addNeighbor(b);
          b.addNeighbor(a);
        }
      }
    }
  }

  /**
   * Checks if two rooms are neighbors based on their locations.
   *
   * @param a The first room.
   * @param b The second room.
   * @return {@code true} if the rooms are neighbors; {@code false} otherwise.
   */
  public static boolean isNeighbor(Room a, Room b) {
    int[] locationA = a.getLocation();
    int[] locationB = b.getLocation();
    if (locationA[0] == locationB[2] + 1 
        || locationA[2] == locationB[0] - 1) {
      if (locationA[1] >= locationB[3] + 1 
          || locationA[3] <= locationB[1] - 1) {
        return false;
      }
      return true;
    } else if (locationA[1] == locationB[3] + 1
        || locationA[3] == locationB[1] - 1) {
      if (locationA[0] >= locationB[2] + 1
          || locationA[2] <= locationB[0] - 1) {
        return false;
      }
      return true;
    } else {
      return false;
    }
  }

  /**
   * Draws a map of the game world and saves it to an image file.
   *
   * @param outputFilePath The path and filename where the map image should be saved.
   * @return bufferedImage The buffered image that could be stored in file.
   */
  public BufferedImage draw(String outputFilePath) {
    BufferedImage bufferedImage = new BufferedImage(
        (mansion.getColumn() + 2) * 30, (mansion.getRow() + 2) * 30,
        BufferedImage.TYPE_INT_RGB);

    Graphics2D graphics2D = bufferedImage.createGraphics();

    graphics2D.setColor(Color.white);
    graphics2D.fillRect(0, 0, (mansion.getColumn() + 2) * 30, (mansion.getRow() + 2) * 30);

    BasicStroke stroke = new BasicStroke(3.0f);
    Font font = new Font("Arial", Font.BOLD, 15);

    graphics2D.setColor(Color.black);
    graphics2D.setStroke(stroke);
    graphics2D.setFont(font);

    graphics2D.drawRect(30, 30, mansion.getColumn() * 30, mansion.getRow() * 30);

    for (Room room : mansion.getRoomList()) {
      int[] location = room.getLocation();
      int height = (location[2] - location[0] + 1) * 30;
      int width = (location[3] - location[1] + 1) * 30;

      graphics2D.drawRect((location[1] + 1) * 30, (location[0] + 1) * 30, width, height);
      graphics2D.drawString(room.getName(), (location[1] + 1) * 30 + 15,
          (location[0] + 1) * 30 + 20);
    }
    graphics2D.dispose();

    return bufferedImage;
  }
}


