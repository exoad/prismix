// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.clrplte.ui;

import javax.swing.*;

import static com.jackmeng.clrplte._1const.*;
import com.jackmeng.stl.stl_Colors;
import com.jackmeng.stl.stl_Function;
import com.jackmeng.stl.stl_ListenerPool;
import com.jackmeng.stl.stl_Struct;

import java.awt.*;

public class gui_Container
    extends JSplitPane
{
  public Container_TopPane top;
  public Container_BottomPane bottom;

  public gui_Container()
  {
    top = new Container_TopPane();
    bottom = new Container_BottomPane();

    setPreferredSize(new Dimension(_2const.WIDTH, _2const.HEIGHT));
    setOrientation(JSplitPane.VERTICAL_SPLIT);
    setDividerLocation(_2const.HEIGHT / 2 + _2const.HEIGHT / 6);
    setTopComponent(top);
    setBottomComponent(bottom);
    setBorder(BorderFactory.createEmptyBorder());
  }

  public static class Container_TopPane
      extends JSplitPane
  {
    public JPanel rgbData, miscAttributes;
    JPanel colorChooser;
    JScrollPane colorAttributes;

    public Container_TopPane()
    {
      setPreferredSize(new Dimension(_2const.WIDTH, _2const.HEIGHT / 2));
      setDividerLocation(_2const.WIDTH / 2 + _2const.WIDTH / 10);
      setOrientation(JSplitPane.HORIZONTAL_SPLIT);
      setBorder(BorderFactory.createEmptyBorder());

      colorChooser = new JPanel();

      Color defaultColor = new Color((float) Math.random(), (float) Math.random(), (float) Math.random(),
          (float) Math.random());
      colorAttributes = new JScrollPane();
      colorAttributes.setBorder(BorderFactory.createEmptyBorder());
      colorAttributes.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
      colorAttributes.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

      JPanel wrapper_ColorAttributes = new JPanel();
      wrapper_ColorAttributes.setOpaque(true);
      wrapper_ColorAttributes.setLayout(new BoxLayout(wrapper_ColorAttributes, BoxLayout.Y_AXIS));
      wrapper_ColorAttributes.setBorder(BorderFactory.createEmptyBorder());

      JLabel colorDisplay = new JLabel("[][][][][][][][][]"); // IMPORTANT STARTUP OBJECT
      colorDisplay.setPreferredSize(new Dimension(50, 20));
      colorDisplay.setOpaque(true);
      colorDisplay.setForeground(null);
      colorDisplay.setBackground(defaultColor);
      colorDisplay.setBorder(BorderFactory.createEmptyBorder());

      JLabel colorDisplay_R = new JLabel(colorDisplay.getText());
      colorDisplay_R.setPreferredSize(new Dimension(50, 20));
      colorDisplay_R.setOpaque(true);
      colorDisplay_R.setForeground(null);
      colorDisplay_R.setBackground(new Color(colorDisplay.getBackground().getRed() / 255F, 0F, 0F));
      colorDisplay_R.setBorder(colorDisplay.getBackground().getRed() / 255F < _2const.COLOR_BLEND_THRESHOLD_BORDER
          ? BorderFactory
              .createLineBorder(new Color((255F - colorDisplay.getBackground().getRed()) / 255F, 0F, 0F))
          : BorderFactory.createEmptyBorder());

      JLabel colorDisplay_G = new JLabel(colorDisplay.getText());
      colorDisplay_G.setPreferredSize(new Dimension(50, 20));
      colorDisplay_G.setOpaque(true);
      colorDisplay_G.setForeground(null);
      colorDisplay_G.setBackground(new Color(0F, colorDisplay.getBackground().getGreen() / 255F, 0F));
      colorDisplay_G.setBorder(colorDisplay.getBackground().getGreen() / 255F < _2const.COLOR_BLEND_THRESHOLD_BORDER
          ? BorderFactory
              .createLineBorder(
                  new Color(0F, (255 - colorDisplay.getBackground().getGreen()) / 255F, 0F))
          : BorderFactory.createEmptyBorder());

      JLabel colorDisplay_B = new JLabel(colorDisplay.getText());
      colorDisplay_B.setPreferredSize(new Dimension(50, 20));
      colorDisplay_B.setOpaque(true);
      colorDisplay_B.setForeground(null);
      colorDisplay_B.setBackground(new Color(0F, 0F, colorDisplay.getBackground().getBlue() / 255F));
      colorDisplay_B.setBorder(colorDisplay.getBackground().getBlue() / 255F < _2const.COLOR_BLEND_THRESHOLD_BORDER
          ? BorderFactory.createLineBorder(
              new Color(0F, 0F, (255F - colorDisplay.getBackground().getBlue()) / 255F))
          : BorderFactory.createEmptyBorder());

      JLabel colorDisplay_A = new JLabel(colorDisplay.getText());
      colorDisplay_A.setPreferredSize(new Dimension(50, 20));
      colorDisplay_A.setOpaque(true);
      colorDisplay_A.setBackground(new Color(0F, 0F, 0F, colorDisplay.getBackground().getAlpha() / 255F));
      colorDisplay_A.setBorder(BorderFactory.createEmptyBorder());

      wrapper_ColorAttributes.add(colorDisplay);
      wrapper_ColorAttributes.add(colorDisplay_R);
      wrapper_ColorAttributes.add(colorDisplay_G);
      wrapper_ColorAttributes.add(colorDisplay_B);
      wrapper_ColorAttributes.add(colorDisplay_A);

      JPanel rawRGBData = new JPanel();
      rawRGBData.setOpaque(true);
      rawRGBData.setLayout(new BoxLayout(rawRGBData, BoxLayout.Y_AXIS));

      JLabel rgba_main = new JLabel("RGB      :  " + colorDisplay.getBackground().getRGB());
      JLabel rgba_R = new JLabel("Red   (R):  " + colorDisplay_R.getBackground().getRed());
      JLabel rgba_G = new JLabel("Green (G):  " + colorDisplay_G.getBackground().getGreen());
      JLabel rgba_B = new JLabel("Blue  (B):  " + colorDisplay_B.getBackground().getBlue());
      JLabel rgba_A = new JLabel("Alpha (A):  " + colorDisplay_A.getBackground().getAlpha());

      rawRGBData.add(rgba_main);
      rawRGBData.add(rgba_R);
      rawRGBData.add(rgba_G);
      rawRGBData.add(rgba_B);
      rawRGBData.add(rgba_A);

      rgbData = new JPanel();
      rgbData.setOpaque(true);
      rgbData.setAlignmentX(Component.LEFT_ALIGNMENT);
      rgbData.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0), "-- RGBA"));
      rgbData.setLayout(new BoxLayout(rgbData, BoxLayout.X_AXIS));
      rgbData.add(wrapper_ColorAttributes);
      rgbData.add(rawRGBData);

      miscAttributes = new JPanel();
      miscAttributes.setLayout(new BoxLayout(miscAttributes, BoxLayout.Y_AXIS));
      miscAttributes.setAlignmentX(Component.LEFT_ALIGNMENT);
      miscAttributes
          .setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0), "-- MISC"));

      JLabel hexColor = new JLabel("Hex: " + stl_Colors.RGBAtoHex(defaultColor.getAlpha(), defaultColor.getRed(),
          defaultColor.getGreen(), defaultColor.getBlue()));
      JLabel transparency = new JLabel("Transparency: " + defaultColor.getTransparency());

      miscAttributes.add(hexColor);
      miscAttributes.add(transparency);

      JPanel attributes_List = new JPanel();
      attributes_List.setLayout(new BoxLayout(attributes_List, BoxLayout.Y_AXIS));

      attributes_List.add(Box.createRigidArea(new Dimension(30, 30)));
      attributes_List.add(miscAttributes);
      attributes_List.add(rgbData);

      colorAttributes.setViewportView(attributes_List);

      setLeftComponent(colorChooser);
      setRightComponent(colorAttributes);

      COLOR_ENQ.add(pair -> { // GUI ONLY LISTENER
        Color x = pair.first;
        SwingUtilities.invokeLater(() -> {
          if (rgbData.isVisible())
          {
            System.out.println("[TopContainer] RGBA_Attributes.isVisible = true");
            colorDisplay.setForeground(x);
            colorDisplay.setBackground(x);
            colorDisplay.setBorder(BorderFactory.createLineBorder(new Color((255F -
                colorDisplay.getBackground().getRed()) / 255F,
                (255F - colorDisplay.getBackground().getGreen()) / 255F,
                (255F - colorDisplay.getBackground().getBlue()) / 255F), 2));

            colorDisplay_R.setBackground(new Color(colorDisplay.getBackground().getRed() / 255F, 0F, 0F));
            colorDisplay_R.setForeground(colorDisplay_R.getBackground());
            colorDisplay_R.setBorder(BorderFactory
                .createLineBorder(new Color((255F - colorDisplay.getBackground().getRed()) / 255F, 0F, 0F), 2));

            colorDisplay_G.setBackground(new Color(0F, colorDisplay.getBackground().getGreen() / 255F, 0F));
            colorDisplay_G.setForeground(colorDisplay_G.getBackground());
            colorDisplay_G
                .setBorder(BorderFactory
                    .createLineBorder(
                        new Color(0F, (255 - colorDisplay.getBackground().getGreen()) / 255F, 0F), 2));

            colorDisplay_B.setBackground(new Color(0F, 0F, colorDisplay.getBackground().getBlue() / 255F));
            colorDisplay_B.setForeground(colorDisplay_B.getBackground());
            colorDisplay_B
                .setBorder(BorderFactory.createLineBorder(
                    new Color(0F, 0F, (255F - colorDisplay.getBackground().getBlue()) / 255F), 2));

            colorDisplay_A.setBorder(
                BorderFactory.createLineBorder(new Color((255F - colorDisplay.getBackground().getRed()) / 255F,
                    (255F - colorDisplay.getBackground().getGreen()) / 255F,
                    (255F - colorDisplay.getBackground().getBlue()) / 255F), 2)); // ! not displayed correctly
            colorDisplay_A.setBackground(new Color((colorDisplay.getBackground().getAlpha()) / 255F,
                (colorDisplay.getBackground().getAlpha()) / 255F,
                (colorDisplay.getBackground().getAlpha()) / 255F));
            colorDisplay_A.setForeground(colorDisplay_A.getBackground());

            /*----------------------------------------------------------------------------------------------------- /
            / rgba_main.setText("<html><strong>RGB</strong>      :  " + x.getRGB() + "</html>");                    /
            / colorDisplay.setToolTipText(rgba_main.getText());                                                     /
            /                                                                                                       /
            / rgba_R.setText("<html><strong>Red   (R)</strong>:  " + x.getRed() + " ["                              /
            /     + String.format("%,.2f", x.getRed() / 255F) + "%]</html>");                                       /
            / rgba_R.setToolTipText(                                                                                /
            /     "<html><strong>Red   (R)</strong>:  " + x.getRed() + " [" + x.getRed() / 255F + "%]</html>");     /
            / colorDisplay_G.setToolTipText(rgba_G.getToolTipText());                                               /
            /                                                                                                       /
            / rgba_G.setText("<html><strong>Green (G)</strong>:  " + x.getGreen() + " ["                            /
            /     + String.format("%,.2f", x.getGreen() / 255F) + "%]</html>");                                     /
            / rgba_G.setToolTipText(                                                                                /
            /     "<html><strong>Green (G)</strong>:  " + x.getGreen() + " [" + x.getGreen() / 255F + "%]</html>"); /
            / colorDisplay_G.setToolTipText(rgba_G.getToolTipText());                                               /
            /                                                                                                       /
            / rgba_B.setText("<html><strong>Blue  (B)</strong>:  " + x.getBlue() + " ["                             /
            /     + String.format("%,.2f", x.getBlue() / 255F) + "%]</html>");                                      /
            / rgba_B.setToolTipText("<html><strong>Blue  (B)</strong>:  " + x.getBlue() + " [" + x.getBlue() / 255F /
            /     + "%]</html>");                                                                                   /
            / colorDisplay_B.setToolTipText(rgba_B.getToolTipText());                                               /
            /                                                                                                       /
            / rgba_A.setText("<html><strong>Alpha (A)</strong>:  " + x.getAlpha() + " ["                            /
            /     + String.format("%,.2f", x.getAlpha() / 255F) + "%]</html>");                                     /
            / rgba_A.setToolTipText(                                                                                /
            /     "<html><strong>Alpha (A)</strong>:  " + x.getAlpha() + " [" + x.getAlpha() / 255F + "%]</html>"); /
            / colorDisplay_A.setToolTipText(rgba_A.getToolTipText());                                               /
            /------------------------------------------------------------------------------------------------------*/

            rgba_main.setText(" RGB      :  " + x.getRGB());
            colorDisplay.setToolTipText(rgba_main.getText());

            rgba_R
                .setText(" Red   (R):  " + x.getRed() + " [" + String.format("%,.2f", 100 * x.getRed() / 255F) + "%]");
            rgba_R.setToolTipText("Red   (R):  " + x.getRed() + " [" + 100 * x.getRed() / 255F + "%]");
            colorDisplay_G.setToolTipText(rgba_G.getToolTipText());

            rgba_G.setText(
                " Green (G):  " + x.getGreen() + " [" + String.format("%,.2f", 100 * x.getGreen() / 255F) + "%]");
            rgba_G.setToolTipText("Green (G):  " + x.getGreen() + " [" + 100 * x.getGreen() / 255F + "%]");
            colorDisplay_G.setToolTipText(rgba_G.getToolTipText());

            rgba_B.setText(
                " Blue  (B):  " + x.getBlue() + " [" + String.format("%,.2f", 100 * x.getBlue() / 255F) + "%]");
            rgba_B.setToolTipText("Blue  (B):  " + x.getBlue() + " [" + 100 * x.getBlue() / 255F
                + "%]");
            colorDisplay_B.setToolTipText(rgba_B.getToolTipText());

            rgba_A.setText(
                " Alpha (A):  " + x.getAlpha() + " [" + String.format("%,.2f", 100 * x.getAlpha() / 255F) + "%]");
            rgba_A.setToolTipText("Alpha (A):  " + x.getAlpha() + " [" + 100 * x.getAlpha() / 255F + "%]");
            colorDisplay_A.setToolTipText(rgba_A.getToolTipText());
          }

          if (miscAttributes.isVisible())
          {
            System.out.println("[TopContainer] MISC_ATTRIBUTES.isVisible = true");
            hexColor.setText("Hex: " + stl_Colors.RGBAtoHex(x.getAlpha(), x.getRGB(), x.getGreen(), x.getBlue()));
            transparency.setText("Transparency: " + x.getTransparency());
          }

          revalidate();

        });
        return (Void) null;
      });

      COLOR_ENQ.dispatch(stl_Struct.make_pair(colorDisplay.getBackground(), false));
    }

  }

  public void validate_size()
  {
    top.colorAttributes.setMinimumSize(top.colorAttributes.getPreferredSize());
  }

  public static class Container_BottomPane
      extends JSplitPane
      implements
      stl_Function< Color, Void >
  {

    JTable history, currentPalette;

    public Container_BottomPane()
    {
      setPreferredSize(new Dimension(_2const.WIDTH, _2const.HEIGHT / 2));
      setOrientation(JSplitPane.HORIZONTAL_SPLIT);
      setDividerLocation(_2const.WIDTH / 2 + _2const.WIDTH / 8);
      setBorder(BorderFactory.createEmptyBorder());

      history = new JTable();
      currentPalette = new JTable();

      setRightComponent(currentPalette);
      setLeftComponent(history);
    }

    @Override public Void apply(Color arg0)
    {
      throw new UnsupportedOperationException("Unimplemented method 'apply'");
    }

  }
}