package world;

import static java.lang.System.exit;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;


/**
 * This driver class helps manipulate the World class
 * before the controller is written.
 */
public class Driver {
  /**
   * In main, we can manipulate the World in an interactive way.
   *
   * @param args          File path could be passed through
   *                      command line arguments.
   */
  public static void main(String[] args) {
    // read user input: the mansion file, or direct string input
    Reader fileReader;
    World world = null;
    Scanner scanner = new Scanner(System.in);
    String pathToFile;

    if (args.length != 0) {
      pathToFile = args[0];
    } else {
      System.out.println("Please provide the path to the world file:");
      pathToFile = scanner.nextLine();
    }

    try {
      fileReader = new FileReader(pathToFile);
      world = new World(fileReader);
    } catch (IOException e) {
      System.out.println("There are problems with path to file, exit.");
      exit(1);
    }

    System.out.println("Successfully read in the file!\n\n"
         + "Here are detailed information:");
    System.out.println(world.getMansion().toString());

    String hint = String.format("Select Option:\n"
        + "\t1: Move target\n"
        + "\t2: Output image\n"
        + "\t3: Display room by index\n"
        + "\t4: Display room's visibility by index\n"
        + "\t5: Exit\n"
    );

    int option = 0;

    while (true) {
      System.out.print(hint);
      option = scanner.nextInt();

      switch (option) {
        case (1):
          world.getTarget().move();
          System.out.println(
              String.format("Currently the target locates in room %d.\n",
                  world.getTarget().getCurrentRoom()));
          break;

        case (2):
          BufferedImage bufferedImage = world.draw("example.png");
          File output = new File("example.png");
          try {
            ImageIO.write(bufferedImage, "png", output);
          } catch (IOException exception) {
            System.out.println("Can not write to example.pnp");
            exit(1);
          }
          System.out.println("The image is output to 'example.png'.\n");
          break;

        case (3):
          int index = scanner.nextInt();
          if (index < 0
              || index >= world.getMansion().getRoomList().size()) {
            System.out.println("Invalid index.\n");
            break;
          }
          String roomInfo = world.getMansion().getRoomList().get(index).toString();
          System.out.println("Room " + String.valueOf(index) + " " + roomInfo);
          break;

        case (4):
          index = scanner.nextInt();
          if (index < 0
              || index >= world.getMansion().getRoomList().size()) {
            System.out.println("Invalid index.\n");
            break;
          }
          List<Room> visibleRoom = world.getMansion().getRoomList().get(index).getVisibleRoom();
          System.out.println("Visible Room of Room " + String.valueOf(index) + ": ");
          for (Room room : visibleRoom) {
            System.out.println("\t" + room.toString());
          }
          break;

        case (5):
          exit(1);
          break;

        default:
          System.out.println("Invalid input.");
          break;
      }
    }
  }
}
