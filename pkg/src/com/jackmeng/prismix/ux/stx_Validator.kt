// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import com.jackmeng.prismix.ux.stx_Validator
import java.util.regex.Pattern

object stx_Validator
{
	private val HEX_COLOR_PATTERN=Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")
	fun isValidHexColorCode(input:String?):Boolean
	{
		return HEX_COLOR_PATTERN.matcher(input).matches()
	}
}