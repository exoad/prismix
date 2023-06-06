
package com.jackmeng.prismix.test;

import java.awt.Color;

public class test_ColorSway
{
  public static double calculateLuminance(Color color)
  {

    int r = color.getRed();
    int g = color.getGreen();
    int b = color.getBlue();
    double luminance = (0.299 * r) + (0.587 * g) + (0.114 * b);
    return luminance;
  }

  public static Color generateSwayedColor(Color foreground, Color background, Color color)
  {
    double foregroundLuminance = calculateLuminance(foreground);
    double backgroundLuminance = calculateLuminance(background);

    if (foregroundLuminance == backgroundLuminance)
    {
      return color;
    }

    double luminanceDifference = foregroundLuminance - backgroundLuminance;

    int r = color.getRed();
    int g = color.getGreen();
    int b = color.getBlue();

    double swayFactor = luminanceDifference / (0.299 * r + 0.587 * g + 0.114 * b);
    swayFactor = Math.max(0, Math.min(swayFactor, 1));

    int swayedRed = (int) Math.round(r + swayFactor * (foreground.getRed() - r));
    int swayedGreen = (int) Math.round(g + swayFactor * (foreground.getGreen() - g));
    int swayedBlue = (int) Math.round(b + swayFactor * (foreground.getBlue() - b));

    return new Color(swayedRed, swayedGreen, swayedBlue);
  }

  public static void main(String[] args)
  {
    Color foreground = Color.GREEN;
    Color background = Color.BLACK;

    Color color = Color.BLUE;

    Color swayedColor = generateSwayedColor(foreground, background, color);
    System.out.println("Swayed Color: " + swayedColor);
  }
}