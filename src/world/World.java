package world;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class World {
  private static World instance;
  private Target target;
  private Mansion mansion;

  private World() {

  }

  public static World getInstance() {
    if (instance == null) {
      instance = new World();
    }
    return instance;
  }

  public void setUp(Readable input) throws IOException {
    BufferedReader bufferedReader = new BufferedReader((Reader) input);
    StringBuffer stringBuffer = new StringBuffer();
    String line;

    while ((line = bufferedReader.readLine()) != null) {
      stringBuffer.append(line);
      stringBuffer.append('\n');
    }

    parseString(new String(stringBuffer));
  }

  private void parseString(String information) {
    String[] parts = information.split("\n");

    String[] tmp = (parts[0]).split(" ");
    mansion = new Mansion(
        Integer.parseInt(parts[0].split(" ")[0]),
        Integer.parseInt(parts[0].split(" ")[1]),
        parts[0].substring(parts[0].lastIndexOf(tmp[2])));

    int roomNum = Integer.parseInt(parts[2]);

    tmp = (parts[1]).split(" ");
    target = new Target(Integer.parseInt(tmp[0]),
        parts[1].substring(parts[1].lastIndexOf(tmp[1])),
        roomNum);

    List<Room> roomList = new ArrayList<Room>();

    for (int i = 0; i < roomNum; i++) {
      tmp = parts[3 + i].trim().split("\\s+");
      int[] location = new int[4];

      location[0] = Integer.parseInt(tmp[0]);
      location[1] = Integer.parseInt(tmp[1]);
      location[2] = Integer.parseInt(tmp[2]);
      location[3] = Integer.parseInt(tmp[3]);
      roomList.add(new Room(
          parts[3 + i].substring(11),
          location));
    }

    mansion.setRoomList(roomList);

    calculateNeighbor(roomList);

    int itemNum = Integer.parseInt(parts[3 + roomNum]);
    int roomNumber;

    for (int i = 0; i < itemNum; i++) {
      tmp = parts[4 + roomNum + i].split("\\s+");
      int[] location = new int[2];

      roomNumber = Integer.parseInt(tmp[0]);

      Room tmpRoom = roomList.get(roomNumber);
      tmpRoom.addItem(new Item(
          parts[4 + roomNum + i].substring(parts[4 + roomNum + i].lastIndexOf(tmp[2])),
          Integer.parseInt(tmp[1]),
          roomNumber));
    }

    System.out.println(mansion.toString());

  }

  private void calculateNeighbor(List<Room> roomList) {
    for (int i=0; i<roomList.size(); i++) {
      for (int j=i+1; j<roomList.size(); j++) {
        Room a = roomList.get(i);
        Room b = roomList.get(j);
        if (isNeighbor(a, b)) {
          a.addNeighbor(b);
          b.addNeighbor(a);
        }
      }
    }
  }

  private boolean isNeighbor(Room a, Room b) {
    int[] location_a = a.getLocation();
    int[] location_b = b.getLocation();
    if (location_a[0] == location_b[2] + 1
      || location_a[2] == location_b[0] - 1) {
      if (location_a[1] >= location_b[3] + 1
        || location_a[3] <= location_b[1] - 1) {
        return false;
      }
      return true;
    } else if (location_a[1] == location_b[3] + 1
      || location_a[3] == location_b[1] - 1) {
      if (location_a[0] >= location_b[2] + 1
      || location_a[2] <= location_b[0] - 1) {
        return false;
      }
      return true;
    } else {
      return false;
    }
  }
}
