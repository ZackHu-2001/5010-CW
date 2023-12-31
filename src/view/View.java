package view;

import controller.Controller;

/**
 * The View interface represents the view.
 */
public interface View {

  /**
   * Connect the controller with the view.
   *
   * @param listener the controller
   */
  void connect(Controller listener);

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();

  /**
   * Give hint when game overs.
   * @param hint  the hint to be displayed
   */
  void gameOverHint(String hint);

}
