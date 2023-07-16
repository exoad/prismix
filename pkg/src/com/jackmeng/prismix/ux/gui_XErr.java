// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.jackmeng.ansicolors.jm_Ansi;
import com.jackmeng.prismix._1const;
import com.jackmeng.stl.stl_Colors;
import com.jackmeng.stl.stl_Wrap;

import static com.jackmeng.prismix.jm_Prismix.*;

public final class gui_XErr
		extends
		gui
{

	public static void invoke(String content, String title, String url, Err_CloseState closeState)
	{
		log("ERRW", "Invoking a new window: " + title);
		SwingUtilities.invokeLater(new gui_XErr(content, title, url, closeState));
	}

	public enum Err_CloseState {
		EXIT, OK;
	}

	Color r = stl_Colors.hexToRGB(ux_Theme.get().get("rose"));
	transient stl_Wrap< Boolean > touched = new stl_Wrap<>(false);

	private gui_XErr(String content, String title, String url, Err_CloseState closeState)
	{
		super(title);
		setPreferredSize(new Dimension(710, 420));
		setAlwaysOnTop(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setDefaultCloseOperation(closeState == Err_CloseState.EXIT ? WindowConstants.EXIT_ON_CLOSE : DISPOSE_ON_CLOSE);
		JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createEmptyBorder());
		pane.setPreferredSize(new Dimension(0, 350));
		pane.setLayout(new BorderLayout());

		JEditorPane jep = new JEditorPane();
		jep.setContentType("text/html");
		jep.setEditorKit(new ux_HTMLPRE());

		jep.setText(content);
		jep.setOpaque(true);
		jep.setEditable(false);
		jep.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		jep.setBackground(Color.black);
		jep.setForeground(Color.white);

		JScrollPane jsp = new JScrollPane();
		jsp.getVerticalScrollBar().setBackground(Color.black);
		jsp.getHorizontalScrollBar().setBackground(Color.black);
		jsp.getVerticalScrollBar().setUnitIncrement(8);
		jsp.setOpaque(true);
		jsp.setViewportView(ui_LazyViewport.make(jep));
		jsp.setBorder(BorderFactory.createEmptyBorder());

		pane.add(jsp, BorderLayout.CENTER);

		JButton closeState_btn = new JButton(closeState.name());
		closeState_btn.setBackground(stl_Colors.hexToRGB(ux_Theme.get().get("mint")));
		closeState_btn.setForeground(Color.black);
		closeState_btn.setRolloverEnabled(false);
		closeState_btn.setFocusPainted(false);
		closeState_btn.setBorderPainted(false);
		closeState_btn.addActionListener(closeState == Err_CloseState.EXIT ? ev -> System.exit(1) : ev -> {
		});

		JButton url_btn = new JButton("Help");
		url_btn.setBackground(stl_Colors.hexToRGB(ux_Theme.get().get("orange")));
		url_btn.setForeground(Color.black);
		url_btn.setRolloverEnabled(false);
		url_btn.setFocusPainted(false);
		url_btn.setBorderPainted(false);
		url_btn.addActionListener(ev -> {
			try
			{
				Desktop.getDesktop().browse(new URI(url));
			} catch (Exception e)
			{
				if (e instanceof UnsupportedOperationException)
					log("PRISMIX", jm_Ansi.make().bold().underline().cyan_bg().black()
							.toString("PLEASE VISIT " + url + " FOR SUPPORT ON YOUR ISSUE!"));
				e.printStackTrace();
				if (e instanceof UnsupportedOperationException)
					for (int i = 0; i < 5; i++)
						log("PRISMIX", jm_Ansi.make().bold().underline().cyan_bg().black()
								.toString("PLEASE VISIT " + url + " FOR SUPPORT ON YOUR ISSUE!"));
			}
		});

		JButton copy_btn = new JButton("Copy Text");
		copy_btn.setBackground(ux_Theme.get().get_awt("cyan"));
		copy_btn.setForeground(Color.black);
		copy_btn.setRolloverEnabled(false);
		copy_btn.setFocusPainted(false);
		copy_btn.setBorderPainted(false);
		copy_btn.addActionListener(
				ev -> Toolkit.getDefaultToolkit().getSystemClipboard()
						.setContents(new StringSelection(stx_HTMLDebuff.parse(jep.getText())), null));

		JPanel wrap = new JPanel();
		wrap.setLayout(new GridLayout(1, 3));
		wrap.setOpaque(false);
		wrap.add(url_btn);
		wrap.add(copy_btn);
		wrap.add(closeState_btn);

		getContentPane().setBackground(Color.black);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(pane);
		getContentPane().add(wrap);

		if (Boolean.TRUE.equals((Boolean) _1const.val.parse("use_flashing_error_window").get()))
		{
			jsp.addMouseListener(new MouseAdapter() {
				@Override public void mouseEntered(MouseEvent e)
				{
					touched.obj(true);
				}

				@Override public void mouseExited(MouseEvent e)
				{
					touched.obj(false);
				}
			});
			_1const.worker2.scheduleAtFixedRate(new TimerTask() {

				boolean alternate = true;

				@Override public void run()
				{
					if (Boolean.FALSE.equals(touched.obj))
					{
						if (isActive() || isVisible())
						{
							jep.setBackground(alternate ? r : Color.black);
							jep.setForeground(alternate ? Color.black : Color.white);
							alternate = !alternate;
						}
					}
				}
			}, 0L, 750L);
		}
	}

}