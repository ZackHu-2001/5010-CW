package controller;

import controller.command.LookAround;
import controller.command.MovePlayer;
import controller.command.PickItem;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import javax.imageio.ImageIO;
import model.Player;
import model.WorldModel;

/**
 * This is the controller in MVC pattern. Once the program runs,
 * it will take over control from the main method, give prompts
 * and handle user input.
 */
public class GameController implements Controller {

  private WorldModel worldModel;
  private Scanner scan;
  private final Scanner humanInputScan;
  private final Appendable out;
  private int maxTurn;
  private int currentTurn;
  private final String reEnterPromot = "Please enter again: ";

  /**
   * Game controller, control over the flow of the game.
   *
   * @param in  Readable input.
   * @param out Appendable output.
   * @param maxTurn Max turn number allowed.
   */
  public GameController(Readable in, Appendable out, int maxTurn) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    this.humanInputScan = new Scanner(in);
    this.scan = humanInputScan;
    this.maxTurn = maxTurn;
  }

  /**
   * Add players, whether human player or computer player.
   *
   * @return Whether user wants to quit.
   */
  private boolean addPlayer() {
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
              .append(reEnterPromot);
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
      int roomCnt = worldModel.getRoomCnt();
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
                .append(reEnterPromot);
          }
        } catch (NumberFormatException nfe) {
          out.append("Invalid position: ")
              .append(next)
              .append(", should be integer. ")
              .append("\n")
              .append(reEnterPromot);
        }
      }
      position -= 1;

      // add player to the game
      worldModel.addPlayer(name, position, isHuman);
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

  /**
   * Update current turn.
   *
   * @return Whether turn runs up and game should exit.
   */
  private boolean updateTurn() {
    currentTurn += 1;
    if (currentTurn > maxTurn) {
      try {
        out.append("Maximum turn reached, game over.");
        return true;
      } catch (IOException ioe) {
        throw new IllegalStateException("Append failed\n");
      }
    } else {
      return false;
    }
  }

  /**
   * Save map when exit the game.
   */
  private void saveMap() {
    BufferedImage bufferedImage = worldModel.draw("./map.png");
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
          .append(String.valueOf(currentTurn))
          .append(", Doctor Lucky at room ")
          .append(String.valueOf(worldModel.getTargetPosition() + 1))
          .append(", ")
          .append(worldModel.getPetName())
          .append(" at room ")
          .append(String.valueOf(worldModel.getPetPosition() + 1));

      Player player = worldModel.getTurn();
      if (player.isHuman()) {
        scan = humanInputScan;
        out.append("\nInformation of the current turn's human player: \n");
      } else {
        Readable computerInput = worldModel.computerPlayerAction(player);
        scan = new Scanner(computerInput);
        out.append("\nInformation of the current turn's computer player: \n");
      }
      out.append(player.toString())
          .append("In room ")
          .append(worldModel.getRoomInfo(player.getCurrentRoom()));

      out.append("Available options of this turn:\n")
          .append("1. look around (show neighbor room information)\n")
          .append("2. move (move to a neighbor room)\n");

      if (!worldModel.showItems(player).equals("[Empty]")) {
        out.append("3. pick item (pick up item in the room)\n");
      }

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
    if (worldModel.showItems(worldModel.getTurn()).equals("[Empty]")) {
      knownCommands.remove("pick item");
    } else {
      knownCommands.put("pick item", s -> new PickItem(scan, out));
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

  /**
   * Start the game, handling user's commands through command pattern.
   * Three commands provided, the detailed implementation of those
   * command are in the model.
   *
   * @param worldModel The model that it controls over.
   */
  public void startGame(WorldModel worldModel) {
    // validate world model
    if (worldModel == null) {
      throw new IllegalArgumentException("Invalid model.");
    }
    this.worldModel = worldModel;

    // add player
    try {
      String input;

      // auto save map
      saveMap();
      out.append("The graphical representation of the world is saved to map.png.\n");

      out.append("\nStart game by adding a player? "
          + "Enter y to add player and start, anything else to quit.\n");
      if (scan.hasNextLine()) {
        input = scan.nextLine();
        if (quitCheck(input)) {
          return;
        }
        if ("y".equalsIgnoreCase(input) || "yes".equalsIgnoreCase(input)) {
          out.append("\nLet's add players.\n");
          if (addPlayer()) {
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
          input = scan.nextLine();
          if (quitCheck(input)) {
            return;
          }
          if ("y".equalsIgnoreCase(input) || "yes".equalsIgnoreCase(input)) {
            if (addPlayer()) {
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

      out.append("All players loaded, game starts now!\n");

      // update turn returns whether turn runs up
      if (updateTurn()) {
        // return if turn runs up
        return;
      }

      printTurnInfo();
      upDateCommands(worldModel, knownCommands);

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
          out.append(reEnterPromot);
          continue;
        } else {
          command = cmd.apply(scan);
          command.act(worldModel);
        }

        // update turn returns whether turn runs up
        if (updateTurn()) {
          // return if turn runs up
          return;
        }
        printTurnInfo();
        upDateCommands(worldModel, knownCommands);
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }
}
