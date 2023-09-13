package com.jackmeng.prismix.ux.intf;

import com.jackmeng.stl.stl_Callback;

import java.util.function.Consumer;

public final class Intf
{
	private Intf() {}

	public static final Runnable DEAD_VOID_VOID = () -> {};

	public static final Consumer<?> DEAD_T_VOID = e -> {};

	public static final stl_Callback<?, ?> DEAD_T_T = e -> null;
}
