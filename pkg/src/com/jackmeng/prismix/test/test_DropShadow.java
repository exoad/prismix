
package com.jackmeng.prismix.test;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.AbstractBorder;

public class test_DropShadow extends AbstractBorder
{
  private final Color shadowColor;
  private final int shadowSize;

  public test_DropShadow(Color shadowColor, int shadowSize)
  {
    this.shadowColor = shadowColor;
    this.shadowSize = shadowSize;
  }

  @Override public Insets getBorderInsets(Component c)
  {
    return new Insets(shadowSize, shadowSize, shadowSize, shadowSize);
  }

  @Override public Insets getBorderInsets(Component c, Insets insets)
  {
    insets.left = insets.top = insets.right = insets.bottom = shadowSize;
    return insets;
  }

  @Override public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
  {
    Graphics2D g2 = (Graphics2D) g.create();

    g2.setColor(shadowColor);

    g2.fillRect(x + shadowSize, y + height - shadowSize, width - 2 * shadowSize, shadowSize);
    g2.fillRect(x + width - shadowSize, y + shadowSize, shadowSize, height - 2 * shadowSize);

    g2.setColor(c.getForeground());
    g2.drawRect(x + shadowSize, y + shadowSize, width - 2 * shadowSize, height - 2 * shadowSize);

    g2.dispose();
  }

  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame("uwu");
      frame.setDefaultCloseOperation(
          WindowConstants.EXIT_ON_CLOSE);

      JButton button = new JButton("owo");
      button.setForeground(Color.WHITE);
      button.setBackground(Color.GRAY);
      button.setFont(new Font("Arial", Font.BOLD, 14));
      button.setBorder(new test_DropShadow(Color.DARK_GRAY, 5));

      frame.add(button);
      frame.setSize(300, 200);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
    });
  }
}
