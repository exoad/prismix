// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JSplitPane;

/**
 * Represents the inner shell of the content of the GUI. The parent is the ux
 * handler.
 *
 * Most code for the GUI goes here.
 *
 * @author Jack Meng
 */
public class ui_Cntnr
		extends JSplitPane
{
	public ui_Cntnr_TopPane top;
	public ui_Cntnr_BottomPane bottom;

	public ui_Cntnr()
	{
		this.top = new ui_Cntnr_TopPane();
		this.bottom = new ui_Cntnr_BottomPane();

		this.setPreferredSize(new Dimension(_2const.WIDTH, _2const.HEIGHT));
		this.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.setDividerLocation(_2const.HEIGHT / 2 + _2const.HEIGHT / 8);
		this.setTopComponent(this.top);
		this.setBottomComponent(this.bottom);
		this.setBorder(BorderFactory.createEmptyBorder());
	}

	public synchronized void append_attribute(final JComponent e)
	{
		e.setAlignmentX(Component.CENTER_ALIGNMENT);
		e.setAlignmentY(Component.LEFT_ALIGNMENT);
		this.top.attributes_List.add(e);
		this.top.attributes_List.validate();
	}

	public void validate_size()
	{
		this.top.colorAttributes.setMinimumSize(this.top.colorAttributes.getPreferredSize());
		this.top.setDividerLocation(this.top.colorChooser.getPreferredSize().width + 50);
	}
}