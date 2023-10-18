package controller.command;

import controller.Command;
import model.WorldModel;

public class movePlayer implements Command {
  int targetRoom;
  public movePlayer(int targetRoom) {
    this.targetRoom = targetRoom;
  }

  @Override
  public void act(WorldModel m) {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    m.movePlayer(m.getTurn(), targetRoom);
  }

}
