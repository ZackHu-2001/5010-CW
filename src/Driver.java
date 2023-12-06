import controller.Controller;
import controller.GameController;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;
import model.World;
import model.WorldModel;
import view.View;
import view.ViewFactory;

/**
 * This driver class helps manipulate the World class
 * before the controller is written.
 */
public class Driver {
  /**
   * In main, we can manipulate the World in an interactive way.
   *
   * @param args          Game's playing mode is given through args.
   *                      For example, the CMD mode or the GUI mode.
   */
  public static void main(String[] args) {

    // initialize world model
    WorldModel model = new World();

    // initialize view factory to create view
    ViewFactory viewFactory = new ViewFactory();
    View view = viewFactory.createView(args[0], model);

    // initialize controller
    Controller controller = new GameController(model, view);

    controller.playGame();
  }
}
