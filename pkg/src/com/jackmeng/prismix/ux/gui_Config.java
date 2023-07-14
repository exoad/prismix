
// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.text.MessageFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.jm_Prismix;
import com.jackmeng.prismix.use_Maker;
import com.jackmeng.prismix.uwu;
import com.jackmeng.prismix.user.use_LSys;

import static com.jackmeng.prismix.jm_Prismix.*;

public final class gui_Config
    extends
    gui
{

  private JPanel pane;

  public gui_Config()
  {
    super("Primsix ~ Config");
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setPreferredSize(new Dimension(710, 830));

    pane = new JPanel();
    pane.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
    pane.setPreferredSize(getPreferredSize());
    pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
    JLabel title = new JLabel();
    title.setText(uwu.fowmat("assets/text/TEXT_configui_title.html", jm_Prismix._VERSION_ + ""));
    title.setHorizontalAlignment(SwingConstants.LEFT);

    pane.add(title);
    pane.setBorder(BorderFactory.createEmptyBorder());

    String loaded = use_LSys.read_all(_1const.fetcher.file("assets/text/TEXT_config_item_tagtitle.html"));

    _1const.val._foreach((key, value, type) -> {
      JLabel jl = new JLabel(
          MessageFormat.format(loaded, stx_Map.normalize_key(key), _1const.val.get_description(key)));
      jl.setHorizontalAlignment(SwingConstants.LEFT);

      JPanel wrapper = new JPanel();
      wrapper.setOpaque(true);
      /*------------------------------------------------------------------------------------------- /
      / wrapper.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 0));                             /
      / wrapper.setBackground(new Color(255, 255, 255, 75)); // 75 ~ 0.3*255 quick math ^w^ oh yea! /
      /--------------------------------------------------------------------------------------------*/

      wrapper.setLayout(new BorderLayout());
      wrapper.add(jl, BorderLayout.CENTER);

      switch (type) { // most likely assured to be a valid type, else is a bug
        case stx_Map.Bool:
          ui_FatSlider toggle = new ui_FatSlider("True | On", "False | Off", ux_Theme._theme.get_awt("teal"),
              ux_Theme._theme.get_awt("rose"), new Color(230, 230, 230), (Boolean) _1const.val.parse(key).get(), 10,
              16, 2, 3);
          toggle.setAlignmentY(Component.CENTER_ALIGNMENT);
          toggle.setPreferredSize(new Dimension(40, 24));
          JPanel temp_wrap = new JPanel();
          temp_wrap.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
          temp_wrap.setLayout(new GridBagLayout());
          // took me too long lol
          GridBagConstraints gbc = new GridBagConstraints();
          gbc.anchor = GridBagConstraints.CENTER;
          temp_wrap.add(toggle, gbc);
          wrapper.add(temp_wrap, BorderLayout.EAST);
          break;
        case stx_Map.StrBound:
          JComboBox< String > list = new JComboBox<>();
          for (String r : _1const.val.get_allowed(key))
            list.addItem(r);
          JPanel temp_wrap1 = new JPanel();
          temp_wrap1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
          temp_wrap1.setLayout(new GridBagLayout());
          GridBagConstraints gbc1 = new GridBagConstraints();
          gbc1.anchor = GridBagConstraints.CENTER;
          temp_wrap1.add(list, gbc1);
          wrapper.add(temp_wrap1, BorderLayout.EAST);
          break;
        case stx_Map.IntBound:
          JSlider slider = new JSlider(SwingConstants.HORIZONTAL);

          break;
        case stx_Map.Any:
      }

      pane.add(wrapper);
      pane.add(Box.createVerticalStrut(10));
      pane.add(new ui_Line(ux_Theme._theme.get_awt("rose"), 4, true, 2));
    });
    pane.add(Box.createVerticalGlue());
    pane.setPreferredSize(stx_Helper.from_h(getPreferredSize().height));

    JScrollPane jsp = new JScrollPane(pane);
    jsp.setBorder(BorderFactory.createEmptyBorder());
    jsp.setPreferredSize(pane.getPreferredSize());

    jsp.getVerticalScrollBar().setBackground(Color.black);
    jsp.getVerticalScrollBar().setUnitIncrement(7);
    jsp.getHorizontalScrollBar().setBackground(Color.black);

    getContentPane().add(jsp);

    log("CONF_GUI", "Loaded the GUI");
  }

}