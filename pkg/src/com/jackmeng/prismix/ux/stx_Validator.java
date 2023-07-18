// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.util.regex.Pattern;

public final class stx_Validator
{
	private stx_Validator()
	{
	}

	private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");

	public static boolean isValidHexColorCode(String input)
	{
		return HEX_COLOR_PATTERN.matcher(input).matches();
	}

}