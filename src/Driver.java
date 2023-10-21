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
    StringReader filePath = new StringReader("res/mansion.txt\n");
    StringReader command = new StringReader("jkl\n10\ny\nh\nzack\n2\nn\nlook around\nmove\n2\n1\n1\n0");
    Appendable output = System.out;

    // read user input: the mansion file, or direct string input
    Reader fileReader;
    String pathToFile;

    if (args.length != 0) {
      pathToFile = args[0];
    } else {
      System.out.println("Please provide the path to the world file:");
      pathToFile = new Scanner(filePath).nextLine();
    }

    try {
      fileReader = new FileReader(pathToFile);
      WorldModel worldModel = new World(fileReader);

      System.out.println("Successfully read in the file!\n\n"
          + "Here are detailed information:");

      output.append(worldModel.toString());

      GameController gameController = new GameController(new InputStreamReader(System.in), output);
//      GameController gameController = new GameController(command, output);
      gameController.startGame(worldModel);

    } catch (IOException e) {
      System.out.println("There are problems with path to file, exit now.");
      exit(1);
    }

  }
}
