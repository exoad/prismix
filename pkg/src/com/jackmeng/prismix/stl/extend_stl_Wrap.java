// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.stl;

import com.jackmeng.stl.stl_Wrap;

// Explicit extension of the STL's wrap class for more definitions.
// Requirement Level: None
public class extend_stl_Wrap< T > extends stl_Wrap< T >
{
  public extend_stl_Wrap(T obj)
  {
    super(obj);
  }

  public T get()
  {
    return obj;
  }

  public void set(T obj)
  {
    this.obj = obj;
  }
}