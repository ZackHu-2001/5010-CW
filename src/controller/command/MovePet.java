package controller.command;

import controller.Command;
import java.io.IOException;
import java.util.Scanner;
import model.WorldModel;

/**
 * This command implements move pet.
 */
public class MovePet implements Command {
  private final Scanner scan;
  private final Appendable out;

  /**
   * Default constructor.
   *
   * @param scan where to read user input.
   * @param out  where to append output.
   */
  public MovePet(Scanner scan, Appendable out) {
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
      out.append("\nPlease enter the index of the room you want the pet move to: ");
      String next;

      while (scan.hasNextLine()) {
        next = scan.nextLine();
        try {
          targetRoomId = Integer.parseInt(next);
          if (!m.movePet(targetRoomId - 1)) {
            out.append("\nCan not move to room ")
                .append(String.valueOf(targetRoomId))
                .append(", index out of bound.\nPlease enter again: ");
          } else {
            out.append("\nSuccessfully moved pet to room ")
                .append(String.valueOf(targetRoomId))
                .append(".\n");
            break;
          }

        } catch (NumberFormatException nfe) {
          out.append("Invalid room id, integer expected.\nPlease enter again: ");
        }
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }
}
