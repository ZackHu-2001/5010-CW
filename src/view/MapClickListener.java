package view;

import controller.Controller;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

/**
 * The MapClickListener class listens to mouse input from the user
 * and implements the MouseAdapter interface to handle mouse events.
 */
public class MapClickListener extends MouseAdapter {
  Controller listener;

  public MapClickListener(Controller listener) {
    this.listener = listener;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();

    if (SwingUtilities.isLeftMouseButton(e)) {
      listener.handleLeftClick(x, y);
    } else if (SwingUtilities.isRightMouseButton(e)) {
      listener.handleRightClick(x, y);
    }
  }
}
