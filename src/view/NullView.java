package view;

import controller.Controller;

/**
 * The NullView class represents a null view.
 */
public class NullView implements View {
  public NullView() {}


  @Override
  public void connect(Controller listener) {
  }

  @Override
  public void refresh() {

  }

  @Override
  public void makeVisible() {

  }

  @Override
  public void gameOverHint(String hint) {

  }
}
