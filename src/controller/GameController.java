package controller;

import model.World;
import model.WorldModel;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

import static java.lang.System.*;

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

    while (scan.hasNext()) {

    }
  }

}
