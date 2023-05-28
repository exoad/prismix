// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import javax.swing.*;

import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.stl.stl_Colors;
import com.jackmeng.stl.stl_Function;
import com.jackmeng.stl.stl_Struct;

import static com.jackmeng.prismix._1const.*;

import java.awt.*;

/**
 * Represents the inner shell of the content of the GUI. The parent is the ux
 * handler.
 *
 * @author Jack Meng
 */
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
		private JPanel rgbData, miscAttributes, colorSpace, hsvData, controls; // controls should be the only
																				// section where it cannot be
																				// exported and thus let ux
																				// change its visibility at any
																				// time!
		private JPanel colorChooser;
		private JScrollPane colorAttributes;
		private final JPanel attributes_List;

		public Container_TopPane()
		{
			setPreferredSize(new Dimension(_2const.WIDTH, _2const.HEIGHT / 2));
			setDividerLocation(_2const.WIDTH / 2 + _2const.WIDTH / 10);
			setOrientation(JSplitPane.HORIZONTAL_SPLIT);
			setBorder(BorderFactory.createEmptyBorder());

			colorChooser = new JPanel();
			/*---------------------------------------------------------------------------- /
			/                                                                              /
			/ Color defaultColor = new Color((float) Math.random(), (float) Math.random(), /
			/                 (float) Math.random(),                                       /
			/                 (float) Math.random());                                      /
			/-----------------------------------------------------------------------------*/

			colorAttributes = new JScrollPane();
			colorAttributes.setOpaque(true);
			colorAttributes.getVerticalScrollBar().setUnitIncrement(7);
			colorAttributes.setAutoscrolls(false);
			colorAttributes.setBorder(BorderFactory.createEmptyBorder());
			colorAttributes.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			colorAttributes.setHorizontalScrollBarPolicy(
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			JPanel wrapper_ColorAttributes = new JPanel();
			wrapper_ColorAttributes.setOpaque(false);
			wrapper_ColorAttributes.setLayout(new BoxLayout(wrapper_ColorAttributes, BoxLayout.Y_AXIS));
			wrapper_ColorAttributes.setBorder(BorderFactory.createEmptyBorder());

			JLabel colorDisplay = new JLabel("[][][][][][][][][]"); // IMPORTANT STARTUP OBJECT
			colorDisplay.setPreferredSize(new Dimension(50, 20));
			colorDisplay.setOpaque(true);

			JLabel colorDisplay_R = new JLabel(colorDisplay.getText());
			colorDisplay_R.setPreferredSize(new Dimension(50, 20));
			colorDisplay_R.setOpaque(true);

			JLabel colorDisplay_G = new JLabel(colorDisplay.getText());
			colorDisplay_G.setPreferredSize(new Dimension(50, 20));
			colorDisplay_G.setOpaque(true);

			JLabel colorDisplay_B = new JLabel(colorDisplay.getText());
			colorDisplay_B.setPreferredSize(new Dimension(50, 20));
			colorDisplay_B.setOpaque(true);

			JLabel colorDisplay_A = new JLabel(colorDisplay.getText());
			colorDisplay_A.setPreferredSize(new Dimension(50, 20));
			colorDisplay_A.setOpaque(true);

			wrapper_ColorAttributes.add(colorDisplay);
			wrapper_ColorAttributes.add(colorDisplay_R);
			wrapper_ColorAttributes.add(colorDisplay_G);
			wrapper_ColorAttributes.add(colorDisplay_B);
			wrapper_ColorAttributes.add(colorDisplay_A);

			JPanel rawRGBData = new JPanel();
			rawRGBData.setOpaque(false);
			rawRGBData.setLayout(new BoxLayout(rawRGBData, BoxLayout.Y_AXIS));

			JLabel rgba_main = new JLabel();
			JLabel rgba_R = new JLabel();
			JLabel rgba_G = new JLabel();
			JLabel rgba_B = new JLabel();
			JLabel rgba_A = new JLabel();

			rawRGBData.add(rgba_main);
			rawRGBData.add(rgba_R);
			rawRGBData.add(rgba_G);
			rawRGBData.add(rgba_B);
			rawRGBData.add(rgba_A);

			rgbData = new JPanel();
			rgbData.setName("RGBA");
			rgbData.setOpaque(true);
			rgbData.setAlignmentX(Component.LEFT_ALIGNMENT);
			rgbData.setBorder(ux_Helper.bottom_container_AttributesBorder("-- RGBA"));
			rgbData.setLayout(new BoxLayout(rgbData, BoxLayout.X_AXIS));
			rgbData.add(wrapper_ColorAttributes);
			rgbData.add(rawRGBData);

			miscAttributes = new JPanel();
			miscAttributes.setName("Miscellaneous");
			miscAttributes.setLayout(new BoxLayout(miscAttributes, BoxLayout.Y_AXIS));
			miscAttributes.setAlignmentX(Component.LEFT_ALIGNMENT);
			miscAttributes.setBorder(
					ux_Helper.bottom_container_AttributesBorder("-- MISC"));

			JLabel hexColor = new JLabel();
			JLabel transparency = new JLabel();
			JLabel colorFunction_RGB = new JLabel();
			JLabel colorFunction_RGBA = new JLabel();
			JLabel colorFunction_HSV = new JLabel();

			miscAttributes.add(hexColor);
			miscAttributes.add(transparency);
			miscAttributes.add(colorFunction_RGB);
			miscAttributes.add(colorFunction_RGBA);
			miscAttributes.add(colorFunction_HSV);

			colorSpace = new JPanel();
			colorSpace.setName("Color Space");
			colorSpace.setLayout(new BoxLayout(colorSpace, BoxLayout.Y_AXIS));
			colorSpace.setAlignmentX(Component.LEFT_ALIGNMENT);
			colorSpace.setBorder(ux_Helper.bottom_container_AttributesBorder("-- Color Space"));

			JEditorPane colorSpace_scrollPane = new JEditorPane();
			colorSpace_scrollPane.setContentType("text/html");
			colorSpace_scrollPane.setEditable(false);
			colorSpace_scrollPane.setFocusable(false);

			/*-------------------------------------------------------------------------------------------- /
			/ JLabel colorSpace_componentsCount = new JLabel(                                              /
			/                 "Components: " + defaultColor.getColorSpace().getNumComponents());           /
			/ JLabel colorSpace_name = new JLabel("Color Space: "                                          /
			/                 + extend_stl_Colors.awt_colorspace_NameMatch(defaultColor.getColorSpace())); /
			/---------------------------------------------------------------------------------------------*/

			colorSpace.add(colorSpace_scrollPane);

			hsvData = new JPanel();
			hsvData.setName("HSV");
			hsvData.setLayout(new BoxLayout(hsvData, BoxLayout.Y_AXIS));
			hsvData.setAlignmentX(Component.LEFT_ALIGNMENT);
			hsvData.setBorder(
					ux_Helper.bottom_container_AttributesBorder("-- HSV"));

			controls = new JPanel();
			controls.setLayout(new GridLayout(2, 2)); // 4 buttons
			controls.setAlignmentX(Component.LEFT_ALIGNMENT);
			controls.setBorder(ux_Helper.bottom_container_AttributesBorder("-- Controls"));

			JButton controls_randomColor = new JButton("Random Color");
			controls_randomColor.setFocusPainted(false);
			controls_randomColor.setBorderPainted(false);
			controls_randomColor.setForeground(controls_randomColor.getForeground().brighter());
			controls_randomColor.addActionListener(ev -> _1const.COLOR_ENQ
					.dispatch(stl_Struct.make_pair(extend_stl_Colors.awt_random_Color(), false)));

			

			controls.add(controls_randomColor);

			attributes_List = new JPanel();
			attributes_List.setLayout(new BoxLayout(attributes_List, BoxLayout.Y_AXIS));
			attributes_List.setOpaque(true);
			attributes_List.add(controls);
			attributes_List.add(miscAttributes);
			attributes_List.add(rgbData);
			attributes_List.add(colorSpace);
			attributes_List.add(hsvData);

			ui_LazyViewport $0219430 = new ui_LazyViewport();
			$0219430.setView(attributes_List);

			colorAttributes.setViewportView($0219430);

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
						colorDisplay.setBorder(BorderFactory.createLineBorder(new Color(
								(255F - colorDisplay.getBackground().getRed()) / 255F,
								(255F - colorDisplay.getBackground().getGreen()) / 255F,
								(255F - colorDisplay.getBackground().getBlue()) / 255F),
								2));

						colorDisplay_R.setBackground(new Color(
								colorDisplay.getBackground().getRed() / 255F, 0F, 0F));
						colorDisplay_R.setForeground(colorDisplay_R.getBackground());
						colorDisplay_R.setBorder(BorderFactory
								.createLineBorder(
										new Color((255F - colorDisplay
												.getBackground()
												.getRed()) / 255F, 0F,
												0F),
										2));

						colorDisplay_G.setBackground(new Color(0F,
								colorDisplay.getBackground().getGreen() / 255F, 0F));
						colorDisplay_G.setForeground(colorDisplay_G.getBackground());
						colorDisplay_G
								.setBorder(BorderFactory
										.createLineBorder(
												new Color(0F, (255
														- colorDisplay.getBackground()
																.getGreen())
														/ 255F,
														0F),
												2));

						colorDisplay_B.setBackground(new Color(0F, 0F,
								colorDisplay.getBackground().getBlue() / 255F));
						colorDisplay_B.setForeground(colorDisplay_B.getBackground());
						colorDisplay_B
								.setBorder(BorderFactory.createLineBorder(
										new Color(0F, 0F, (255F - colorDisplay
												.getBackground()
												.getBlue()) / 255F),
										2));

						colorDisplay_A.setBorder(
								BorderFactory.createLineBorder(
										new Color((255F - colorDisplay
												.getBackground()
												.getRed()) / 255F,
												(255F - colorDisplay
														.getBackground()
														.getGreen())
														/ 255F,
												(255F - colorDisplay
														.getBackground()
														.getBlue())
														/ 255F),
										2)); // ! not displayed correctly
						colorDisplay_A.setBackground(new Color(
								(colorDisplay.getBackground().getAlpha()) / 255F,
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
								.setText(" Red   (R):  " + x.getRed() + " ["
										+ String.format("%,.2f",
												100 * x.getRed() / 255F)
										+ "%]");
						rgba_R.setToolTipText("Red   (R):  " + x.getRed() + " ["
								+ 100 * x.getRed() / 255F + "%]");
						colorDisplay_G.setToolTipText(rgba_G.getToolTipText());

						rgba_G.setText(
								" Green (G):  " + x.getGreen() + " ["
										+ String.format("%,.2f",
												100 * x.getGreen()
														/ 255F)
										+ "%]");
						rgba_G.setToolTipText("Green (G):  " + x.getGreen() + " ["
								+ 100 * x.getGreen() / 255F + "%]");
						colorDisplay_G.setToolTipText(rgba_G.getToolTipText());

						rgba_B.setText(
								" Blue  (B):  " + x.getBlue() + " ["
										+ String.format("%,.2f",
												100 * x.getBlue()
														/ 255F)
										+ "%]");
						rgba_B.setToolTipText("Blue  (B):  " + x.getBlue() + " ["
								+ 100 * x.getBlue() / 255F
								+ "%]");
						colorDisplay_B.setToolTipText(rgba_B.getToolTipText());

						rgba_A.setText(
								" Alpha (A):  " + x.getAlpha() + " ["
										+ String.format("%,.2f",
												100 * x.getAlpha()
														/ 255F)
										+ "%]");
						rgba_A.setToolTipText("Alpha (A):  " + x.getAlpha() + " ["
								+ 100 * x.getAlpha() / 255F + "%]");
						colorDisplay_A.setToolTipText(rgba_A.getToolTipText());
					}

					if (miscAttributes.isVisible())
					{
						System.out.println("[TopContainer] MISC_ATTRIBUTES.isVisible = true");
						hexColor.setText(
								"<html><strong>Hex</strong>: " + stl_Colors.RGBAtoHex(
										x.getAlpha(), x.getRGB(),
										x.getGreen(), x.getBlue()) + "</html>");
						transparency.setText("<html><strong>Transparency</strong>: "
								+ x.getTransparency() + "</html>");
						colorFunction_RGB.setText(
								"<html><strong>CSS rgb</strong>: <p style=\"background-color:black;color:#48aff0\">rgb<span style=\"color:white\">("
										+ x.getRed() + ", "
										+ x.getGreen() + ", " + x.getBlue()
										+ ")</span></p></html>");
						colorFunction_RGBA.setText(
								"<html><strong>CSS rgba</strong>: <p style=\"background-color:black;color:#48aff0\">rgba<span style=\"color:white\">("
										+ x.getRed() + ", "
										+ x.getGreen() + ", " + x.getBlue()
										+ ", "
										+ x.getAlpha() + ")</span></p></html>");
						float[] hsv = extend_stl_Colors.rgbToHsv(
								new float[] { x.getRed(), x.getGreen(), x.getBlue() });
						colorFunction_HSV.setText(
								"<html><strong>CSS hsv</strong>: <p style=\"background-color:black;color:#48aff0\">hsv<span style=\"color:white\">("
										+ hsv[0] + ", " + hsv[1]
										+ "%, " + hsv[2]
										+ "%)</span></p></html>");
					}

					if (colorSpace.isVisible())
					{
						System.out.println(
								"[TopContainer] COLOR_SPACE_ATTRIBUTES.isVisible = true");
						StringBuilder sb = new StringBuilder(
								"<strong>Component Names</strong>:"),
								sb2 = new StringBuilder(
										"<strong>Component Min , Max</strong>:"),
								sb3 = new StringBuilder(
										"<strong>CIEXYZ Form</strong>:");
						float[] ciexyz = x.getColorSpace().toCIEXYZ(new float[] { x.getRed(),
								x.getGreen(), x.getBlue(), x.getAlpha() });
						for (float rrr : ciexyz)
							sb3.append("<br>&nbsp;&nbsp;" + rrr);

						for (int i = 0; i < x.getColorSpace().getNumComponents(); i++)
						{
							sb.append("<br>&nbsp;&nbsp;" + (i + 1) + ": "
									+ x.getColorSpace().getName(i));
							sb2.append("<br>&nbsp;&nbsp;" + (i + 1) + ": "
									+ x.getColorSpace().getMinValue(i) + " , "
									+ x.getColorSpace().getMaxValue(i));

						}

						colorSpace_scrollPane.setText("<html><strong>Components</strong>: "
								+ x.getColorSpace().getNumComponents()
								+ "<br><strong>Color Space</strong>: "
								+ extend_stl_Colors.awt_colorspace_NameMatch(
										x.getColorSpace())
								+ "<br>"
								+ sb.toString() + "<br>" + sb2.toString()
								+ "<br>" +
								sb3.toString()
								+
								"</html>");
					}

					if (hsvData.isVisible())
					{

					}

					validate();

				});
				return (Void) null;
			});

			COLOR_ENQ.dispatch(stl_Struct.make_pair(colorDisplay.getBackground(), false));
			/*------------------------------------------------------------------------------------------ /
			/ _1const.add(() -> { // this task basically just constantly changes the color of the button /
			/   Color randomColor = extend_stl_Colors.awt_random_Color();                                /
			/   controls_randomColor.setBackground(randomColor);                                         /
			/   controls_randomColor.setForeground(stl_Colors.awt_ColorInvert(randomColor));             /
			/   controls_randomColor.repaint(50L);                                                       /
			/ }, 200L, 500L);                                                                            /
			/-------------------------------------------------------------------------------------------*/
		}

		public JPanel[] exports()
		{
			return new JPanel[] { rgbData, miscAttributes, colorSpace, hsvData };
		}

	}

	public synchronized void append_attribute(JComponent e)
	{
		e.setAlignmentX(Component.CENTER_ALIGNMENT);
		e.setAlignmentY(Component.LEFT_ALIGNMENT);
		top.attributes_List.add(e);
		top.attributes_List.validate();
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