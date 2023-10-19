package controller.command;

import controller.Command;
import java.io.IOException;
import java.util.Scanner;
import model.WorldModel;

public class movePlayer implements Command {
  private final Scanner scan;
  private final Appendable out;

  public movePlayer(Scanner scan, Appendable out) {
    this.scan = scan;
    this.out = out;
  }

  @Override
  public void act(WorldModel m) {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null");
    }

    int targetRoomId;
    try {
      out.append("Please enter the index of the room you want to move to: ");
      String next;

      while (scan.hasNextLine()) {
          next = scan.nextLine();
          try {
            targetRoomId = Integer.parseInt(next);
            if (!m.movePlayer(m.getTurn(), targetRoomId - 1)) {
              out.append("\nCan not move to room ")
                  .append(String.valueOf(targetRoomId))
                  .append(", not neighbor of current room.\nPlease enter again: ");
            }

          } catch (NumberFormatException nfe) {
            out.append("Invalid room id, integer expected.\nPlease enter again: ");
          }
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
//    m.movePlayer(m.getTurn(), targetRoom);
  }

}
