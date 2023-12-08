package controller;

import controller.command.Attack;
import controller.command.LookAround;
import controller.command.MovePet;
import controller.command.MovePlayer;
import controller.command.PickItem;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import javax.imageio.ImageIO;
import model.Player;
import model.Room;
import model.World;
import model.WorldModel;
import view.NullView;
import view.View;

/**
 * This is the controller in MVC pattern. Once the program runs,
 * it will take over control from the main method, give prompts
 * and handle user input.
 */
public class GameController implements Controller {

  private WorldModel model;
  private View view;
  private Scanner scan;
  private Scanner humanInputScan;
  private Appendable out;
  private boolean isCMD;
  private final String reEnterPrompt = "Please enter again: ";
  private Map<String, Function<Scanner, Command>> knownCommands = new HashMap<>();

  /**
   * Game controller, control over the flow of the game.
   */
  public GameController(WorldModel model, View view) {
    // validate world model
    if (model == null) {
      throw new IllegalArgumentException("Invalid model.");
    }

    // validate view
    if (view == null) {
      throw new IllegalArgumentException("Invalid view.");
    }

    this.model = model;
    this.view = view;

    knownCommands.put("look around", s -> new LookAround(out));
    knownCommands.put("move", s -> new MovePlayer(scan, out));
    knownCommands.put("move pet", s -> new MovePet(scan, out));
    knownCommands.put("attack", s -> new Attack(scan, out));
    knownCommands.put("pick item", s -> new PickItem(scan, out));

    if (this.view instanceof NullView) {

//    if (in == null || out == null) {
//    throw new IllegalArgumentException("Readable and Appendable can't be null");
//  }
//    this.out = out;
//    this.humanInputScan = new Scanner(in);
      this.out = System.out;
      this.humanInputScan = new Scanner(System.in);
      this.scan = humanInputScan;
      this.isCMD = true;
    } else {
      view.connect(this);
      view.makeVisible();
      view.refresh();
    }
  }

  @Override
  public void setMaxTurn(int maxTurn) {
    model.setMaxTurn(maxTurn);
  }

  /**
   * Add players, whether human player or computer player.
   *
   * @return Whether user wants to quit.
   */
  private boolean addPlayerCMD() {
    String next;
    try {
      // let the user decides whether create a human player or computer player
      boolean isHuman = true;
      out.append("Do you want to add a human player or a computer player? [H/C] \n");
      while (scan.hasNextLine()) {
        next = scan.nextLine();
        if (quitCheck(next)) {
          return true;
        }
        if ("H".equalsIgnoreCase(next) || "human".equalsIgnoreCase(next)) {
          isHuman = true;
          break;
        } else if ("C".equalsIgnoreCase(next) || "computer".equalsIgnoreCase(next)) {
          isHuman = false;
          break;
        } else {
          out.append("Undetectable input: ")
              .append(next)
              .append("\n")
              .append(reEnterPrompt);
        }
      }

      // read in the name of the player
      out.append("Enter the name of the player: \n");
      final String name;
      if (scan.hasNextLine()) {
        name = scan.nextLine();
      } else {
        return true;
      }
      int position = 0;

      // read in starting position of the player
      int roomCnt = model.getRoomCnt();
      out.append("Enter the starting position (range from 1 to ")
          .append(String.valueOf(roomCnt))
          .append(") of the player: \n");

      while (scan.hasNextLine()) {
        next = scan.nextLine();
        if (quitCheck(next)) {
          return true;
        }
        try {
          position = Integer.valueOf(next);
          if (position <= roomCnt && position >= 1) {
            break;
          } else {
            out.append("Invalid position: ")
                .append(String.valueOf(position))
                .append(", should be in range 1 to ")
                .append(String.valueOf(roomCnt))
                .append(". \n")
                .append(reEnterPrompt);
          }
        } catch (NumberFormatException nfe) {
          out.append("Invalid position: ")
              .append(next)
              .append(", should be integer. ")
              .append("\n")
              .append(reEnterPrompt);
        }
      }
      position -= 1;

      // add player to the game
      model.addPlayer(name, position, isHuman);
      out.append("\nOne player added successfully!\n")
          .append("\nA summary of the ");
      if (isHuman) {
        out.append("human ");
      } else {
        out.append("computer ");
      }
      out.append("player added:\n")
          .append("Name: ")
          .append(name)
          .append("\nStarting position: ")
          .append(String.valueOf(position + 1))
          .append("\n\n");

      return false;
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  @Override
  public boolean addPlayerGUI(String name, int position, int capacity, boolean isHuman) {

      // add player to the game
      model.addPlayer(name, position - 1, isHuman);

      view.refresh();

      return true;
  }

  /**
   * Update current turn.
   *
   * @return Whether turn runs up and game should exit.
   */
  private boolean checkTurn() {
    if (model.checkTurnUsedUp()) {
        System.out.append("Maximum turn reached! Doctor lucky escaped!");
        if (!(view instanceof NullView)) {
          view.gameOverHint("Maximum turn reached! Doctor lucky escaped!");
        }
        return true;
    } else if (model.getTargetRemainingHealth() <= 0) {
      StringBuilder prompt = new StringBuilder();
      prompt.append("Target killed!\n")
          .append("Player ")
          .append(model.getCurrentTurnPlayer().getName())
          .append(" win the game!");
      System.out.println(prompt.toString());
      if (!(view instanceof NullView)) {
        view.gameOverHint(prompt.toString());
      }
      return true;
    } else {
      return false;
    }
  }

  /**
   * Save map when exit the game.
   */
  private void saveMap() {
    BufferedImage bufferedImage = model.drawMap();
    try {
      File outputFile = new File("map.png");
      ImageIO.write(bufferedImage, "png", outputFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Print current turn's player, and the items that the player holds.
   * Also displays the information of the room that the player current inside.
   */
  private void printTurnInfo() {
    try {
      out.append("\nTurn ")
          .append(String.valueOf(model.getTurn() + 1))
          .append(": Doctor Lucky[")
          .append(String.valueOf(model.getTargetRemainingHealth()))
          .append("] at room ")
          .append(String.valueOf(model.getTargetPosition() + 1))
          .append(", ")
          .append(model.getPetName())
          .append(" at room ")
          .append(String.valueOf(model.getPetPosition() + 1));

      Player player = model.getCurrentTurnPlayer();
      if (player.isHuman()) {
        scan = humanInputScan;
        out.append("\nInformation of the current turn's human player: \n");
      } else {
        Readable computerInput = model.computerPlayerAction(player);
        scan = new Scanner(computerInput);
        out.append("\nInformation of the current turn's computer player: \n");
      }
      out.append(player.toString())
          .append("In room ")
          .append(model.getRoomInfo(player.getCurrentRoom()));

      out.append("Available options of this turn:\n")
          .append("1. look around (show neighbor room information)\n")
          .append("2. move (move to a neighbor room)\n")
          .append("3. move pet (move pet to a specific room)\n");

      int cnt = 4;
      if (!model.showItemsInRoom(player).equals("[Empty]")) {
        out.append(String.valueOf(cnt))
            .append(". pick item (pick up item in the room)\n");
        cnt += 1;
      }

      if (player.getCurrentRoom() == model.getTargetPosition()) {
        out.append(String.valueOf(cnt))
            .append(". attack (attack the target)\n");
      }
      out.append("\n");

    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }


  /**
   * The purpose of this method is to make sure that, when player have no item
   * then the command pick item should be temporarily removed from the options.
   *
   * @param worldModel Would model to tell if player have item.
   * @param knownCommands Known command map to be modified.
   */
  private void upDateCommands(WorldModel worldModel, Map<String,
      Function<Scanner, Command>> knownCommands) {
    if (worldModel.showItemsInRoom(worldModel.getCurrentTurnPlayer()).equals("[Empty]")) {
      knownCommands.remove("pick item");
    } else {
      knownCommands.put("pick item", s -> new PickItem(scan, out));
    }
    if (worldModel.getCurrentTurnPlayer().getCurrentRoom() == worldModel.getTargetPosition()) {
      knownCommands.put("attack", s -> new Attack(scan, out));
    } else {
      knownCommands.remove("attack");
    }
  }

  /**
   * Check if user input quit or q, if so, return true to tell caller
   * to exit the game.
   *
   * @param input User input.
   * @return Whether user input quit.
   */
  private boolean quitCheck(String input) {
    if ("q".equalsIgnoreCase(input) || "quit".equalsIgnoreCase(input)) {
      try {
        out.append("Exit game, have a nice day~\n");
        return true;
      } catch (IOException ioe) {
        throw new IllegalStateException("Append failed", ioe);
      }
    } else {
      return false;
    }
  }

  public void playGameUnderCMD() {

    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;

    // read user input: the mansion file, or direct string input
    Reader fileReader;
    String pathToFile;

    System.out.println("Please input path to the file: ");
    pathToFile = new Scanner(System.in).nextLine();
    model.initializeWorld(pathToFile);

    try {
      fileReader = new FileReader(pathToFile);
      WorldModel worldModel = new World();

      System.out.println("Successfully read in the file!\n\n"
          + "Here are detailed information:");

      output.append(worldModel.toString());

      // read in max turn
      int maxTurn = 0;
      String inputString;
      Scanner scan = new Scanner(System.in);

      output.append("What is the max turn of the game?\n");
      while (scan.hasNextLine()) {
        inputString = scan.nextLine();
        if ("quit".equalsIgnoreCase(inputString)
            || "q".equalsIgnoreCase(inputString)) {
          output.append("Exit game, have a nice day~\n");
          return;
        }
        try {
          maxTurn = Integer.valueOf(inputString);
          if (maxTurn <= 0) {
            output.append("Invalid max turn, one positive integer expected.\n");
            continue;
          }
          break;
        } catch (NumberFormatException nfe) {
          output.append("Invalid max turn, one positive integer expected.\n")
              .append("Please enter again:");
        }
      }
      setMaxTurn(maxTurn);
//      GameController gameController = new GameController(, );
//      gameController.playGame();

    } catch (IOException e) {
      System.out.println("There are problems with path to file, exit now.");
      System.exit(1);
    }

    // add player
    try {
      String inputLine;

      // auto save map
      saveMap();
      out.append("The graphical representation of the world is saved to map.png.\n");

      out.append("\nStart game by adding a player? "
          + "Enter y to add player and start, anything else to quit.\n");
      if (scan.hasNextLine()) {
        inputLine = scan.nextLine();
        if (quitCheck(inputLine)) {
          return;
        }
        if ("y".equalsIgnoreCase(inputLine) || "yes".equalsIgnoreCase(inputLine)) {
          out.append("\nLet's add players.\n");
          if (addPlayerCMD()) {
            return;
          }
        } else {
          out.append("Exit game, have a nice day~\n");
          return;
        }
      }

      // allow for 7 players in total
      for (int i = 0; i < 6; i++) {
        out.append("Do you want to add one more player? (Maximum 7, now ")
            .append(String.valueOf(i + 1))
            .append(") [Y/N]\n");
        if (scan.hasNextLine()) {
          inputLine = scan.nextLine();
          if (quitCheck(inputLine)) {
            return;
          }
          if ("y".equalsIgnoreCase(inputLine) || "yes".equalsIgnoreCase(inputLine)) {
            if (addPlayerCMD()) {
              return;
            }
          } else {
            out.append("\nStop adding player.\n");
            break;
          }
        } else {
          out.append("\nStop adding player.\n");
          break;
        }
      }

      // initialize all commands
      Map<String, Function<Scanner, Command>> knownCommands = new HashMap<>();
      knownCommands.put("look around", s -> new LookAround(out));
      knownCommands.put("move", s -> new MovePlayer(scan, out));
      knownCommands.put("move pet", s -> new MovePet(scan, out));
      knownCommands.put("attack", s -> new Attack(scan, out));

      out.append("All players loaded, game starts now!\n");

      // update turn returns whether turn runs up
      if (checkTurn()) {
        // return if turn runs up
        return;
      }

      printTurnInfo();
      upDateCommands(model, knownCommands);

      // handle user input command
      while (scan.hasNextLine()) {
        Command command;
        String in = scan.nextLine();

        if (quitCheck(in)) {
          return;
        }

        Function<Scanner, Command> cmd = knownCommands.getOrDefault(in, null);
        if (cmd == null) {
          out.append("Invalid input. Commands like [look around], [move], [pick item] expected.\n");
          out.append(reEnterPrompt);
          continue;
        } else {
          command = cmd.apply(scan);
          command.act(model);
        }

        // update turn returns whether turn runs up
        if (checkTurn()) {
          // return if turn runs up
          return;
        }
        printTurnInfo();
        upDateCommands(model, knownCommands);
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  public void playGame() {
    if (isCMD) {
      playGameUnderCMD();
    }
  }

  @Override
  public void handleRightClick(int x, int y) {
    if (model.getTurn() == -1 || model.isGameOver()) {
      return;
    }
    Room room = model.getRoom(x, y);
    if (room == null) {
      System.out.println("You can not move to this place!");
      return;
    }
    System.out.println("You right clicked room " + room.getName() + ".");

    Player player = model.getCurrentTurnPlayer();
    StringBuilder stringBuilder = new StringBuilder();
    if (!model.movePlayer(player, room.getId())) {
      stringBuilder.append("\nCan not move to room ")
          .append(room.getName())
          .append(", not neighbor of current room.\nPlease select again: ");
      System.out.println(stringBuilder.toString());
      return;
    } else {
      stringBuilder.append("\nSuccessfully moved to room ")
          .append(room.getName()).append("!");
      System.out.println(stringBuilder.toString());
      view.refresh();
      if (checkTurn()) {
        model.gameOver();
        return;
      }
      printTurnInfo();
      upDateCommands(model, knownCommands);
    }
  }

  @Override
  public void handleKeyPress(char key) {
    if (model.getTurn() == -1 || model.isGameOver()) {
      return;
    }

    Function<Scanner, Command> cmd;
    StringBuilder output;

    Map<String, Integer> highestDamageItem;
    String itemName = "";
    int itemIdx = 0;

    switch (key) {
      case 'n':
        break;

      case 'l':
        System.out.println("You choose to look around.");
        System.out.println(model.lookAround(model.getCurrentTurnPlayer()));
        view.refresh();
        if (checkTurn()) {
          model.gameOver();
          return;
        }
        printTurnInfo();
        upDateCommands(model, knownCommands);
        break;

      case 'p':
        cmd = knownCommands.getOrDefault("pick item", null);
        if (cmd == null) {
          return;
        }
        output = new StringBuilder()
            .append("Here are available items: ")
            .append(model.showItemsHold(model.getCurrentTurnPlayer()))
            .append("\nYou picked the item with the highest attack among all the items: ");

        highestDamageItem = model.getHighestDamageItem();

        for (Map.Entry<String, Integer> entry : highestDamageItem.entrySet()) {
          itemName = entry.getKey();
          itemIdx = entry.getValue();
        }
        output.append(itemName);
        model.pickUpItem(model.getCurrentTurnPlayer(), itemIdx);

        System.out.println(output.toString());
        view.refresh();
        if (checkTurn()) {
          model.gameOver();
          return;
        }
        printTurnInfo();
        upDateCommands(model, knownCommands);
        break;

      case 'a':
        cmd = knownCommands.getOrDefault("attack", null);
        if (cmd == null) {
          return;
        }
        output = new StringBuilder();
        String items = model.showItemsHold(model.getCurrentTurnPlayer());
        if ("[Empty]".equals(items)) {
          output.append("\nYou hold no item, so you poke him in the eye.\n");

          boolean isSuccess = model.attackWithHand();
          if (!isSuccess) {
            output.append("Oops! Your attack was seen by others, attack failed.\n");
            return;
          }

          output.append("Attack success! Target's remaining health: ")
              .append(String.valueOf(model.getTargetRemainingHealth()))
              .append("\n");
          System.out.println(output.toString());
          return;
        } else  {
          output.append("\nHere are the items you hold: ")
              .append(items)
              .append("\nYou used the item with the highest attack: ");
          highestDamageItem = model.getHighestDamageItem();
          for (Map.Entry<String, Integer> entry : highestDamageItem.entrySet()) {
            itemName = entry.getKey();
            itemIdx = entry.getValue();
          }
          output.append(itemName);

          boolean[] result = model.attackWithItem(model.getCurrentTurnPlayer(), itemIdx);
          if (result[0]) {
            if (result[1]) {
              output.append("Oops! Your attack was seen by other, attack failed.\n");
              return;
            }
            output.append("Attack success! Target's remaining health: ")
                .append(String.valueOf(model.getTargetRemainingHealth()))
                .append("\n");
            break;
          } else {
            output.append("Index out of bound.\n")
                .append("Please enter again: ");
          }
          System.out.println(output);
        }

        view.refresh();
        if (checkTurn()) {
          model.gameOver();
          return;
        }
        printTurnInfo();
        upDateCommands(model, knownCommands);

        break;
      default:
    }

//    Command command;
//    Function<Scanner, Command> cmd = knownCommands.getOrDefault(input, null);
//    if (cmd == null) {
//      try {
//        out.append("Invalid input. Commands like l [look around], m [move], p 1[pick item] expected.\n");
//        out.append(reEnterPrompt);
//      } catch (IOException e) {
//        System.out.println(e);
//      }
//    } else {
//      command = cmd.apply(scan);
//      command.act(model);
//    }

  }

  @Override
  public void initializeWorld(String pathToFile) {
    model.initializeWorld(pathToFile);
    view.refresh();
  }


  @Override
  public void playGameUnderGUI() {
    model.startGame();

    // as the system.out is redirected to the console
    out = System.out;

    upDateCommands(model, knownCommands);
    printTurnInfo();

    view.refresh();
  }

  @Override
  public Map<String, Function<Scanner, Command>> getAvailableCommand() {
    return knownCommands;
  }

  @Override
  public void handleLeftClick(int x, int y) {
    if (model.getTurn() == -1 || model.isGameOver()) {
      return;
    }
    Map<String, int[]> positions = model.getPositions();
    for (Map.Entry<String, int[]> entry : positions.entrySet()) {
      String name = entry.getKey();
      if (name.equals("target") || name.equals("pet")) {
        continue;
      }
      int[] position = entry.getValue();
      if (x > position[0] && x <position[0] + 40) {
        if (y > position[1] && y < position[1] + 60) {
          System.out.println(model.getPlayerDescription(name));
        }
      }
    }
  }

}
