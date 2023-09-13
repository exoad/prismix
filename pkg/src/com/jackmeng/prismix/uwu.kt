// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix

import com.jackmeng.prismix.user.use_LSys
import java.text.MessageFormat

object uwu
{
	@JvmStatic
	fun fowmat(name:String? , pattern:String?):String
	{
		return MessageFormat.format(use_LSys.read_all(_1const.fetcher.file(name)) , pattern)
	}
	
	@JvmStatic
	fun wemove_ansi(content:String):String
	{
		return content.replace("\\e\\[[\\d;]*[^\\d;]".toRegex() , "").replace("\u001B\\[[\\d;]*[^\\d;]".toRegex() , "")
			.replace("(?:\\x1B[@-Z\\\\-_]|[\\x80-\\x9A\\x9C-\\x9F]|(?:\\x1B\\[|\\x9B)[0-?]*[ -/]*[@-~])".toRegex() , "")
	}
}