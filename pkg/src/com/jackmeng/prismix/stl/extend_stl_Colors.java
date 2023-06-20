// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.stl;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.color.ColorSpace;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import com.jackmeng.prismix._1const;
import com.jackmeng.stl.stl_Colors;

public final class extend_stl_Colors
{
  private extend_stl_Colors()
  {
  }
  /*---------------------------------------- /
  /                                          /
  / public static float[] cyan_rgb()         /
  / {                                        /
  /   return new float[] { 0F, 255F, 255F }; /
  / }                                        /
  /                                          /
  / public static float[] magenta_rgb()      /
  / {                                        /
  /   return new float[] { 255F, 0F, 255F }; /
  / }                                        /
  /                                          /
  / public static float[] black_rgb()        /
  / {                                        /
  /   return new float[] { 0F, 0F, 0F };     /
  / }                                        /
  /                                          /
  / public static float[] yellow_rgb()       /
  / {                                        /
  /   return new float[] { 255F, 255F, 0F }; /
  / }                                        /
  /-----------------------------------------*/

  public static int[] awt_colorspace_AllTypes()
  {
    return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 }; // we dont
                                                                                                           // know why
                                                                                                           // 10 isnt
                                                                                                           // here
  }

  public static String RGBToHex(final int r, final int g, final int b)
  {
    return String.format("#%02x%02x%02x", r, g, b);
  }

  public static String color2hex_2(final Color color)
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

  public static boolean is_red(final float[] e)
  {
    return e[0] > e[1] && e[0] > e[2];
  }

  public static boolean is_blue(final float[] e)
  {
    return e[2] > e[1] && e[2] > e[0];
  }

  public static boolean is_green(final float[] e)
  {
    return e[1] > e[0] && e[1] > e[2];
  }

  public static float[] binary_fg_decider(final float[] bg)
  {
    return extend_stl_Colors
        .awt_strip_rgba(
            (0.2126F * bg[0] + 0.7152F * bg[1] + 0.0722F * bg[2]) / 255F > 0.5F ? Color.BLACK : Color.WHITE); // supplied
                                                                                                              // weights
                                                                                                              // for
                                                                                                              // determining
                                                                                                              // luminance
  }

  public static float[][] tints(final float[] ir, final int n)
  {
    final float[][] generatedTints = new float[n][3];
    generatedTints[0] = ir;
    float red = ir[0], green = ir[1], blue = ir[2];
    final float redStep = (255F - red) / (n - 1), greenStep = (255F - green) / (n - 1),
        blueStep = (255F - blue) / (n - 1);

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

  public static float[] stripHex(final String hex)
  {
    return extend_stl_Colors.awt_strip_rgba(stl_Colors.hexToRGB(hex));
  }

  public static float[][] shades(final float[] ir, final int n)
  {
    final float[][] generatedTints = new float[n][3];
    generatedTints[0] = ir;
    float red = ir[0], green = ir[1], blue = ir[2];
    final float maxColor = Math.max(Math.max(red, green), blue), blackStep = maxColor / (n - 1);
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

  public static float[][] tones(final float[] ir, final int n)
  {
    float[][] tones = new float[n][3];

    float red = ir[0];
    float green = ir[1];
    float blue = ir[2];

    float toneStep = 1.0f / (n - 1);

    for (int i = 0; i < n; i++)
    {
      float toneAmount = i * toneStep;

      float toneRed = red * (1 - toneAmount) + toneAmount;
      float toneGreen = green * (1 - toneAmount) + toneAmount;
      float toneBlue = blue * (1 - toneAmount) + toneAmount;

      tones[i][0] = toneRed;
      tones[i][1] = toneGreen;
      tones[i][2] = toneBlue;
    }

    return tones;
  }

  public static float brightness(float[] color)
  {
    return (0.299f * color[0] + 0.587f * color[1] + 0.114f * color[2]);
  }

  public static void sort_l2d(float[][] colors)
  {
    Arrays.sort(colors, (color1, color2) -> Float.compare(brightness(color1), brightness(color2)));
  }

  public static void sort_d2l(float[][] colors)
  {
    Arrays.sort(colors, (color1, color2) -> Float.compare(brightness(color2), brightness(color1)));
  }

  public static float[][] complementaries(float[] initialColor, int n)
  {
    float[][] complementaryColors = new float[n][3];

    // Scale the initial color values to the range of 0-1
    float red = initialColor[0] / 255.0f;
    float green = initialColor[1] / 255.0f;
    float blue = initialColor[2] / 255.0f;

    // Calculate the complementary color by adding 0.5 and taking modulo 1
    float complementaryRed = (red + 0.5f) % 1.0f;
    float complementaryGreen = (green + 0.5f) % 1.0f;
    float complementaryBlue = (blue + 0.5f) % 1.0f;

    for (int i = 0; i < n; i++)
    {
      float complementaryAmount = (float) i / (n - 1);

      // Calculate the tone by interpolating the initial color and its complementary
      // color
      float toneRed = red + (complementaryRed - red) * complementaryAmount;
      float toneGreen = green + (complementaryGreen - green) * complementaryAmount;
      float toneBlue = blue + (complementaryBlue - blue) * complementaryAmount;

      // Store the tone color in the complementaryColors array
      complementaryColors[i][0] = toneRed * 255.0f;
      complementaryColors[i][1] = toneGreen * 255.0f;
      complementaryColors[i][2] = toneBlue * 255.0f;
    }

    return complementaryColors;
  }

  public static Color awt_empty()
  {
    return new Color(0, 0, 0, 0);
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

  public static BufferedImage cpick_gradient2(final int size, final Color interest)
  {
    final BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
    final Graphics2D g = image.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
    g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
    g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
    g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
    g.setRenderingHint(RenderingHints.KEY_RESOLUTION_VARIANT, RenderingHints.VALUE_RESOLUTION_VARIANT_SIZE_FIT);
    final GradientPaint primary = new GradientPaint(
        0f, 0f, Color.WHITE, size, 0f, interest);
    final GradientPaint shade = new GradientPaint(
        0f, 0f, new Color(0, 0, 0, 0),
        0f, size, new Color(0, 0, 0, 255));
    g.setPaint(primary);
    g.fillRect(0, 0, size, size);
    g.setPaint(shade);
    g.fillRect(0, 0, size, size);
    g.dispose();
    return image;
  }

  public static BufferedImage OLD_cpick_gradient(final int size, final Color interest)
  {
    final BufferedImage image = new BufferedImage(
        size, size, BufferedImage.TYPE_INT_RGB);
    final Graphics2D g = image.createGraphics();
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
    final LinearGradientPaint lgp_shade = new LinearGradientPaint(new Point2D.Float(0F, 0F),
        new Point2D.Float(0F, size),
        new float[] { 0.25F, 0.85F },
        new Color[] { new Color(Color.BLACK.getRed(), Color.BLACK.getBlue(), Color.BLACK.getBlue(), 0), Color.BLACK });

    /*---------------------------------- /
    / g.setPaint(lgp_white_to_interest); /
    / g.fillRect(0, 0, size, size);      /
    / g.setPaint(lgp_shade);             /
    / g.fillRect(0, 0, size, size);      /
    / g.dispose();                       /
    /-----------------------------------*/

    final GradientPaint primary = new GradientPaint(
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

  public static float[] awt_strip_rgba(final Color awt)
  {
    return new float[] { awt.getRed(), awt.getGreen(), awt.getBlue(), awt.getAlpha() };
  }

  public static Color awt_remake(final float[] rgba)
  {
    return rgba.length == 4 ? new Color(rgba[0] / 255F, rgba[1] / 255F, rgba[2] / 255F, rgba[3] / 255F)
        : rgba.length == 3 ? new Color(rgba[0] / 255F, rgba[1] / 255F, rgba[2] / 255F) : Color.MAGENTA; // debug color
                                                                                                        // == Magenta???
  }

  /*------------------------------------------------------------------------------------------ /
  / didnt use variable declarations and stuffed everything into a single return statement with /
  / ternary operators bc uh idk (hey it works,but not as efficient)                            /
  /-------------------------------------------------------------------------------------------*/

  public static float[] rgbToHsv(final float[] rgb)
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
                / Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F))),
        Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F)) };
  }

  public static float[] rgbToCmyk(final float[] rgb)
  {
    // all elements here are multiplied by 100 to make them not like %
    return new float[] {
        (1 - rgb[0] / 255F - (1 - Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F))))
            / (1 - (1 - Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F)))),
        (1 - rgb[1] / 255F - (1 - Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F))))
            / (1 - (1 - Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F)))),
        (1 - rgb[2] / 255F - (1 - Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F))))
            / (1 - (1 - Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F)))),
        1 - Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F)) }; // values here should never be *100
  }

  public static float[] rgbToHsl(final float[] rgb)
  {
    final float M = Math.max(rgb[0], Math.max(rgb[1], rgb[2])), m = Math.min(rgb[0], Math.min(rgb[1], rgb[2]));
    final float L = ((0.5F * (M + m)) / 255F);
    final float S = Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F)) == 0F ? 0F
        : ((Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F))
            - Math.min(rgb[0] / 255F, Math.min(rgb[1] / 255F, rgb[2] / 255F)))
            / Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F)));
    final float H = Math.max(
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
                                            : 0;
    return new float[] { H, S, L };
  }

  public static float[] cmykToRgb(final float[] cmyk)
  {
    return new float[] { 255F * (1F - cmyk[0]) * (1F - cmyk[3]), 255F * (1F - cmyk[1]) * (1F - cmyk[3]),
        255 * (1F - cmyk[2]) * (1F - cmyk[3]) };
  }

  public static float[][] cmykToRgb_Comps(final float[] cmyk)
  {
    return new float[][] { extend_stl_Colors.cmykToRgb(new float[] { cmyk[0], 0F, 0F, 0F }),
        extend_stl_Colors.cmykToRgb(new float[] { 0F, cmyk[1], 0F, 0F }),
        extend_stl_Colors.cmykToRgb(new float[] { 0F, 0F, cmyk[2], 0F }),
        extend_stl_Colors.cmykToRgb(new float[] { 0F, 0F, 0F, cmyk[3] }) };
  }

  public static String awt_colorspace_NameMatch(final ColorSpace e)
  {
    return switch (e.getType()) {
      case 0 -> "XYZ";
      case 1 -> "Lab";
      case 2 -> "Luv";
      case 3 -> "YCbCr";
      case 4 -> "Yxy";
      case 5 -> "RGB";
      case 6 -> "GRAY";
      case 7 -> "HSV";
      case 8 -> "HLS";
      case 9 -> "CMYK";
      case 11 -> "CMY";
      case 12 -> "2CLR";
      case 13 -> "3CLR";
      case 14 -> "4CLR";
      case 15 -> "5CLR";
      case 16 -> "6CLR";
      case 17 -> "7CLR";
      case 18 -> "8CLR";
      case 19 -> "9CLR";
      case 20 -> "ACLR";
      case 21 -> "BCLR";
      case 22 -> "CCLR";
      case 23 -> "DCLR";
      default -> "UNKNOWN";
    };
  }
}