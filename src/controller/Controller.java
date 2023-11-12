package controller;

import model.WorldModel;

/**
 * The interface for controller specify that the detailed
 * controller would provide a playGame method that take over
 * control and starts game.
 */
public interface Controller {
  /**
   * Controller should have a public method: Start Game.
   * @param m the world model to be controlled.
   */
  void startGame(WorldModel m);
}
