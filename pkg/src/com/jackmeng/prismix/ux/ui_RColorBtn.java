// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.stl.stl_Commons;
import com.jackmeng.stl.stl_Struct;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

public class ui_RColorBtn
		extends JPanel
{
	private JButton _nextColor, _select, _prevColor;
	private LinkedList<Color> _generated;

	public ui_RColorBtn()
	{
		setLayout(new GridLayout(1, 3));
		setBorder(BorderFactory.createEmptyBorder());

		_select = new JButton(Boolean.TRUE.equals(_1const.val.parse("descriptive_labels").get()) ? "Select" : "+");
		ux_Theme.based_set_rrect(_select);
		_select.setToolTipText("Select this current RANDOM color");
		_select
				.addActionListener(ev -> _1const.COLOR_ENQ.dispatch(stl_Struct.make_pair(_select.getBackground(), false)));

		_nextColor = new JButton(Boolean.TRUE.equals(_1const.val.parse("descriptive_labels").get()) ? "Next" : ">");
		_nextColor.setToolTipText("Next random color.");
		ux_Theme.based_set_rrect(_nextColor);
		_nextColor.addActionListener(Boolean.TRUE.equals(_1const.val.parse("more_components_variations").get()) ? ev -> {
			Color nextRandomColor = extend_stl_Colors.awt_random_Color();
			_nextColor.setBackground(nextRandomColor);
			_nextColor.setForeground(extend_stl_Colors
					.awt_remake(extend_stl_Colors.binary_fg_decider(extend_stl_Colors.awt_strip_rgba(nextRandomColor))));
			_select.setBackground(_nextColor.getBackground());
			_select.setForeground(extend_stl_Colors
					.awt_remake(extend_stl_Colors.binary_fg_decider(extend_stl_Colors.awt_strip_rgba(nextRandomColor))));
		} : ev -> {
			// ignore
		});



		add(_select);
		add(_nextColor);
	}

}