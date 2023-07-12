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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.jackmeng.prismix._1const;
import com.jackmeng.stl.stl_Colors;
import static com.jackmeng.prismix.jm_Prismix.*;

public final class ui_XInf
		extends
		JFrame
		implements
		Runnable
{

	public static void invoke(String content, String title)
	{
		log("INFW", "Invoking a new window: " + title);
		SwingUtilities.invokeLater(new ui_XInf(content, title));
	}

	Color r = stl_Colors.hexToRGB(ux_Theme.get().get("blue"));

	private ui_XInf(String content, String title)
	{
		super(title);
		setIconImage(_1const.fetcher.image("assets/_icon.png"));
		setPreferredSize(new Dimension(710, 420)); // 420 funny numero
		setAlwaysOnTop(true);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createEmptyBorder());
		pane.setPreferredSize(new Dimension(450, 350));
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

		JButton closeState_btn = new JButton("OK");
		closeState_btn.setBackground(stl_Colors.hexToRGB(ux_Theme.get().get("cyan")));
		closeState_btn.setForeground(Color.black);
		closeState_btn.setRolloverEnabled(false);
		closeState_btn.setFocusPainted(false);
		closeState_btn.setBorderPainted(false);
		closeState_btn.addActionListener(ev -> dispose());

		JPanel wrap = new JPanel();
		wrap.setLayout(new GridLayout(1, 1));
		wrap.setOpaque(false);
		wrap.add(closeState_btn);

		getContentPane().setBackground(Color.black);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(pane);
		getContentPane().add(wrap);
	}

	@Override public void run()
	{
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}