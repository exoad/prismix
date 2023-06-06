
package com.jackmeng.prismix.ux;

import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;

import com.formdev.flatlaf.intellijthemes.FlatCarbonIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatGrayIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatHighContrastIJTheme;
import com.jackmeng.prismix._colors;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.stl.stl_AnsiColors;
import com.jackmeng.stl.stl_AnsiMake;

public final class ux_Theme
{
  public enum ThemeType {
    GRAY("t_Gray"), DARK("t_Dark"), LIGHT("t_Light");

    final String code;

    ThemeType(String code)
    {
      this.code = code;
    }

    public String code()
    {
      return code;
    }
  }

  public static final ux_Theme _theme = new ux_Theme();

  public static synchronized void light_preset_1()
  {
    _theme.dominant(_colors.ROSE);
    _theme.secondary(_colors.MINT);
    _theme.theme(ThemeType.LIGHT);
  }

  public static synchronized void dark_preset_1()
  {
    _theme.dominant(_colors.GRAPEFRUIT);
    _theme.dominant(_colors.MAGENTA);
    _theme.theme(ThemeType.DARK);
  }

  private ThemeType mainTheme;
  private float[] dominant, secondary;

  public static final Map< String, String > colorCodes = new HashMap<>();
  static
  {
    colorCodes.put("rose", _colors.ROSE);
    colorCodes.put("ocean", _colors.OCEAN);
    colorCodes.put("blue", _colors.BLUE);
    colorCodes.put("cyan", _colors.CYAN);
    colorCodes.put("teal", _colors.TEAL);
    colorCodes.put("green", _colors.GREEN);
    colorCodes.put("yellow", _colors.YELLOW);
    colorCodes.put("orange", _colors.ORANGE);
    colorCodes.put("red", _colors.RED);
    colorCodes.put("magenta", _colors.MAGENTA);
    colorCodes.put("purple", _colors.PURPLE);
  }

  private ux_Theme()
  {
    // default theme
    dominant(_colors.ROSE);
    secondary(_colors.MINT);
    theme(ThemeType.LIGHT);
  }

  public ThemeType theme()
  {
    return mainTheme;
  }

  public void theme(ThemeType e)
  {
    this.mainTheme = e;
    reload();
  }

  public float[] dominant()
  {
    return dominant;
  }

  public float[] secondary()
  {
    return secondary;
  }

  public float[] fg()
  {
    return extend_stl_Colors.binary_fg_decider(mainTheme == ThemeType.DARK ? new float[] { 0F, 0F, 0F }
        : mainTheme == ThemeType.LIGHT ? new float[] { 255F, 255F, 255F }
            : new float[] { 255F / 2F, 255F / 2F, 255F / 2F });
  }

  public java.awt.Color fg_awt()
  {
    return extend_stl_Colors.awt_remake(fg());
  }

  public java.awt.Color secondary_awt()
  {
    return extend_stl_Colors.awt_remake(secondary);
  }

  public java.awt.Color dominant_awt()
  {
    return extend_stl_Colors.awt_remake(dominant);
  }

  public String get(String colorName)
  {
    colorName = colorName.toLowerCase();
    return colorCodes.containsKey(colorName) ? colorCodes.get(colorName)
        : extend_stl_Colors.RGBToHex((int) dominant[0], (int) dominant[1], (int) dominant[2]);
  }

  public void dominant(float[] dominant)
  {
    this.dominant = dominant;
    System.out.println(new stl_AnsiMake(stl_AnsiColors.YELLOW_BG,
        "[UX_THEME] Themer " + hashCode() + " made an unsuggested move using **dominant(float[])!"));
    SwingUtilities.invokeLater(() -> SwingUtilities.updateComponentTreeUI(ux.ux.getMainUI()));
  }

  public void secondary(float[] secondary)
  {
    this.secondary = secondary;
    System.out.println(new stl_AnsiMake(stl_AnsiColors.YELLOW_BG,
        "[UX_THEME] Themer " + hashCode() + " made an unsuggested move using **secondary(float[])!"));
    SwingUtilities.invokeLater(() -> SwingUtilities.updateComponentTreeUI(ux.ux.getMainUI()));
  }

  public void secondary(String secondary)
  {
    this.secondary = extend_stl_Colors.stripHex(secondary);
    SwingUtilities.invokeLater(() -> SwingUtilities.updateComponentTreeUI(ux.ux.getMainUI()));
  }

  public void dominant(String dominant)
  {
    this.dominant = extend_stl_Colors.stripHex(dominant);
    SwingUtilities.invokeLater(() -> SwingUtilities.updateComponentTreeUI(ux.ux.getMainUI()));
  }

  public synchronized void reload() // reloads the master theme
  {
    SwingUtilities.invokeLater(() -> {
      try
      {
        ColorUIResource rsc_dominant_1 = new ColorUIResource(ux_Theme._theme.dominant_awt());
        UIManager.put("ScrollBar.thumb", rsc_dominant_1);
        UIManager.put("Scrollbar.pressedThumbColor", rsc_dominant_1);
        UIManager.put("ScrollBar.hoverThumbColor", rsc_dominant_1);
        UIManager.put("Component.focusColor", rsc_dominant_1);
        UIManager.put("Component.focusedBorderColor", rsc_dominant_1);
        UIManager.put("TabbedPane.underlineColor", rsc_dominant_1);
        UIManager.setLookAndFeel(theme() == ThemeType.DARK ? new FlatHighContrastIJTheme()
            : theme() == ThemeType.LIGHT ? new FlatGrayIJTheme() : new FlatCarbonIJTheme());
        SwingUtilities.updateComponentTreeUI(ux.ux.getMainUI());
        ux.ux.getMainUI().pack();
      } catch (UnsupportedLookAndFeelException e)
      {
        e.printStackTrace();
      }
    });
  }

}