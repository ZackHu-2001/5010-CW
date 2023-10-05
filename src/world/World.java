package world;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

  public void setUp(String input, boolean isFilePath) throws FileNotFoundException {
    if (isFilePath) {
      readFile(input);
    } else {
      readString(input);
    }
  }

  private void readFile(String pathToFile) throws IllegalStateException, FileNotFoundException {
    try (FileReader fileReader = new FileReader(pathToFile)) {
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      StringBuffer stringBuffer = new StringBuffer();
      String line;

      while ((line = bufferedReader.readLine()) != null) {
        stringBuffer.append(line);
        stringBuffer.append('\n');
      }

      parseString(new String(stringBuffer));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void parseString(String information) {
    String[] parts = information.split("\n");

    String[] tmp = (parts[0]).split(" ");
//    System.out.println(parts[0].substring(parts[0].lastIndexOf(tmp[2])));
    mansion = new Mansion(
        Integer.parseInt(parts[0].split(" ")[0]),
        Integer.parseInt(parts[0].split(" ")[1]),
        parts[0].substring(parts[0].lastIndexOf(tmp[2])));

    tmp = (parts[1]).split(" ");
    target = new Target(Integer.parseInt(tmp[0]),
        parts[1].substring(parts[1].lastIndexOf(tmp[1])));

    int roomNum = Integer.parseInt(parts[2]);
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

  private void readString(String inputString) throws IllegalStateException {
    try (StringReader stringReader = new StringReader(inputString)) {
      int character;

      while ((character = stringReader.read()) != -1) {
        System.out.print((char) character);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
