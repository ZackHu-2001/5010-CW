package world;

import java.io.FileNotFoundException;

public class Driver {
  public static void main(String[] args) throws FileNotFoundException {
    // read user input: the mansion file
    String pathToFile = "/Users/zackhu/IdeaProjects/killDoctorLucky/res/mansion.txt";
//    System.out.println(pathToFile);
    World world = World.getInstance();
    world.setUp(pathToFile, true);


  }
}
