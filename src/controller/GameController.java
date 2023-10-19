package controller;

import controller.command.lookAround;
import controller.command.movePlayer;
import controller.command.pickItem;
import model.Player;
import model.WorldModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;

public class GameController {

  private WorldModel worldModel;
  private final Scanner scan;
  private final Appendable out;

  public GameController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    scan = new Scanner(in);
  }

  /**
   * Add players, whether human player or computer player.
   */
  public void addPlayer() {
    String next;
    try {
      // let the user decides whether create a human player or computer player
      boolean isHuman = true;
      out.append("Do you want to add a human player or a computer player? [H/C] \n");
      while (scan.hasNext()) {
        next = scan.next();
        if (next.equalsIgnoreCase("H") || next.equalsIgnoreCase("human")) {
          isHuman = true;
          break;
        } else if (next.equalsIgnoreCase("C") || next.equalsIgnoreCase("computer")) {
          isHuman = false;
          break;
        } else {
          out.append("Undetectable input: ")
              .append(next)
              .append("\nPlease enter again: ");
        }
      }

      // read in name of the player
      out.append("Enter the name of the player: \n");
      String name = "";
      int position = 0;

      if (scan.hasNext()) {
        name = scan.next();
      }

      // read in starting position of the player
      int roomNum = worldModel.getRoomNum();
      out.append("Enter the starting position (range from 1 to ")
          .append(String.valueOf(roomNum))
          .append(") of the player: \n");

      while (scan.hasNext()) {
        next = scan.next();
        try {
          position = Integer.valueOf(next);
          if (position <= roomNum && position >= 1) {
            break;
          } else {
            out.append("Invalid position: ")
                .append(String.valueOf(position))
                .append(", should be in range 1 to ")
                .append(String.valueOf(roomNum))
                .append(". \nPlease enter again: ");
          }
        } catch (NumberFormatException nfe) {
          out.append("Invalid position: ")
              .append(next)
              .append(", should be integer. ")
              .append("\nPlease enter again: ");
        }

      }

      // add player to the game
      worldModel.addPlayer(name, position - 1, isHuman);
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
          .append(String.valueOf(position))
          .append("\n\n");
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  /**
   * Print current turn's player, and the items that the player holds.
   * Also displays the information of the room that the player current inside.
   */
  public void printTurnInfo() {
    try {
      Player player = worldModel.getTurn();
      if (player.isHuman()) {
        out.append("Information of current turn's human player: \n");
      } else {
        out.append("Information of current turn's computer player: \n");
      }
      out.append(player.toString())
          .append("In room ")
          .append(worldModel.getRoomInfo(player.getCurrentRoom()));

      out.append("\nAvailable options in this turn:\n")
          .append("1. look around (show neighbor room information)\n")
          .append("2. move (move to a neighbor room)\n")
          .append("3. pick item (pick up item in the room)\n");
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
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

    if (worldModel == null) {
      throw new IllegalArgumentException("Invalid model.");
    }

    this.worldModel = worldModel;
    try {
      out.append("Start game by adding a playe\n" +
          "\n" +
          "    Map<String, Function<Scanner, Command>> knownCommands = new HashMap<>();\n" +
          "    knownCommands.put(\"move\", s -> new movePlayer(s.nextInt()));\n" +
          "    knownCommands.put(\"look around\", s -> new lookAround());\n" +
          "    knownCommands.put(\"pick item\", s -> new pickItem());\nr? Enter y to add player and start, anything else to quit.\n");
      String input = scan.next();
      if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
        out.append("\nLet's add players.\n");
        addPlayer();
      } else {
        out.append("Exit game, have a nice day~\n");
        System.exit(1);
      }

      for (int i = 0; i < 6; i++) {
        out.append("Do you want to add one more player? (Maximum 7, now ")
            .append(String.valueOf(i+1))
            .append(") [Y/N]\n");
        input = scan.next();
        if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("yes")) {
          addPlayer();
        } else {
          out.append("\nStop adding player.\n");
          break;
        }
      }

      out.append("All players loaded, game starts now!\n");
      printTurnInfo();

      while (scan.hasNext()) {
        Command command;
        String in = scan.next();
        if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit")) {
          return;
        }
        Function<Scanner, Command> cmd = knownCommands.getOrDefault(in, null);
        if (cmd == null) {
          throw new IllegalArgumentException();
        } else {
          command = cmd.apply(scan);
          command.act(worldModel);
        }
        printTurnInfo();

      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

  }

}
