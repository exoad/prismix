// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.user

import com.jackmeng.prismix.user.use_LSys
import com.jackmeng.prismix.jm_Prismix
import com.jackmeng.ansicolors.jm_Ansi
import com.jackmeng.prismix._1const
import com.jackmeng.stl.stl_In
import org.yaml.snakeyaml.DumperOptions
import com.jackmeng.prismix.ux.stx_Map
import org.yaml.snakeyaml.Yaml
import com.jackmeng.stl.stl_Struct.struct_Pair
import com.jackmeng.stl.stl_Callback
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.function.BiConsumer

/**
 * A bare utility class for things to do with Local FileSystem
 *
 * @author Jack Meng
 */
object use_LSys
{
	var locale=_here()+"/"+String(byteArrayOf(0x65 , 0x78 , 0x6F , 0x61 , 0x64))
	fun _home():String
	{
		return System.getProperty("user.home")
	}
	
	fun _here():String
	{
		return System.getProperty("user.dir")
	}
	
	@Synchronized
	fun init()
	{
		val ran=ensure_dirs()
		jm_Prismix.log(
			"L_SYS" ,
			"Initted LSYS with res: "+(if (ran) jm_Ansi.make().green_bg().white().toString("OK")
			else jm_Ansi.make().red_bg().white().toString("ERR"))+" @ "+File(locale).absolutePath
		)
	}
	
	@Synchronized
	fun ensure_dirs():Boolean
	{
		var ran=false
		val f=File(locale)
		if (!f.exists()&&!f.isDirectory) ran=f.mkdir()
		for (r in _1const.sub_dirs)
		{
			val f2=File(locale+"/"+r)
			if (!f2.exists()&&!f2.isDirectory) ran=f2.mkdir()
		}
		return ran
	}
	
	fun read_all(f:File?):String
	{
		val sb=StringBuilder()
		try
		{
			val `in`=stl_In(FileInputStream(f))
			while (`in`.reader().ready()) sb.append(`in`.nextln())
		} catch (e:Exception)
		{
			e.printStackTrace()
		}
		return sb.toString()
	}
	
	fun write(args:String , name:Any , overwrite:Boolean)
	{
		val f=File(locale+"/"+name)
		_1const.worker2.schedule(object:TimerTask()
		{
			override fun run()
			{
				try
				{
					val pw=PrintWriter(FileOutputStream(f , overwrite) , true , StandardCharsets.UTF_16)
					pw.print(args)
					pw.close()
					jm_Prismix.log(
						"L_SYS" , jm_Ansi.make().blue().toString("Wrote: "+args.length+" to "+f.absolutePath)
					)
				} catch (e:IOException)
				{
					e.printStackTrace()
				}
			}
		} , 0)/*------------------------------------------------------------------------------------------------------ /
    / }                                                                                                      /
    / else log("L_SYS", jm_Ansi.make().red_bg().white()                                                      /
    /     .toString("Failed to write because the init system was not ran first or returned faulty.") + " | " /
    /     + f.getAbsolutePath());                                                                            /
    /-------------------------------------------------------------------------------------------------------*/
	}
	
	val options=DumperOptions()
	
	init
	{
		options.indent=4
		options.splitLines=true
		options.isAllowUnicode=true
		options.isPrettyFlow=true
		options.defaultFlowStyle=DumperOptions.FlowStyle.BLOCK
	}
	
	fun delete_files(directory:String)
	{
	}
	
	fun write_obj(name:String , r:Any)
	{
		val f=File(locale+"/"+name)
		if (f.exists()||f.isFile) f.delete()
		try
		{
			FileOutputStream(f).use { fos->
				ObjectOutputStream(fos).use { oos->
					f.createNewFile()
					oos.writeObject(r)
					oos.flush()
				}
			}
		} catch (e:IOException)
		{
			e.printStackTrace()
		}
	}
	
	fun read_obj(name:String , _default:Any):Any
	{
		val f=File(locale+"/"+name)
		if (f.exists()&&f.isFile&&f.canRead())
		{
			var temp:Any
			try
			{
				FileInputStream(f).use { fis-> ObjectInputStream(fis).use { ois-> temp=ois.readObject() } }
			} catch (e:IOException)
			{
				e.printStackTrace()
				jm_Prismix.log(
					"L_SYS" , jm_Ansi.make().red_bg().white().toString("Failed to read: ")+name+" | Reason: "+e.message
				)
				return _default
			} catch (e:ClassNotFoundException)
			{
				e.printStackTrace()
				jm_Prismix.log(
					"L_SYS" , jm_Ansi.make().red_bg().white().toString("Failed to read: ")+name+" | Reason: "+e.message
				)
				return _default
			}
			return (if (temp==null) _default else temp)
		}
		return _default
	}
	
	fun soft_write(file:String , content:String)
	{
		val f=File(locale+"/"+file)
		try
		{
			FileWriter(f , StandardCharsets.UTF_16 , true).use { fw->
				fw.write(content)
				fw.flush()
			}
		} catch (e:Exception)
		{
			if (e !is FileNotFoundException) e.printStackTrace()
		}
		jm_Prismix.log("L_SYS" , "Soft_Wrote: "+content.length+" to "+f.absolutePath)
	}
	
	fun soft_write_silent(file:String , content:String)
	{
		val f=File(locale+"/"+file)
		try
		{
			FileWriter(f , StandardCharsets.UTF_16 , true).use { fw->
				fw.write(content)
				fw.flush()
			}
		} catch (e:Exception)
		{
			if (e !is FileNotFoundException) e.printStackTrace()
		}
	}
	
	fun write(map:stx_Map)
	{
		val f=File(locale+"/"+map.name.replace("\\s+" , "%")+".yml")
		val yaml=Yaml(options)
		if (f.exists()||f.isFile) f.delete()
		try
		{
			FileWriter(f , StandardCharsets.UTF_16 , false).use { fw->
				val create_map:MutableMap<String , Any>=HashMap()
				map.forEach { (x:String , y:struct_Pair<stl_Callback<* , String?>? , Array<Any>>)->
					create_map[x]=y.second[1]
				}
				yaml.dump(create_map , fw)
			}
		} catch (e:Exception)
		{
			if (e !is FileNotFoundException) e.printStackTrace()
		}
		jm_Prismix.log("L_SYS" , "Wrote: "+map.name+" to "+f.absolutePath)/*-------------------------------------------------------------------------------------------------------- /
    / }else                                                                                                    /
    /                                                                                                          /
    / log("L_SYS", jm_Ansi.make().red_bg().white()                                                             /
    /       .toString("Failed to write because the init system was not ran first or returned faulty.") + " | " /
    /       + f.getAbsolutePath());                                                                            /
    /---------------------------------------------------------------------------------------------------------*/
	}
	
	fun load_map(name:String , callback:BiConsumer<String? , String?>)
	{
		val f=File(locale+"/"+name+".yml")
		val yaml=Yaml()
		try
		{
			(yaml.load<Any>(FileInputStream(f)) as LinkedHashMap<* , *>).forEach { (x:Any , y:Any)->
					callback.accept(
						x.toString() ,
						y.toString()
					)
				}
		} catch (e:FileNotFoundException)
		{
			e.printStackTrace()
		}/*----------------------------------------------------------------------------------------------------- /
    / }                                                                                                     /
    / else log("L_SYS", jm_Ansi.make().red_bg().white()                                                     /
    /     .toString("Failed to read because the init system was not ran first or returned faulty.") + " | " /
    /     + f.getAbsolutePath());                                                                           /
    /------------------------------------------------------------------------------------------------------*/
	}
}