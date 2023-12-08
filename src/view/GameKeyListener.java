package view;

import controller.Controller;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The GameKeyListener class listens to keyboard input from the user
 * and implements the KeyListener interface to handle key events.
 */
public class GameKeyListener implements KeyListener {

  private Controller controller;

  /**
   * Constructs a GameKeyListener with the specified controller.
   *
   * @param controller The controller responsible for handling key events.
   */
  public GameKeyListener(Controller controller) {
    this.controller = controller;
  }

  /**
   * Invoked when a key is typed. This implementation does nothing.
   *
   * @param e The KeyEvent associated with the key typed.
   */
  @Override
  public void keyTyped(KeyEvent e) {
    // Do nothing for keyTyped event
  }

  /**
   * Invoked when a key is pressed. Delegates the key press event
   * to the associated controller for further handling.
   *
   * @param e The KeyEvent associated with the key pressed.
   */
  @Override
  public void keyPressed(KeyEvent e) {
    controller.handleKeyPress(e.getKeyChar());
  }

  /**
   * Invoked when a key is released. This implementation does nothing.
   *
   * @param e The KeyEvent associated with the key released.
   */
  @Override
  public void keyReleased(KeyEvent e) {
    // Do nothing for keyReleased event
  }
}
