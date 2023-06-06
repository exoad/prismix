// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.test;

import javax.swing.*;
import java.awt.*;

public class test_BorderCurve extends JPanel
{

  @Override protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    int width = getWidth();
    int height = getHeight();
    int arcSize = 35;

    Graphics2D g2 = (Graphics2D) g;
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

  public static void main(String[] args)
  {
    JFrame frame = new JFrame("uwu");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    test_BorderCurve panel = new test_BorderCurve();
    panel.setBackground(Color.WHITE);

    frame.add(panel);
    frame.pack();
    frame.setVisible(true);
  }
}