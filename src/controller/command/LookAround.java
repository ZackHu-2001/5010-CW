package controller.command;

import controller.Command;
import java.io.IOException;
import model.WorldModel;

/**
 * This command implements look around. Look around would
 * display information of the neighbor rooms. Which includes
 * the items inside and the player inside.
 */
public class LookAround implements Command {
  private final Appendable out;

  public LookAround(Appendable out) {
    this.out = out;
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
    try {
      out.append(m.lookAround(m.getTurn()));
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }
}
