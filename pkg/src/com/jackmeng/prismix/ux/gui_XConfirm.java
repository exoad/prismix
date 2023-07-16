// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import com.jackmeng.stl.stl_Colors;

public final class gui_XConfirm
		extends
		gui
{
	public gui_XConfirm(String content, String title, Runnable onNo, Runnable onYes, String yesString, String noString)
	{
		super(title);
		setPreferredSize(new Dimension(500, 250));
		setAlwaysOnTop(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
		jsp.getVerticalScrollBar().setUnitIncrement(6);
		jsp.getHorizontalScrollBar().setBackground(Color.black);
		jsp.getHorizontalScrollBar().setUnitIncrement(6);
		jsp.setOpaque(true);
		jsp.setBackground(Color.black);
		jsp.setViewportView(ui_LazyViewport.make(jep));
		jsp.setBorder(BorderFactory.createEmptyBorder());

		pane.add(jsp, BorderLayout.CENTER);

		JButton no_btn = new JButton(noString);
		no_btn.setBackground(stl_Colors.hexToRGB(ux_Theme.get().get("rose")));
		no_btn.setForeground(Color.black);
		no_btn.setRolloverEnabled(false);
		no_btn.setFocusPainted(false);
		no_btn.setBorderPainted(false);
		no_btn.addActionListener(ev -> {
			onNo.run();
			dispose();
		});

		JButton yes_btn = new JButton(yesString);
		yes_btn.setBackground(stl_Colors.hexToRGB(ux_Theme.get().get("rose")));
		yes_btn.setForeground(Color.black);
		yes_btn.setRolloverEnabled(false);
		yes_btn.setFocusPainted(false);
		yes_btn.setBorderPainted(false);
		yes_btn.addActionListener(ev -> {
			onYes.run();
			dispose();
		});

		JPanel wrap = new JPanel();
		wrap.setLayout(new GridLayout(1, 2));
		wrap.setOpaque(false);
		wrap.add(no_btn);
		wrap.add(yes_btn);

		getContentPane().setBackground(Color.black);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(pane);
		getContentPane().add(wrap);
	}
}