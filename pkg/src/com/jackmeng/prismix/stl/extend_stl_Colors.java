// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.stl;

import java.awt.color.ColorSpace;
import java.awt.geom.Point2D;

import com.jackmeng.prismix._1const;

import java.awt.*;
import java.awt.image.*;

public final class extend_stl_Colors
{
  private extend_stl_Colors()
  {
  }

  public static int[] awt_colorspace_AllTypes()
  {
    return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 }; // we dont
                                                                                                           // know why
                                                                                                           // 10 isnt
                                                                                                           // here
  }

  public static String RGBToHex(int r, int g, int b)
  {
    return String.format("#%02x%02x%02x", r, g, b);
  }

  public static String color2hex_2(Color color)
  {
    String hex = Integer.toHexString(color.getRGB() & 0xffffff);
    if (hex.length() < 6)
    {
      if (hex.length() == 5)
        hex = "0" + hex;
      if (hex.length() == 4)
        hex = "00" + hex;
      if (hex.length() == 3)
        hex = "000" + hex;
    }
    hex = "#" + hex;
    return hex;
  }

  public static boolean is_red(float[] e)
  {
    return e[0] > e[1] && e[0] > e[2];
  }

  public static boolean is_blue(float[] e)
  {
    return e[2] > e[1] && e[2] > e[0];
  }

  public static boolean is_green(float[] e)
  {
    return e[1] > e[0] && e[1] > e[2];
  }

  public static float[] binary_fg_decider(float[] bg)
  {
    return extend_stl_Colors
        .awt_strip_rgba(
            (0.2126F * bg[0] + 0.7152F * bg[1] + 0.0722F * bg[2]) / 255F > 0.5F ? Color.BLACK : Color.WHITE); // supplied
                                                                                                              // weights
                                                                                                              // for
                                                                                                              // determining
                                                                                                              // luminance
  }

  public static float[][] tints(float[] ir, int n)
  {
    float[][] generatedTints = new float[n][3];
    generatedTints[0] = ir;
    float red = ir[0], green = ir[1], blue = ir[2], redStep = (255F - red) / (n - 1),
        greenStep = (255F - green) / (n - 1), blueStep = (255F - blue) / (n - 1);

    for (int i = 1; i < n; i++)
    {
      red += redStep;
      green += greenStep;
      blue += blueStep;

      red = Math.max(0.0f, Math.min(red, 255F));
      green = Math.max(0.0f, Math.min(green, 255F));
      blue = Math.max(0.0f, Math.min(blue, 255F));

      generatedTints[i] = new float[] { red, green, blue };
    }

    return generatedTints;
  }

  public static float[][] shades(float[] ir, int n)
  {
    float[][] generatedTints = new float[n][3];
    generatedTints[0] = ir;
    float red = ir[0], green = ir[1], blue = ir[2], maxColor = Math.max(Math.max(red, green), blue),
        blackStep = maxColor / (n - 1);
    for (int i = 1; i < n; i++)
    {
      red -= blackStep;
      green -= blackStep;
      blue -= blackStep;

      red = Math.max(0.0f, Math.min(red, 255F));
      green = Math.max(0.0f, Math.min(green, 255F));
      blue = Math.max(0.0f, Math.min(blue, 255F));

      generatedTints[i] = new float[] { red, green, blue };
    }

    return generatedTints;
  }

  public static float[][] tones(float[] ir, int n)
  {
    float[][] generatedTints = new float[n][3];
    generatedTints[0] = ir;
    float red = ir[0], green = ir[1], blue = ir[2];
    float grayStep = Math.min((255 - red) / (n - 1), Math.min((255 - green) / (n - 1), (255 - blue) / (n - 1)));

    for (int i = 1; i < n; i++)
    {
      red += grayStep;
      green += grayStep;
      blue += grayStep;

      red = Math.max(0.0f, Math.min(red, 255F));
      green = Math.max(0.0f, Math.min(green, 255F));
      blue = Math.max(0.0f, Math.min(blue, 255F));

      generatedTints[i] = new float[] { red, green, blue };
    }

    return generatedTints;
  }
  /*---------------------------------------------------- /
  / private static int getMaxColorIndex(float[] color)   /
  / {                                                    /
  /   int maxIndex = 0;                                  /
  /   for (int i = 1; i < color.length; i++)             /
  /     if (color[i] > color[maxIndex])                  /
  /       maxIndex = i;                                  /
  /   return maxIndex;                                   /
  / }                                                    /
  /                                                      /
  / private static float getMinColorValue(float[] color) /
  / {                                                    /
  /   float minValue = color[0];                         /
  /   for (int i = 1; i < color.length; i++)             /
  /     if (color[i] < minValue)                         /
  /       minValue = color[i];                           /
  /   return minValue;                                   /
  / }                                                    /
  /-----------------------------------------------------*/

  public static BufferedImage cpick_gradient2(int size, Color interest)
  {
    BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = image.createGraphics();
    GradientPaint primary = new GradientPaint(
        0f, 0f, Color.WHITE, size, 0f, interest);
    GradientPaint shade = new GradientPaint(
        0f, 0f, new Color(0, 0, 0, 0),
        0f, size, new Color(0, 0, 0, 255));
    g.setPaint(primary);
    g.fillRect(0, 0, size, size);
    g.setPaint(shade);
    g.fillRect(0, 0, size, size);
    g.dispose();
    return image;
  }

  public static BufferedImage OLD_cpick_gradient(int size, Color interest)
  {
    BufferedImage image = new BufferedImage(
        size, size, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = image.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
    g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
    g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
    g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
    g.setRenderingHint(RenderingHints.KEY_RESOLUTION_VARIANT, RenderingHints.VALUE_RESOLUTION_VARIANT_SIZE_FIT);
    /*------------------------------------------------------------------------------------------------- /
    / LinearGradientPaint lgp_white_to_interest = new LinearGradientPaint(new Point2D.Float(0F, 0F),    /
    /     new Point2D.Float(size, 0F), new float[]{0.1F, 0.2F}, new Color[] { Color.WHITE, interest }); /
    /--------------------------------------------------------------------------------------------------*/
    LinearGradientPaint lgp_shade = new LinearGradientPaint(new Point2D.Float(0F, 0F), new Point2D.Float(0F, size),
        new float[] { 0.25F, 0.85F },
        new Color[] { new Color(Color.BLACK.getRed(), Color.BLACK.getBlue(), Color.BLACK.getBlue(), 0), Color.BLACK });

    /*---------------------------------- /
    / g.setPaint(lgp_white_to_interest); /
    / g.fillRect(0, 0, size, size);      /
    / g.setPaint(lgp_shade);             /
    / g.fillRect(0, 0, size, size);      /
    / g.dispose();                       /
    /-----------------------------------*/

    GradientPaint primary = new GradientPaint(
        0F, 0F, Color.white, size, size, interest);
    /*---------------------------------------- /
    / int rC = shadeColor.getRed();            /
    / int gC = shadeColor.getGreen();          /
    / int bC = shadeColor.getBlue();           /
    / GradientPaint shade = new GradientPaint( /
    /     0F, 0F, new Color(rC, gC, bC, 0),    /
    /     0F, size, shadeColor);               /
    /-----------------------------------------*/
    /*--------------------------------------- /
    / g.setPaint(primary);                    /
    / g.fillRect(0, 0, size, size);           /
    / g.setComposite(AlphaComposite.SrcAtop); /
    / g.setPaint(shade);                      /
    / g.fillRect(0, 0, size, size);           /
    / g.dispose();                            /
    /----------------------------------------*/

    g.setPaint(primary);
    g.fillRect(0, 0, size, size);
    g.setPaint(lgp_shade);
    g.fillRect(0, 0, size, size);
    g.dispose();
    return image;
  }

  public static Color awt_random_Color()
  {
    return new Color(_1const.RNG.nextFloat(), _1const.RNG.nextFloat(), _1const.RNG.nextFloat());
  }

  public static float[] awt_strip_rgba(Color awt)
  {
    return new float[] { awt.getRed(), awt.getGreen(), awt.getBlue(), awt.getAlpha() };
  }

  public static Color awt_remake(float[] rgba)
  {
    return rgba.length == 4 ? new Color(rgba[0] / 255F, rgba[1] / 255F, rgba[2] / 255F, rgba[3] / 255F)
        : rgba.length == 3 ? new Color(rgba[0] / 255F, rgba[1] / 255F, rgba[2] / 255F) : Color.MAGENTA; // debug color
                                                                                                        // == Magenta???
  }

  /*------------------------------------------------------------------------------------------ /
  / didnt use variable declarations and stuffed everything into a single return statement with /
  / ternary operators bc uh idk (hey it works,but not as efficient)                            /
  /-------------------------------------------------------------------------------------------*/

  public static float[] rgbToHsv(float[] rgb)
  {
    return new float[] {
        Math.max(
            rgb[0] / 255F, Math
                .max(rgb[1] / 255F, rgb[2] / 255F)) == Math.min(rgb[0] / 255F,
                    Math.min(rgb[1] / 255F, rgb[2] / 255F)) ? 0
                        : Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F,
                            rgb[2] / 255F)) == rgb[0]
                                / 255F
                                    ? (60
                                        * ((rgb[1] / 255F - rgb[2] / 255F)
                                            / (Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F))
                                                - Math.min(rgb[0] / 255F, Math.min(rgb[1] / 255F, rgb[2] / 255F))))
                                        + 360) % 360
                                    : Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F)) == rgb[1] / 255F
                                        ? (60
                                            * ((rgb[2] / 255F - rgb[0] / 255F) / (Math.max(rgb[0] / 255F,
                                                Math.max(rgb[1] / 255F, rgb[2] / 255F))
                                                - Math.min(rgb[0] / 255F, Math.min(rgb[1] / 255F, rgb[2] / 255F))))
                                            + 120) % 360
                                        : Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F)) == rgb[2]
                                            / 255F
                                                ? (60 * ((rgb[0] / 255F - rgb[1] / 255F) / (Math.max(rgb[0] / 255F,
                                                    Math.max(rgb[1] / 255F, rgb[2] / 255F))
                                                    - Math.min(rgb[0] / 255F, Math.min(rgb[1] / 255F, rgb[2] / 255F))))
                                                    + 240) % 360
                                                : -1,
        Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F)) == 0 ? 0
            : ((Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F))
                - Math.min(rgb[0] / 255F, Math.min(rgb[1] / 255F, rgb[2] / 255F)))
                / Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F))) * 100,
        Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F)) * 100 };
  }

  public static float[] rgbToCmyk(float[] rgb)
  {
    // all elements here are multiplied by 100 to make them not like %
    return new float[] {
        (1 - rgb[0] / 255F - (1 - Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F))))
            / (1 - (1 - Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F)))) * 100F,
        (1 - rgb[1] / 255F - (1 - Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F))))
            / (1 - (1 - Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F)))) * 100F,
        (1 - rgb[2] / 255F - (1 - Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F))))
            / (1 - (1 - Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F)))) * 100F,
        1 - Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F)) * 100F };
  }

  public static float[] rgbToHsl(float[] rgb)
  {
    float M = Math.max(rgb[0], Math.max(rgb[1], rgb[2])), m = Math.min(rgb[0], Math.min(rgb[1], rgb[2]));
    float L = ((0.5F * (M + m)) / 255F) * 100F;
    float S = Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F)) == 0F ? 0F
        : ((Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F))
            - Math.min(rgb[0] / 255F, Math.min(rgb[1] / 255F, rgb[2] / 255F)))
            / Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F))) * 100F;
    float H = Math.max(
        rgb[0] / 255F, Math
            .max(rgb[1] / 255F, rgb[2] / 255F)) == Math.min(rgb[0] / 255F,
                Math.min(rgb[1] / 255F, rgb[2] / 255F)) ? 0F
                    : Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F,
                        rgb[2] / 255F)) == rgb[0]
                            / 255F
                                ? (60
                                    * ((rgb[1] / 255F - rgb[2] / 255F)
                                        / (Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F))
                                            - Math.min(rgb[0] / 255F, Math.min(rgb[1] / 255F, rgb[2] / 255F))))
                                    + 360) % 360
                                : Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F)) == rgb[1] / 255F
                                    ? (60
                                        * ((rgb[2] / 255F - rgb[0] / 255F) / (Math.max(rgb[0] / 255F,
                                            Math.max(rgb[1] / 255F, rgb[2] / 255F))
                                            - Math.min(rgb[0] / 255F, Math.min(rgb[1] / 255F, rgb[2] / 255F))))
                                        + 120) % 360
                                    : Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F)) == rgb[2]
                                        / 255F
                                            ? (60 * ((rgb[0] / 255F - rgb[1] / 255F) / (Math.max(rgb[0] / 255F,
                                                Math.max(rgb[1] / 255F, rgb[2] / 255F))
                                                - Math.min(rgb[0] / 255F, Math.min(rgb[1] / 255F, rgb[2] / 255F))))
                                                + 240) % 360
                                            : -1;
    return new float[] { H, S, L };
  }

  public static String awt_colorspace_NameMatch(ColorSpace e)
  {
    switch (e.getType()) {
      case 0:
        return "XYZ";
      case 1:
        return "Lab";
      case 2:
        return "Luv";
      case 3:
        return "YCbCr";
      case 4:
        return "Yxy";
      case 5:
        return "RGB";
      case 6:
        return "GRAY";
      case 7:
        return "HSV";
      case 8:
        return "HLS";
      case 9:
        return "CMYK";
      case 11:
        return "CMY";
      case 12:
        return "2CLR";
      case 13:
        return "3CLR";
      case 14:
        return "4CLR";
      case 15:
        return "5CLR";
      case 16:
        return "6CLR";
      case 17:
        return "7CLR";
      case 18:
        return "8CLR";
      case 19:
        return "9CLR";
      case 20:
        return "ACLR";
      case 21:
        return "BCLR";
      case 22:
        return "CCLR";
      case 23:
        return "DCLR";
      default:
        return "UNKNOWN";
    }
  }
}