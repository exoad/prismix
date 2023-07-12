// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import com.jackmeng.ansicolors.jm_Ansi;
import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.use_Maker;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.stl.stl_Callback;
import com.jackmeng.stl.stl_Colors;
import com.jackmeng.stl.stl_Struct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Optional;

import static com.jackmeng.prismix.jm_Prismix.*;

public class ui_Tag
    extends JButton

{
  @SuppressWarnings("unchecked") private static ActionListener colorListener = ev -> {

    ui_Tag tag = (ui_Tag) ev.getSource();
    if (tag.isVisible())
    {
      if (tag.copyToClipboard || tag.depositsInPool)
      {
        java.util.List< stl_Struct.struct_Pair< String, stl_Callback< Void, Void > > > options = new ArrayList<>();
        if (tag.depositsInPool)
          options.add(stl_Struct.make_pair("Inspect color", (stl_Callback< Void, Void >) nil -> {
            _1const.COLOR_ENQ.dispatch(stl_Struct.make_pair(stl_Colors.hexToRGB(tag.getText()), false));
            log("UICTAG",
                jm_Ansi.make().green().toString("{2} Dispatched the target color to the global inspect queue: ")
                    + tag.getText());
            return (Void) null;
          }));
        if (tag.copyToClipboard)
          options.add(stl_Struct.make_pair("Copy to clipboard", (stl_Callback< Void, Void >) nil -> {
            Toolkit.getDefaultToolkit().getSystemClipboard()
                .setContents(new java.awt.datatransfer.StringSelection(tag.getText()), null);
            log("UICTAG",
                jm_Ansi.make().green().toString("{1} Copied the target color to the clipboard: ") + tag.getText());
            return (Void) null;
          }));

        if (tag.copyToClipboard && tag.depositsInPool)
          options.add(stl_Struct.make_pair("Inspect & Copy", (stl_Callback< Void, Void >) nil -> {
            Toolkit.getDefaultToolkit().getSystemClipboard()
                .setContents(new java.awt.datatransfer.StringSelection(tag.getText()), null);
            _1const.COLOR_ENQ.dispatch(stl_Struct.make_pair(stl_Colors.hexToRGB(tag.getText()), false));

            log("UICTAG", jm_Ansi.make().green().toString(
                "{3} Dispatched & Copied the target color: ")
                + tag.getText());
            return (Void) null;
          }));
        Optional< JPopupMenu > menu = use_Maker.jpop(tag.getText(), options.toArray(new stl_Struct.struct_Pair[0]));
        log("UICTAG", "Action received the desired state!");
        menu.ifPresent(x -> {
          java.awt.Point pt = new Point(__ux._ux.getMainUI().getMousePosition().x + 15,
              __ux._ux.getMainUI().getMousePosition().y);
          log("UICTAG", "Rendering the final popup menu to the screen at: " + pt.x + ","
              + pt.y);
          ((javax.swing.JPopupMenu) x).show(__ux._ux.getMainUI(), pt.x, pt.y);
        });
      }
    }
  };

  private boolean depositsInPool, copyToClipboard;

  public ui_Tag()
  {
    this(Color.black, true, true);
  }

  public ui_Tag(Color initialColor, boolean depositsInPool, boolean copyToClipboard)
  {
    sync(initialColor);
    this.depositsInPool = depositsInPool;
    this.copyToClipboard = copyToClipboard;
    addActionListener(colorListener);
    ux_Theme.based_set_rrect(this);
  }

  public void sync(Color initialColor)
  {
    setBackground(initialColor);
    setForeground(extend_stl_Colors
        .awt_remake(extend_stl_Colors.binary_fg_decider(extend_stl_Colors.awt_strip_rgba(initialColor))));
    setText(extend_stl_Colors.RGBToHex(initialColor.getRed(), initialColor.getGreen(), initialColor.getBlue()));
    setToolTipText(getText());

  }

  public Color color()
  {
    return getBackground();
  }

}