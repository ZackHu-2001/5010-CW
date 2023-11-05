package model;

/**
 * Interface movable apply for those object that can move.
 */
public interface Movable {
  /**
   * Move the object from current to target room.
   *
   * @param targetRoom target room to be moved to.
   */
  void move(int targetRoom);
}
