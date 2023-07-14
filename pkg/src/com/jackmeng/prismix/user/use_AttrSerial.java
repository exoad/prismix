// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.user;

import java.io.Serializable;
import java.util.HashMap;

import lombok.Getter;

public final class use_AttrSerial< T extends Serializable >
		implements Serializable
{
	@Getter T payload;
	@Getter HashMap< String, String > properties;

	public use_AttrSerial(T payload)
	{
		this.payload = payload;
		properties.put("SERIAL_TIME", "-1");
	}

	public use_AttrSerial(T payload, HashMap< String, String > properties)
	{
		this(payload);
		properties.forEach((key, val) -> this.properties.put(key, val));
	}
}