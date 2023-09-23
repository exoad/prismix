// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import com.jackmeng.prismix.ux.stx_FixedPopper.Popper_Priority
import com.jackmeng.prismix.jm_Prismix
import com.jackmeng.ansicolors.jm_Ansi
import com.jackmeng.prismix._1const
import com.jackmeng.stl.stl_Struct
import com.jackmeng.prismix.stl.extend_stl_Colors
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JPanel
import java.awt.Color
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.lang.Boolean

class ui_R_ColorBtn:JPanel()
{
	private val popper:stx_FixedPopper<Color?>=stx_FixedPopper(128 , Popper_Priority.TAIL , true) { s:Color?->
		if (s!=null)
		{
			jm_Prismix.log(
				"CLRBTN" ,
				"Removed"+jm_Ansi.make().blue().toString(s.red.toString()+","+s.green+","+s.blue)
			)
		}
	}
	private val _nextColor:JButton
	private val _select:JButton
	private val _prevColor:JButton
	
	init
	{
		layout=GridLayout(1 , 4)
		border=BorderFactory.createEmptyBorder()
		_select=JButton(if (Boolean.TRUE==_1const.`val`.parse("descriptive_labels").get()) "Select" else "o")
		ux_Theme.based_set_rrect(_select)
		_select.background=ux_Theme._theme.dominant_awt()
		_select.toolTipText="Select this current RANDOM color"
		_select.addActionListener { ev:ActionEvent?->
				if (popper.at()!=null)
				{
					_1const.COLOR_ENQ.dispatch(stl_Struct.make_pair(popper.at() , false))
				}
			}
		_nextColor=JButton(if (Boolean.TRUE==_1const.`val`.parse("descriptive_labels").get()) "Next" else ">")
		_nextColor.toolTipText="Next random color."
		ux_Theme.based_set_rrect(_nextColor)
		_nextColor.background=ux_Theme._theme.dominant_awt()
		_nextColor.addActionListener { ev:ActionEvent?->
			popper.next()
			update()
		}
		_prevColor=JButton(if (Boolean.TRUE==_1const.`val`.parse("descriptive_labels").get()) "Prev." else "<")
		_prevColor.toolTipText="Previous random color."
		ux_Theme.based_set_rrect(_prevColor)
		_prevColor.background=ux_Theme._theme.dominant_awt()
		_prevColor.addActionListener { ev:ActionEvent?->
			popper.previous()
			update()
		}
		val _generate=JButton(
			if (Boolean.TRUE==_1const.`val`.parse("descriptive_labels").get()) "Generate" else "+"
		)
		ux_Theme.based_set_rrect(_generate)
		_generate.toolTipText="Generate a new color"
		_generate.background=ux_Theme._theme.dominant_awt()
		_generate.addActionListener { ev:ActionEvent?->
				popper.force_push(extend_stl_Colors.awt_random_Color())
				popper.toTail()
				update()
			}
		add(_prevColor)
		add(_select)
		add(_nextColor)
		add(_generate)
		popper.toTail()
		update()
	}
	
	@Synchronized
	private fun update()
	{
		if (popper.at()!=null)
		{			/*------------------------------------------------------------------------------------------------------------ /
			/ log("RCOLOR", "[ " + popper.previous_where() + " | " + popper.where() + " | " + popper.next_where() + " ]"); /
			/-------------------------------------------------------------------------------------------------------------*/
			_select.background=popper.at()
			_select.foreground=extend_stl_Colors.awt_remake(
				extend_stl_Colors.binary_fg_decider(
					extend_stl_Colors.awt_strip_rgba(_select.background)
				)
			)
			_select.toolTipText=extend_stl_Colors.RGBToHex(popper.at()!!.red , popper.at()!!.green , popper.at()!!.blue)
			_nextColor.background=
				if (popper.at(popper.next_where())==null) Color.black else popper.at(popper.next_where())
			_nextColor.foreground=extend_stl_Colors.awt_remake(
				extend_stl_Colors.binary_fg_decider(extend_stl_Colors.awt_strip_rgba(_nextColor.background))
			)
			_prevColor.background=
				if (popper.at(popper.previous_where())==null) Color.black else popper.at(popper.previous_where())
			_prevColor.foreground=extend_stl_Colors.awt_remake(
				extend_stl_Colors.binary_fg_decider(extend_stl_Colors.awt_strip_rgba(_prevColor.background))
			)
		}
	}
}