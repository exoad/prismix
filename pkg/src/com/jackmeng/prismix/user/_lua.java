// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

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

  public static void simple_load(String locale)
  {
    if (locale.endsWith((".lua")))
    {
      log("LUA", "Loading Lua script: " + locale);
      try
      {
        G().load(new FileInputStream(locale), "main.lua", "bt", null);
      } catch (FileNotFoundException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    else
    {
      log("LUA", jm_Ansi.make("Failed to load: " + locale).red_bg().white_fg().toString()
          + " because the expected file extension \".lua\" was not found!");
    }
  }

}