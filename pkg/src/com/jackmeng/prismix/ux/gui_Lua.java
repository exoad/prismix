// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;

import com.jackmeng.stl.stl_Colors;

public final class gui_Lua
		extends
		gui
{
	private static gui_Lua __instance = null;

	public static gui_Lua instance()
	{
		if (__instance == null)
			__instance = new gui_Lua();
		return __instance;
	}

	private final JEditorPane output;

	public gui_Lua()
	{
		super("Prismix ~ Lua");
		setName("LUAOP_WINDOW");
		setPreferredSize(new Dimension(740, 560));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		output = new JEditorPane();
		output.setContentType("text/html");

		output.setText("<html><body>");
		output.setEditable(false);
		output.setEditorKit(new ux_HTMLPRE());
		output.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		output.setPreferredSize(new Dimension(740, 505));

		JScrollPane jsp_output = new JScrollPane(output);
		jsp_output.setBorder(BorderFactory.createEmptyBorder());
		jsp_output.getVerticalScrollBar().setBackground(Color.black);
		jsp_output.getHorizontalScrollBar().setBackground(Color.black);

		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		bottom.setPreferredSize(new Dimension(740, 555));

		JEditorPane enterField = new JEditorPane();
		enterField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(ux_Theme._theme.fg_awt()),
				BorderFactory.createEmptyBorder(7, 7, 7, 7)));
		enterField.setContentType("text");

		JButton execEnter = new JButton(">");
		execEnter.addActionListener(ev -> {
			if (!enterField.getText().isBlank())
			{
				out(enterField.getText());
				enterField.setText("");
			}
		});
		ux_Theme.based_set_rrect(execEnter);
		execEnter.setBackground(stl_Colors.hexToRGB(ux_Theme._theme.get("rose")));
		execEnter.setForeground(ux_Theme._theme.fg_awt());
		execEnter.setPreferredSize(new Dimension(50, 50));

		bottom.add(enterField, BorderLayout.CENTER);
		bottom.add(execEnter, BorderLayout.EAST);

		bottom.addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override public void componentResized(java.awt.event.ComponentEvent evt)
			{
				Dimension size = bottom.getSize();
				int buttonWidth = (int) (size.width * 0.1);
				execEnter.setSize(new Dimension(buttonWidth, buttonWidth));
				enterField.setPreferredSize(new Dimension(size.width - buttonWidth, size.height));
				bottom.revalidate();
			}
		});

		JSplitPane master_jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		master_jsp.setBorder(BorderFactory.createEmptyBorder());
		master_jsp.setDividerLocation(470);

		master_jsp.setTopComponent(output);
		master_jsp.setBottomComponent(bottom);

		getContentPane().add(master_jsp);
	}

	void out(String content) // content preferably should be formatted with html for best viewing
	{
		try
		{
			output.getDocument().insertString(output.getDocument().getLength(), content + "<br>", null);
		} catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	public String final_history() // should be used for like logging purposes especially logging to a file for
																// future reference
	{
		return output.getText();
	}
}