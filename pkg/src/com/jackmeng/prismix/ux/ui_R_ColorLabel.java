// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import javax.swing.JLabel;
import java.awt.*;

/**
 * Color label
 */
public class ui_R_ColorLabel extends JLabel
{
  private int cornerRadius, borderThickness;
  private Color borderColor;

  public ui_R_ColorLabel(String text, int cornerRadius, Color borderColor, int borderThickness)
  {
    super(text);
    this.cornerRadius = cornerRadius;
    this.borderColor = borderColor;
    this.borderThickness = borderThickness;
  }

  public void borderColor(Color r)
  {
    this.borderColor = r;
    repaint(70L);
  }

  public Color borderColor()
  {
    return borderColor;
  }

  public void borderThickness(int i)
  {
    this.borderThickness = i;
    repaint(70L);
  }

  public int borderThickness()
  {
    return borderThickness;
  }

  @Override public void paintComponent(Graphics g)
  {
    Graphics2D g2d = (Graphics2D) g;

    Dimension dimension = getSize();
    int width = dimension.width;
    int height = dimension.height;

    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    /*---------------------------------------------------------------------------------------------------- /
    / g2d.setClip(new RoundRectangle2D.Double(0, 0, width - 1D, height - 1D, cornerRadius, cornerRadius)); /
    /-----------------------------------------------------------------------------------------------------*/
    g2d.setColor(this.getBackground());
    g2d.fillRoundRect(borderThickness, borderThickness, width - borderThickness - 2, height - borderThickness - 2,
        cornerRadius, cornerRadius);
    if (borderThickness > 0)
    {
      g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
      g2d.setColor(borderColor);
      g2d.setStroke(new BasicStroke(borderThickness));
      g2d.drawRoundRect(borderThickness, borderThickness, width - borderThickness - 2, height - borderThickness - 2,
          cornerRadius, cornerRadius);
    }
    // super.paintComponent(g2d);

    g2d.dispose();
  }
}