package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

/**
 * The {@code World} class represents a virtual game world containing a mansion, 
 * a target, and various rooms. It provides functionality to set up the world 
 * from input data, calculate neighbors between rooms, and draw a map of the world.
 */
public class World implements WorldModel {
  private Target target;
  private Pet pet;
  private Mansion mansion;
  private Queue<Player> playerQueue;
  private RandomNumGenerator randomNumGenerator;


  /**
   * Inner class to help generate random number.
   */
  private class RandomNumGenerator {
    private ArrayList<Integer> numbers;
    private int currentIndex;
    private Random random;

    public RandomNumGenerator(int... numbers) {
      if (numbers.length == 0) {
        random = new Random(10);
        this.currentIndex = -1;
        this.numbers = null;
      } else {
        this.numbers = new ArrayList<>();
        for (int number : numbers) {
          this.numbers.add(number);
        }
        this.currentIndex = 0;
        this.random = null;
      }
    }

    public int getNextNumber(int range) {
      if (random == null) {
        return numbers.get(currentIndex++ % numbers.size()) % range;
      } else {
        return random.nextInt(range);
      }
    }
  }

  /**
   * Sets up the world based on input data from a Readable source.
   *
   * @param input The Readable input source containing world configuration data.
   * @param numbers The numbers to control the computer player.
   * @throws IOException if an I/O error occurs while reading the input.
   */
  public World(Readable input, int... numbers) throws IOException {
    BufferedReader bufferedReader = new BufferedReader((Reader) input);
    StringBuffer stringBuffer = new StringBuffer();
    String line;

    while ((line = bufferedReader.readLine()) != null) {
      stringBuffer.append(line);
      stringBuffer.append('\n');
    }

    parseString(new String(stringBuffer));
    this.playerQueue = new ArrayDeque<>();
    this.randomNumGenerator = new RandomNumGenerator(numbers);
  }

  /**
   * Get the player for this turn.
   *
   * @return The player of this turn.
   */
  @Override
  public Player getTurn() {
    return playerQueue.peek();
  }

  /**
   * Return the information of a specific room.
   *
   * @param roomId The room that its information needed.
   * @return The information of the room.
   */
  public String getRoomInfo(int roomId) {
    return getMansion().getRoomList().get(roomId).toString();
  }

  /**
   * Return the total room count in the mansion, which is to help
   * handle user creating player.
   *
   * @return The total room count.
   */
  public int getRoomCnt() {
    return getMansion().getRoomList().size();
  }

  /**
   * Return the remaining health of target.
   *
   * @return the remaining health of target.
   */
  public int getTargetRemainingHealth() {
    return target.getHealth();
  }

  /**
   * Update the turn, poll the first player to the end of the queue.
   */
  @Override
  public void updateTurn() {
    Player player = playerQueue.poll();
    playerQueue.offer(player);
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
   * Return the command of computer player.
   *
   * @param player The current turn's player.
   * @return The command of computer player.
   */
  public Readable computerPlayerAction(Player player) {
    StringBuilder computerCommand = new StringBuilder();

    if (player.getCurrentRoom() == getTargetPosition()) {
      computerCommand.append("attack\n");
      List<Item> itemList = player.getItemList();
      if (!itemList.isEmpty()) {
        int maxIdx = -1;
        int max = 0;
        int cnt = 0;
        for (Item item : itemList) {
          if (item.getDamage() > max) {
            max = item.getDamage();
            maxIdx = cnt;
          }
          cnt++;
        }
        computerCommand.append(String.valueOf(maxIdx + 1)).append("\n");
      } else {
        return new StringReader(computerCommand.toString());
      }
    }

    int option = 3;
    if (!mansion.getRoomList().get(player.getCurrentRoom()).getItemList().isEmpty()) {
      option += 1;
    }


    int command = randomNumGenerator.getNextNumber(option);

    if (command == 0) {
      computerCommand.append("look around\n");
    } else if (command == 1) {
      computerCommand.append("move\n");
      List<Room> neighborList = mansion.getRoomList().get(player
          .getCurrentRoom()).getNeightborList();
      int max = neighborList.size();
      Room selected = neighborList.get(randomNumGenerator.getNextNumber(max));

      computerCommand.append(selected.getId() + 1).append("\n");
    } else if (command == 2) {
      computerCommand.append("move pet\n");
      int maxMoveOption = mansion.getRoomList().size() - 1;
      computerCommand.append(randomNumGenerator.getNextNumber(maxMoveOption) + 1);
    } else {
      computerCommand.append("pick item\n");
      computerCommand.append("1");
    }
    return new StringReader(computerCommand.toString());
  }

  /**
   * Moves the player to the target room.
   *
   * @param player The player to be moved to the target room.
   * @param targetRoomId The target room's id.
   */
  @Override
  public boolean movePlayer(Player player, int targetRoomId) {
    if (targetRoomId < 0 || targetRoomId >= mansion.getRoomList().size()) {
      return false;
    }
    Room targetRoom = mansion.getRoomList().get(targetRoomId);
    List<Room> neightborList = targetRoom.getNeightborList();

    boolean isNeighbor = false;
    for (Room tmp : neightborList) {
      if (tmp.getId() == player.getCurrentRoom()) {
        isNeighbor = true;
        break;
      }
    }

    if (isNeighbor) {
      mansion.getRoomList().get(player.getCurrentRoom()).deletePlayer(player);
      mansion.getRoomList().get(targetRoomId).addPlayer(player);
      player.move(targetRoomId);

      // turn + 1
      target.move();
      pet.move();
      updateTurn();

      return true;
    } else {
      return false;
    }
  }

  /**
   * Moves the pet to the target room.
   *
   * @param targetRoomId The target room's id.
   */
  @Override
  public boolean movePet(int targetRoomId) {
    if (targetRoomId < 0 || targetRoomId >= mansion.getRoomList().size()) {
      return false;
    }
    pet.move(targetRoomId);

    // turn + 1
    target.move();
    updateTurn();

    return true;
  }

  /**
   * Show a list of available items in the room.
   *
   * @param player The player whose turn it is at the moment.
   * @return The string shows the detailed information of those items.
   */
  public String showItemsInRoom(Player player) {
    List<Item> itemList = mansion.getRoomList().get(player.getCurrentRoom()).getItemList();
    StringBuilder stringBuilder = new StringBuilder();
    if (itemList.isEmpty()) {
      stringBuilder.append("[Empty]");
    } else {
      int cnt = 1;
      for (Item item : itemList) {
        stringBuilder.append(cnt)
                .append(". ")
                .append(item.toString());
        cnt += 1;
      }
    }

    return stringBuilder.toString();
  }

  /**
   * Allow the player to pick up item from the room they currently stay in.
   *
   * @param player  The player that choose to pick up item.
   * @param index    Index of the item to take.
   * @return Whether this command successfully executed.
   */
  public boolean pickUpItem(Player player, int index) {
    List<Item> itemList = mansion.getRoomList().get(player.getCurrentRoom()).getItemList();
    if (index < 0 || index >= itemList.size()) {
      return false;
    }
    player.addItem(itemList.get(index));
    getMansion().getRoomList().get(player.getCurrentRoom()).deleteItem(index);

    // turn + 1
    target.move();
    updateTurn();
    return true;
  }

  /**
   * Displaying information about where a specific player is in the world including
   * what spaces that can be seen from where they are.
   *
   * @param player The player whose turn it is.
   * @return The information to display.
   */
  public String lookAround(Player player) {

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Neighbor rooms' information: \n");
    List<Room> neightborList = getMansion().getRoomList()
        .get(player.getCurrentRoom()).getNeightborList();

    int petLocation = pet.getCurrentRoom();
    for (Room neighborRoom : neightborList) {
      if (petLocation == neighborRoom.getId()) {
        stringBuilder.append(neighborRoom.toStringHideVersion());
      } else {
        stringBuilder.append(neighborRoom.toString());
      }
    }

    // turn + 1
    target.move();
    pet.move();
    updateTurn();

    return stringBuilder.toString();
  }

  /**
   * Show a list of item that player holds.
   *
   * @param player The player whose turn it is at the moment.
   * @return The string shows the detailed information of items holds.
   */
  public String showItemsHold(Player player) {
    List<Item> itemList = player.getItemList();
    StringBuilder stringBuilder = new StringBuilder();
    if (itemList.isEmpty()) {
      stringBuilder.append("[Empty]");
    } else {
      int cnt = 1;
      for (Item item : itemList) {
        stringBuilder.append(cnt)
            .append(". ")
            .append(item.toString());
        cnt += 1;
      }
    }

    return stringBuilder.toString();
  }

  /**
   * Check if the attack can seen bt others.
   *
   * @param currentPlayer the player that initiates an attack.
   * @return if the attack can seen bt others
   */
  public boolean attackCheck(Player currentPlayer) {
    int currentRoomId = currentPlayer.getCurrentRoom();

    // if the pet stay in the exact same room as player
    if (pet.getCurrentRoom() == currentRoomId) {
      Room currentRoom = mansion.getRoomList().get(currentRoomId);
      if (currentRoom.getPlayerList().size() > 1) {
        return true;
      } else {
        return false;
      }
    } else {
      // if the pet does not stay in the same room as player
      List<Room> roomList = mansion.getRoomList().get(currentRoomId).getNeightborList();

      Set<Integer> neighborSet = new HashSet<>();
      for (Room room : roomList) {
        neighborSet.add(room.getId());
      }

      if (playerQueue.stream().anyMatch(p -> neighborSet.contains(p.getCurrentRoom()))) {
        return true;
      }
      return false;
    }
  }

  /**
   * Attack the target with bare hand.
   *
   * @return whether the attack attempt success.
   */
  public boolean attackWithHand() {
    if (attackCheck(getTurn())) {
      return false;
    }

    boolean isGameOver = target.takeAnAttack(1);

    if (! isGameOver) {
      // turn + 1
      target.move();
      pet.move();
      updateTurn();
    }
    return true;
  }

  /**
   * Attack the target with chosen item.
   *
   * @param player    The player that choose to attack.
   * @param index     Index of the item to use.
   * @return          Whether index out of bound and whether seen by others.
   */
  public boolean[] attackWithItem(Player player, int index) {
    List<Item> itemList = player.getItemList();
    if (index < 0 || index >= itemList.size()) {
      return new boolean[]{false, false};
    }
    if (attackCheck(getTurn())) {
      return new boolean[]{true, true};
    }

    Item item = itemList.get(index);
    itemList.remove(index);

    boolean isGameOver = target.takeAnAttack(item.getDamage());

    if (! isGameOver) {
      // turn + 1
      target.move();
      pet.move();
      updateTurn();
    }

    return new boolean[]{true, false};
  }


  /**
   * Get doctor lucky's current position.
   *
   * @return Current position of doctor lucky.
   */
  public int getTargetPosition() {
    return target.getCurrentRoom();
  }

  /**
   * Get the position of pet.
   *
   * @return the position of pet.
   */
  public int getPetPosition() {
    return pet.getCurrentRoom();
  }

  /**
   * Get the name of pet.
   *
   * @return the name of pet.
   */
  public String getPetName() {
    return pet.getName();
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
   * Parses the input information to create the mansion, target, rooms, and items in the world.
   *
   * @param information The input information containing world configuration data.
   */
  private void parseString(String information) {
    String[] parts = information.split("\n");
    // TODO: check if the input follow the format required

    String[] tmp;

    int roomNum = Integer.parseInt(parts[3]);

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
      tmp = parts[4 + i].trim().split("\\s+");
      int[] location = new int[4];

      location[0] = Integer.parseInt(tmp[0]);
      location[1] = Integer.parseInt(tmp[1]);
      location[2] = Integer.parseInt(tmp[2]);
      location[3] = Integer.parseInt(tmp[3]);
      roomList.add(new Room(
          parts[4 + i].substring(11),
          location, i));
    }

    calculateNeighbor(roomList);

    int itemNum = Integer.parseInt(parts[4 + roomNum]);
    int roomNumber;

    for (int i = 0; i < itemNum; i++) {
      tmp = parts[5 + roomNum + i].split("\\s+");

      roomNumber = Integer.parseInt(tmp[0]);

      Room tmpRoom = roomList.get(roomNumber);
      tmpRoom.addItem(new Item(
          parts[5 + roomNum + i].substring(parts[5 + roomNum + i].lastIndexOf(tmp[2])),
          Integer.parseInt(tmp[1]),
          roomNum));
    }

    pet = new Pet(parts[2], 0, depthFirstTraversal(mansion));

  }

  /**
   * Calculate the path for pet to move around.
   */
  private int[] depthFirstTraversal(Mansion mansion) {
    Set<Room> visitedRoom = new HashSet<>();
    Stack<Room> roomStack = new Stack<>();
    int roomCnt = mansion.getRoomList().size();
    int[] routine = new int[roomCnt];

    Room currentRoom = mansion.getRoomList().get(0);
    roomStack.push(currentRoom);
    visitedRoom.add(currentRoom);
    routine[0] = 0;

    while (visitedRoom.size() != roomCnt && roomStack.size() != 0) {
      currentRoom = roomStack.peek();
      boolean isFound = false;
      for (Room room : currentRoom.getNeightborList()) {
        if (!visitedRoom.contains(room)) {
          routine[visitedRoom.size()] = room.getId();
          visitedRoom.add(room);
          roomStack.push(room);
          isFound = true;
          break;
        }
      }

      if (!isFound) {
        roomStack.pop();
      }
    }
    return routine;
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


