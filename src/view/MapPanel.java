package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import model.ReadOnlyModel;

public class MapPanel extends JPanel {
  public static int WIDTH = 800;
  public static int HEIGHT = 900;
  private ReadOnlyModel readOnlyModel;

  public MapPanel(ReadOnlyModel readOnlyModel) {
    this.readOnlyModel = readOnlyModel;
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    BufferedImage bufferedImage = readOnlyModel.drawMap();
    setPreferredSize(new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight()));
    g2d.drawImage(bufferedImage, 0, 0, this);
  }

}
