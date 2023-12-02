package view;

import controller.Controller;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

public class GameKeyListener implements KeyListener {
  private Controller controller;

  public GameKeyListener(Controller controller) {
    this.controller = controller;
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyChar()) {
      case "m":

    }

  }

  @Override
  public void keyReleased(KeyEvent e) {

  }
}
