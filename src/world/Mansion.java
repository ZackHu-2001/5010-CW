package world;

import java.util.List;

public class Mansion {
  private List<Room> roomList;
  private int row;
  private int column;
  private String name;

  Mansion(int row, int column, String name) {
    if (column < 0 || row < 0) {
      throw new IllegalArgumentException("Size of mansion should not be negative.");
    }
    this.column = column;
    this.row = row;
    this.name = name;
  }

  void setRoomList(List<Room> roomList) {
    this.roomList = roomList;
  }

  public List<Room> getRoomList() {
    return roomList;
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  public String getName() {
    return name;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder()
        .append(new String(name))
        .append("\nRow: ")
        .append(String.valueOf(row))
        .append(" Column: ")
        .append(String.valueOf(column))
        .append("\n")
        .append("Room List:\n");

    for (Room room: roomList) {
      sb.append(room.toString());
    }

    return new String(sb);
  }
}
