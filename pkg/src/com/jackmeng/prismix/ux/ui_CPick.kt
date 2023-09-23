// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import com.jackmeng.stl.stl_Listener
import com.jackmeng.stl.stl_Struct.struct_Pair
import com.jackmeng.stl.stl_Callback
import com.jackmeng.prismix._1const
import javax.swing.JComponent
import javax.swing.JPanel
import java.awt.Color

// Implementations of color pickers visually
// All of these should be able to dispatch events to the color queue found in _1const
abstract class ui_CPick:JPanel() , stl_Listener<struct_Pair<Color? , Boolean?>?>
{
	companion object
	{
		fun attach_asis(e:JComponent):JComponent
		{
			if (e is stl_Callback<* , *>) // finalized types at compile time are so fucking stupid
				_1const.COLOR_ENQ.add(e as stl_Listener<struct_Pair<Color? , Boolean?>?>)
			return e
		}
	}
}