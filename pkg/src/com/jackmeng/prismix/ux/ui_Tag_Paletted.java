package com.jackmeng.prismix.ux;

import static com.jackmeng.prismix.jm_Prismix.log;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.jackmeng.prismix.stl.extend_stl_Colors;

/**
 * Similar to ui_Tag except for that it adds an extra option for adding a
 * palette
 * chooser
 *
 * @author Jack Meng
 */
public class ui_Tag_Paletted
    extends ui_Tag
{
  private static ActionListener paletteListener = ev -> {
    ui_Tag_Paletted tag = (ui_Tag_Paletted) ev.getSource();
    String color = tag.getText();

    log("PTAG", "Building a PopupMenu for: " + tag.hashCode());
    JPopupMenu menu = new JPopupMenu("Operations");

    JMenuItem printToConsole = new JMenuItem("Print to console");
    printToConsole.addActionListener(evrr -> log("USER", "Requested: " + color));

    menu.add(printToConsole);

    JMenu addToPalettes = new JMenu("Add to palette");

    ux_Palette.PALETTES.forEach((name, pool) -> {
      JMenuItem e = new JMenuItem(name);
      e.setBorderPainted(false);
      e.addActionListener(evrrrr -> pool.append(extend_stl_Colors.stripHex(color)));
      addToPalettes.add(e);
    });

    menu.add(addToPalettes);

    java.awt.Point pt = new Point(__ux._ux.getMainUI().getMousePosition().x + 15,
        __ux._ux.getMainUI().getMousePosition().y + 20);
    menu.show(__ux._ux.getMainUI(), pt.x, pt.y);

    log("PTAG", "Showing PopupMenu at: " + pt.x + "," + pt.y);
  };

  public ui_Tag_Paletted()
  {
    this(Color.black, true, true);
  }

  public ui_Tag_Paletted(Color initialColor, boolean depositsInPool, boolean copyToClipboard)
  {
    super(initialColor, depositsInPool, copyToClipboard);
    addActionListener(paletteListener);
  }

}