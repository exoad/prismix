// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingContainer;
import javax.swing.SwingUtilities;

import com.jackmeng.ansicolors.jm_Ansi;
import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.stl.extend_stl_Colors;

import static com.jackmeng.prismix.jm_Prismix.*;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

@SwingContainer abstract sealed class gui
		extends
		JFrame
		implements
		Runnable
		permits
		gui_Main,
		gui_XErr,
		gui_XInf,
		gui_Lua,
		gui_Config,
		gui_XColor
{
	private JPanel notifier;

	protected gui(String r)
	{
		super(r);
		setIconImage(_1const.fetcher.image("assets/_icon.png"));
	}

	protected void attach_vnotifier(Component master)
	{
		if (notifier == null)
		{
			if (isVisible())
				log("GUI_MX", jm_Ansi.make().yellow_bg().black().toString(
						"Attach_VisualNotifier should be done when the GUI is offline/invisible | This is not a fatal bug."));
			notifier = new JPanel();
			notifier.setOpaque(false);
			notifier.setLayout(new ux_WrapLayout(FlowLayout.CENTER, 6, 10));
			notifier.setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
			JLayeredPane wrapper1 = new JLayeredPane();
			wrapper1.setLayout(new OverlayLayout(wrapper1));
			// this fixes the issue with the menubar and layeredpane being on different
			// layers within the ui stack which is dumb but ok
			if (getMenuBar() != null)
			{
				wrapper1.add(notifier, JLayeredPane.DEFAULT_LAYER + 1);
				wrapper1.add(master, JLayeredPane.DEFAULT_LAYER);
				setMenuBar(getMenuBar());
			}
			else
			{
				wrapper1.add(notifier, 1);
				wrapper1.add(master, 2);
			}
			setContentPane(wrapper1);
			revalidate();
			repaint(70L);
		}
		else log("GUI_MX",
				jm_Ansi.make().red_bg().white().toString("Already had an attached VisualNotifier | This is not a fatal bug."));
	}

	public synchronized void deploy_notif(String htmlContent, Color bg, float fadeStep, long totalDuration,
			long durationOnStep, int strokeWidth, boolean noFill)
	{
		if (notifier != null)
		{
			ui_Faded faded = noFill ? new ui_Faded(fadeStep, totalDuration, durationOnStep) {
				@Override public void paintComponent(Graphics g)
				{

					super.paintComponent(g);
					Graphics2D g2 = (Graphics2D) g;
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
					g2.setStroke(new BasicStroke(strokeWidth));
					g2.setColor(bg);
					g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
				}
			} : new ui_Faded(fadeStep, totalDuration, durationOnStep) {
				@Override public void paintComponent(Graphics g)
				{

					super.paintComponent(g);
					/*------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ /
					/ absolutely fuck this dumb fucking ass shit. i hate this framework painting schema so fucking much, i spent half a fucking day trying to see why "jl" wasnt beign printed and its apparantely all because dispose was called. sometimes it works, sometimes it doesnt, fuck ass undefined behavior with opengl suck me bitch fuck this. ok it works now lets go ^w^ /
					/-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
					Graphics2D g2 = (Graphics2D) g;
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2.setColor(bg);
					g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
					// dont dispose here (see above angry comment >w<) im going mental
				}
			};

			JLabel jl = new JLabel(htmlContent);
			jl.setHorizontalAlignment(SwingConstants.CENTER);
			jl.setVerticalAlignment(SwingConstants.CENTER);
			jl.setForeground(
					extend_stl_Colors.awt_remake(extend_stl_Colors.binary_fg_decider(extend_stl_Colors.awt_strip_rgba(bg))));

			faded.setPreferredSize(new Dimension(jl.getPreferredSize().width + 64, jl.getPreferredSize().height + 10));
			faded.setLayout(new BorderLayout());
			faded.setOpaque(false);
			faded.setBounds(0, (getWidth() - faded.getPreferredSize().width) / 2, faded.getPreferredSize().width,
					getPreferredSize().height);

			faded.setLayout(new BorderLayout());
			faded.add((JComponent) jl, BorderLayout.CENTER);
			notifier.add(faded);
			notifier.revalidate();
			notifier.repaint(70L);
			SwingUtilities.invokeLater(faded);

			log("GUI_MX", "Deployed notif: " + htmlContent + " @ " + faded.getX() + "," + faded.getY());
		}
	}

	public synchronized void deploy_notif(String htmlContent, Color bg)
	{
		deploy_notif(htmlContent, bg, 0.05F, 2000L, 50L, -1, false);
	}

	@Override public void run()
	{
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		log("GUI_MX", "Rendered a new window to the screen [" + getClass().getCanonicalName() + "#" + hashCode() + "]");
	}
}