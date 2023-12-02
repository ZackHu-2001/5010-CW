package view;

import controller.Controller;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapClickListener extends MouseAdapter {
  Controller listener;

  public MapClickListener(Controller listener) {
    this.listener = listener;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();
    listener.handleMapClick(x, y);
  }
}
