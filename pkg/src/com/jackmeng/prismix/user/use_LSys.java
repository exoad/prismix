// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.user;

import com.jackmeng.ansicolors.jm_Ansi;
import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.ux.stx_Map;
import com.jackmeng.stl.stl_In;
import lombok.NonNull;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.function.BiConsumer;

import static com.jackmeng.prismix.jm_Prismix.log;

/**
 * A bare utility class for things to do with Local FileSystem
 *
 * @author Jack Meng
 */
public final class use_LSys
{
	static String locale = _here() + "/" + new String(new byte[] { 0x65, 0x78, 0x6F, 0x61, 0x64 });

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

	public static synchronized void init()
	{
		boolean ran = ensure_dirs();
		log("L_SYS",
				"Initted LSYS with res: "
						+ (ran ?
						jm_Ansi.make().green_bg().white().toString("OK") :
						jm_Ansi.make().red_bg().white().toString("ERR"))
						+ " @ " + new File(locale).getAbsolutePath());
	}

	public static synchronized boolean ensure_dirs()
	{
		boolean ran = false;
		File f = new File(locale);
		if (!f.exists() && !f.isDirectory())
			ran = f.mkdir();
		for (String r : _1const.sub_dirs)
		{
			File f2 = new File(locale + "/" + r);
			if (!f2.exists() && !f2.isDirectory())
				ran = f2.mkdir();
		}
		return ran;
	}

	public static String read_all(File f)
	{
		StringBuilder sb = new StringBuilder();
		try
		{
			stl_In in = new stl_In(new FileInputStream(f));
			while (in.reader().ready())
				sb.append(in.nextln());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static void write(String args, Object name, boolean overwrite)
	{
		File f = new File(locale + "/" + name);

		_1const.worker2.schedule(new TimerTask()
		{
			@Override public void run()
			{
				try
				{
					PrintWriter pw = new PrintWriter(new FileOutputStream(f, overwrite), true, StandardCharsets.UTF_16);
					pw.print(args);
					pw.close();
					log("L_SYS",
							jm_Ansi.make().blue().toString("Wrote: " + args.length() + " to " + f.getAbsolutePath()));
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}, 0);
    /*------------------------------------------------------------------------------------------------------ /
    / }                                                                                                      /
    / else log("L_SYS", jm_Ansi.make().red_bg().white()                                                      /
    /     .toString("Failed to write because the init system was not ran first or returned faulty.") + " | " /
    /     + f.getAbsolutePath());                                                                            /
    /-------------------------------------------------------------------------------------------------------*/
	}

	static final DumperOptions options = new DumperOptions();

	static
	{
		options.setIndent(4);
		options.setSplitLines(true);
		options.setAllowUnicode(true);
		options.setPrettyFlow(true);
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
	}

	public static void delete_files(@NonNull String directory)
	{

	}

	public static void write_obj(@NonNull String name, @NonNull Object r)
	{
		File f = new File(locale + "/" + name);
		if (f.exists() || f.isFile())
			f.delete();
		try (FileOutputStream fos = new FileOutputStream(f); ObjectOutputStream oos = new ObjectOutputStream(fos))
		{
			f.createNewFile();
			oos.writeObject(r);
			oos.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static Object read_obj(@NonNull String name, Object _default)
	{
		File f = new File(locale + "/" + name);
		if (f.exists() && f.isFile() && f.canRead())
		{
			Object temp;
			try (FileInputStream fis = new FileInputStream(f); ObjectInputStream ois = new ObjectInputStream(fis))
			{
				temp = ois.readObject();
			} catch (IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
				log("L_SYS",
						jm_Ansi.make().red_bg().white().toString("Failed to read: ") + name + " | Reason: "
								+ e.getMessage());
				return _default;
			}
			return temp == null ? _default : temp;
		}
		return _default;
	}

	public static void soft_write(@NonNull String file, @NonNull String content)
	{
		File f = new File(locale + "/" + file);
		try (FileWriter fw = new FileWriter(f, StandardCharsets.UTF_16, true))
		{
			fw.write(content);
			fw.flush();
		} catch (Exception e)
		{
			if (!(e instanceof FileNotFoundException))
				e.printStackTrace();
		}
		log("L_SYS", "Soft_Wrote: " + content.length() + " to " + f.getAbsolutePath());
	}

	public static void soft_write_silent(@NonNull String file, @NonNull String content)
	{
		File f = new File(locale + "/" + file);
		try (FileWriter fw = new FileWriter(f, StandardCharsets.UTF_16, true))
		{
			fw.write(content);
			fw.flush();
		} catch (Exception e)
		{
			if (!(e instanceof FileNotFoundException))
				e.printStackTrace();
		}
	}

	public static void write(@NonNull stx_Map map)
	{
		File f = new File(locale + "/" + map.name.replace("\\s+", "%") + ".yml");

		Yaml yaml = new Yaml(options);
		if (f.exists() || f.isFile())
			f.delete();
		try (FileWriter fw = new FileWriter(f, StandardCharsets.UTF_16, false))
		{
			Map<String, Object> create_map = new HashMap<>();
			map.forEach((x, y) -> create_map.put(x, y.second[1]));
			yaml.dump(create_map, fw);
		} catch (Exception e)
		{
			if (!(e instanceof FileNotFoundException))
				e.printStackTrace();
		}
		log("L_SYS", "Wrote: " + map.name + " to " + f.getAbsolutePath());
    /*-------------------------------------------------------------------------------------------------------- /
    / }else                                                                                                    /
    /                                                                                                          /
    / log("L_SYS", jm_Ansi.make().red_bg().white()                                                             /
    /       .toString("Failed to write because the init system was not ran first or returned faulty.") + " | " /
    /       + f.getAbsolutePath());                                                                            /
    /---------------------------------------------------------------------------------------------------------*/
	}

	public static void load_map(@NonNull String name, @NonNull BiConsumer<String, String> callback)
	{
		File f = new File(locale + "/" + name + ".yml");
		Yaml yaml = new Yaml();
		try
		{
			((LinkedHashMap<?, ?>) yaml.load(new FileInputStream(f)))
					.forEach((x, y) -> callback.accept(x.toString(), y.toString()));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
    /*----------------------------------------------------------------------------------------------------- /
    / }                                                                                                     /
    / else log("L_SYS", jm_Ansi.make().red_bg().white()                                                     /
    /     .toString("Failed to read because the init system was not ran first or returned faulty.") + " | " /
    /     + f.getAbsolutePath());                                                                           /
    /------------------------------------------------------------------------------------------------------*/
	}
}