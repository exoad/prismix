// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.test;

import javax.swing.*;
import java.awt.*;

public class test_WarnStripes extends JPanel
{

	double stripeWidth = 30;

	@Override protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.YELLOW);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		
	}

	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Diagonal Striped Box");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		test_WarnStripes box = new test_WarnStripes();
		box.setPreferredSize(new Dimension(400, 300));

		frame.getContentPane().add(box);
		frame.pack();
		frame.setVisible(true);
	}
}
