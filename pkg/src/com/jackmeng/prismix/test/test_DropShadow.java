
package com.jackmeng.prismix.test;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.AbstractBorder;

public class test_DropShadow extends AbstractBorder
{
  private final Color shadowColor;
  private final int shadowSize;

  public test_DropShadow(final Color shadowColor, final int shadowSize)
  {
    this.shadowColor = shadowColor;
    this.shadowSize = shadowSize;
  }

  @Override public Insets getBorderInsets(final Component c)
  {
    return new Insets(this.shadowSize, this.shadowSize, this.shadowSize, this.shadowSize);
  }

  @Override public Insets getBorderInsets(final Component c, final Insets insets)
  {
    insets.left = insets.top = insets.right = insets.bottom = this.shadowSize;
    return insets;
  }

  @Override public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height)
  {
    final Graphics2D g2 = (Graphics2D) g.create();

    g2.setColor(this.shadowColor);

    g2.fillRect(x + this.shadowSize, y + height - this.shadowSize, width - 2 * this.shadowSize, this.shadowSize);
    g2.fillRect(x + width - this.shadowSize, y + this.shadowSize, this.shadowSize, height - 2 * this.shadowSize);

    g2.setColor(c.getForeground());
    g2.drawRect(x + this.shadowSize, y + this.shadowSize, width - 2 * this.shadowSize, height - 2 * this.shadowSize);

    g2.dispose();
  }

  public static void main(final String[] args)
  {
    SwingUtilities.invokeLater(() -> {
      final JFrame frame = new JFrame("uwu");
      frame.setDefaultCloseOperation(
          WindowConstants.EXIT_ON_CLOSE);

      final JButton button = new JButton("owo");
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
