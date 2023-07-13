// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.user;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import com.jackmeng.ansicolors.jm_Ansi;

import static com.jackmeng.prismix.jm_Prismix.*;

/**
 * Objects for handling scripting with LUA including interop for calling Java
 * functions.
 *
 * @author Jack Meng
 */
public final class _lua
{
  public static _lua instance;

  private _lua()
  {

  }

  public static _lua instance() // lazy loaded singleton
  {
    if (instance == null)
      instance = new _lua();
    return instance;
  }

  public static Globals G()
  {
    return JsePlatform.standardGlobals();
  }

  public static Globals d_G()
  {
    return JsePlatform.debugGlobals();
  }

  public static String exec(String luacode)
  {
    return d_G().load(luacode).call().toString();
  }

  public static String exec(String luacode, LuaValue e)
  {
    return d_G().load(luacode).call(e).toString();
  }

  public static String exec(String luacode, LuaValue e, LuaValue e1)
  {
    return d_G().load(luacode).call(e, e1).toString();
  }

  public static String exec(String luacode, LuaValue e, LuaValue e1, LuaValue e2)
  {
    return d_G().load(luacode).call(e, e1, e2).toString();
  }

  public static void simple_load(String locale)
  {
    if (locale.endsWith((".lua")))
    {
      log("LUA", "Loading Lua script: " + locale);
      LuaValue chunk = G().loadfile(locale);
      chunk.call();
    }
    else
    {
      log("LUA", jm_Ansi.make("Failed to load: " + locale).red_bg().white_fg().toString()
          + " because the expected file extension \".lua\" was not found!");
    }
  }

}