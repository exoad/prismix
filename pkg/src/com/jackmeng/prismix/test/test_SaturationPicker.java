// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class test_SaturationPicker extends JPanel
{

  private Color selectedColor;
  private int saturation;
  private int value;

  public test_SaturationPicker()
  {
    selectedColor = Color.BLACK;
    saturation = 0;
    value = 0;

    setPreferredSize(new Dimension(200, 200));

    addMouseListener(new MouseAdapter() {
      @Override public void mouseClicked(MouseEvent e)
      {
        int x = e.getX();
        int y = e.getY();

        saturation = (int) (x / (double) getWidth() * 100);
        value = (int) ((getHeight() - y) / (double) getHeight() * 100);

        selectedColor = Color.getHSBColor(0, saturation / 100f, value / 100f);
        repaint();
      }
    });
  }

  @Override protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    int width = getWidth();
    int height = getHeight();

    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < height; y++)
      {
        float saturation = x / (float) width;
        float value = 1 - (y / (float) height);
        Color color = Color.getHSBColor(0, saturation, value);
        g2d.setColor(color);
        g2d.fillRect(x, y, 1, 1);
      }
    }

    g2d.setColor(selectedColor);
    g2d.setStroke(new BasicStroke(2.0f));
    g2d.drawRect(saturation * width / 100, (100 - value) * height / 100, 10, 10);

    g2d.dispose();
  }

  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame("Saturation and Value Color Picker");
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      test_SaturationPicker colorPicker = new test_SaturationPicker();
      frame.add(colorPicker);
      frame.pack();
      frame.setVisible(true);
    });
  }
}