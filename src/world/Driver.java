package world;

import static java.lang.System.exit;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
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
   * @throws IOException  Throw IOException when having trouble
   *                      reading the content from buffer.
   */
  public static void main(String[] args) throws IOException {
    // read user input: the mansion file, or direct string input
    Reader fileReader = null;
    World world = null;
    Scanner scanner = new Scanner(System.in);
    String pathToFile;

    System.out.println("Please provide the path to the world file:");

    pathToFile = scanner.nextLine();

    try {
      fileReader = new FileReader(pathToFile);
      world = new World(fileReader);
    } catch (FileNotFoundException e) {
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
        + "\t4: Exit\n"
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
                  world.getTarget().currentRoom));
          break;

        case (2):
          BufferedImage bufferedImage = world.draw("example.png");
          File output = new File("example.png");
          ImageIO.write(bufferedImage, "png", output);
          System.out.println("The image is output to \'example.png\'.\n");
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
          exit(1);
          break;

        default:
          System.out.println("Invalid input.");
          break;
      }
    }
  }
}
