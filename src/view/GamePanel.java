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
  private JScrollPane scrollPane;
  private StatusPanel statusPanel;
  private JFrame jFrame;
  private Controller controller;


  public GamePanel(ReadOnlyModel readOnlyModel, JFrame jFrame) {
    this.readOnlyModel = readOnlyModel;
    this.jFrame = jFrame;
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
    this.controller = listener;
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
    JMenuItem loadMap = new JMenuItem("Load map");
    JMenuItem restartGame = new JMenuItem("Restart Game");
    JMenuItem exitGame = new JMenuItem("Exit");

    loadMap.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        selectFile(jFrame);
      }
    });

    restartGame.addActionListener(e -> System.out.println("Restart clicked"));

    exitGame.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("Exit clicked");
      }
    });

    menuBar.add(loadMap);
    menuBar.add(restartGame);
    menuBar.add(exitGame);
    menuBar.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 0)); // Adjust the gap here

    add(menuBar, BorderLayout.NORTH);
  }

  private void addPlayer(JFrame parentFrame) {
    JDialog popupDialog = new JDialog(parentFrame, "Add players", true);
    popupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);


  }

  private void selectFile(JFrame parentFrame) {
    JDialog popupDialog = new JDialog(parentFrame, "Game Setting", true);
    popupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    // Use a FlowLayout with horizontal and vertical gaps
    JPanel popupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

    // 1. Path selector to select the path to the file
    JTextField pathTextField = new JTextField("path to the map");
    pathTextField.setColumns(20);  // Set the number of columns
    pathTextField.setHorizontalAlignment(JTextField.CENTER);
    pathTextField.setEditable(false);
    JButton chooseFileButton = new JButton("Load map");

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

    popupPanel.add(pathTextField);
    popupPanel.add(chooseFileButton);

    // Add the popup panel to the dialog content pane
    popupDialog.getContentPane().add(popupPanel);

    JButton confirmButton = new JButton("Continue");
    confirmButton.setPreferredSize(new Dimension(100, 30));

    JButton cancelButton = new JButton("Cancel");
    cancelButton.setPreferredSize(new Dimension(100, 30));

    cancelButton.addActionListener(e -> popupDialog.dispose());
    confirmButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Access the selected path and player number here
        String selectedPath = pathTextField.getText();

        controller.initializeWorld(selectedPath);
        addPlayer(parentFrame);

        // Close the dialog
        popupDialog.dispose();
      }
    });

    // Create a panel for the button with a FlowLayout
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.add(cancelButton);
    buttonPanel.add(confirmButton);

    // Add the Confirm button to the dialog
    popupDialog.add(buttonPanel, BorderLayout.SOUTH);

    // Set the dialog size
    popupDialog.setSize(400, 120);
    popupDialog.setLocationRelativeTo(parentFrame);

    // Make the dialog visible
    popupDialog.setVisible(true);
  }

}
