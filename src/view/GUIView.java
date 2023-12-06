package view;

import controller.Controller;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import model.ReadOnlyModel;

public class GUIView extends JFrame implements View {
  private CardLayout cardLayout;
  private WelcomePanel welcomePanel;
  private GamePanel gamePanel;
  private JPanel cardPanel;
  private Controller controller;

  public GUIView(ReadOnlyModel model) {
    super("Kill Doctor Lucky");
    setLocation(300, 300);
    setMinimumSize(new Dimension(300, 300));
    setSize(WelcomePanel.WIDTH, WelcomePanel.HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    cardLayout = new CardLayout();
    cardPanel = new JPanel(cardLayout);
    welcomePanel = new WelcomePanel();
    gamePanel = new GamePanel(model, this);

    cardPanel.add(welcomePanel, "welcome");
    cardPanel.add(gamePanel, "game");
    cardLayout.show(cardPanel, "welcome");

    getContentPane().add(cardPanel);

    // after displaying welcome panel for 2 seconds, switch to the game panel
    Timer timer = new Timer(2000, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        cardLayout.show(cardPanel, "game");
        setLocation(0,0);
        setSize(GamePanel.WIDTH, GamePanel.HEIGHT);
        repaint();
      }
    });
    timer.setRepeats(false);
    timer.start();
  }

  @Override
  public void connect(Controller listener) {
    this.controller = listener;
    gamePanel.connect(listener);
    this.addKeyListener(new GameKeyListener(listener));
    this.requestFocusInWindow();
  }

  @Override
  public void refresh() {
    gamePanel.repaint();
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

}
