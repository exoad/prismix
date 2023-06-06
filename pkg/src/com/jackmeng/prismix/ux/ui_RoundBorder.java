// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.BasicStroke;
import javax.swing.border.AbstractBorder;

public class ui_RoundBorder extends AbstractBorder
{
  private final int topThickness;
  private final int leftThickness;
  private final int bottomThickness;
  private final int rightThickness;
  private final Color color;

  public ui_RoundBorder(int topThickness, int leftThickness, int bottomThickness, int rightThickness, Color color)
  {
    this.topThickness = topThickness;
    this.leftThickness = leftThickness;
    this.bottomThickness = bottomThickness;
    this.rightThickness = rightThickness;
    this.color = color;
  }

  @Override public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
  {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setColor(color);
    g2.setStroke(new BasicStroke(1));

    for (int i = 0; i < topThickness; i++)
      g2.drawRoundRect(x + i, y + i, width - 2 * i - 1, height - 2 * i - 1, height - 2 * i - 1, height - 2 * i - 1);

    for (int i = 0; i < leftThickness; i++)
      g2.drawRoundRect(x + i, y + i, width - 2 * i - 1, height - 2 * i - 1, width - 2 * i - 1, width - 2 * i - 1);

    for (int i = 0; i < bottomThickness; i++)
      g2.drawRoundRect(x + i, y + i, width - 2 * i - 1, height - 2 * i - 1, height - 2 * i - 1, height - 2 * i - 1);

    for (int i = 0; i < rightThickness; i++)
      g2.drawRoundRect(x + i, y + i, width - 2 * i - 1, height - 2 * i - 1, width - 2 * i - 1, width - 2 * i - 1);

    g2.dispose();
  }

  @Override public Insets getBorderInsets(Component c)
  {
    return new Insets(topThickness, leftThickness, bottomThickness, rightThickness);
  }

  @Override public Insets getBorderInsets(Component c, Insets insets)
  {
    insets.set(topThickness, leftThickness, bottomThickness, rightThickness);
    return insets;
  }
}