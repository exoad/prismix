package com.jackmeng.prismix.test;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Window;

import java.awt.event.MouseEvent;
import java.awt.event.AWTEventListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class test_GlobalListener
{
	private JWindow tip;

	private final AWTEventListener mouseHandler = e -> {
		Window window = tip.getOwner();
		MouseEvent event = null;

		switch (e.getID()) {
			case MouseEvent.MOUSE_ENTERED:
			case MouseEvent.MOUSE_MOVED:
			case MouseEvent.MOUSE_DRAGGED:
				event = (MouseEvent) e;
				if (window.isAncestorOf(event.getComponent()))
				{
					Point loc = event.getLocationOnScreen();
					tip.setLocation(loc.x + 10, loc.y + 10);
					tip.setVisible(true);
				}
				break;
			case MouseEvent.MOUSE_EXITED:
				event = (MouseEvent) e;
				Point p = SwingUtilities.convertPoint(
						event.getComponent(), event.getPoint(), window);
				if (!window.contains(p))
				{
					tip.setVisible(false);
				}
				break;
			default:
				break;
		}
	};

	public test_GlobalListener(String text,
			Window window)
	{

		JLabel tipLabel = new JLabel(text);

		tipLabel.setForeground(UIManager.getColor("ToolTip.foreground"));
		tipLabel.setBackground(UIManager.getColor("ToolTip.background"));
		tipLabel.setFont(UIManager.getFont("ToolTip.font"));
		tipLabel.setBorder(UIManager.getBorder("ToolTip.border"));

		tip = new JWindow(window);
		tip.setType(Window.Type.POPUP);
		tip.setFocusableWindowState(false);
		tip.getContentPane().add(tipLabel);
		tip.pack();
	}

	public void activate()
	{
		Window window = tip.getOwner();
		window.getToolkit().addAWTEventListener(mouseHandler,
				AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);

		Point p = window.getMousePosition();
		if (p != null)
		{
			SwingUtilities.convertPointToScreen(p, window);
			tip.setLocation(p.x + 10, p.y + 10);
			tip.setVisible(true);
		}
	}

	public void deactivate()
	{
		Window window = tip.getOwner();
		window.getToolkit().removeAWTEventListener(mouseHandler);

		tip.setVisible(false);
	}

	static void showWindow()
	{
		Object[][] data = new Object[12][];
		Object[] headings = new Object[data.length];
		for (int i = 0; i < data.length; i++)
		{
			data[i] = new Object[data.length];
			for (int j = 0; j < data[i].length; j++)
			{
				data[i][j] = (i + 1) * (j + 1);
			}
			headings[i] = i;
		}

		JTable table = new JTable(data, headings);

		JToggleButton button = new JToggleButton("Active");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(button);

		JFrame frame = new JFrame("test_GlobalListener");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().add(new JScrollPane(table));
		frame.getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);

		test_GlobalListener tip = new test_GlobalListener("This is a tip", frame);

		button.addActionListener(e -> {
			if (button.isSelected())
			{
				tip.activate();
			}
			else
			{
				tip.deactivate();
			}
		});
	}

	public static void main(String[] args)
	{
		EventQueue.invokeLater(test_GlobalListener::showWindow);
	}
}
