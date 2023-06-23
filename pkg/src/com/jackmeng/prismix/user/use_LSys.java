// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.user;

/**
 * A bare utility class for things to do with Local FileSystem
 *
 * @author Jack Meng
 */
public final class use_LSys
{
  private use_LSys()
  {
  }

  public static String _home()
  {
    return System.getProperty("user.home");
  }

  public static String _here()
  {
    return System.getProperty("user.dir");
  }

  public static void init()
  {
    
  }
}