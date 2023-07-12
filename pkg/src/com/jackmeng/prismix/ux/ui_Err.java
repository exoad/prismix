// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.net.URI;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.jackmeng.ansicolors.jm_Ansi;
import com.jackmeng.prismix._1const;
import com.jackmeng.stl.stl_Colors;

import static com.jackmeng.prismix.jm_Prismix.*;

public final class ui_Err
		extends
		JFrame
		implements
		Runnable
{

	public static void invoke(String content, String title, String url, Err_CloseState closeState)
	{
		log("ERRW", "Invoking a new window: " + title);
		SwingUtilities.invokeLater(new ui_Err(content, title, url, closeState));
	}

	public enum Err_CloseState {
		EXIT, OK;
	}

	Color r = stl_Colors.hexToRGB(ux_Theme._theme.get("rose"));

	private ui_Err(String content, String title, String url, Err_CloseState closeState)
	{
		super(title);
		setIconImage(_1const.fetcher.image("assets/_icon.png"));
		setPreferredSize(new Dimension(450, 370));
		setAlwaysOnTop(true);
		setDefaultCloseOperation(closeState == Err_CloseState.EXIT ? WindowConstants.EXIT_ON_CLOSE : DISPOSE_ON_CLOSE);
		JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createEmptyBorder());
		pane.setPreferredSize(new Dimension(450, 300));
		pane.setLayout(new BorderLayout());

		JEditorPane jep = new JEditorPane();
		jep.setContentType("text/html");
		jep.setText(content);
		jep.setEditable(false);
		jep.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		jep.setBackground(Color.black);
		jep.setForeground(Color.white);
		JScrollPane jsp = new JScrollPane();
		jsp.setViewportView(ui_LazyViewport.make(jep));
		jsp.setBorder(BorderFactory.createEmptyBorder());
		pane.add(jsp, BorderLayout.CENTER);

		JButton closeState_btn = new JButton(closeState.name());
		closeState_btn.setBackground(stl_Colors.hexToRGB(ux_Theme._theme.get("mint")));
		closeState_btn.setForeground(Color.black);
		closeState_btn.setRolloverEnabled(false);
		closeState_btn.setFocusPainted(false);
		closeState_btn.setBorderPainted(false);
		closeState_btn.addActionListener(closeState == Err_CloseState.EXIT ? ev -> System.exit(1) : ev -> {
		});

		JButton url_btn = new JButton("Help");
		url_btn.setBackground(stl_Colors.hexToRGB(ux_Theme._theme.get("orange")));
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

		JPanel wrap = new JPanel();
		wrap.setLayout(new GridLayout(1, 2));
		wrap.setOpaque(false);
		wrap.add(url_btn);
		wrap.add(closeState_btn);

		getContentPane().setBackground(Color.black);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(pane, BorderLayout.NORTH);
		getContentPane().add(wrap, BorderLayout.SOUTH);

		_1const.worker2.scheduleAtFixedRate(new TimerTask() {

			boolean alternate = true;

			@Override public void run()
			{
				if (isActive() || isVisible())
				{
					jep.setBackground(alternate ? r : Color.black);
					jep.setForeground(alternate ? Color.black : Color.white);
					alternate = !alternate;
				}
			}
		}, 0L, 750L);
	}

	@Override public void run()
	{
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args)
	{
		invoke("hello", "lol", "https://open.spotify.com/track/1srR6pbN1YCtIulufVNeMZ?si=b8ff5776036b487c",
				Err_CloseState.EXIT);
	}

}