package view;

import java.awt.*;
import javax.swing.*;
import model.ReadOnlyModel;

public class StatusPanel extends JPanel {
  public static int WIDTH = 400;
  public static int HEIGHT = 900;
  private ReadOnlyModel readOnlyModel;

  private final int BASE_OPTION = 240;
  private final int INTERVAL_OPTION = 35;
  private final int BASE_CONSOLE = 460;

  public StatusPanel(ReadOnlyModel readOnlyModel) {
    this.readOnlyModel = readOnlyModel;

  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.BLACK);

    // draw borders
    g2d.setStroke(new BasicStroke(3.0f));
    g2d.drawLine(0,0,0,900);
    g2d.setStroke(new BasicStroke(1.5f));
    g2d.drawLine(0, 150, 400, 150);
    g2d.drawLine(0, 420, 400, 420);

    // draw titles
    g2d.setFont(new Font("Arial", Font.BOLD, 28));
    g2d.drawString("Current Turn:", 30, 40);
    g2d.drawString("Available Options: ", 30, 190);
    g2d.drawString("Console", 30, BASE_CONSOLE);

    // draw detailed information
    g2d.setFont(new Font("Arial", Font.ITALIC, 24));
//    g2d.drawString("Current Turn:" + readOnlyModel.getCurrentTurnPlayer(), 10, 10);
    g2d.drawString("Player:", 30, 80);
    g2d.drawString("Holds: ", 30, 120);
    g2d.drawString("Move:", 30, BASE_OPTION);
    g2d.drawString("Move Pet:", 30, BASE_OPTION + INTERVAL_OPTION);
    g2d.drawString("Look Around:", 30, BASE_OPTION + 2 * INTERVAL_OPTION);
    g2d.drawString("Pick Item:", 30, BASE_OPTION + 3 * INTERVAL_OPTION);
    g2d.drawString("Attack:", 30, BASE_OPTION + 4 * INTERVAL_OPTION);

    // draw option key
    g2d.setFont(new Font("Arial", Font.PLAIN, 24));
    g2d.drawString("M", 300, BASE_OPTION);
    g2d.drawString("N", 300, BASE_OPTION + INTERVAL_OPTION);
    g2d.drawString("L", 300, BASE_OPTION + 2 * INTERVAL_OPTION);
    g2d.drawString("P", 300, BASE_OPTION + 3 * INTERVAL_OPTION);
    g2d.drawString("A", 300, BASE_OPTION + 4 * INTERVAL_OPTION);

    // draw console
    g2d.drawRect(30, BASE_CONSOLE + 30, 340, 270);

  }

}
