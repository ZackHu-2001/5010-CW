package controller;

import model.Player;
import model.WorldModel;

import java.io.IOException;
import java.util.Scanner;

public class GameController {

  private WorldModel worldModel;
  private final Scanner scan;
  private final Appendable out;

  public GameController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    scan = new Scanner(in);
  }

  public void startGame(WorldModel worldModel) {

    if (worldModel == null) {
      throw new IllegalArgumentException("Invalid model.");
    }

    this.worldModel = worldModel;
    try {
      out.append("Start game by adding a player? [Y/N]\n");
      // if not enter Y, exit
      if (scan.hasNext() && scan.next() != "Y") {
        System.exit(1);
      }
      out.append("Enter the name of the player: \n");
      String name = "";
      int position = 0;

      if (scan.hasNext()) {
        name = scan.next();
      }
      out.append("Enter the starting position of the player: \n");
      if (scan.hasNextInt()) {
        position = scan.nextInt();
      }

      for (int i = 0; i < 6; i++) {
        out.append("Do you want to add one more player? [Y/N]");
        if (scan.hasNext()) {

        }
      }

      while (!worldModel.isGameOver()) {
        if ()
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);

    }

  }

}
