// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

public class ui_RoundBorder extends AbstractBorder
{
  private final int topThickness;
  private final int leftThickness;
  private final int bottomThickness;
  private final int rightThickness;
  private final Color color;

  public ui_RoundBorder(final int topThickness, final int leftThickness, final int bottomThickness, final int rightThickness, final Color color)
  {
    this.topThickness = topThickness;
    this.leftThickness = leftThickness;
    this.bottomThickness = bottomThickness;
    this.rightThickness = rightThickness;
    this.color = color;
  }

  @Override public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height)
  {
    final Graphics2D g2 = (Graphics2D) g.create();
    g2.setColor(this.color);
    g2.setStroke(new BasicStroke(1));

    for (int i = 0; i < this.topThickness; i++)
      g2.drawRoundRect(x + i, y + i, width - 2 * i - 1, height - 2 * i - 1, height - 2 * i - 1, height - 2 * i - 1);

    for (int i = 0; i < this.leftThickness; i++)
      g2.drawRoundRect(x + i, y + i, width - 2 * i - 1, height - 2 * i - 1, width - 2 * i - 1, width - 2 * i - 1);

    for (int i = 0; i < this.bottomThickness; i++)
      g2.drawRoundRect(x + i, y + i, width - 2 * i - 1, height - 2 * i - 1, height - 2 * i - 1, height - 2 * i - 1);

    for (int i = 0; i < this.rightThickness; i++)
      g2.drawRoundRect(x + i, y + i, width - 2 * i - 1, height - 2 * i - 1, width - 2 * i - 1, width - 2 * i - 1);

    g2.dispose();
  }

  @Override public Insets getBorderInsets(final Component c)
  {
    return new Insets(this.topThickness, this.leftThickness, this.bottomThickness, this.rightThickness);
  }

  @Override public Insets getBorderInsets(final Component c, final Insets insets)
  {
    insets.set(this.topThickness, this.leftThickness, this.bottomThickness, this.rightThickness);
    return insets;
  }
}