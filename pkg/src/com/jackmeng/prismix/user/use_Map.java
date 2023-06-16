// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.user;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import com.jackmeng.ansicolors.jm_Ansi;
import com.jackmeng.prismix._1const;
import com.jackmeng.stl.stl_AnsiColors;
import com.jackmeng.stl.stl_AnsiMake;
import com.jackmeng.stl.stl_Callback;
import com.jackmeng.stl.stl_Struct;
import com.jackmeng.stl.stl_Struct.struct_Pair;

import static com.jackmeng.prismix.jm_Prismix.*;

public final class use_Map extends HashMap< String, stl_Struct.struct_Pair< stl_Callback< ?, String >, Object[] > >
{
  public static final String[] type_Bool = new String[] { "true", "false" };

  public static final String[] type_IntBound(int min, int max) // inclusive of min, max i.e [min, max]
  {
    return new String[] { "" + min, "" + max };
    /*--------------------------------------- /
    / String[] r = new String[max - min + 1]; /
    / for (int i = min; i <= max; i++)        /
    /   r[i] = "" + i;                        /
    / return r;                               /
    /----------------------------------------*/
  }

  // Bool type is for {true, false}
  // IntBound is for a Minima and Maxima range
  public static final String Bool = "Bool", IntBound = "IntBound", StrBound = "StrBound", Any = "Any";

  // result_type, payload
  public static final stl_Callback< Boolean, String > parse_Bool = "true"::equalsIgnoreCase;

  public static stl_Callback< Integer, String > parse_IntBound(int min, int max)
  {
    return x -> {
      int e = Integer.parseInt(x);
      return e < min ? min : e > max ? max : e;
    };
  }

  public static stl_Callback< String, String > parse_StrBound(String[] bounds, String default_value)
  {
    return x -> Arrays.binarySearch(bounds, x) >= 0 ? x : default_value;
  }

  public static final stl_Callback< Object, String > parse_Any = x -> x;

  public final String name;
  public final String prefix;

  /**
   * The cache will always reset a property if it has the target value but is out
   * of sync. If however, it is not out of sync (lazy-load), the getters would
   * always default to the cache instead of reparsing in order to save computation
   * time and resources.
   *
   * It stores: <key, key's cached value>
   */
  private transient HashMap< String, String > cache;

  public use_Map()
  {
    this(_1const.RNG.nextLong(4000) + "", "");
  }

  public use_Map(String name, String prefix)
  {
    super();
    this.name = name;
    this.prefix = prefix;
    cache = new HashMap<>();
  }

  /**
   * Sets just the property or the second element of the array.
   *
   *
   * @param key
   *          The property name
   * @param new_value
   *          The new value to be moved to
   */
  public synchronized void set_property(String key, String new_value)
  {
    if (this.containsKey(key))
    {
      key = prefix + key.toLowerCase();
      // perform preliminary checks on if the value is allowed
      if (((String[]) this.get(key).second[2]).length > 0) // this part checks if <key, pair<fx, vals>> where vals.length > 0
      {
        if (Arrays.binarySearch((String[]) this.get(key).second[2], key) >= 0)
        {
          this.put(key, stl_Struct.make_pair(this.get(key).first,
              new Object[] { this.get(key).second[0], new_value, this.get(key).second[2], this.get(key).second[3] }));
          cache.put(key, new_value);
          log("SYSVAL", jm_Ansi.make().green().toString(name + " changed value of " + key + " to " + new_value));
        }
        else
          log("SYSVAL", jm_Ansi.make().yellow().toString(name + " failed to set property " + key + " to " + new_value)
              + " | Value unaltered");
      }
      else
      {
        log("SYSVAL", jm_Ansi.make().magenta()
            .toString(name + " this is a dangerous operation for a property to not have property checked values!"));
        this.put(key,
            stl_Struct.make_pair(this.get(key).first,
                new Object[] { this.get(key).second[0], new_value, this.get(key).second[2], this.get(key).second[3] }));
        log("SYSVAL", jm_Ansi.make().green().toString(name + " changed value of " + key + " to " + new_value));
      }
    }
  }

  // you should prefer to use #parse instead of this in order to assert that a
  // cast to the correct type would work (especially useful for dynamics)
  public String get_value(String key)
  {
    key = prefix + key.toLowerCase();
    log("SYSVAL",
        !this.containsKey(key)
            ? jm_Ansi.make().red().bold().toString(name + " failed to retrieve an element with key: ") + " " + key
                + " | " + jm_Ansi.make().yellow().toString("{!} This is a bug!")
            : jm_Ansi.make().green().bold().toString(name + " found key: ")
                + jm_Ansi.make().underline().black().cyan_bg().toString(key)
                + jm_Ansi.make().green().bold().toString(" with value: ")
                + this.get(key).second[1]);
    return (String) this.get(key).second[1]; // casting guranteed due
    // to #put override
    // implementation
  }

  // asserts that the value returned will be in the correct type to be casted.
  // this is for properties where values can be dynamic.
  //! it also helps the map
  //! perform lazy checking where the value is checked and set
  @SuppressWarnings("unchecked") public Optional< Object > parse(String key)
  {
    String value = get_value(key);
    return switch (get_type(key)) {
      case Bool:
        log("SYSVAL", "Parsing: " + key + " as type " + Bool);
        boolean result_bool = parse_Bool.call(value);
        set_property(key, "" + result_bool);
        yield Optional.of(result_bool);
      case IntBound:
        log("SYSVAL", "Parsing: " + key + " as type " + IntBound);
        int result_intbound = ((stl_Callback< Integer, String >) get(prefix + key).first).call(value);
        set_property(key,
            "" + result_intbound);
        yield Optional.of(result_intbound);
      case StrBound:
        log("SYSVAL", "Parsing: " + key + " as type " + StrBound);
        String[] temp_1 = get_allowed(key);
        String result_strbound = parse_StrBound(temp_1, temp_1[0])
            .call(value);
        set_property(key, result_strbound);
        yield Optional.of(result_strbound);
      case Any:
        log("SYSVAL", "Parsing: " + key + " as type " + Any);
        yield Optional.of(parse_Any.call(value));
      default:
        log("SYSVAL", jm_Ansi.make().yellow().bold().toString("Parsing: " + key + " could not find a proper type")
            + " | " + jm_Ansi.make().underline().toString("{!} This is a bug"));
        yield Optional.empty();
    };
  }

  public String[] get_allowed(String key)
  {
    key = key.toLowerCase();
    return this.containsKey(key) ? (String[]) this.get(key).second[2] : new String[0]; // casting guranteed
  }

  public String get_type(String key)
  {
    key = prefix + key.toLowerCase();
    return this.containsKey(key) ? (String) this.get(key).second[0] : "undef";
  }

  public void put_(String key, stl_Callback< ?, String > func, Object[] value)
  {
    if (key.contains("-"))
    {
      log("SYSVAL", jm_Ansi.make().red()
          .toString("Property " + key + " cannot have this invalid character '-', changing '-' to '_'"));
      key = key.replace("-", "_");
    }
    put(key, stl_Struct.make_pair(func, value));
  }

  @Override public synchronized struct_Pair< stl_Callback< ?, String >, Object[] > put(String key,
      struct_Pair< stl_Callback< ?, String >, Object[] > value)
  {
    key = prefix + key.toLowerCase();
    log("SYSVAL", jm_Ansi.make().magenta().toString(name + " validating: " + key));
    try
    {
      log("SYSVAL", name + " " + jm_Ansi.make().blue().toString("value.length == 4 -> " + (value.second.length == 4)));
      if (value.second.length != 4)
        throw new ExceptionInInitializerError(new stl_AnsiMake(stl_AnsiColors.RED_BG,
            "[SYS_VAL#" + name + "] Failed component: " + key + " for: value.length == 4").toString());

      log("SYSVAL", name + " " + jm_Ansi.make().blue().toString(value.second[0].getClass().getCanonicalName()
          + " in value.second[0].getClass().equals(String.class) "
          + String.class.getCanonicalName() + " -> "
          + (value.second[0].getClass().equals(String.class))));

      if (!value.second[0].getClass().equals(String.class))
        throw new ExceptionInInitializerError(new stl_AnsiMake(stl_AnsiColors.RED_BG,
            "[SYS_VAL#" + name + "] Failed component: " + key + " for: value[0].getClass().equals(String.class)")
                .toString());
      log("SYSVAL", name + " " + jm_Ansi.make().blue().toString(value.second[1].getClass().getCanonicalName()
          + " in value.second[1].getClass().equals(String.class) "
          + String.class.getCanonicalName() + " -> "
          + (value.second[1].getClass().equals(String.class))));
      if (!value.second[1].getClass().equals(String.class))
        throw new ExceptionInInitializerError(new stl_AnsiMake(stl_AnsiColors.RED_BG,
            "[SYS_VAL#" + name + "] Failed component: " + key + " for: value[1].getClass().equals(String.class)")
                .toString());
      log("SYSVAL", name + " " + jm_Ansi.make().blue().toString(value.second[2].getClass().getCanonicalName()
          + " in value.second[2].getClass().equals(String[].class) "
          + String[].class.getCanonicalName() + "-> "
          + (value.second[2].getClass().equals(String[].class))));
      if (!value.second[2].getClass().equals(String[].class))
        throw new ExceptionInInitializerError(new stl_AnsiMake(stl_AnsiColors.RED_BG,
            "[SYS_VAL#" + name + "] Failed component: " + key + " for: value[2].getClass().equals(String[].class)")
                .toString());
      log("SYSVAL", name + " " + jm_Ansi.make().blue().toString(value.second[3].getClass().getCanonicalName()
          + " in value[3].getClass().equals(String.class) "
          + String.class.getCanonicalName() + "-> "
          + (value.second[3].getClass().equals(String.class))));
      if (!value.second[3].getClass().equals(String.class))
        throw new ExceptionInInitializerError(new stl_AnsiMake(stl_AnsiColors.RED_BG,
            "[SYS_VAL#" + name + "] Failed component: " + key + " for:  value[3].getClass().equals(String.class)")
                .toString());
    } catch (Exception e)
    {
      log("SYSVAL", jm_Ansi.make().red().toString("Failed component " + key + " with: " + e.getMessage()));
      throw e;
    }

    // checker phase for making sure the property are all met
    for (Object r : value.second)
      log("SYSVAL", "Checked property instance: " + r.getClass().getSimpleName() + " typed");
    return super.put(key, value); // for simplification purposes
  }

  @Override public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("use_Map[#" + name + "@" + hashCode()
        + "] contains:\n");
    this.forEach(
        (key, val) -> sb.append(key + " -> " + ((String) get(key).second[0]) + " is " + ((String) get(key).second[1])
            + " for "
            + Arrays.toString((String[]) get(key).second[2]) + " descriptor: " + ((String) get(key).second[3]) + "\n"));
    return sb.toString();
  }

  public static String normalize_key(String key)
  {
    StringBuilder sb = new StringBuilder();
    for (String r : key.split("_"))
    {
      if (r.length() >= 2)
        sb.append(("" + r.charAt(0)).toUpperCase()).append(r.substring(1));
      else sb.append(r);
      sb.append(" ");
    }
    return sb.toString();
  }
}