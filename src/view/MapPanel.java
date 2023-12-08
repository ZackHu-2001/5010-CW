package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import model.ReadOnlyModel;

/**
 * The MapPanel class represents the map panel.
 */
public class MapPanel extends JPanel {
  public static int WIDTH = 800;
  public static int HEIGHT = 900;
  private ReadOnlyModel readOnlyModel;

  /**
   * Constructor for MapPanel.
   *
   * @param readOnlyModel   the read only model
   */
  public MapPanel(ReadOnlyModel readOnlyModel) {
    this.readOnlyModel = readOnlyModel;
    setPreferredSize(new Dimension(800, 900));
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    if (readOnlyModel.isInitialized()) {
      BufferedImage bufferedImage = readOnlyModel.drawMap();
      setPreferredSize(new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight()));
      g2d.drawImage(bufferedImage, 0, 0, this);
      drawObjects(readOnlyModel.getPositions(), g2d);
    }
  }

  private void drawObjects(Map<String, int[]> positions, Graphics2D g2d) {
    for (Map.Entry<String, int[]> entry : positions.entrySet()) {
      String name = entry.getKey();
      int[] position = entry.getValue();
    }

    try {
      g2d.drawImage(ImageIO.read(new File("res/img/Dr.lucky.png")),
          positions.get("target")[0], positions.get("target")[1], 50, 50, this);
    } catch (IOException e) {
      System.out.println("failed to find the file for Dr.lucky");
    }

//    try {
//      g2d.drawImage(ImageIO.read(new File("res/img/cat.jpeg")),
//          positions.get("pet")[0], positions.get("target")[1], 30, 30, this);
//    } catch (IOException e) {
//      System.out.println("failed to find the file for cat");
//    }

    g2d.setFont(new Font("Arial", Font.BOLD, 22));
    for (Map.Entry<String, int[]> entry : positions.entrySet()) {
      String name = entry.getKey();
      if ("target".equals(name) || "pet".equals(name)) {
        continue;
      }
      int[] position = entry.getValue();
      g2d.drawString(name, position[0], position[1] + 20);
    }
  }

}
