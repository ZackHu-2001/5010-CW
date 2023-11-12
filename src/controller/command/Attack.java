package controller.command;

import controller.Command;
import java.io.IOException;
import java.util.Scanner;
import model.WorldModel;

/**
 * Here is implementation of attack command.
 */
public class Attack implements Command {

  private final Scanner scan;
  private final Appendable out;

  public Attack(Scanner scan, Appendable out) {
    this.scan = scan;
    this.out = out;
  }

  @Override
  public void act(WorldModel m) {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null");
    }

    try {
      String items = m.showItemsHold(m.getTurn());
      if ("[Empty]".equals(items)) {
        out.append("\nYou hold no item, so you poke him in the eye.\n");

        boolean isSuccess = m.attackWithHand();
        System.out.println(isSuccess);
        if (!isSuccess) {
          out.append("Oops! Your attack was seen by others, attack failed.\n");
          return;
        }

        out.append("Attack success! Target's remaining health: ")
            .append(String.valueOf(m.getTargetRemainingHealth()))
            .append("\n");
        return;
      } else  {
        out.append("\nHere are the items you hold: ")
            .append(items)
            .append("\nWhich one you want to use, enter index of item: ");
      }

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

        boolean[] result = m.attackWithItem(m.getTurn(), index - 1);
        if (result[0]) {
          if (result[1]) {
            out.append("Oops! Your attack was seen by other, attack failed.\n");
            return;
          }
          out.append("Attack success! Target's remaining health: ")
              .append(String.valueOf(m.getTargetRemainingHealth()))
              .append("\n");
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
