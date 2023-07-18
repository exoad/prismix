// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;

import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.prismix.ux.model.h_Graff;
import com.jackmeng.prismix.ux.model.h_GraphPoint;
import com.jackmeng.prismix.ux.ui_Graff.DrawAxis;
import com.jackmeng.prismix.ux.ui_Graff.DrawType;
import com.jackmeng.stl.stl_Colors;

import lombok.Getter;
import lombok.Setter;

import static com.jackmeng.prismix.jm_Prismix.*;

public final class gui_XColor
		extends
		gui
{
	@Getter private final JSplitPane jsp;
	@Getter @Setter private Color color;

	public gui_XColor(Color t)
	{
		super("Inspect: " + extend_stl_Colors.RGBToHex(t.getRed(), t.getGreen(), t.getBlue()));
		this.color = t;
		String r = extend_stl_Colors.RGBToHex(t.getRed(), t.getGreen(), t.getBlue());

		setPreferredSize(new Dimension(670, 350));

		jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		jsp.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		jsp.setDividerLocation(0.5D);

		JPanel image = new JPanel() {
			@Override public void paintComponent(Graphics g)
			{
				Graphics2D g2 = (Graphics2D) g;
				g2.clearRect(0, 0, getWidth(), getHeight());
				g2.setColor(color);
				g2.fill(new Rectangle2D.Float(0, 0, getWidth(), getHeight()));
				g2.dispose();
			}
		};
		image.setPreferredSize(new Dimension(getPreferredSize().width / 3, getPreferredSize().height));

		JLabel textLabel = new JLabel("<html><strong style>" + r + "</strong></html>");
		textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		textLabel.setForeground(
				extend_stl_Colors.awt_remake(extend_stl_Colors.binary_fg_decider(extend_stl_Colors.stripHex(r))));

		JPanel imageOverlay = new JPanel();
		imageOverlay.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		imageOverlay.setLayout(new OverlayLayout(imageOverlay));
		imageOverlay.add(textLabel);
		imageOverlay.add(image);

		JScrollPane rightInfoPane = new JScrollPane();
		ux_Theme.make_scrollbar(
				rightInfoPane.getVerticalScrollBar());
		ux_Theme.make_scrollbar(
				rightInfoPane.getHorizontalScrollBar());
		rightInfoPane.setBorder(BorderFactory.createEmptyBorder());
		rightInfoPane.setPreferredSize(
				new Dimension(getPreferredSize().width / 2, getPreferredSize().height));

		JPanel information = new JPanel();
		information.setBorder(BorderFactory.createEmptyBorder(10, 5, 4, 5));
		information.setLayout(new BoxLayout(information, BoxLayout.Y_AXIS));

		float[] hsv = extend_stl_Colors.rgbToHsv(extend_stl_Colors.awt_strip_rgba(color));
		float[] cmyk = extend_stl_Colors.rgbToCmyk(extend_stl_Colors.awt_strip_rgba(color));
		float[] hsl = extend_stl_Colors.rgbToHsl(extend_stl_Colors.awt_strip_rgba(color));
		float[] ciexyz_temp = color.getComponents(new float[4]);
		float[] ciexyz = color.getColorSpace().toCIEXYZ(ciexyz_temp);

		debug(Arrays.toString(ciexyz));

		information.add(_m("Hex", r));

		information
				.add(_m("ARGB", stl_Colors.RGBAtoHex(t.getAlpha(), t.getRed(), t.getGreen(), t.getBlue()) + "</html>"));
		information.add(_m("RGB", color.getRGB()));
		information.add(_m(
				"<code style=\"background-color:" + extend_stl_Colors.RGBToHex(255, 0, 0) + ";color:"
						+ extend_stl_Colors.RGBToHex(255, 0, 0) + "\">[]</code> Red [R]",
				t.getRed() + "&emsp;&emsp;<em>(" + t.getRed() / 255F + "%)</em>"));
		information.add(_m("<code style=\"background-color:" + extend_stl_Colors.RGBToHex(0, 255, 0) + ";color:"
				+ extend_stl_Colors.RGBToHex(0, 255, 0) + "\">[]</code> Green [G]",
				t.getRed() + "&emsp;&emsp;<em>(" + t.getGreen() / 255F + "%)</em>"));
		information.add(_m("<code style=\"background-color:" + extend_stl_Colors.RGBToHex(0, 0, 255) + ";color:"
				+ extend_stl_Colors.RGBToHex(0, 0, 255) + "\">[]</code> Blue [B]",
				t.getBlue() + "&emsp;&emsp;<em>(" + t.getBlue() / 255F + "%)</em>"));
		information.add(_m("Alpha [A]", t.getAlpha() + "&emsp;&emsp;<em>(" + t.getAlpha() / 255F + "%)</em>"));
		information.add(_m("Hue [H]", hsv[0]));
		information.add(_m("Saturation [S]", hsv[1]));
		information.add(_m("Value [V]", hsv[2]));
		information.add(_m("Lightness [L]", hsl[2]));
		information.add(_m("<code style=\"background-color:" + extend_stl_Colors.RGBToHex(0, 255, 255) + ";color:"
				+ extend_stl_Colors.RGBToHex(0, 255, 255) + "\">[]</code> Cyan [C]",
				String.format("%,.4f", cmyk[0]) + "&emsp;&emsp;<em>("
						+ String.format("%,.2f", 100 * cmyk[0]) + "%)</em>"));
		information.add(_m("<code style=\"background-color:" + extend_stl_Colors.RGBToHex(255, 0, 255) + ";color:"
				+ extend_stl_Colors.RGBToHex(255, 0, 255) + "\">[]</code> Magenta [M]",
				String.format("%,.4f", cmyk[1]) + "&emsp;&emsp;<em>("
						+ String.format("%,.2f", 100 * cmyk[1]) + "%)</em>"));
		information.add(_m("<code style=\"background-color:" + extend_stl_Colors.RGBToHex(255, 255, 0) + ";color:"
				+ extend_stl_Colors.RGBToHex(255, 255, 0) + "\">[]</code> Yellow [Y]",
				String.format("%,.4f", cmyk[2]) + "&emsp;&emsp;<em>("
						+ String.format("%,.2f", 100 * cmyk[2]) + "%)</em>"));
		information.add(_m("Key [K]", String.format("%,.4f", cmyk[3]) + "&emsp;&emsp;<em>("
				+ String.format("%,.2f", 100 * cmyk[3]) + "%)</em>"));
		information.add(_m("Luma (Formula 1)", extend_stl_Colors.relative_luminance_1(color)));
		information.add(_m("Luma (Formula 2)", extend_stl_Colors.relative_luminance_2(color)));
		information.add(_m("Luma (Formula 3)", extend_stl_Colors.relative_luminance_3(color)));
		information.add(_m("Luma (Formula 4)", extend_stl_Colors.relative_luminance_4(color)));
		information.add(_m("Color space", extend_stl_Colors.awt_colorspace_NameMatch(color.getColorSpace())));
		information.add(_m("Temperature", extend_stl_Colors.color_temp(color)));
		information.add(_m("CSS RGB",
				"<code style=\"color:" + (extend_stl_Colors.RGBToHex((int) ux_Theme.get().dominant()[0],
						(int) ux_Theme.get().dominant()[1], (int) ux_Theme.get().dominant()[2])) + "\">rgb</code><code>("
						+ color.getRed()
						+ ", " +
						color.getGreen() + ", " + color.getBlue() + ")</code>"));
		information.add(_m("CSS RGBA",
				"<code style=\"color:" + (extend_stl_Colors.RGBToHex((int) ux_Theme.get().dominant()[0],
						(int) ux_Theme.get().dominant()[1], (int) ux_Theme.get().dominant()[2])) + "\">rgba</code><code>("
						+ color.getRed()
						+ ", " +
						color.getGreen() + ", " + color.getBlue() + ", " + color.getAlpha() + ")</code>"));
		information.add(_m("CSS CMYK",
				"<code style=\"color:" + (extend_stl_Colors.RGBToHex((int) ux_Theme.get().dominant()[0],
						(int) ux_Theme.get().dominant()[1], (int) ux_Theme.get().dominant()[2])) + "\">cmyk</code><code>("
						+ cmyk[0] * 100F + "%, " + cmyk[1] * 100F
						+ "%, " + cmyk[2] * 100F
						+ "%" + cmyk[3] * 100F + "%)</code>"));
		information.add(_m("CSS HSV",
				"<code style=\"color:" + (extend_stl_Colors.RGBToHex((int) ux_Theme.get().dominant()[0],
						(int) ux_Theme.get().dominant()[1], (int) ux_Theme.get().dominant()[2])) + "\">hsv</code><code>("
						+ hsv[0] + ", " + (hsv[1] * 100F) + ", " + (hsv[2] * 100F) + ")</code>"));
		information.add(Box.createVerticalGlue());

		rightInfoPane.setViewportView(ui_LazyViewport.make(information));

		jsp.setLeftComponent(imageOverlay);
		jsp.setRightComponent(rightInfoPane);

		JSplitPane rgbValues_JSP = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		ArrayList< h_GraphPoint > rgbPoints = new ArrayList<>();
		rgbPoints.add(new h_GraphPoint(255 / 3 - (255 / 3 / 2), color.getRed(), true, Color.red));
		rgbPoints.add(new h_GraphPoint((255 / 3 * 2) - (255 / 3 / 2), color.getGreen(), true, Color.green));
		rgbPoints.add(new h_GraphPoint(255 - (255 / 3 / 2), color.getBlue(), true, Color.blue));
		Color ttttt = new Color(0.8F, 0.8F, 0.8F);

		h_Graff config = new h_Graff(true, ttttt, new Color(0, 0, 0, 0), ux_Theme._theme.get_awt("rose"),
				ux_Theme._theme.get_awt("rose").darker().darker(),
				Color.gray, "", 2, 2);

		ui_Graff graff_RGB = new ui_Graff(10, 255, rgbPoints, DrawType.BOTH, DrawAxis.Y, config, "{0}");
		graff_RGB.setFont(_1const._Mono_().deriveFont(9F));
		graff_RGB.setPreferredSize(new Dimension(getPreferredSize().height, getPreferredSize().height));

		JPanel rgbWrap = new JPanel();
		rgbWrap.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		rgbWrap.setLayout(new BorderLayout());
		rgbWrap.add(graff_RGB, BorderLayout.CENTER);
		rgbValues_JSP.setDividerLocation(410);
		rgbValues_JSP.setRightComponent(rgbWrap);
		rgbValues_JSP.setLeftComponent(jsp);

		getContentPane().add(rgbValues_JSP);
	}

	JComponent _m(String title, Object content)
	{
		JTextPane jtp = new JTextPane();
		jtp.setContentType("text/html");
		jtp.setBorder(BorderFactory.createEmptyBorder());
		jtp.setEditable(false);
		jtp.setText("<html><p><strong>" + title + "</strong></p>&emsp;&emsp;" + content.toString() + "</html>");
		jtp.setFont(jtp.getFont().deriveFont(13F));

		JPanel wrap = new JPanel();
		wrap.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));

		JButton copy = stx_Helper.quick_btn("Copy", () -> Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(new java.awt.datatransfer.StringSelection(stx_HTMLDebuff.parse(content.toString())), null));
		/*-------------------------------------------------------------------------------------------------------- /
		/ copy.setBorder(BorderFactory.createLineBorder(new Color(255 / 2, 255 / 2, 255 / 2, (int) (0.3 * 255)))); /
		/---------------------------------------------------------------------------------------------------------*/
		/*--------------------------------------------- /
		/ copy.setPreferredSize(new Dimension(46, 25)); /
		/----------------------------------------------*/

		wrap.add(copy);
		wrap.add(jtp);

		return wrap;

		/*------------------------------------------------------------------------------------------------------------------ /
		/ JLabel r = new JLabel("<html><p><strong>" + title + "</strong></p>&emsp;&emsp;" + content.toString() + "</html>"); /
		/ r.setRequestFocusEnabled(true);                                                                                    /
		/ r.setFocusCycleRoot(true);                                                                                         /
		/ r.setAlignmentX(Component.LEFT_ALIGNMENT);                                                                         /
		/ r.setHorizontalAlignment(SwingConstants.LEFT);                                                                     /
		/ return r;                                                                                                          /
		/-------------------------------------------------------------------------------------------------------------------*/
	}
}