package view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The WelcomePanel class represents the welcome panel.
 */
public class WelcomePanel extends JPanel {
  public static int WIDTH = 600;
  public static int HEIGHT = 350;
  private JLabel gameNameLabel;
  private JLabel authorLabel;

  /**
   * Constructor for WelcomePanel.
   */
  public WelcomePanel() {
    setLayout(new GridBagLayout());
    setSize(600, 400);

    // Create GridBagConstraints
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.fill = GridBagConstraints.BOTH;

    gameNameLabel = new JLabel("Kill Doctor Lucky");
    gameNameLabel.setFont(new Font("Arial", Font.ITALIC, 42));
    gameNameLabel.setHorizontalAlignment(JLabel.CENTER);
    gameNameLabel.setVerticalAlignment(JLabel.CENTER);
    gbc.insets = new Insets(100, 0, 0,  0);
    add(gameNameLabel, gbc);

    gbc.gridy = 1;

    authorLabel = new JLabel("Zack Hu");
    authorLabel.setFont(new Font("Arial", Font.PLAIN, 22));
    authorLabel.setHorizontalAlignment(JLabel.RIGHT);
    authorLabel.setVerticalAlignment(JLabel.CENTER);
    gbc.insets = new Insets(0, 0, 100, 70);
    add(authorLabel, gbc);
  }
}
