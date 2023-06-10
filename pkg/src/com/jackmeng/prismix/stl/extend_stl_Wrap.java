// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.stl;

import com.jackmeng.stl.stl_Wrap;

// Explicit extension of the STL's wrap class for more definitions.
// Requirement Level: None
public class extend_stl_Wrap< T > extends stl_Wrap< T >
{
  public extend_stl_Wrap(final T obj)
  {
    super(obj);
  }

  public T get()
  {
    return this.obj;
  }

  public void set(final T obj)
  {
    this.obj = obj;
  }

  public static <T> T copy(final T e)
  {
    return e;
  }
}