package world;

import java.util.List;
import java.util.Objects;

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
  private final List<Room> roomList;
  private final int row;
  private final int column;
  private final String name;

  /**
   * Constructs a new Mansion object with the specified dimensions and name.
   *
   * @param row    The number of rows in the mansion grid.
   * @param column The number of columns in the mansion grid.
   * @param name   The name of the mansion.
   * @param roomList The list of room it contains.
   * @throws IllegalArgumentException if the column or row is negative.
   */
  public Mansion(int row, int column, String name, List<Room> roomList) {
    if (column < 0 || row < 0) {
      throw new IllegalArgumentException("Size of mansion should not be negative.");
    }
    this.column = column;
    this.row = row;
    this.name = name;
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
   * Indicates whether some other object is "equal to" this one. The equality is
   * determined based on comparing the attributes of two Mansion objects.
   *
   * @param o The object to compare with this Mansion.
   * @return {@code true} if the specified object is equal to this Mansion; {@code false}
   *         otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (! (o instanceof Mansion)) {
      return false;
    }

    Mansion otherMansion = (Mansion) o;
    if (otherMansion.roomList.size() != roomList.size()) {
      return false;
    }

    for (int i = 0; i < roomList.size(); i++) {
      if (!(otherMansion.roomList.get(i).equals(roomList.get(i)))) {
        return false;
      }
    }

    return name.equals(otherMansion.name)
        && row == otherMansion.row
        && column == otherMansion.column;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, roomList, row, column);
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
        .append(row)
        .append(" Column: ")
        .append(column)
        .append("\n")
        .append("Room List:\n");

    for (int i = 0; i < roomList.size(); i++) {
      sb.append("\t" + i + " ");
      sb.append(roomList.get(i).toString());
    }

    return new String(sb);
  }
}
