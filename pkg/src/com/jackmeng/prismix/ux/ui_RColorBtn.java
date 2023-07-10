// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.stl.stl_Struct;

import java.awt.Color;
import java.awt.GridLayout;

public class ui_RColorBtn
		extends JPanel
{
	private transient stx_FixedPopper< Color > popper;
	private JButton _nextColor, _select, _prevColor;

	public ui_RColorBtn()
	{
		popper = new stx_FixedPopper<>(128);

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
		_nextColor.addActionListener(ev -> {
			if (popper.where() == popper.size() - 1 || popper.at() == null)
				popper.force_push(extend_stl_Colors.awt_random_Color());
			else
				popper.itr_to(popper.next_where());
			update();
		});

		_prevColor = new JButton(Boolean.TRUE.equals(_1const.val.parse("descriptive_labels").get()) ? "Prev." : "<");
		_prevColor.setToolTipText("Previous random color.");
		ux_Theme.based_set_rrect(_prevColor);
		_prevColor.addActionListener(ev -> {
			popper.itr_to(popper.previous_where());
			update();
		});

		add(_prevColor);
		add(_select);
		add(_nextColor);
	}

	private synchronized void update()
	{
		if (popper.at() != null)
		{
			_select.setBackground(popper.at());
			_select.setForeground(extend_stl_Colors
					.awt_remake(extend_stl_Colors.binary_fg_decider(extend_stl_Colors.awt_strip_rgba(_select.getBackground()))));
			_select.setToolTipText(
					extend_stl_Colors.RGBToHex(popper.at().getRed(), popper.at().getGreen(), popper.at().getBlue()));

			_nextColor.setBackground(popper.next() == null ? Color.black : popper.next());
			_nextColor.setForeground(extend_stl_Colors
					.awt_remake(
							extend_stl_Colors.binary_fg_decider(extend_stl_Colors.awt_strip_rgba(_nextColor.getBackground()))));

			_prevColor.setBackground(popper.previous() == null ? Color.black : popper.previous());
			_prevColor.setForeground(extend_stl_Colors
					.awt_remake(
							extend_stl_Colors.binary_fg_decider(extend_stl_Colors.awt_strip_rgba(_prevColor.getBackground()))));
		}
	}

}