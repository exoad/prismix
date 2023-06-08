// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class test_BorderCurve extends JPanel
{

  @Override protected void paintComponent(final Graphics g)
  {
    super.paintComponent(g);

    final int width = this.getWidth();
    final int height = this.getHeight();
    final int arcSize = 35;

    final Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
    g2.setColor(Color.BLACK);
    g2.setStroke(new BasicStroke(2));

    /*-------------------------------------------------------------------- /
    / g2.drawRoundRect(10, 10, width - 20, height - 20, arcSize, arcSize); /
    /---------------------------------------------------------------------*/

    g2.drawArc(0, height - arcSize * 2, arcSize * 2, arcSize * 2, 0, -180);
    g2.drawLine(arcSize, height, width - arcSize, height);
    g2.drawArc(width - arcSize * 2, height - arcSize * 2, arcSize * 2, arcSize * 2, 0, -180);

    g2.dispose();
  }

  @Override public Dimension getPreferredSize()
  {
    return new Dimension(200, 200);
  }

  public static void main(final String[] args)
  {
    final JFrame frame = new JFrame("uwu");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    final test_BorderCurve panel = new test_BorderCurve();
    panel.setBackground(Color.WHITE);

    frame.add(panel);
    frame.pack();
    frame.setVisible(true);
  }
}