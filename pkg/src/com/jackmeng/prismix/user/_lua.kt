// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.user

import com.jackmeng.prismix.user._lua
import org.luaj.vm2.Globals
import org.luaj.vm2.lib.jse.JsePlatform
import org.luaj.vm2.LuaValue
import com.jackmeng.prismix.jm_Prismix
import com.jackmeng.ansicolors.jm_Ansi

/**
 * Objects for handling scripting with LUA including interop for calling Java
 * functions.
 *
 * @author Jack Meng
 */
object _lua
{
	var instance:_lua?=null
	fun instance():_lua? // lazy loaded singleton
	{
		if (instance==null) instance=_lua
		return instance
	}
	
	fun G():Globals
	{
		return JsePlatform.standardGlobals()
	}
	
	fun d_G():Globals
	{
		return JsePlatform.debugGlobals()
	}
	
	fun exec(luacode:String?):String
	{
		return d_G().load(luacode).call().toString()
	}
	
	fun exec(luacode:String? , e:LuaValue?):String
	{
		return d_G().load(luacode).call(e).toString()
	}
	
	fun exec(luacode:String? , e:LuaValue? , e1:LuaValue?):String
	{
		return d_G().load(luacode).call(e , e1).toString()
	}
	
	fun exec(luacode:String? , e:LuaValue? , e1:LuaValue? , e2:LuaValue?):String
	{
		return d_G().load(luacode).call(e , e1 , e2).toString()
	}
	
	fun simple_load(locale:String)
	{
		if (locale.endsWith(".lua"))
		{
			jm_Prismix.log("LUA" , "Loading Lua script: $locale")
			val chunk=G().loadfile(locale)
			chunk.call()
		}
		else
		{
			jm_Prismix.log(
				"LUA" ,
				jm_Ansi.make("Failed to load: $locale").red_bg().white_fg()
					.toString()+" because the expected file extension \".lua\" was not found!"
			)
		}
	}
}