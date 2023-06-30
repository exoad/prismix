
package com.jackmeng.prismix.ux;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;

import com.formdev.flatlaf.intellijthemes.FlatCarbonIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatGrayIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatHighContrastIJTheme;
import com.jackmeng.ansicolors.jm_Ansi;
import com.jackmeng.prismix._1const;
import com.jackmeng.prismix._colors;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import static com.jackmeng.prismix.jm_Prismix.*;

public final class ux_Theme
{
  public enum ThemeType {
    GRAY("t_Gray"), DARK("t_Dark"), LIGHT("t_Light");

    final String code;

    ThemeType(final String code)
    {
      this.code = code;
    }

    public String code()
    {
      return this.code;
    }
  }

  public static final ux_Theme _theme = new ux_Theme();

  public static synchronized void light_preset_1()
  {
    ux_Theme._theme.dominant(_colors.ROSE);
    ux_Theme._theme.secondary(_colors.MINT);
    ux_Theme._theme.theme(ThemeType.LIGHT);
  }

  public static synchronized void dark_preset_1()
  {
    ux_Theme._theme.dominant(_colors.GRAPEFRUIT);
    ux_Theme._theme.dominant(_colors.MAGENTA);
    ux_Theme._theme.theme(ThemeType.DARK);
  }

  private ThemeType mainTheme;
  private float[] dominant, secondary;

  public static final Map< String, String > colorCodes = new HashMap<>();
  static
  {
    ux_Theme.colorCodes.put("rose", _colors.ROSE);
    ux_Theme.colorCodes.put("ocean", _colors.OCEAN);
    ux_Theme.colorCodes.put("blue", _colors.BLUE);
    ux_Theme.colorCodes.put("cyan", _colors.CYAN);
    ux_Theme.colorCodes.put("teal", _colors.TEAL);
    ux_Theme.colorCodes.put("green", _colors.GREEN);
    ux_Theme.colorCodes.put("yellow", _colors.YELLOW);
    ux_Theme.colorCodes.put("orange", _colors.ORANGE);
    ux_Theme.colorCodes.put("red", _colors.RED);
    ux_Theme.colorCodes.put("magenta", _colors.MAGENTA);
    ux_Theme.colorCodes.put("purple", _colors.PURPLE);
  }

  public static void based_set_rrect(JButton r)
  {
    r.setBorderPainted(false);

    if (!Boolean.TRUE.equals((Boolean) _1const.val.parse("containers_rounded").get()))
    {
      r.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
    }
  }

  private ux_Theme()
  {
    // default theme
    this.dominant(_colors.ROSE);
    this.secondary(_colors.MINT);
    this.theme(ThemeType.LIGHT);
  }

  public ThemeType theme()
  {
    return this.mainTheme;
  }

  public void theme(final ThemeType e)
  {
    this.mainTheme = e;
    this.reload();
  }

  public float[] dominant()
  {
    return this.dominant;
  }

  public float[] secondary()
  {
    return this.secondary;
  }

  public float[] fg()
  {
    return extend_stl_Colors.binary_fg_decider(this.mainTheme == ThemeType.DARK ? new float[] { 0F, 0F, 0F }
        : this.mainTheme == ThemeType.LIGHT ? new float[] { 255F, 255F, 255F }
            : new float[] { 255F / 2F, 255F / 2F, 255F / 2F });
  }

  public java.awt.Color fg_awt()
  {
    return extend_stl_Colors.awt_remake(this.fg());
  }

  public java.awt.Color secondary_awt()
  {
    return extend_stl_Colors.awt_remake(this.secondary);
  }

  public java.awt.Color dominant_awt()
  {
    return extend_stl_Colors.awt_remake(this.dominant);
  }

  public String get(String colorName)
  {
    colorName = colorName.toLowerCase();
    return ux_Theme.colorCodes.containsKey(colorName) ? ux_Theme.colorCodes.get(colorName)
        : extend_stl_Colors.RGBToHex((int) this.dominant[0], (int) this.dominant[1], (int) this.dominant[2]);
  }

  public void dominant(final float[] dominant)
  {
    this.dominant = dominant;
    log("UXTHEME",
        jm_Ansi.make().yellow_bg().black()
            .toString("Themer " + this.hashCode() + " made an unsuggested move using **dominant(float[])!"));
    SwingUtilities.invokeLater(() -> SwingUtilities.updateComponentTreeUI(ux._ux.getMainUI()));
  }

  public void secondary(final float[] secondary)
  {
    this.secondary = secondary;
    log("UXTHEME",
        jm_Ansi.make().yellow_bg().black()
            .toString("Themer " + this.hashCode() + " made an unsuggested move using **secondary(float[])!"));
    SwingUtilities.invokeLater(() -> SwingUtilities.updateComponentTreeUI(ux._ux.getMainUI()));
  }

  public void secondary(final String secondary)
  {
    this.secondary = extend_stl_Colors.stripHex(secondary);
    SwingUtilities.invokeLater(() -> SwingUtilities.updateComponentTreeUI(ux._ux.getMainUI()));
  }

  public void dominant(final String dominant)
  {
    this.dominant = extend_stl_Colors.stripHex(dominant);
    SwingUtilities.invokeLater(() -> SwingUtilities.updateComponentTreeUI(ux._ux.getMainUI()));
  }

  public synchronized void reload() // reloads the master theme
  {
    SwingUtilities.invokeLater(() -> {
      try
      {
        log("UXTHEME", jm_Ansi.make().blue().toString("Refreshing the entire theme"));
        final ColorUIResource rsc_dominant_1 = new ColorUIResource(ux_Theme._theme.dominant_awt());
        UIManager.put("ScrollBar.thumb", rsc_dominant_1);
        UIManager.put("Scrollbar.pressedThumbColor", rsc_dominant_1);
        UIManager.put("ScrollBar.hoverThumbColor", rsc_dominant_1);
        UIManager.put("Component.focusColor", rsc_dominant_1.darker());
        UIManager.put("Component.focusedBorderColor", rsc_dominant_1);
        UIManager.put("TabbedPane.underlineColor", rsc_dominant_1);
        UIManager.put("TabbedPane.selectedColor", rsc_dominant_1);
        UIManager.put("TabbedPane.selectedBackground", this.theme() == ThemeType.DARK ? Color.BLACK : Color.WHITE);
        UIManager.setLookAndFeel(this.theme() == ThemeType.DARK ? new FlatHighContrastIJTheme()
            : this.theme() == ThemeType.LIGHT ? new FlatGrayIJTheme() : new FlatCarbonIJTheme());
        SwingUtilities.updateComponentTreeUI(ux._ux.getMainUI());
        ux._ux.getMainUI().pack();
      } catch (final UnsupportedLookAndFeelException e)
      {
        e.printStackTrace();
      }
    });
  }

}