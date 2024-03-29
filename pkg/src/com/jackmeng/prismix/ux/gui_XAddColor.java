// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public final class gui_XAddColor
		extends
		gui
{

	public static void request()
	{
		new gui_XAddColor().run();
	}

	private gui_XAddColor()
	{
		super("New Color");
		setPreferredSize(new Dimension(450, 530));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		JLabel title = new JLabel("<html><h1 style=\"font-size:23\"><strong>New color</strong></h1></html>");
		title.setHorizontalAlignment(SwingConstants.LEFT);

		JPanel jp = new JPanel();
		SpringLayout layout = new SpringLayout();
		jp.setLayout(layout);
		jp.setPreferredSize(getPreferredSize());
		jp.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		jp.add(title);
		layout.putConstraint(SpringLayout.NORTH, title, 0, SpringLayout.NORTH, jp);
		layout.putConstraint(SpringLayout.WEST, title, 0, SpringLayout.WEST, jp);

		JTextArea hex = new JTextArea(1, 6);
		hex.addKeyListener(new KeyAdapter() {
			@Override public void keyPressed(KeyEvent e)
			{
				hex.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1, true));
			}
		});
		hex.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1, true));

		JPanel hex_TextPanel = createLabelAndTextArea("HEX:  ", hex);
		jp.add(hex_TextPanel);
		layout.putConstraint(SpringLayout.NORTH, hex_TextPanel, 10, SpringLayout.SOUTH, title);
		layout.putConstraint(SpringLayout.WEST, hex_TextPanel, 0, SpringLayout.WEST, jp);
		layout.putConstraint(SpringLayout.EAST, hex_TextPanel, 0, SpringLayout.EAST, jp);

		JTextArea rawRGBA_textAreanew = new JTextArea(1, 10);
		rawRGBA_textAreanew.addKeyListener(new KeyAdapter() {
			@Override public void keyPressed(KeyEvent e)
			{
				rawRGBA_textAreanew.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1, true));
			}
		});
		JPanel rgbaRaw_TextPanel = createLabelAndTextArea("Raw RGBA:  ", rawRGBA_textAreanew);
		jp.add(rgbaRaw_TextPanel);
		layout.putConstraint(SpringLayout.NORTH, rgbaRaw_TextPanel, 10, SpringLayout.SOUTH, hex_TextPanel);
		layout.putConstraint(SpringLayout.WEST, rgbaRaw_TextPanel, 0, SpringLayout.WEST, jp);
		layout.putConstraint(SpringLayout.EAST, rgbaRaw_TextPanel, 0, SpringLayout.EAST, jp);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(jp, BorderLayout.CENTER);
	}

	private JPanel createLabelAndTextArea(String label, JTextArea area)
	{
		JPanel panel = new JPanel();
		SpringLayout layout = new SpringLayout();
		panel.setLayout(layout);
		JLabel titleLabel = new JLabel(label);
		panel.add(titleLabel);
		panel.add(area);

		layout.putConstraint(SpringLayout.NORTH, titleLabel, 5, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, titleLabel, 0, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, area, 5, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, area, 5, SpringLayout.EAST, titleLabel);
		layout.putConstraint(SpringLayout.EAST, panel, 5, SpringLayout.EAST, area);
		layout.putConstraint(SpringLayout.SOUTH, panel, 5, SpringLayout.SOUTH, area);
		return panel;
	}
}