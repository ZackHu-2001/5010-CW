package controller.command;

import controller.Command;
import model.Player;
import model.WorldModel;

import java.io.IOException;
import java.util.Scanner;

public class pickItem implements Command {
  private final Scanner scan;
  private final Appendable out;

  public pickItem(Scanner scan, Appendable out) {
    this.out = out;
    this.scan = scan;
  }

  public void act(WorldModel m) {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    Player player = m.getTurn();
    try {
      out.append("Here are available items: ")
          .append(m.showItems(player))
          .append("\nWhich one you want to pick, enter index of item:");

      String next;
      int index;
      while (true) {
        next = scan.next();
        try {
          index = Integer.parseInt(next);
        } catch (NumberFormatException nfe) {
          out.append("Invalid index: ")
              .append(next)
              .append("\nPlease enter again: ");
          continue;
        }
        if (m.pickUpItem(player, index)) {
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
