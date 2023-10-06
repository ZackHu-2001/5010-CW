package world;

import java.util.List;

/**
 * The {@code Mansion} class represents a mansion with a grid layout of rooms.
 * It stores information about the mansion's dimensions, name, and a list of rooms.
 * Each room within the mansion can be accessed through the room list.
 *
 * <p>Instances of this class are typically created to model and manage mansion data
 * in a larger application.
 *
 */
public class Mansion {
  private List<Room> roomList;
  private int row;
  private int column;
  private String name;

  /**
   * Constructs a new Mansion object with the specified dimensions and name.
   *
   * @param row    The number of rows in the mansion grid.
   * @param column The number of columns in the mansion grid.
   * @param name   The name of the mansion.
   * @throws IllegalArgumentException if the column or row is negative.
   */
  Mansion(int row, int column, String name) {
    if (column < 0 || row < 0) {
      throw new IllegalArgumentException("Size of mansion should not be negative.");
    }
    this.column = column;
    this.row = row;
    this.name = name;
  }

  /**
   * Sets the list of rooms within the mansion.
   *
   * @param roomList The list of rooms to set.
   */
  void setRoomList(List<Room> roomList) {
    this.roomList = roomList;
  }

  /**
   * Gets the list of rooms within the mansion.
   *
   * @return The list of rooms.
   */
  public List<Room> getRoomList() {
    return roomList;
  }

  /**
   * Gets the number of rows in the mansion grid.
   *
   * @return The number of rows.
   */
  public int getRow() {
    return row;
  }

  /**
   * Gets the number of columns in the mansion grid.
   *
   * @return The number of columns.
   */
  public int getColumn() {
    return column;
  }

  /**
   * Gets the name of the mansion.
   *
   * @return The name of the mansion.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns a string representation of the mansion, including its name, dimensions,
   * and a list of rooms.
   *
   * @return A string representation of the mansion.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder()
        .append(new String(name))
        .append("\nRow: ")
        .append(String.valueOf(row))
        .append(" Column: ")
        .append(String.valueOf(column))
        .append("\n")
        .append("Room List:\n");

    for (int i = 0; i < roomList.size(); i++) {
      sb.append("\t" + String.valueOf(i) + " ");
      sb.append(roomList.get(i).toString());
    }

    return new String(sb);
  }
}
