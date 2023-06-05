// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;

import com.jackmeng.prismix._colors;
import com.jackmeng.prismix.jm_Prismix;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.stl.stl_AnsiColors;
import com.jackmeng.stl.stl_AnsiMake;
import com.jackmeng.stl.stl_Colors;
import com.jackmeng.stl.stl_Struct;

// WIP and is unused
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

  // ! All color components should be treated as rgb values
  private ThemeType mainTheme;
  private float[] dominant;
  // name, color(StringHex)
  private Map< String, String > colorCodes;

  private ux_Theme()
  {
    mainTheme = ThemeType.DARK;
    dominant = extend_stl_Colors.awt_strip_rgba(stl_Colors.hexToRGB(_colors.ROSE));
    colorCodes = new HashMap<>();
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

  public ThemeType theme()
  {
    return mainTheme;
  }

  public void theme(ThemeType e)
  {
    this.mainTheme = e;
  }

  public float[] dominant()
  {
    return dominant;
  }

  public String get(String colorName)
  {
    colorName = colorName.toLowerCase();
    return colorCodes.containsKey(colorName) ? colorCodes.get(colorName)
        : extend_stl_Colors.RGBToHex((int) dominant[0], (int) dominant[1], (int) dominant[2]);
  }

  public void dominant(float[] dominant) // very bad to use!
  {
    this.dominant = dominant;
    System.out.println(new stl_AnsiMake(stl_AnsiColors.YELLOW_BG,
        "[UX_THEME] Themer " + hashCode() + " made an unsuggested move using dominanet(float[])!"));
    SwingUtilities.invokeLater(() -> SwingUtilities.updateComponentTreeUI(ux.ux.getMainUI()));

  }

  public void dominant(String dominant) // suggested
  {
    this.dominant = extend_stl_Colors.stripHex(dominant);
    SwingUtilities.invokeLater(() -> SwingUtilities.updateComponentTreeUI(ux.ux.getMainUI()));
  }

  public synchronized void reload()
  {

  }

}