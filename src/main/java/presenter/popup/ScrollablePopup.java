package main.java.presenter.popup;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

/**
 * Created by ritobrotoseth on 21/07/18.
 */
public class ScrollablePopup {
  private static final int WINDOW_WIDTH = 400;
  private static final int WINDOW_HEIGHT = 400;
  private static JFrame frame = null;

  public static void openPopupWindow(String content) {
    if (frame != null) {
      frame.setVisible(false); //you can't see me!
      frame.dispose();
    }

    frame = null;
    frame = new JFrame("Stock Alert");
    configureJframeProperties(frame);
    setJframePosition(frame);








    JLabel label = null;
    label = new JLabel();
    setJlabelTextContent(label, content);
    configureJlabelProperties(label);

//    label.invalidate();
//    label.validate();
//    label.repaint();

    JScrollPane scrollableLabel = createAndConfigureJScrollPane(label);

//    scrollableLabel.invalidate();
//    scrollableLabel.validate();
//    scrollableLabel.repaint();

    frame.getContentPane().add(scrollableLabel);

//    frame.invalidate();
//    frame.validate();
//    frame.repaint();

//    refreshFrame();
  }

  private static void configureJframeProperties(JFrame frame) {
    frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //    frame.getContentPane().setBackground(Color.black);
    //    frame.getContentPane().setLayout(new FlowLayout());
  }

  private static void setJframePosition(JFrame frame) {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
    Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
    int x = (int) rect.getMaxX() - frame.getWidth();
    int y = 0;
    frame.setLocation(x, y);
  }

  private static JScrollPane createAndConfigureJScrollPane(JLabel label) {
    JScrollPane scrollableLabel = new JScrollPane(label, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollableLabel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

    return scrollableLabel;
  }

  private static void setJlabelTextContent(JLabel label, String content) {
    label.setText(content);
    label.repaint();
  }

  private static void configureJlabelProperties(JLabel label) {
    label.setBackground(Color.WHITE);
    label.setOpaque(true);
  }

  private static void refreshFrame() {
    SwingUtilities.updateComponentTreeUI(frame);
    frame.invalidate();
    frame.validate();
    frame.repaint();
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    String content = "Some Content";
    Random rand = new Random();

    while (true) {
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          int  n = rand.nextInt(50) + 1;
          String data = content + " " + n;
          System.out.println(data);
          openPopupWindow(data);
        }
      });
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }


  }
}
