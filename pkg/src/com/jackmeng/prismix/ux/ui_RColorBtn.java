// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.jackmeng.ansicolors.jm_Ansi;
import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.prismix.ux.stx_FixedPopper.Popper_Priority;
import com.jackmeng.stl.stl_Struct;

import java.awt.Color;
import java.awt.GridLayout;

import static com.jackmeng.prismix.jm_Prismix.*;

public class ui_RColorBtn
		extends JPanel
{
	private transient stx_FixedPopper< Color > popper;
	private JButton _nextColor, _select, _prevColor, _generate;

	public ui_RColorBtn()
	{
		popper = new stx_FixedPopper<>(128, Popper_Priority.TAIL, true, s -> {
			if (s != null)
			{
				log("CLRBTN", "Removed" + jm_Ansi.make().blue().toString(s.getRed() + "," + s.getGreen() + "," + s.getBlue()));
			}
		});

		setLayout(new GridLayout(1, 4));
		setBorder(BorderFactory.createEmptyBorder());

		_select = new JButton(Boolean.TRUE.equals(_1const.val.parse("descriptive_labels").get()) ? "Select" : "o");
		ux_Theme.based_set_rrect(_select);
		_select.setToolTipText("Select this current RANDOM color");
		_select
				.addActionListener(ev -> {
					if (popper.at() != null)
					{
						_1const.COLOR_ENQ.dispatch(stl_Struct.make_pair(popper.at(), false));
					}
				});

		_nextColor = new JButton(Boolean.TRUE.equals(_1const.val.parse("descriptive_labels").get()) ? "Next" : ">");
		_nextColor.setToolTipText("Next random color.");
		ux_Theme.based_set_rrect(_nextColor);
		_nextColor.addActionListener(ev -> {
			popper.next();
			update();
		});

		_prevColor = new JButton(Boolean.TRUE.equals(_1const.val.parse("descriptive_labels").get()) ? "Prev." : "<");
		_prevColor.setToolTipText("Previous random color.");
		ux_Theme.based_set_rrect(_prevColor);
		_prevColor.addActionListener(ev -> {
			popper.previous();
			update();
		});

		_generate = new JButton(Boolean.TRUE.equals(_1const.val.parse("descriptive_labels").get()) ? "Generate" : "+");
		ux_Theme.based_set_rrect(_generate);
		_generate.setToolTipText("Generate a new color");
		_generate
				.addActionListener(ev -> {
					popper.force_push(extend_stl_Colors.awt_random_Color());
					popper.toTail();
					update();
				});

		add(_prevColor);
		add(_select);
		add(_generate);
		add(_nextColor);

		popper.toTail();
		update();
	}

	private synchronized void update()
	{
		if (popper.at() != null)
		{
      /*------------------------------------------------------------------------------------------------------------ /
      / log("RCOLOR", "[ " + popper.previous_where() + " | " + popper.where() + " | " + popper.next_where() + " ]"); /
      /-------------------------------------------------------------------------------------------------------------*/
			_select.setBackground(popper.at());
			_select.setForeground(extend_stl_Colors
					.awt_remake(extend_stl_Colors.binary_fg_decider(extend_stl_Colors.awt_strip_rgba(_select.getBackground()))));
			_select.setToolTipText(
					extend_stl_Colors.RGBToHex(popper.at().getRed(), popper.at().getGreen(), popper.at().getBlue()));

			_nextColor.setBackground(popper.at(popper.next_where()) == null ? Color.black : popper.at(popper.next_where()));
			_nextColor.setForeground(extend_stl_Colors
					.awt_remake(
							extend_stl_Colors.binary_fg_decider(extend_stl_Colors.awt_strip_rgba(_nextColor.getBackground()))));

			_prevColor
					.setBackground(popper.at(popper.previous_where()) == null ? Color.black : popper.at(popper.previous_where()));
			_prevColor.setForeground(extend_stl_Colors
					.awt_remake(
							extend_stl_Colors.binary_fg_decider(extend_stl_Colors.awt_strip_rgba(_prevColor.getBackground()))));
		}
	}

}