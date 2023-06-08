// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class test_SaturationPicker extends JPanel
{

  private Color selectedColor;
  private int saturation;
  private int value;

  public test_SaturationPicker()
  {
    this.selectedColor = Color.BLACK;
    this.saturation = 0;
    this.value = 0;

    this.setPreferredSize(new Dimension(200, 200));

    this.addMouseListener(new MouseAdapter() {
      @Override public void mouseClicked(final MouseEvent e)
      {
        final int x = e.getX();
        final int y = e.getY();

        test_SaturationPicker.this.saturation = (int) (x / (double) test_SaturationPicker.this.getWidth() * 100);
        test_SaturationPicker.this.value = (int) ((test_SaturationPicker.this.getHeight() - y) / (double) test_SaturationPicker.this.getHeight() * 100);

        test_SaturationPicker.this.selectedColor = Color.getHSBColor(0, test_SaturationPicker.this.saturation / 100f, test_SaturationPicker.this.value / 100f);
        test_SaturationPicker.this.repaint();
      }
    });
  }

  @Override protected void paintComponent(final Graphics g)
  {
    super.paintComponent(g);
    final int width = this.getWidth();
    final int height = this.getHeight();

    final Graphics2D g2d = (Graphics2D) g.create();
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < height; y++)
      {
        final float saturation = x / (float) width;
        final float value = 1 - (y / (float) height);
        final Color color = Color.getHSBColor(0, saturation, value);
        g2d.setColor(color);
        g2d.fillRect(x, y, 1, 1);
      }
    }

    g2d.setColor(this.selectedColor);
    g2d.setStroke(new BasicStroke(2.0f));
    g2d.drawRect(this.saturation * width / 100, (100 - this.value) * height / 100, 10, 10);

    g2d.dispose();
  }

  public static void main(final String[] args)
  {
    SwingUtilities.invokeLater(() -> {
      final JFrame frame = new JFrame("Saturation and Value Color Picker");
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      final test_SaturationPicker colorPicker = new test_SaturationPicker();
      frame.add(colorPicker);
      frame.pack();
      frame.setVisible(true);
    });
  }
}