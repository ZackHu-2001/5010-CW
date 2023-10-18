package controller.command;

import controller.Command;
import model.WorldModel;

public class lookAround implements Command {
  public lookAround() {}

  public void act(WorldModel m) {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    m.lookAround(m.getTurn());
  }
}
