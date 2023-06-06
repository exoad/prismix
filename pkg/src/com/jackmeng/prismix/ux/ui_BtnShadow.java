// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.border.AbstractBorder;

public class ui_BtnShadow extends AbstractBorder
{
  private final int thickness_;

  public ui_BtnShadow(int thickness_)
  {
    this.thickness_ = thickness_;
  }

  public void paintBorder(Component c, Graphics gr, int x, int y, int width,
      int height)
  {
    Graphics2D g = (Graphics2D) gr;
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    Color oldColor = g.getColor();
    int i;

    for (i = 0; i < thickness_; i++)
    {
      g.setColor(Color.white);
      g.drawRect(x + i, y + i, width - i - i - 1, height - i - i - 1);
    }
    for (i = 0; i < thickness_ / 2; i++)
    {
      g.setColor(Color.black);
      g.drawLine(x + i, y + i, (width - x) - (i * 2), y + i); // Top Outer Edge
      g.drawLine(x + i, y + i, x + i, (height - y) - (i * 2)); // Left Outer Edge
    }
    for (i = thickness_ / 2; i < thickness_; i++)
    {
      g.setColor(Color.gray);
      g.drawLine(x + i, y + i, (width - x) - (i * 2), y + i); // Top Inner Edge
      g.drawLine(x + i, y + i, x + i, (height - y) - (i * 2)); // Left Inner Edge

    }
    g.setColor(oldColor);
  }

  public Insets getBorderInsets(Component c)
  {
    return new Insets(thickness_, thickness_, thickness_, thickness_);
  }
}