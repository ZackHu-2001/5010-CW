import controller.GameController;
import model.Room;
import model.World;
import model.WorldModel;

import static java.lang.System.exit;
import static java.lang.System.out;

import java.awt.image.BufferedImage;
import java.io.*;
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
    Scanner scanner = new Scanner(System.in);
    Appendable output = System.out;

    // read user input: the mansion file, or direct string input
    Reader fileReader;
    String pathToFile;

    if (args.length != 0) {
      pathToFile = args[0];
    } else {
      System.out.println("Please provide the path to the world file:");
      pathToFile = scanner.nextLine();
    }

    try {
      fileReader = new FileReader(pathToFile);
      WorldModel worldModel = new World(fileReader);

      System.out.println("Successfully read in the file!\n\n"
          + "Here are detailed information:");

//      StringReader input = new StringReader("2 2 1 1 3 3 1 2 1 3 2 3 2 1 3 1 3 2");
      output.append(worldModel.toString());

      GameController gameController = new GameController(new InputStreamReader(System.in), output);
      gameController.startGame(worldModel);

    } catch (IOException e) {
      System.out.println("There are problems with path to file, exit now.");
      exit(1);
    }

  }
}
