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

	public static String wemove_ansi(String content)
	{
		return content.replaceAll("\\e\\[[\\d;]*[^\\d;]", "").replaceAll("\u001B\\[[\\d;]*[^\\d;]", "").replaceAll("(?:\\x1B[@-Z\\\\-_]|[\\x80-\\x9A\\x9C-\\x9F]|(?:\\x1B\\[|\\x9B)[0-?]*[ -/]*[@-~])", "");
	}

}