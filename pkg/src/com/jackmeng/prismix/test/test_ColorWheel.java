package com.jackmeng.prismix.test;
// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class test_ColorWheel extends JPanel
{

  private Color selectedColor;

  public test_ColorWheel()
  {
    selectedColor = Color.WHITE;
    setPreferredSize(new Dimension(300, 300));
    addMouseListener(new MouseAdapter() {
      @Override public void mouseClicked(MouseEvent e)
      {
        int x = e.getX();
        int y = e.getY();
        int radius = getWidth() / 2;
        double dx = (double) x - radius;
        double dy = (double) y - radius;
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance <= radius)
        {
          double theta = Math.atan2(dy, dx);
          if (theta < 0)
            theta += 2 * Math.PI;
          float hue = (float) (theta / (2 * Math.PI));
          selectedColor = Color.getHSBColor(hue, 1.0f, 1.0f);
          repaint();
        }
      }
    });
  }

  @Override protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    int width = getWidth();
    int height = getHeight();
    int radius = Math.min(width, height) / 2;

    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    for (int angle = 0; angle < 360; angle++)
    {
      float hue = (float) angle / 360;
      g2d.setColor(Color.getHSBColor(hue, 1.0f, 1.0f));
      double theta = Math.toRadians(angle);
      double x = width / 2D + radius * Math.cos(theta);
      double y = height / 2D + radius * Math.sin(theta);
      g2d.draw(new Ellipse2D.Double(x, y, 1, 1));
    }

    g2d.setColor(selectedColor);
    g2d.setStroke(new BasicStroke(2.0f));
    g2d.drawOval(width / 2 - 10, height / 2 - 10, 20, 20);

    g2d.dispose();
  }

  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame("Color Wheel");
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      test_ColorWheel colorWheel = new test_ColorWheel();
      frame.add(colorWheel);
      frame.pack();
      frame.setVisible(true);
    });
  }
}
