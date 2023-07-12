// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import java.awt.Point;
import java.awt.Color;
import java.awt.Dimension;

import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.prismix.ux.ui_Tag.ui_PTag;

public final class ux_Helper
{
  private ux_Helper()
  {
  }

  public static class Exposed_UISlider
      extends JSlider
  {

  }

  public static JButton quick_btn(String name, Runnable r)
  {
    JButton t = new JButton(name);
    t.addActionListener(e -> r.run());
    return t;
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

  public static JScrollPane cpick_zoomer_jsp(JComponent component) // originally made for the gradient rectangle color
                                                                   // picker
  {
    ui_LazyViewport viewport = new ui_LazyViewport();
    viewport.setView(component);

    JScrollPane jsp = new JScrollPane();
    jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jsp.setViewportView(viewport);

    return jsp;
  }


  public static ui_PTag history_palette_btn(Color clr, int w, int h)
  {
    ui_PTag r = new ui_PTag(clr, true, true);
    r.setPreferredSize(new Dimension(w, h));
    r.setMinimumSize(r.getPreferredSize());
    r.setRolloverEnabled(false);
    r.setFocusPainted(false);
    r.setBorderPainted(false);
    r.setFocusable(false);
    return r;
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