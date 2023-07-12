// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix;

import java.text.MessageFormat;

import com.jackmeng.prismix.user.use_LSys;

public final class uwu
{
	public static String fowmat(String name, String pattern)
	{
		return MessageFormat.format(use_LSys.read_all(_1const.fetcher.file(name)), pattern);
	}


}