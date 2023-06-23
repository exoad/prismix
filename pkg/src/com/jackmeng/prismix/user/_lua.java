// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.user;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * Objects for handling scripting with LUA including interop for calling Java
 * functions.
 *
 * @author Jack Meng
 */
public final class _lua
{
  private static Map<String, 

  public static LuaValue G()
  {
    return JsePlatform.standardGlobals();
  }

}