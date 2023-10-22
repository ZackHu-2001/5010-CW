
import static java.lang.System.exit;

import controller.GameController;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Scanner;
import model.World;
import model.WorldModel;


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
    Appendable output = System.out;

    // read user input: the mansion file, or direct string input
    Reader fileReader;
    String pathToFile;

    if (args.length != 0) {
      pathToFile = args[0];
    } else {
      pathToFile = new Scanner(filePath).nextLine();
    }

    try {
      fileReader = new FileReader(pathToFile);
      WorldModel worldModel = new World(fileReader);

      System.out.println("Successfully read in the file!\n\n"
          + "Here are detailed information:");

      output.append(worldModel.toString());

      GameController gameController = new GameController(new InputStreamReader(System.in), output);
      gameController.startGame(worldModel);

    } catch (IOException e) {
      System.out.println("There are problems with path to file, exit now.");
      exit(1);
    }

  }
}
