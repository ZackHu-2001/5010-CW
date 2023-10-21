package controller;

import model.WorldModel;

/**
 * Interface command defines all the command would have an
 * act method, that takes a model and apply the command.
 */
public interface Command {
  /**
   * Starting point for the controller.
   *
   * @param m the model to use
   */
  void act(WorldModel m);
}
