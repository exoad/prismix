package com.jackmeng.prismix.test;
// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class test_ColorWheel extends JPanel
{

  private Color selectedColor;

  public test_ColorWheel()
  {
    this.selectedColor = Color.WHITE;
    this.setPreferredSize(new Dimension(300, 300));
    this.addMouseListener(new MouseAdapter() {
      @Override public void mouseClicked(final MouseEvent e)
      {
        final int x = e.getX();
        final int y = e.getY();
        final int radius = test_ColorWheel.this.getWidth() / 2;
        final double dx = (double) x - radius;
        final double dy = (double) y - radius;
        final double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance <= radius)
        {
          double theta = Math.atan2(dy, dx);
          if (theta < 0)
            theta += 2 * Math.PI;
          final float hue = (float) (theta / (2 * Math.PI));
          test_ColorWheel.this.selectedColor = Color.getHSBColor(hue, 1.0f, 1.0f);
          test_ColorWheel.this.repaint();
        }
      }
    });
  }

  @Override protected void paintComponent(final Graphics g)
  {
    super.paintComponent(g);
    final int width = this.getWidth();
    final int height = this.getHeight();
    final int radius = Math.min(width, height) / 2;

    final Graphics2D g2d = (Graphics2D) g.create();
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    final int numColors = 360; // Number of colors in the rainbow
    final double angleIncrement = 360.0 / numColors;

    for (int i = 0; i < numColors; i++)
    {
      final float saturation = (float) i / numColors;
      g2d.setColor(Color.getHSBColor(0.0f, saturation, 1.0f));
      final double theta = Math.toRadians(i * angleIncrement);
      final double x = width / 2D + radius * Math.cos(theta);
      final double y = height / 2D + radius * Math.sin(theta);
      g2d.fill(new Ellipse2D.Double(x, y, 8, 8));
    }

    g2d.setColor(Color.WHITE); // Set the center color as white
    g2d.fillOval(width / 2 - radius, height / 2 - radius, radius * 2, radius * 2);

    g2d.dispose();

  }

  public static void main(final String[] args)
  {
    SwingUtilities.invokeLater(() -> {
      final JFrame frame = new JFrame("Color Wheel");
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      final test_ColorWheel colorWheel = new test_ColorWheel();
      frame.add(colorWheel);
      frame.pack();
      frame.setVisible(true);
    });
  }
}
