package com.jackmeng.prismix.ux;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import com.jackmeng.prismix._1const;

public class ui_Cntnr_TopPane
		extends JPanel
{
	public JPanel[] exports()
	{
		return new JPanel[] {

		};
	}

	// section where it cannot be
	// exported and thus let ux
	// change its visibility at any
	// time!
	JTabbedPane colorChooser; // this is the left side of the panel where the color gradient is
	/*----------------------------- /
	/ final JPanel attributes_List; /
	/------------------------------*/

	public ui_Cntnr_TopPane()
	{
		this.setPreferredSize(new Dimension(_2const.WIDTH, _2const.HEIGHT / 2));
		this.setMinimumSize(this.getPreferredSize());
		/*---------------------------------------------------------------- /
		/ this.setDividerLocation(_2const.WIDTH / 2 + _2const.WIDTH / 13); /
		/ this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);                /
		/-----------------------------------------------------------------*/
		this.setBorder(BorderFactory.createEmptyBorder());

		this.colorChooser = new JTabbedPane();
		/*------------------------------------------------------------------------------------------------------------- /
		/ this.colorChooser.setPreferredSize(new Dimension(this.getDividerLocation(), this.getPreferredSize().height)); /
		/--------------------------------------------------------------------------------------------------------------*/

		final ui_CPick_SugList colorChooser_Shades = new ui_CPick_SugList();
		_1const.COLOR_ENQ.add(colorChooser_Shades);
		this.colorChooser.addTab("Generator", colorChooser_Shades);
		final ui_CPick_GradRect colorChooser_gradientRect = new ui_CPick_GradRect(); // RGBA
																																									// Gradient
																																									// Rectangle
		_1const.COLOR_ENQ.add(colorChooser_gradientRect);
		this.colorChooser.addTab("GradRect Pick", colorChooser_gradientRect);

		if ("true".equalsIgnoreCase(_1const.val.get_value("soft_debug")))
		{
			final ui_CPick_GenericDisp colorChooser_Debug = new ui_CPick_GenericDisp();
			colorChooser_Debug.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 1));
			_1const.COLOR_ENQ.add(colorChooser_Debug);
			this.colorChooser.addTab("!Debug", colorChooser_Debug);
		}

		setLayout(new BorderLayout());

		add(this.colorChooser, BorderLayout.CENTER);

	}

	public synchronized void redo()
	{
		this.setMinimumSize(new Dimension(this.getPreferredSize().width, this.getPreferredSize().height + 65));
		this.doLayout();
		this.revalidate();
		this.repaint(100L);
	}

}