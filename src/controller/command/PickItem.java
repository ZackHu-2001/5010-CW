package controller.command;

import controller.Command;
import java.io.IOException;
import java.util.Scanner;
import model.Player;
import model.WorldModel;

/**
 * This command implements pick item. Pick item would display
 * the items in the room and let player pick which one they
 * want to pick.
 */
public class PickItem implements Command {
  private final Scanner scan;
  private final Appendable out;

  public PickItem(Scanner scan, Appendable out) {
    this.out = out;
    this.scan = scan;
  }

  /**
   * Apply the command.
   *
   * @param m the model to use
   */
  public void act(WorldModel m) {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    Player player = m.getTurn();
    try {
      out.append("Here are available items: ")
          .append(m.showItems(player))
          .append("\nWhich one you want to pick, enter index of item: ");

      String next;
      int index;
      while (scan.hasNextLine()) {
        next = scan.nextLine();
        try {
          index = Integer.parseInt(next);
        } catch (NumberFormatException nfe) {
          out.append("Invalid index: ")
              .append(next)
              .append("\nPlease enter again: ");
          continue;
        }
        if (m.pickUpItem(player, index - 1)) {
          out.append("Successfully picked up item.\n");
          break;
        } else {
          out.append("Index out of bound.\n")
              .append("Please enter again: ");
        }
      }

    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }




  }
}
