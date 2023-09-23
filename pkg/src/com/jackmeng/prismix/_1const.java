// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix

import com.jackmeng.stl.stl_AssetFetcher
import com.jackmeng.stl.stl_AssetFetcher.assetfetcher_FetcherStyle
import com.jackmeng.prismix._1const
import com.jackmeng.prismix.ux.stx_Map
import com.jackmeng.stl.stl_Listener
import com.jackmeng.stl.stl_Struct.struct_Pair
import com.jackmeng.stl.stl_ListenerPool.ListenerPool_Attached.Attached_States
import com.jackmeng.prismix.jm_Prismix
import com.jackmeng.ansicolors.jm_Ansi
import com.jackmeng.stl.stl_ListenerPool.ListenerPool_Attached
import java.awt.Color
import java.awt.Font
import java.awt.FontFormatException
import java.io.IOException
import java.lang.ref.SoftReference
import java.util.*
import java.util.concurrent.Executors

/**
 * Critical constants for the program itself as a whole
 *
 * @author Jack Meng
 */
object _1const
{
	/*---------------------------------------------- /
  / public static final boolean DEBUG_GUI = false; /
  / public static final boolean SOFT_DEBUG = true; /
  /-----------------------------------------------*/
	var sub_dirs=arrayOf(
		"state" , "log"
	)
	val RNG=Random()
	var worker=Timer("com-jackmeng-prismix-worker01")
	var worker2=Timer("com-jackmeng-prismix-worker02")
	var worker3=Executors.newWorkStealingPool(4)
	var fetcher=stl_AssetFetcher(assetfetcher_FetcherStyle.WEAK)
	var MONO_FONT:Font?=null
	var MONO_FONT_MEDIUM:Font?=null
	fun _Mono_():Font?
	{
		if (MONO_FONT==null) try
		{
			MONO_FONT=Font.createFont(Font.TRUETYPE_FONT , fetcher.file("assets/font/FiraMono-Regular.ttf"))
		} catch (e:FontFormatException)
		{
			e.printStackTrace()
			return Font(Font.MONOSPACED , Font.PLAIN , 12)
		} catch (e:IOException)
		{
			e.printStackTrace()
			return Font(Font.MONOSPACED , Font.PLAIN , 12)
		}
		return MONO_FONT
	}
	
	fun _Mono_Medium_():Font?
	{
		if (MONO_FONT_MEDIUM==null) try
		{
			MONO_FONT_MEDIUM=Font.createFont(Font.TRUETYPE_FONT , fetcher.file("assets/font/FiraMono-Medium.ttf"))
		} catch (e:FontFormatException)
		{
			e.printStackTrace()
			return Font(Font.MONOSPACED , Font.PLAIN , 12)
		} catch (e:IOException)
		{
			e.printStackTrace()
			return Font(Font.MONOSPACED , Font.PLAIN , 12)
		}
		return MONO_FONT_MEDIUM
	}
	
	private var lastColor=SoftReference(Color(1f , 1f , 1f , 0f))
	
	// property-name, {property_type, default_value, {valid_values},
	// description}
	val `val`=stx_Map("com_jackmeng_prismix_CONFIG" , "com.jackmeng.prismix.")
	val PATH=String(byteArrayOf(0x65 , 0x78 , 0x6F , 0x61 , 0x64))
	fun __init()
	{
		COLOR_ENQ.attach { payload:struct_Pair<stl_Listener<struct_Pair<Color , Boolean?>>? , Attached_States>->
			if (payload.first!=null)
			{
				jm_Prismix.log(
					"COLORPANEL" ,
					jm_Ansi.make().white_bg().black_fg().toString("Pool Listener:  received: $payload")
				)
				if (payload.second==Attached_States.ADD_LISTENER) jm_Prismix.log(
					"COLORPANEL" ,
					jm_Ansi.make().blue().toString("Enqueued a POOL Listener: "+payload.first.toString())
				)
				else if (payload.second==Attached_States.ATTACHED) jm_Prismix.log(
					"COLORPANEL" ,
					jm_Ansi.make().green().toString("The current pool listener  has been attached")
				)
				else if (payload.second==Attached_States.DETACHED) jm_Prismix.log(
					"COLORPANEL" ,
					jm_Ansi.make().yellow_fg().toString("The current pool listener  has been detached")
				)
				else if (payload.second==Attached_States.RMF_LISTENER) jm_Prismix.log(
					"COLORPANEL" , jm_Ansi.make().yellow_bg().black_fg().toString(
						"Dequeued a POOL_LISTENER: "+payload.first.toString()
					)
				)
				else jm_Prismix.log(
					"COLORPANEL" , jm_Ansi.make().red_bg().bold().white_fg().toString(
						"The current pool listener  received an invalid signal: "+payload
					)
				)
			}
			null
		}
		COLOR_ENQ.add { x:struct_Pair<Color , Boolean?>->
			jm_Prismix.log(
				"COLORPANEL" , jm_Ansi.make().blue_fg().toString(
					"Enqueued another color for GUI elements to process: rgba("+x.first.red+","+x.first.green+","+x.first.blue+","+x.first.alpha+")"
				)
			)
			lastColor=SoftReference(x.first)
			null
		}
	}
	
	/**
	 * PAIR[0] = (java.awt.Color) Color payload
	 * PAIR[1] = (java.lang.Boolean) Ignore Payload for storage
	 */
	var COLOR_ENQ:ListenerPool_Attached<struct_Pair<Color , Boolean?>>=
		object:ListenerPool_Attached<struct_Pair<Color? , Boolean?>?>(
			"current-processing-pool"
		)
		{
			@Synchronized
			override fun dispatch(e:struct_Pair<Color , Boolean?>)
			{ // Here we want it so that the same color would not be reused and redispatched
				// everytime causing unnecessary GUI updates. Note: all GUI updates are heavily
				// tied to this queue system
				if (java.lang.Boolean.TRUE==!e.second!!&&e.first!=last_color()) super.dispatch(e)
			}
		}
	
	fun shutdown_hook(r:Runnable?)
	{
		Runtime.getRuntime().addShutdownHook(Thread(r))
	}
	
	val MOUSE_LOCATION=struct_Pair(0 , 0)
	fun last_color():Color
	{
		return if (lastColor.get()==null) Color.gray else lastColor.get()!!
	}
	
	@Synchronized
	fun add(r:Runnable , delay:Long)
	{
		worker.schedule(object:TimerTask()
		{
			override fun run()
			{
				r.run()
			}
		} , delay)
	}
	
	@Synchronized
	fun add(r:Runnable , delay:Long , rep_delay:Long)
	{
		worker.scheduleAtFixedRate(object:TimerTask()
		{
			override fun run()
			{
				r.run()
			}
		} , delay , rep_delay)
	}
}