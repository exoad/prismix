
package com.jackmeng.prismix.test;

import java.awt.Color;

public class test_ColorSway
{
  public static double calculateLuminance(final Color color)
  {

    final int r = color.getRed();
    final int g = color.getGreen();
    final int b = color.getBlue();
    final double luminance = (0.299 * r) + (0.587 * g) + (0.114 * b);
    return luminance;
  }

  public static Color generateSwayedColor(final Color foreground, final Color background, final Color color)
  {
    final double foregroundLuminance = calculateLuminance(foreground);
    final double backgroundLuminance = calculateLuminance(background);

    if (foregroundLuminance == backgroundLuminance)
    {
      return color;
    }

    final double luminanceDifference = foregroundLuminance - backgroundLuminance;

    final int r = color.getRed();
    final int g = color.getGreen();
    final int b = color.getBlue();

    double swayFactor = luminanceDifference / (0.299 * r + 0.587 * g + 0.114 * b);
    swayFactor = Math.max(0, Math.min(swayFactor, 1));

    final int swayedRed = (int) Math.round(r + swayFactor * (foreground.getRed() - r));
    final int swayedGreen = (int) Math.round(g + swayFactor * (foreground.getGreen() - g));
    final int swayedBlue = (int) Math.round(b + swayFactor * (foreground.getBlue() - b));

    return new Color(swayedRed, swayedGreen, swayedBlue);
  }

  public static void main(final String[] args)
  {
    final Color foreground = Color.GREEN;
    final Color background = Color.BLACK;

    final Color color = Color.BLUE;

    final Color swayedColor = generateSwayedColor(foreground, background, color);
    System.out.println("Swayed Color: " + swayedColor);
  }
}