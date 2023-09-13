// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.stl

import com.jackmeng.stl.stl_Wrap

// Explicit extension of the STL's wrap class for more definitions.
// Requirement Level: None
class extend_stl_Wrap<T>(obj:T):stl_Wrap<T>(obj)
{
	fun get():T?
	{
		return obj
	}
	
	fun set(obj:T)
	{
		this.obj=obj
	}
	
	companion object
	{
		fun <T> copy(e:T):T
		{
			return e
		}
	}
}