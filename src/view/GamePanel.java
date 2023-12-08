package view;

import controller.Controller;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import model.ReadOnlyModel;

/**
 * The GamePanel class represents the game panel.
 */
public class GamePanel extends JPanel {

  public static int HEIGHT = 900;
  public static int WIDTH = 1200;
  ReadOnlyModel readOnlyModel;
  private JScrollPane scrollPane;
  private StatusPanel statusPanel;
  private JFrame jframe;
  private Controller controller;

  /**
   * Constructor for GamePanel.
   *
   * @param readOnlyModel the read only model
   * @param jframe        the jframe
   */
  public GamePanel(ReadOnlyModel readOnlyModel, JFrame jframe) {
    this.readOnlyModel = readOnlyModel;
    this.jframe = jframe;
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

  /**
   * Connect the controller to the game panel.
   *
   * @param listener  the controller
   */
  public void connect(Controller listener) {
    scrollPane.addMouseListener(new MapClickListener(listener));
    statusPanel.connect(listener);
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
    JMenuItem loadMap = new JMenuItem("Load map");
    JMenuItem startGame = new JMenuItem("Start Game");
    JMenuItem exitGame = new JMenuItem("Exit");

    loadMap.addActionListener(e -> {
      selectFile(jframe);
    });

    startGame.addActionListener(e -> {
      if (!readOnlyModel.isInitialized()) {
        showInitializationWarning();
        return;
      }
      setMaxTurn();
    });

    exitGame.addActionListener(e -> {
      showExitConfirmationDialog();
    });

    JMenuBar menuBar = new JMenuBar();

    menuBar.add(loadMap);
    menuBar.add(startGame);
    menuBar.add(exitGame);
    menuBar.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 0)); // Adjust the gap here

    add(menuBar, BorderLayout.NORTH);
  }

  private void setMaxTurn() {
    JDialog maxTurnSetUp = new JDialog(jframe, "Set max turn", true);
    maxTurnSetUp.setSize(270, 120);
    JTextField userInputField = new JTextField();
    userInputField.setColumns(5);
    maxTurnSetUp.setLayout(new GridBagLayout());
    maxTurnSetUp.setLocationRelativeTo(jframe);

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.insets = new Insets(5, 5, 5, 5);

    constraints.gridx = 0;
    constraints.gridy = 0;
    maxTurnSetUp.add(new JLabel("Max turn:"), constraints);

    constraints.gridx = 1;
    maxTurnSetUp.add(userInputField, constraints);

    constraints.gridx = 0;
    constraints.gridy = 1;
    constraints.gridwidth = 2;
    constraints.anchor = GridBagConstraints.CENTER;

    JButton confirmButton = new JButton("Confirm");
    maxTurnSetUp.add(confirmButton, constraints);
    confirmButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String userInput = userInputField.getText();
        controller.setMaxTurn(Integer.valueOf(userInput));
        controller.playGameUnderGui();
        maxTurnSetUp.dispose();
      }
    });
    maxTurnSetUp.setVisible(true);
  }

  private void showInitializationWarning() {
    JOptionPane.showMessageDialog(this,
        "Please load the map before starting.",
        "Initialization Warning", JOptionPane.WARNING_MESSAGE);
  }

  private void showExitConfirmationDialog() {
    int result = JOptionPane.showConfirmDialog(this,
        "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.YES_OPTION) {
      // User clicked Yes, perform exit action
      System.exit(0);
    }
    // If user clicks No or closes the dialog, do nothing
  }

  private void selectFile(JFrame parentFrame) {
    JDialog popupDialog = new JDialog(parentFrame, "Game Setting", true);
    popupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    // 1. Path selector to select the path to the file
    JTextField pathTextField = new JTextField("res/map/mansion.txt");
    pathTextField.setColumns(20);  // Set the number of columns
    pathTextField.setHorizontalAlignment(JTextField.CENTER);
    pathTextField.setEditable(false);
    JButton chooseFileButton = new JButton("Select map");

    chooseFileButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

        int result = fileChooser.showOpenDialog(popupDialog);

        if (result == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          pathTextField.setText(selectedFile.getAbsolutePath());
        }
      }
    });

    // Use a FlowLayout with horizontal and vertical gaps
    JPanel popupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

    popupPanel.add(pathTextField);
    popupPanel.add(chooseFileButton);

    // Add the popup panel to the dialog content pane
    popupDialog.getContentPane().add(popupPanel);

    JButton confirmButton = new JButton("Load map");
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

        // Close the dialog
        popupDialog.dispose();

        PlayerCreationDialog playerCreationDialog =
            new PlayerCreationDialog(jframe, readOnlyModel, controller);
        playerCreationDialog.setVisible(true);
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
