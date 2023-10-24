import controller.GameController;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
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
    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;

    // read user input: the mansion file, or direct string input
    Reader fileReader;
    String pathToFile;

    System.out.println("Please input path to the file: ");

    if (args.length != 0) {
      pathToFile = args[0];
    } else {
      pathToFile = new Scanner(System.in).nextLine();
    }

    try {
      fileReader = new FileReader(pathToFile);
      WorldModel worldModel = new World(fileReader);

      System.out.println("Successfully read in the file!\n\n"
          + "Here are detailed information:");

      output.append(worldModel.toString());

      // read in max turn
      int maxTurn = 0;
      String inputString;
      Scanner scan = new Scanner(System.in);

      output.append("What is the max turn of the game?\n");
      while (scan.hasNextLine()) {
        inputString = scan.nextLine();
        if ("quit".equalsIgnoreCase(inputString)
            || "q".equalsIgnoreCase(inputString)) {
          output.append("Exit game, have a nice day~\n");
          return;
        }
        try {
          maxTurn = Integer.valueOf(inputString);
          if (maxTurn <= 0) {
            output.append("Invalid max turn, one positive integer expected.\n");
            continue;
          }
          break;
        } catch (NumberFormatException nfe) {
          output.append("Invalid max turn, one positive integer expected.\n")
              .append("Please enter again:");
        }
      }

      GameController gameController = new GameController(input, output, maxTurn);
      gameController.startGame(worldModel);

    } catch (IOException e) {
      System.out.println("There are problems with path to file, exit now.");
      System.exit(1);
    }

  }
}
