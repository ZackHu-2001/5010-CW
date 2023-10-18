package controller;

import model.WorldModel;

public interface Command {
  /**
   * Starting point for the controller.
   *
   * @param m the model to use
   */
  void act(WorldModel m);
}
