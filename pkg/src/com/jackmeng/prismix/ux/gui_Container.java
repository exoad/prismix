// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import com.jackmeng.ansicolors.jm_Ansi;
import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.prismix.ux.ui_Tag.ui_PTag;
import com.jackmeng.stl.stl_Colors;
import com.jackmeng.stl.stl_Listener;
import com.jackmeng.stl.stl_Struct;
import com.jackmeng.stl.stl_Struct.struct_Pair;
import com.jackmeng.stl.stl_Ware;

import static com.jackmeng.prismix.jm_Prismix.*;

/**
 * Represents the inner shell of the content of the GUI. The parent is the ux
 * handler.
 *
 * Most code for the GUI goes here.
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
		this.top = new Container_TopPane();
		this.bottom = new Container_BottomPane();

		this.setPreferredSize(new Dimension(_2const.WIDTH, _2const.HEIGHT));
		this.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.setDividerLocation(_2const.HEIGHT / 2 + _2const.HEIGHT / 8);
		this.setTopComponent(this.top);
		this.setBottomComponent(this.bottom);
		this.setBorder(BorderFactory.createEmptyBorder());
	}

	public static class Container_TopPane
			extends JSplitPane
	{
		public JPanel[] exports()
		{
			return new JPanel[] { this.rgbData, this.miscAttributes, this.colorSpace, this.hsvData, this.hslData,
					this.cmykData };
		}

		private JPanel rgbData, miscAttributes, colorSpace, hsvData, hslData, cmykData, controls; // controls should be the
																																															// only
		// section where it cannot be
		// exported and thus let ux
		// change its visibility at any
		// time!
		private JTabbedPane colorChooser; // this is the left side of the panel where the color gradient is
		private JScrollPane colorAttributes;
		private final JPanel attributes_List;

		public Container_TopPane()
		{
			this.setPreferredSize(new Dimension(_2const.WIDTH, _2const.HEIGHT / 2));
			this.setMinimumSize(this.getPreferredSize());
			this.setDividerLocation(_2const.WIDTH / 2 + _2const.WIDTH / 13);
			this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
			this.setBorder(BorderFactory.createEmptyBorder());

			this.colorChooser = new JTabbedPane();
			this.colorChooser.setPreferredSize(new Dimension(this.getDividerLocation(), this.getPreferredSize().height));
			this.colorChooser.setOpaque(true);

			final ui_ColorPicker.CPick_SuggestionsList colorChooser_Shades = new ui_ColorPicker.CPick_SuggestionsList();
			_1const.COLOR_ENQ.add(colorChooser_Shades);
			this.colorChooser.addTab("Generator", colorChooser_Shades);
			final ui_ColorPicker.CPick_GradRect colorChooser_gradientRect = new ui_ColorPicker.CPick_GradRect(); // RGBA
																																																						// Gradient
																																																						// Rectangle
			_1const.COLOR_ENQ.add(colorChooser_gradientRect);
			this.colorChooser.addTab("GradRect Pick", colorChooser_gradientRect);

			if ("true".equalsIgnoreCase(_1const.val.get_value("soft_debug")))
			{
				final ui_ColorPicker.CPick_GenericDisp colorChooser_Debug = new ui_ColorPicker.CPick_GenericDisp();
				colorChooser_Debug.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 1));
				_1const.COLOR_ENQ.add(colorChooser_Debug);
				this.colorChooser.addTab("!Debug", colorChooser_Debug);
			}

			/*---------------------------------------------------------------------------- /
			/                                                                              /
			/ Color defaultColor = new Color((float) Math.random(), (float) Math.random(), /
			/                 (float) Math.random(),                                       /
			/                 (float) Math.random());                                      /
			/-----------------------------------------------------------------------------*/

			this.colorAttributes = new JScrollPane();
			this.colorAttributes.setOpaque(true);
			this.colorAttributes.getVerticalScrollBar().setUnitIncrement(8);
			this.colorAttributes.setAutoscrolls(false);
			this.colorAttributes.setBorder(BorderFactory.createEmptyBorder());
			this.colorAttributes.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			this.colorAttributes.setHorizontalScrollBarPolicy(
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			/*-------------------------------------------------------------------------------------------------------------- /
			/                                                                                                                /
			/ if a master wrapper component AKA a component that gets added to attributes_List directly must always state the following methods: /
			/ setAlignmentX(Component.LEFT_ALIGNMENT);                                                                       /
			/ setBorder(ux_Helper.bottom_container_AttributesBorder(""))                                                     /
			/                                                                                                                /
			/ otherwise things get funky in the ui due to layout conflicts                                                   /
			/---------------------------------------------------------------------------------------------------------------*/

			final JPanel wrapper_ColorAttributes = new JPanel();
			wrapper_ColorAttributes.setOpaque(false);
			wrapper_ColorAttributes.setLayout(new BoxLayout(wrapper_ColorAttributes, BoxLayout.Y_AXIS));
			wrapper_ColorAttributes.setBorder(BorderFactory.createEmptyBorder());

			final JLabel colorDisplay = Boolean.TRUE.equals((Boolean) _1const.val.parse("containers_rounded").get())
					? new ui_R_ColorLabel("ABCDEFGHIJKLMNOPQ", 6, Color.BLACK, 2)
					: new JLabel("ABCDEFGHIJKLMNOPQ"); // IMPORTANT STARTUP
			// OBJECT
			colorDisplay.setPreferredSize(new Dimension(50, 20));
			colorDisplay.setOpaque(true);

			final JLabel colorDisplay_R = Boolean.TRUE.equals((Boolean) _1const.val.parse("containers_rounded").get())
					? new ui_R_ColorLabel(colorDisplay.getText(), 6, Color.RED, 2)
					: new JLabel(colorDisplay.getText());
			colorDisplay_R.setPreferredSize(new Dimension(50, 20));
			colorDisplay_R.setOpaque(true);
			/*----------------------------------------------------------------------- /
			/ colorDisplay_R.setBorder(BorderFactory.createLineBorder(Color.RED, 2)); /
			/------------------------------------------------------------------------*/

			final JLabel colorDisplay_G = Boolean.TRUE.equals((Boolean) _1const.val.parse("containers_rounded").get())
					? new ui_R_ColorLabel(colorDisplay.getText(), 6, Color.GREEN, 2)
					: new JLabel(colorDisplay.getText());
			colorDisplay_G.setPreferredSize(new Dimension(50, 20));
			colorDisplay_G.setOpaque(true);
			/*------------------------------------------------------------------------- /
			/ colorDisplay_G.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2)); /
			/--------------------------------------------------------------------------*/

			final JLabel colorDisplay_B = Boolean.TRUE.equals((Boolean) _1const.val.parse("containers_rounded").get())
					? new ui_R_ColorLabel(colorDisplay.getText(), 6, Color.BLUE, 2)
					: new JLabel(colorDisplay.getText());
			colorDisplay_B.setPreferredSize(new Dimension(50, 20));
			colorDisplay_B.setOpaque(true);
			/*------------------------------------------------------------------------ /
			/ colorDisplay_B.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2)); /
			/-------------------------------------------------------------------------*/

			final JLabel colorDisplay_A = Boolean.TRUE.equals((Boolean) _1const.val.parse("containers_rounded").get())
					? new ui_R_ColorLabel(colorDisplay.getText(), 6, Color.BLACK, 2)
					: new JLabel(colorDisplay.getText()); // 17 characters to
			// be
			// correct length
			colorDisplay_A.setPreferredSize(new Dimension(50, 20));
			colorDisplay_A.setOpaque(true);
			/*---------------------------------------------------- /
			/ colorDisplay_A.setBorder(                            /
			/     BorderFactory.createLineBorder(Color.black, 2)); /
			/-----------------------------------------------------*/

			wrapper_ColorAttributes.add(colorDisplay);
			wrapper_ColorAttributes.add(colorDisplay_R);
			wrapper_ColorAttributes.add(colorDisplay_G);
			wrapper_ColorAttributes.add(colorDisplay_B);
			wrapper_ColorAttributes.add(colorDisplay_A);

			final JPanel rawRGBData = new JPanel();
			rawRGBData.setOpaque(false);
			rawRGBData.setLayout(new BoxLayout(rawRGBData, BoxLayout.Y_AXIS));

			final JLabel rgba_main = new JLabel();
			final JLabel rgba_R = new JLabel();
			final JLabel rgba_G = new JLabel();
			final JLabel rgba_B = new JLabel();
			final JLabel rgba_A = new JLabel();

			rawRGBData.add(rgba_main);
			rawRGBData.add(rgba_R);
			rawRGBData.add(rgba_G);
			rawRGBData.add(rgba_B);
			rawRGBData.add(rgba_A);

			this.rgbData = new JPanel();
			this.rgbData.setName("RGBA");
			this.rgbData.setOpaque(true);
			this.rgbData.setAlignmentX(Component.LEFT_ALIGNMENT);
			this.rgbData.setBorder(ux_Helper.bottom_container_AttributesBorder("-- RGBA"));
			this.rgbData.setLayout(new BoxLayout(this.rgbData, BoxLayout.X_AXIS));
			this.rgbData.add(wrapper_ColorAttributes);
			this.rgbData.add(rawRGBData);

			this.miscAttributes = new JPanel();
			this.miscAttributes.setName("Miscellaneous");
			this.miscAttributes.setLayout(new BoxLayout(this.miscAttributes, BoxLayout.Y_AXIS));
			this.miscAttributes.setAlignmentX(Component.LEFT_ALIGNMENT);
			this.miscAttributes.setBorder(
					ux_Helper.bottom_container_AttributesBorder("-- MISC"));

			final JLabel hexColor = new JLabel();
			final JLabel transparency = new JLabel();
			final JLabel colorFunction_RGB = new JLabel();
			final JLabel colorFunction_RGBA = new JLabel();
			final JLabel colorFunction_HSV = new JLabel();
			final JLabel colorFunction_HSL = new JLabel();
			final JLabel colorFunction_CMYK = new JLabel();

			this.miscAttributes.add(hexColor);
			this.miscAttributes.add(transparency);
			this.miscAttributes.add(colorFunction_RGB);
			this.miscAttributes.add(colorFunction_RGBA);
			this.miscAttributes.add(colorFunction_HSV);
			this.miscAttributes.add(colorFunction_HSL);
			this.miscAttributes.add(colorFunction_CMYK);

			this.colorSpace = new JPanel();
			this.colorSpace.setName("Color Space");
			this.colorSpace.setLayout(new BoxLayout(this.colorSpace, BoxLayout.Y_AXIS));
			this.colorSpace.setAlignmentX(Component.LEFT_ALIGNMENT);
			this.colorSpace.setBorder(ux_Helper.bottom_container_AttributesBorder("-- Color Space"));

			final JEditorPane colorSpace_scrollPane = new JEditorPane();
			colorSpace_scrollPane.setContentType("text/html");
			colorSpace_scrollPane.setEditable(false);
			colorSpace_scrollPane.setFocusable(false);

			/*-------------------------------------------------------------------------------------------- /
			/ JLabel colorSpace_componentsCount = new JLabel(                                              /
			/                 "Components: " + defaultColor.getColorSpace().getNumComponents());           /
			/ JLabel colorSpace_name = new JLabel("Color Space: "                                          /
			/                 + extend_stl_Colors.awt_colorspace_NameMatch(defaultColor.getColorSpace())); /
			/---------------------------------------------------------------------------------------------*/

			this.colorSpace.add(colorSpace_scrollPane);

			this.hsvData = new JPanel();
			this.hsvData.setName("HSV");
			this.hsvData.setLayout(new BoxLayout(this.hsvData, BoxLayout.Y_AXIS));
			this.hsvData.setAlignmentX(Component.LEFT_ALIGNMENT);
			this.hsvData.setBorder(
					ux_Helper.bottom_container_AttributesBorder("-- HSV"));

			final JLabel hsvData_hue = new JLabel();
			final JLabel hsvData_saturation = new JLabel();
			final JLabel hsvData_value = new JLabel();

			this.hsvData.add(hsvData_hue);
			this.hsvData.add(hsvData_saturation);
			this.hsvData.add(hsvData_value);

			this.controls = new JPanel();
			this.controls.setLayout(new GridLayout(3, 2, 4, 4)); // 4 buttons
			this.controls.setAlignmentX(Component.LEFT_ALIGNMENT);
			this.controls.setBorder(ux_Helper.bottom_container_AttributesBorder("-- Controls"));

			final JButton controls_randomColor = new JButton("Random Color");
			ux_Theme.based_set_rrect(controls_randomColor);

			controls_randomColor.setForeground(ux_Theme._theme.fg_awt());
			controls_randomColor.setBackground(ux_Theme._theme.secondary_awt());
			controls_randomColor.addActionListener(ev -> _1const.COLOR_ENQ
					.dispatch(stl_Struct.make_pair(extend_stl_Colors.awt_random_Color(), false)));

			final JButton controls_screenColorPicker = new JButton("Screen Picker");
			controls_screenColorPicker.setForeground(ux_Theme._theme.fg_awt());
			controls_screenColorPicker.setBackground(ux_Theme._theme.secondary_awt());
			ux_Theme.based_set_rrect(controls_screenColorPicker);

			/*-------------------------------------------------------------------------------------- /
			/ controls_screenColorPicker.addActionListener(ev -> ux.sampled_MousePicker(100L, e -> { /
			/   _1const.COLOR_ENQ.dispatch(stl_Struct.make_pair(e.second, false));                   /
			/   return (Void) null;                                                                  /
			/ }));                                                                                   /
			/---------------------------------------------------------------------------------------*/

			final JButton controls_forceRevalidate = new JButton("Refresh UI");
			ux_Theme.based_set_rrect(controls_forceRevalidate);

			controls_forceRevalidate.setForeground(ux_Theme._theme.fg_awt());
			controls_forceRevalidate.setBackground(ux_Theme._theme.secondary_awt());
			controls_forceRevalidate.addActionListener(ev -> ux._ux.force_redo());

			final JButton controls_gc = new JButton("GC");
			ux_Theme.based_set_rrect(controls_gc);
			controls_gc.setBorderPainted(false);
			controls_gc.setForeground(ux_Theme._theme.fg_awt());
			controls_gc.setBackground(ux_Theme._theme.secondary_awt());
			controls_gc.addActionListener(ev -> System.gc());

			final JButton controls_randomScreenColor = new JButton("Random Screen Color");
			ux_Theme.based_set_rrect(controls_randomScreenColor);

			controls_randomScreenColor.setForeground(ux_Theme._theme.fg_awt());
			controls_randomScreenColor.setBackground(ux_Theme._theme.secondary_awt());
			controls_randomScreenColor.addActionListener(
					ev -> _1const.COLOR_ENQ.dispatch(stl_Struct.make_pair(stl_Ware.screen_colorAt_Rnd().get(), false)));

			final JButton controls_ClearHistory = new JButton("Clear Palette History");
			ux_Theme.based_set_rrect(controls_ClearHistory);
			controls_randomScreenColor.setForeground(ux_Theme._theme.fg_awt());
			controls_randomScreenColor.setBackground(stl_Colors.hexToRGB(ux_Theme._theme.get("blue")));
			controls_randomScreenColor.addActionListener(
					ev -> ux._ux.clear_history());

			this.controls.add(controls_randomColor);
			this.controls.add(controls_randomScreenColor);
			this.controls.add(controls_screenColorPicker);
			this.controls.add(controls_ClearHistory);

			// system level
			this.controls.add(controls_forceRevalidate);
			this.controls.add(controls_gc);

			this.hslData = new JPanel();
			this.hslData.setName("HSL");
			this.hslData.setLayout(new BoxLayout(this.hslData, BoxLayout.Y_AXIS));
			this.hslData.setAlignmentX(Component.LEFT_ALIGNMENT);
			this.hslData.setBorder(ux_Helper.bottom_container_AttributesBorder("-- HSL"));

			final JLabel hslData_hue = new JLabel();
			final JLabel hslData_saturation = new JLabel();
			final JLabel hslData_lightness = new JLabel();

			this.hslData.add(hslData_hue);
			this.hslData.add(hslData_saturation);
			this.hslData.add(hslData_lightness);

			this.cmykData = new JPanel();
			this.cmykData.setLayout(new BoxLayout(this.cmykData, BoxLayout.X_AXIS));
			this.cmykData.setAlignmentX(Component.LEFT_ALIGNMENT);
			this.cmykData.setBorder(ux_Helper.bottom_container_AttributesBorder("-- CMYK"));
			this.cmykData.setName("CMYK");

			final JPanel cmykData_Attributes = new JPanel();
			cmykData_Attributes.setLayout(new BoxLayout(cmykData_Attributes, BoxLayout.Y_AXIS));

			final JLabel cmykData_C = new JLabel();
			final JLabel cmykData_M = new JLabel();
			final JLabel cmykData_Y = new JLabel();
			final JLabel cmykData_K = new JLabel();

			cmykData_Attributes.add(cmykData_C);
			cmykData_Attributes.add(cmykData_M);
			cmykData_Attributes.add(cmykData_Y);
			cmykData_Attributes.add(cmykData_K);

			final JPanel cmykData_ColorDisplay = new JPanel();
			cmykData_ColorDisplay.setLayout(new BoxLayout(cmykData_ColorDisplay, BoxLayout.Y_AXIS));
			final JLabel cmykData_CD_C = Boolean.TRUE.equals((Boolean) _1const.val.parse("containers_rounded").get())
					? new ui_R_ColorLabel(colorDisplay.getText(), 6, Color.CYAN, 2)
					: new JLabel(colorDisplay.getText());
			cmykData_CD_C.setOpaque(true);
			final JLabel cmykData_CD_M = Boolean.TRUE.equals((Boolean) _1const.val.parse("containers_rounded").get())
					? new ui_R_ColorLabel(colorDisplay.getText(), 6, Color.MAGENTA, 2)
					: new JLabel(colorDisplay.getText());
			cmykData_CD_M.setOpaque(true);
			final JLabel cmykData_CD_Y = Boolean.TRUE.equals((Boolean) _1const.val.parse("containers_rounded").get())
					? new ui_R_ColorLabel(colorDisplay.getText(), 6, new Color(0.88F, 0.88F, 0.88F, 1F), 2)
					: new JLabel(colorDisplay.getText());
			cmykData_CD_Y.setOpaque(true);
			final JLabel cmykData_CD_K = Boolean.TRUE.equals((Boolean) _1const.val.parse("containers_rounded").get())
					? new ui_R_ColorLabel(colorDisplay.getText(), 6, Color.BLACK, 2)
					: new JLabel(colorDisplay.getText());
			cmykData_CD_K.setOpaque(true);

			cmykData_ColorDisplay.add(cmykData_CD_C);
			cmykData_ColorDisplay.add(cmykData_CD_M);
			cmykData_ColorDisplay.add(cmykData_CD_Y);
			cmykData_ColorDisplay.add(cmykData_CD_K);

			this.cmykData.add(cmykData_ColorDisplay);
			this.cmykData.add(Box.createHorizontalStrut(6));
			this.cmykData.add(cmykData_Attributes);

			/*---------------------------------------------------------- /
			/ idk why this was created so just commenting it out for now /
			/ JEditorPane hsl_dataScrollPane = new JEditorPane();        /
			/ hsl_dataScrollPane.setContentType("text/html");            /
			/ hsl_dataScrollPane.setEditable(false);                     /
			/ hsl_dataScrollPane.setFocusable(false);                    /
			/-----------------------------------------------------------*/

			this.attributes_List = new JPanel();
			this.attributes_List.setLayout(new BoxLayout(this.attributes_List, BoxLayout.Y_AXIS));
			this.attributes_List.setOpaque(true);
			this.attributes_List.add(this.controls);
			this.attributes_List.add(this.miscAttributes);
			this.attributes_List.add(this.rgbData);
			this.attributes_List.add(this.hsvData);
			this.attributes_List.add(this.hslData);
			this.attributes_List.add(this.cmykData);
			this.attributes_List.add(this.colorSpace);

			final ui_LazyViewport $0219430 = new ui_LazyViewport();
			$0219430.setView(this.attributes_List);

			this.colorAttributes.setViewportView($0219430);

			this.setLeftComponent(this.colorChooser);
			this.setRightComponent(this.colorAttributes);

			_1const.COLOR_ENQ.add(pair -> { // GUI ONLY LISTENER
				_render.unify_(this, nil -> {
					final Color x = pair.first;
					final float[] x_rgba = new float[] { x.getRed(), x.getGreen(), x.getBlue(), x.getAlpha() };
					final float[] hsl = extend_stl_Colors.rgbToHsl(x_rgba);
					final float[] hsv = extend_stl_Colors.rgbToHsv(x_rgba);
					final float[] cmyk = extend_stl_Colors.rgbToCmyk(x_rgba);
					final float[] ciexyz = x.getColorSpace().toCIEXYZ(new float[] { x.getRed(),
							x.getGreen(), x.getBlue(), x.getAlpha() });
					SwingUtilities.invokeLater(() -> {
						log("TOP_G_PANE", jm_Ansi.make().yellow().toString("Rewashing all of the color text properties to set"));
						colorDisplay.setForeground(x);
						colorDisplay.setBackground(x);
						if (Boolean.TRUE.equals((Boolean) _1const.val.parse("containers_rounded").get()))
							((ui_R_ColorLabel) colorDisplay).borderColor(x);

						colorDisplay_R.setBackground(new Color(
								colorDisplay.getBackground().getRed() / 255F, 0F, 0F));
						colorDisplay_R.setForeground(colorDisplay_R.getBackground());
						/*--------------------------------------------------------------- /
						/ colorDisplay_R.setBorder(BorderFactory                          /
						/     .createLineBorder(                                          /
						/         x.getRed() < 255 / 2 ? new Color(x.getRed() / 255F, 0F, /
						/             0F).brighter()                                      /
						/             : new Color(x.getRed() / 255F, 0F,                  /
						/                 0F).darker(),                                   /
						/         2));                                                    /
						/                                                                 /
						/----------------------------------------------------------------*/
						colorDisplay_G.setBackground(new Color(0F,
								colorDisplay.getBackground().getGreen() / 255F, 0F));
						colorDisplay_G.setForeground(colorDisplay_G.getBackground());
						/*----------------------------------------------------------------------- /
						/ colorDisplay_G                                                          /
						/     .setBorder(BorderFactory                                            /
						/         .createLineBorder(                                              /
						/             x.getGreen() < 255 / 2 ? new Color(0F, x.getGreen() / 255F, /
						/                 0F).brighter()                                          /
						/                 : new Color(0F, x.getGreen() / 255F,                    /
						/                     0F).darker(),                                       /
						/             2));                                                        /
						/------------------------------------------------------------------------*/

						colorDisplay_B.setBackground(new Color(0F, 0F,
								colorDisplay.getBackground().getBlue() / 255F));
						colorDisplay_B.setForeground(colorDisplay_B.getBackground());
						/*-------------------------------------------------------------------------------- /
						/ colorDisplay_B                                                                   /
						/     .setBorder(BorderFactory.createLineBorder(                                   /
						/         x.getBlue() < 255 / 2 ? new Color(0F, 0F, x.getBlue() / 255F).brighter() /
						/             : new Color(0F, 0F, x.getBlue() / 255F).darker(),                    /
						/         2));                                                                     /
						/---------------------------------------------------------------------------------*/
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

						hexColor.setText(
								"<html><strong>Hex</strong>: " + extend_stl_Colors.color2hex_2(x) + "</html>");
						transparency.setText("<html><strong>Transparency</strong>: "
								+ x.getTransparency() + "</html>");
						colorFunction_RGB.setText(
								"<html><strong>CSS rgb</strong>: <p style=\"background-color:black;color:"
										+ (extend_stl_Colors.RGBToHex((int) ux_Theme._theme.dominant()[0],
												(int) ux_Theme._theme.dominant()[1], (int) ux_Theme._theme.dominant()[2]))
										+ "\">rgb<span style=\"color:white\">("
										+ x.getRed() + ", "
										+ x.getGreen() + ", " + x.getBlue()
										+ ")</span></p></html>");
						colorFunction_RGBA.setText(
								"<html><strong>CSS rgba</strong>: <p style=\"background-color:black;color:"
										+ (extend_stl_Colors.RGBToHex((int) ux_Theme._theme.dominant()[0],
												(int) ux_Theme._theme.dominant()[1], (int) ux_Theme._theme.dominant()[2]))
										+ "\">rgba<span style=\"color:white\">("
										+ x.getRed() + ", "
										+ x.getGreen() + ", " + x.getBlue()
										+ ", "
										+ x.getAlpha() + ")</span></p></html>");
						colorFunction_HSV.setText(
								"<html><strong>CSS hsv</strong>: <p style=\"background-color:black;color:"
										+ (extend_stl_Colors.RGBToHex((int) ux_Theme._theme.dominant()[0],
												(int) ux_Theme._theme.dominant()[1], (int) ux_Theme._theme.dominant()[2]))
										+ "\">hsv<span style=\"color:white\">("
										+ hsv[0] + ", " + hsv[1] * 100F
										+ "%, " + hsv[2] * 100F
										+ "%)</span></p></html>");

						colorFunction_HSL.setText(
								"<html><strong>CSS hsl</strong>: <p style=\"background-color:black;color:"
										+ (extend_stl_Colors.RGBToHex((int) ux_Theme._theme.dominant()[0],
												(int) ux_Theme._theme.dominant()[1], (int) ux_Theme._theme.dominant()[2]))
										+ "\">hsl<span style=\"color:white\">("
										+ hsv[0] + ", " + hsv[1] * 100F
										+ "%, " + hsl[2] * 100F
										+ "%)</span></p></html>");
						colorFunction_CMYK
								.setText("<html><strong>CSS cmyk</strong>: <p style=\"background-color:black;color:"
										+ (extend_stl_Colors.RGBToHex((int) ux_Theme._theme.dominant()[0],
												(int) ux_Theme._theme.dominant()[1], (int) ux_Theme._theme.dominant()[2]))
										+ "\">cmyk<span style=\"color:white\">("
										+ cmyk[0] * 100F + "%, " + cmyk[1] * 100F
										+ "%, " + cmyk[2] * 100F
										+ "%" + cmyk[3] * 100F + "%)</span></p></html>");

						hsvData_hue.setText("Hue        (H): " + hsv[0]);
						hsvData_saturation.setText("Saturation (S): " + hsv[1]);
						hsvData_value.setText("Value      (V): " + hsv[2]);

						hslData_hue.setText("Hue        (H): " + hsl[0]);
						hslData_saturation.setText("Saturation (S): " + hsl[1]);
						hslData_lightness.setText("Lightness  (L): " + hsl[2]);

						cmykData_C.setText("Cyan    (C): " + String.format("%,.4f", cmyk[0]) + " ["
								+ String.format("%,.2f", 100 * cmyk[0]) + "%]");
						cmykData_M.setText("Magenta (M): " + String.format("%,.4f", cmyk[1]) + " ["
								+ String.format("%,.2f", 100 * cmyk[1]) + "%]");
						cmykData_Y.setText("Yellow  (Y): " + String.format("%,.4f", cmyk[2]) + " ["
								+ String.format("%,.2f", 100 * cmyk[2]) + "%]");
						cmykData_K.setText("Key     (K): " + String.format("%,.4f", cmyk[3]) + " ["
								+ String.format("%,.2f", 100 * cmyk[3]) + "%]");

						final float[][] rrr = extend_stl_Colors.cmykToRgb_Comps(cmyk);
						/*-------------------------------------------------------------------- /
						/ System.out.println(Arrays.toString(cmyk) + "\n=====");               /
						/ for (float[] t : rrr)                                                /
						/   for (float tt : t)                                                 /
						/     System.out.println(tt); // test for cmyk conversion inaccuracies /
						/ System.out.println();                                                /
						/---------------------------------------------------------------------*/
						cmykData_CD_C.setBackground(extend_stl_Colors.awt_remake(rrr[0]));
						cmykData_CD_C.setForeground(cmykData_CD_C.getBackground());
						cmykData_CD_M.setBackground(extend_stl_Colors.awt_remake(rrr[1]));
						cmykData_CD_M.setForeground(cmykData_CD_M.getBackground());
						cmykData_CD_Y.setBackground(extend_stl_Colors.awt_remake(rrr[2]));
						cmykData_CD_Y.setForeground(cmykData_CD_Y.getBackground());
						cmykData_CD_K.setBackground(extend_stl_Colors.awt_remake(rrr[3]));
						cmykData_CD_K.setForeground(cmykData_CD_K.getBackground());

						final StringBuilder sb = new StringBuilder(
								"<strong>Component Names</strong>:"),
								sb2 = new StringBuilder(
										"<strong>Component Min , Max</strong>:"),
								sb3 = new StringBuilder(
										"<strong>CIEXYZ Form</strong>:");

						for (final float rrtr : ciexyz)
							sb3.append("<br>&nbsp;&nbsp;" + rrtr);

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

						this.revalidate();

					});
					return (Void) null;
				});

				return (Void) null;
			});
			/*------------------------------------------------------------------------------------------ /
			/ _1const.add(() -> { // this task basically just constantly changes the color of the button /
			/   Color randomColor = extend_stl_Colors.awt_random_Color();                                /
			/   controls_randomColor.setBackground(randomColor);                                         /
			/   controls_randomColor.setForeground(stl_Colors.awt_ColorInvert(randomColor));             /
			/   controls_randomColor.repaint(50L);                                                       /
			/ }, 200L, 500L);                                                                            /
			/-------------------------------------------------------------------------------------------*/
		}

		public synchronized void redo()
		{
			this.controls.revalidate();
			this.colorAttributes.setMaximumSize(
					new Dimension(this.colorAttributes.getPreferredSize().width + 50,
							this.colorAttributes.getPreferredSize().height));
			this.controls.doLayout();
			this.setMinimumSize(new Dimension(this.getPreferredSize().width, this.getPreferredSize().height + 65));
			this.doLayout();
			this.revalidate();
			this.repaint(100L);
		}

	}

	public synchronized void append_attribute(final JComponent e)
	{
		e.setAlignmentX(Component.CENTER_ALIGNMENT);
		e.setAlignmentY(Component.LEFT_ALIGNMENT);
		this.top.attributes_List.add(e);
		this.top.attributes_List.validate();
	}

	public void validate_size()
	{
		this.top.colorAttributes.setMinimumSize(this.top.colorAttributes.getPreferredSize());
		this.top.setDividerLocation(this.top.colorChooser.getPreferredSize().width + 50);
	}

	public static class Container_BottomPane
			extends JSplitPane
			implements
			stl_Listener< stl_Struct.struct_Pair< Color, Boolean > >
	{

		// we don't care about implementation for History as a palette component
		JTable currentPalette;
		JPanel listHistory;
		HashSet< String > history;

		static int HISTORY_BUTTON_HEIGHT = 35, HISTORY_BUTTON_WIDTH = 115;

		public Container_BottomPane()
		{
			this.setPreferredSize(new Dimension(_2const.WIDTH, _2const.HEIGHT / 3));
			this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
			this.setDividerLocation(_2const.WIDTH / 2 + _2const.WIDTH / 10);
			this.setBorder(BorderFactory.createEmptyBorder());

			this.listHistory = new JPanel();
			this.listHistory.setLayout(new ui_WrapLayout(FlowLayout.LEADING));
			this.currentPalette = new JTable();
			this.history = new HashSet<>(20);

			JScrollPane history_JSP = new JScrollPane();
			history_JSP.setBorder(BorderFactory.createEmptyBorder());
			history_JSP.getVerticalScrollBar().setUnitIncrement(8);

			ui_LazyViewport history_Viewport = new ui_LazyViewport();
			history_Viewport.setView(listHistory);

			history_JSP.setViewportView(history_Viewport);

			this.setRightComponent(this.currentPalette);
			this.setLeftComponent(history_JSP);

			_1const.COLOR_ENQ.add(this);
		}

		public synchronized void clear_history() // ! EXPORT
		{
			/*------------------------------------------------------------------------------------------------------- /
			/ if (listHistory.getComponents().length > 0)                                                             /
			/ {                                                                                                       /
			/   log("BOT_G_HISTORY",                                                                                  /
			/       jm_Ansi.make().blue().toString("Cleared the current history, requested by user!") + "Timestamp: " /
			/           + System.currentTimeMillis()                                                                  /
			/           + " To remove: " + listHistory.getComponents().length);                                       /
			/   SwingUtilities.invokeLater(() -> {                                                                    /
			/     for(int i = 0; i < listHistory.getComponentCount(); i++) listHistory.remove(i);                     /
			/     listHistory.repaint(75L);                                                                           /
			/   });                                                                                                   /
			/ }                                                                                                       /
			/ else log("BOT_H_HISTORY", "Ignored clear request: history is empty!");                                  /
			/--------------------------------------------------------------------------------------------------------*/
		}

		@Override public Void call(struct_Pair< Color, Boolean > arg0)
		{
			String colorHex = extend_stl_Colors.RGBToHex(arg0.first.getRed(), arg0.first.getGreen(), arg0.first.getBlue());

			if (Boolean.FALSE.equals(arg0.second) && !history.contains(colorHex))
			{
				// make a popupmenu for this so we can have proper controls for this button
				// preferably also make a global one and then spawn a unique one for just this
				log("BOT_G_HISTORY", "Logs a new color to history: " + arg0.first);
				ui_PTag jb = ux_Helper.history_palette_btn(arg0.first, HISTORY_BUTTON_WIDTH, HISTORY_BUTTON_HEIGHT);
				jb.setName(colorHex);
				ux_Theme.based_set_rrect(jb);

				// Remove duplicate if it exists
				for (Component component : listHistory.getComponents())
				{
					if (component instanceof ui_PTag && colorHex.equals(component.getName()))
					{
						listHistory.remove(component);
						break;
					}
				}

				listHistory.add(jb, 0);

				SwingUtilities.invokeLater(() -> {
					listHistory.revalidate();
					listHistory.repaint(75L);
				});
			}

			return (Void) null;
		}

	}
}