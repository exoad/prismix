// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.rmi.UnexpectedException;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import com.jackmeng.prismix.stl.extend_stl_Colors;

public final class gui_Dev
		extends
		gui

{
	private ux_WindowRes reser;
	private int pX, pY;

	public gui_Dev()
	{
		super("Prismix : dev_window");
		reser = new ux_WindowRes();
		reser.registerComponent(getFrame());
		reser.setSnapSize(new Dimension(5, 5));
		reser.setDragInsets(new Insets(4, 4, 4, 4));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(470, 350));
		setBackground(Color.black);
		setUndecorated(true);
		setAlwaysOnTop(true);
		JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2, false));
		pane.setLayout(new ux_WrapLayout(FlowLayout.LEFT, 8, 10));
		pane.setOpaque(true);
		pane.setBackground(Color.black);

		pane.addMouseListener(new MouseAdapter() {

			@Override public void mousePressed(MouseEvent me)
			{
				pX = me.getX();
				pY = me.getY();
			}

			@Override public void mouseDragged(MouseEvent me)
			{
				gui_Dev.this.setLocation(gui_Dev.this.getLocation().x + me.getX() - pX,
						gui_Dev.this.getLocation().y + me.getY() - pY);
			}
		});
		pane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override public void mouseDragged(MouseEvent me)
			{
				gui_Dev.this.setLocation(gui_Dev.this.getLocation().x + me.getX() - pX,
						gui_Dev.this.getLocation().y + me.getY() - pY);
			}
		});

		pane.add(stx_Helper.quick_btn("GC", System::gc));
		pane.add(stx_Helper.quick_btn("Hide MAINUI", () -> __ux._ux.getMainUI().setVisible(false)));
		pane.add(stx_Helper.quick_btn("Show MAINUI", () -> __ux._ux.getMainUI().setVisible(true)));
		pane.add(stx_Helper.quick_btn("Reval MAINUI", () -> __ux._ux.getMainUI().getContentPane().revalidate()));
		pane.add(stx_Helper.quick_btn("Repaint MAINUI", () -> __ux._ux.getMainUI().repaint()));
		pane.add(stx_Helper.quick_btn("Update XOR", () -> {
			Graphics g = __ux._ux.getMainUI().getGraphics();
			g.setXORMode(extend_stl_Colors.awt_random_Color());
			__ux._ux.getMainUI().update(g);
		}));
		pane.add(stx_Helper.quick_btn("Intrp_BIC", () -> {
			Graphics2D g = (Graphics2D) __ux._ux.getMainUI().getGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			__ux._ux.getMainUI().update(g);
		}));
		pane.add(stx_Helper.quick_btn("Intrp_AA", () -> {
			Graphics2D g = (Graphics2D) __ux._ux.getMainUI().getGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			__ux._ux.getMainUI().update(g);
		}));
		pane.add(stx_Helper.quick_btn("AlphaC::0.5", () -> {
			Graphics2D g = (Graphics2D) __ux._ux.getMainUI().getGraphics();
			g.setComposite(java.awt.AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5F));
			__ux._ux.getMainUI().update(g);
		}));
		pane.add(stx_Helper.quick_btn("Vali MainUI",
				() -> __ux._ux.getMainUI().paintComponents(__ux._ux.getMainUI().getGraphics())));
		pane.add(stx_Helper.quick_btn("EmitEX", () -> {
			try
			{
				throw new UnexpectedException("CHECKED ERROR [DEVELOPER CONSOLE]");
			} catch (UnexpectedException e)
			{
				throw new RuntimeException("DEVELOPER_CONTROLLED", e);
			}
		}));

		pane.add(stx_Helper.quick_btn("EmitIN",
				() -> gui_XInf.force_invoke("<html><body>Test info windowing</html></body>", "Test Info Window")));
		pane.add(stx_Helper.quick_btn("ExitDev",
				this::dispose));
		pane.add(stx_Helper.quick_btn("Kill0", () -> System.exit(0)));
		pane.add(stx_Helper.quick_btn("Kill1", () -> System.exit(1)));
		pane.add(stx_Helper.quick_btn("ClearLog", () -> System.out.println("\033[H\033[2J")));
		int i = 0;
		for (Runnable r : new Runnable[] {
				() -> ui_Graff.main((String[]) null),
				gui_XAddColor::request
		})
			pane.add(stx_Helper.quick_btn("Action#" + (i++), r));

		JScrollPane jsp = new JScrollPane();
		jsp.setBackground(Color.black);
		jsp.setBorder(BorderFactory.createEmptyBorder());
		jsp.setViewportView(ui_LazyViewport.make(pane));

		getContentPane().add(jsp);

	}
}