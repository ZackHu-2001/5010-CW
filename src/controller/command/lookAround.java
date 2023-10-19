package controller.command;

import controller.Command;
import model.WorldModel;

import java.io.IOException;

public class lookAround implements Command {
  private final Appendable out;

  public lookAround(Appendable out) {
    this.out = out;
  }

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
