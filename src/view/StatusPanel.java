package view;

import controller.Command;
import controller.Controller;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import model.Item;
import model.Player;
import model.ReadOnlyModel;

public class StatusPanel extends JPanel {
  public static int WIDTH = 400;
  public static int HEIGHT = 900;
  private ReadOnlyModel readOnlyModel;
  private Controller controller;
  private JTextArea consoleTextArea;
  private JScrollPane consoleTextAreaScrollPane;
  private float cnt;

  private final int BASE_OPTION = 240;
  private final int INTERVAL_OPTION = 35;
  private final int BASE_CONSOLE = 460;

  public StatusPanel(ReadOnlyModel readOnlyModel) {
    this.readOnlyModel = readOnlyModel;

    consoleTextArea = new JTextArea();
    consoleTextArea.setEditable(false);
    consoleTextArea.setPreferredSize(new Dimension(800, 300));
    consoleTextArea.setTabSize(2);

    Font font = new Font("Arial", Font.PLAIN, 18);
    consoleTextArea.setFont(font);

    // Redirect System.out to the custom PrintStream
    System.setOut(new PrintStream(new TextAreaOutputStream(consoleTextArea)));

    consoleTextAreaScrollPane = new JScrollPane(consoleTextArea);
    consoleTextAreaScrollPane.setPreferredSize(new Dimension(350, 340));

    cnt = 0;
    int topMargin = 40;
    int leftMargin = 25;
    int bottomMargin = 30;
    int rightMargin = 25;

    setBorder(new EmptyBorder(topMargin, leftMargin, bottomMargin, rightMargin));

    setLayout(new BorderLayout());
    add(consoleTextAreaScrollPane, BorderLayout.SOUTH);
  }

  public class TextAreaOutputStream extends OutputStream {
    private final Document document;

    public TextAreaOutputStream(JTextArea textArea) {
      this.document = textArea.getDocument();
    }

    @Override
    public void write(int b) {
      try {
        document.insertString(document.getLength(), String.valueOf((char) b), null);
        consoleTextArea.setPreferredSize(new Dimension(800, 300 + (int) cnt));
        cnt += 1;

      } catch (BadLocationException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void connect(Controller controller) {
    this.controller = controller;
  }

  @Override
  public void paintComponent(Graphics g) {
    drawBasicInfo(g);
    drawOptions(g);
    if (readOnlyModel.getTurn() != -1) {
      drawTurnInfo(g);
    }
  }

  private void drawBasicInfo(Graphics g) {
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
    g2d.drawString("Player:", 30, 80);
    g2d.drawString("Holds: ", 30, 120);

    // draw console
    g2d.drawRect(30, BASE_CONSOLE + 30, 340, 270);
  }

  private void drawOptions(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    Map<String, Function<Scanner, Command>> knownCommands = controller.getAvailableCommand();
    g2d.setFont(new Font("Arial", Font.ITALIC, 24));
    g2d.drawString("Move:", 30, BASE_OPTION);
    g2d.drawString("Move Pet:", 30, BASE_OPTION + INTERVAL_OPTION);
    g2d.drawString("Look Around:", 30, BASE_OPTION + 2 * INTERVAL_OPTION);
    if (knownCommands.get("pick item") != null) {
      g2d.drawString("Pick Item:", 30, BASE_OPTION + 3 * INTERVAL_OPTION);
    }
    if (knownCommands.get("attack") != null) {
      g2d.drawString("Attack:", 30, BASE_OPTION + 4 * INTERVAL_OPTION);
    }

    // draw option key
    g2d.setFont(new Font("Arial", Font.PLAIN, 24));
    g2d.drawString("Right Click", 205, BASE_OPTION);
    g2d.drawString("N", 300, BASE_OPTION + INTERVAL_OPTION);
    g2d.drawString("L", 300, BASE_OPTION + 2 * INTERVAL_OPTION);
    if (knownCommands.get("pick item") != null) {
      g2d.drawString("P", 300, BASE_OPTION + 3 * INTERVAL_OPTION);
    }
    if (knownCommands.get("attack") != null) {
      g2d.drawString("A", 300, BASE_OPTION + 4 * INTERVAL_OPTION);
    }
  }

  private void drawTurnInfo(Graphics g) {

    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.BLACK);

    // draw titles
    g2d.setFont(new Font("Arial", Font.BOLD, 28));
    g2d.drawString(String.valueOf(readOnlyModel.getTurn() + 1)
        + " / " + String.valueOf(readOnlyModel.getMaxTurn()), 260, 40);
    g2d.setFont(new Font("Arial", Font.PLAIN, 24));
    Player currentPlayer = readOnlyModel.getCurrentTurnPlayer();
    g2d.drawString(currentPlayer.getName(), 150, 80);
    List<Item> itemList = currentPlayer.getItemList();
    StringBuilder holdItems = new StringBuilder();

    int modifiedFontSize;
    if (itemList.isEmpty()) {
      holdItems.append("[Empty]");
      modifiedFontSize = 24;
    } else {
      for (Item item: itemList) {
        holdItems.append(" ").append(item.getName()).append("(").append(item.getDamage()).append("),");
      }
      holdItems = holdItems.deleteCharAt(0);
      holdItems = holdItems.deleteCharAt(holdItems.length() - 1);

      modifiedFontSize = 24 / itemList.size() + 2;
    }

    g2d.setFont(new Font("Arial", Font.PLAIN, modifiedFontSize));
    g2d.drawString(holdItems.toString(), 150 - 10 * itemList.size(), 120);
  }

//  private void drawConsoleInfo() {
//    // Clear the console text area before updating
////    consoleTextArea.setText("");
//
//    consoleTextArea.append("hello world");
//
//    // Ensure the console is scrolled to the bottom to show the latest output
//    JScrollBar verticalScrollBar = consoleTextAreaScrollPane.getVerticalScrollBar();
//    verticalScrollBar.setValue(verticalScrollBar.getMaximum());
//  }

}
