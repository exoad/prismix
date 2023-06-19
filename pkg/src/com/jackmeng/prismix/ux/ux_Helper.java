// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import java.awt.Point;
import java.awt.Dimension;

import com.jackmeng.prismix.stl.extend_stl_Colors;

public final class ux_Helper
{
  private ux_Helper()
  {
  }

  public static Border bottom_container_AttributesBorder(final String name)
  {
    return BorderFactory.createTitledBorder(
        BorderFactory.createEmptyBorder(5, 0, 5, 0),
        "<html><strong><p style=\"font-size:12.5px;color:"
            + (extend_stl_Colors.RGBToHex((int) ux_Theme._theme.dominant()[0],
                (int) ux_Theme._theme.dominant()[1], (int) ux_Theme._theme.dominant()[2]))
            + "\">" + name + "</p></strong></strong>");
  }

  public static Border cpick_suggestions_AttributesBorder(final String name)
  { // basically the same thing as bottom_container_AttributesBorder(String)
    return BorderFactory.createTitledBorder(
        BorderFactory.createEmptyBorder(5, 4, 5, 4),
        "<html><strong><p style=\"font-size:12.5px;color:"
            + (extend_stl_Colors.RGBToHex((int) ux_Theme._theme.dominant()[0],
                (int) ux_Theme._theme.dominant()[1], (int) ux_Theme._theme.dominant()[2]))
            + "\">" + name + "</p></strong></strong>");
  }

  public static boolean within(Point target, Point topLeft, Dimension dim)
  {
    return (target.x <= topLeft.x + dim.width && target.x >= topLeft.x)
        && (target.y <= topLeft.y + dim.height && target.y >= topLeft.y);
  }

  public static Point transform_within(Point raw, Point topLeft, Dimension dim)
  {
    return new Point();
  }

}