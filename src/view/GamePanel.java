package view;

import controller.Controller;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import model.ReadOnlyModel;

public class GamePanel extends JPanel {
  ReadOnlyModel readOnlyModel;
  public static int WIDTH = 1200;
  public static int HEIGHT = 900;
  private JMenu menu;
  private JScrollPane scrollPane;
  private StatusPanel statusPanel;

  public GamePanel(ReadOnlyModel readOnlyModel) {
    this.readOnlyModel = readOnlyModel;
    setLayout(new BorderLayout());

    scrollPane = new JScrollPane(new MapPanel(readOnlyModel),
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    statusPanel = new StatusPanel(readOnlyModel);

    scrollPane.setPreferredSize(new Dimension(800, HEIGHT));
    statusPanel.setPreferredSize(new Dimension(400, HEIGHT));

    add(scrollPane, BorderLayout.WEST);
    add(statusPanel, BorderLayout.EAST);

    setUpMenu();
  }

  public void connect(Controller listener) {
    scrollPane.addMouseListener(new MapClickListener(listener));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    scrollPane.paintComponents(g2d);
    statusPanel.paintComponent(g2d);
  }

  private void setUpMenu() {
    // A menu with start game, setting, and exit
    JMenuBar menuBar = new JMenuBar();
    menu = new JMenu("Options");

    JMenuItem startGameMenuItem = new JMenuItem("Start Game");
    JMenuItem settingMenuItem = new JMenuItem("Setting");
    JMenuItem exitMenuItem = new JMenuItem("Exit");

    startGameMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("Start game clicked");
      }
    });

    settingMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("Setting clicked");
      }
    });

    exitMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("Exit clicked");
      }
    });

    menu.add(startGameMenuItem);
    menu.add(settingMenuItem);
    menu.addSeparator();
    menu.add(exitMenuItem);

    menuBar.add(menu);
    add(menuBar, BorderLayout.NORTH);
  }


  private static void showPopupDialog(JFrame parentFrame) {
    JDialog popupDialog = new JDialog(parentFrame, "Game Setting", true);
    popupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    JPanel popupPanel = new JPanel(new GridLayout(3, 1));

    // 1. Path selector to select the path to the file
    JTextField pathTextField = new JTextField();
    JButton chooseFileButton = new JButton("Choose File");

    chooseFileButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(popupDialog);

        if (result == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          pathTextField.setText(selectedFile.getAbsolutePath());
        }
      }
    });

    // 2. Editable textbox for player number
    JTextField playerNumberTextField = new JTextField();

    popupPanel.add(pathTextField);
    popupPanel.add(chooseFileButton);
    popupPanel.add(playerNumberTextField);

    // Add the popup panel to the dialog content pane
    popupDialog.getContentPane().add(popupPanel);

    JButton confirmButton = new JButton("Confirm");
    confirmButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Access the selected path and player number here
        String selectedPath = pathTextField.getText();
        String playerNumber = playerNumberTextField.getText();

        // You can perform further actions with the selectedPath and playerNumber
        // For now, let's print them
        System.out.println("Selected Path: " + selectedPath);
        System.out.println("Player Number: " + playerNumber);

        // Close the dialog
        popupDialog.dispose();
      }
    });

    // Add the Confirm button to the dialog
    popupDialog.add(confirmButton, BorderLayout.SOUTH);

    // Set the dialog size
    popupDialog.setSize(400, 200);
    popupDialog.setLocationRelativeTo(parentFrame); // 居中显示相对于父窗口

    // Make the dialog visible
    popupDialog.setVisible(true);
  }

}
