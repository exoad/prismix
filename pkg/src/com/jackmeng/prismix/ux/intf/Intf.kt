package com.jackmeng.prismix.ux.intf

import com.jackmeng.stl.stl_Callback
import java.util.function.Consumer

object Intf
{
	val DEAD_VOID_VOID=Runnable {}
	val DEAD_T_VOID:Consumer<*> =Consumer { e:Any?-> }
	val DEAD_T_T:stl_Callback<* , *> =stl_Callback<Any? , Any> { e:Any?-> null }
}