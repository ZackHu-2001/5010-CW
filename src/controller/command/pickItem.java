package controller.command;

import controller.Command;
import model.Player;
import model.WorldModel;

public class pickItem implements Command {
  public pickItem() {}
  public void act(WorldModel m) {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    Player Player = m.getTurn();
    System.out.println(m.showItems(Player));




  }
}
