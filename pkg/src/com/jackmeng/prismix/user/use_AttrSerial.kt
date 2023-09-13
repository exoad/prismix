// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.user

import lombok.Getter
import java.io.Serializable
import java.util.HashMap

class use_AttrSerial<T:Serializable?>(@field:Getter var payload:T):Serializable
{
	@Getter
	var properties:HashMap<String , String> =HashMap()
	
	init
	{
		properties["SERIAL_TIME"]="-1"
	}
	
	constructor(payload:T , properties:HashMap<String , String>?):this(payload)
	{
		this.properties.putAll(properties!!)
	}
}