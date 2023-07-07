// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.test;

import javax.swing.*;

import com.jackmeng.prismix.ux.stx_FixedPopper;
import com.jackmeng.prismix.ux.ux_Helper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class test_FixedPopper extends JFrame
{
	private transient stx_FixedPopper< Integer > popper;
	private JLabel currentLabel;
	private JButton previousButton;
	private JButton nextButton;
	private JPanel entire;
	static int SIZE = 25;
	static Random rng = new Random();

	public test_FixedPopper()
	{
		super("Popper Demo");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(1000, 1000));

		// Create a popper instance
		popper = new stx_FixedPopper<>(20);
		for (int i = 0; i < 20; i++)
			popper.force_push(i);

		// Create UI components
		currentLabel = new JLabel();
		previousButton = new JButton("<");
		nextButton = new JButton(">");

		entire = new JPanel() {
			@Override public void paintComponent(Graphics g2)
			{
				super.paintComponent(g2);
				Graphics2D g = (Graphics2D) g2;
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, getWidth(), getHeight());
				for (int i = 0, x = 10, y = 20; i < popper.size(); i++, x += SIZE + 6)
					if (popper.at(i) != null)
					{
						if (i == popper.where() || i == popper.next_where() || i == popper.previous_where())
						{
							g.setColor(i == popper.where() ? Color.green
									: i == popper.next_where() ? Color.blue : Color.magenta);
							g.setStroke(new BasicStroke(2));
							g.drawLine(x + (SIZE / 2), y + SIZE + 10, (x + (SIZE / 2)) - 4, y + SIZE + 14);
							g.drawLine((x + (SIZE / 2)), y + SIZE + 10, (x + (SIZE / 2)) + 4, y + SIZE + 14);
						}
						g.setStroke(new BasicStroke(1));
						g.setColor(Color.white);
						g.fillRect(x, y, SIZE, SIZE);
						g.drawString(i + "", x, y + SIZE * SIZE + 4);
						g.setColor(Color.black);
						g.drawString(popper.at(i) + "", x, y + (SIZE / 2) + 2);
					}
				g.dispose();
			}
		};
		entire.setPreferredSize(new Dimension(300, 300));

		JPanel wrap = new JPanel();
		wrap.setLayout(new BoxLayout(wrap, BoxLayout.X_AXIS));
		wrap.setPreferredSize(new Dimension(200, 55));
		wrap.add(currentLabel);
		wrap.add(ux_Helper.quick_btn("Insert RNG", () -> {
			int i = rng.nextInt(0, 99);
			System.out.println("Inserts: " + i);
			popper.push_at(popper.where(), i);
			entire.repaint(70L);
		}));
		wrap.add(previousButton);
		wrap.add(nextButton);

		add(wrap, BorderLayout.NORTH);
		add(entire, BorderLayout.CENTER);

		updateCurrentLabel();
		entire.repaint(70L);

		previousButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e)
			{
				Integer previousItem = popper.previous();
				updateCurrentLabel();
				System.out.println("Previous item: " + previousItem);
			}
		});

		nextButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e)
			{
				Integer nextItem = popper.next();
				updateCurrentLabel();
				System.out.println("Next item: " + nextItem);
			}
		});

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void updateCurrentLabel()
	{
		Integer currentItem = popper.at();
		entire.repaint(70L);
		currentLabel.setText("Priority: " + popper.priority().name() + " Current: " + currentItem);
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run()
			{
				new test_FixedPopper();
			}
		});
	}
}
