// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import javax.swing.JLabel;
import java.awt.*;
import java.awt.geom.*;

public class ui_RoundLabel extends JLabel
{
  private int cornerRadius;

  public ui_RoundLabel(String text, int cornerRadius)
  {
    super(text);
    this.cornerRadius = cornerRadius;
  }

  @Override protected void paintComponent(Graphics g)
  {
    ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    Dimension dimension = getSize();
    int width = dimension.width;
    int height = dimension.height;

    Shape roundedRectangle = new RoundRectangle2D.Double(0, 0, width - 1D, height - 1D, cornerRadius, cornerRadius);

    g.setColor(getBackground());
    ((Graphics2D) g).fill(roundedRectangle);

    super.paintComponent(g);
  }
}