
// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.user.use_LSys;

import static com.jackmeng.prismix.jm_Prismix.*;

public final class gui_Config
    extends
    gui
{
  public gui_Config()
  {
    super("Primsix ~ Config");
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setPreferredSize(new Dimension(710, 830));
    JLabel title = new JLabel(use_LSys.read_all(_1const.fetcher.file("assets/text/TEXT_configui_title.html")));
    title.setHorizontalAlignment(SwingConstants.LEFT);

    getContentPane().add(title);

    log("CONF_GUI", "Loaded the GUI");
  }
}