// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

import com.jackmeng.ansicolors.jm_Ansi;
import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.uwu;
import com.jackmeng.prismix.ux.gui_XErr.Err_CloseState;
import com.jackmeng.stl.stl_AnsiColors;
import com.jackmeng.stl.stl_AnsiMake;
import com.jackmeng.stl.stl_Callback;
import com.jackmeng.stl.stl_Listener;
import com.jackmeng.stl.stl_ListenerPool;
import com.jackmeng.stl.stl_Struct;
import com.jackmeng.stl.stl_Struct.struct_Pair;

import static com.jackmeng.prismix.jm_Prismix.*;

public final class stx_Map
    extends
    HashMap< String, stl_Struct.struct_Pair< stl_Callback< ?, String >, Object[] > >
{
  public static final String[] type_Bool = new String[] { "true", "false" };

  public static final String[] type_NumBound(double min, double max) // inclusive of min, max i.e [min, max]
  // also this usage of decimal inclusion makes sure that integers and floating
  // points are all included
  {
    return new String[] { "" + Double.toString(min), "" + Double.toString(max) };
    /*--------------------------------------- /
    / String[] r = new String[max - min + 1]; /
    / for (int i = min; i <= max; i++)        /
    /   r[i] = "" + i;                        /
    / return r;                               /
    /----------------------------------------*/ // the old idea here was to make a list of all of the inclusive data,
                                                 // but this would be too upfront and too memory intense for larger
                                                 // ranges, instead we just compute on lazy load.
  }

  // Bool type is for {true, false}
  // NumBound is for a Minima and Maxima range for all floating point and integer
  // data
  public static final String Bool = "Bool", NumBound = "NumBound", StrBound = "StrBound", Any = "Any";

  // result_type, payload
  public static final stl_Callback< Boolean, String > parse_Bool = "true"::equalsIgnoreCase;

  public static stl_Callback< Double, String > parse_NumBound(double min, double max)
  {
    return x -> {
      double e = Double.parseDouble(x);
      return e < min ? min : e > max ? max : e;
    };
  }

  public static stl_Callback< String, String > parse_StrBound(String[] bounds, String default_value)
  {
    return x -> Arrays.binarySearch(bounds, x) >= 0 ? x : default_value;
  }

  public static final stl_Callback< Object, String > parse_Any = x -> x;

  public final String name;

  /**
   * The cache will always reset a property if it has the target value but is out
   * of sync. If however, it is not out of sync (lazy-load), the getters would
   * always default to the cache instead of reparsing in order to save computation
   * time and resources.
   *
   * It stores: <key, key's cached value>
   */

  // <key, listener<key_value>>
  // this is for listening in on change listening for a specific property name
  private final transient HashMap< String, stl_ListenerPool< String > > listeners;

  public stx_Map()
  {
    this(_1const.RNG.nextLong(4000) + "", "");
  }

  public stx_Map(String name, String prefix)
  {
    super();
    this.name = name;
    listeners = new HashMap<>();
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
  public void set_property(String key, String new_value)
  {
    if (this.containsKey(key))
    {
      if (!this.get_value(key).equals(new_value))
      {
        // perform preliminary checks on if the value is allowed
        if (((String[]) this.get(key).second[2]).length > 0) // this part checks if <key, pair<fx, vals>> where
                                                             // vals.length > 0
        {
          boolean found = false;
          for (String r : (String[]) this.get(key).second[2])
            if (r.equals(this.get_value(key)))
              found = true;
          if (found)
          {
            this.put(key, stl_Struct.make_pair(this.get(key).first,
                new Object[] { this.get(key).second[0], new_value, this.get(key).second[2], this.get(key).second[3] }));
            listeners.get(key).dispatch(new_value);
            log("MAP_REGISTRY", jm_Ansi.make().green().toString("Changed value of " + key + " to " + new_value));
          }
          else
            log("MAP_REGISTRY",
                jm_Ansi.make().yellow().toString(name + " failed to set property " + key + " to " + new_value)
                    + " | Value unaltered");
        }
        else
        {
          log("MAP_REGISTRY", jm_Ansi.make().magenta()
              .toString(name + " this is a dangerous operation for a property to not have property checked values!"));
          this.put(key,
              stl_Struct.make_pair(this.get(key).first,
                  new Object[] { this.get(key).second[0], new_value, this.get(key).second[2],
                      this.get(key).second[3] }));
          listeners.get(key).dispatch(new_value);
          log("MAP_REGISTRY", jm_Ansi.make().green().toString(name + " changed value of " + key + " to " + new_value));
        }
      }
    }
  }

  public void rmf_change_listener(String keyName, stl_Listener< String > listener)
  {
    if (listeners.containsKey(keyName))
    {
      log("MAP_REGISTRY", "{1} Was able to find the target key: " + jm_Ansi.make().blue().toString(keyName)
          + " removing the listener: " + listener);
      listeners.get(keyName).rmf(listener);
    }
    else
      log("MAP_REGISTRY", "{1}" + jm_Ansi.make().red_bg().white()
          .toString("Was unable to find the requested key: " + keyName + " for this map: " + name));
  }

  public void add_change_listener(String keyName, stl_Listener< String > listener)
  {
    if (listeners.containsKey(keyName))
    {
      log("MAP_REGISTRY", "{2} Was able to find the target key: " + jm_Ansi.make().blue().toString(keyName)
          + " attaching the listener: " + listener);
      listeners.get(keyName).add(listener);
    }
    else
      log("MAP_REGISTRY", "{2}" + jm_Ansi.make().red_bg().white()
          .toString("Was unable to find the requested key: " + keyName + " for this map: " + name));
  }

  public boolean has_change_listeners(String keyName)
  {
    return listeners.containsKey(keyName);
  }

  public int change_listeners(String keyName)
  {
    return listeners.size();
  }

  public String get_description(String key)
  {
    return get(key).second[3].toString();
  }

  public Object[] get_obj(String key)
  {
    return get(key).second;
  }

  /**
   * The cache will always reset a property if it has the target value but is out
   * of sync.
   *
   * It stores: <key>
   */
  private final transient Set< String > cache = new HashSet<>();

  // you should prefer to use #parse instead of this in order to assert that a
  // cast to the correct type would work (especially useful for dynamics)
  // it is not necessary for lets say a boolean condition to use the following
  // expression:
  //
  // Boolean.TRUE.equals((Boolean) myValues.parse("someKey").get())
  //
  //
  // instead the referral can be made to use:
  //
  // Boolean.TRUE.equals(myValues.parse("someKey").get())
  //
  // reduces code size and overall verbosity^ (the runtime should optimize this
  // cast even if it is there)
  public String get_value(String key)
  {
    if (!this.containsKey(key))
    {
      log("MAP_REGISTRY",
          jm_Ansi.make().red().bold().toString(name + " failed to retrieve an element with key: ") + key
              + " | " + jm_Ansi.make().bold().underline().yellow().toString("{!} This is a bug!"));
      gui_XErr.invoke(
          uwu.fowmat("assets/text/TEXT_exoad_skill_issue.html",
              name + " failed to retrieve an element with key: " + key + "\n" + toString()),
          "BUG!", "https://github.com/exoad/Prismix.java", Err_CloseState.EXIT);
    }
    else if (!cache.contains(key))
    {
      log("MAP_REGISTRY", jm_Ansi.make().green().bold().toString(name + " found key: ")
          + jm_Ansi.make().underline().black().cyan_bg().toString(key)
          + jm_Ansi.make().green().bold().toString(" with value: ")
          + this.get(key).second[1]);
      cache.add(key);
    }
    return (String) this.get(key).second[1]; // casting guranteed due
    // to #put override
    // implementation
  }

  // asserts that the value returned will be in the correct type to be casted.
  // this is for properties where values can be dynamic.
  // ! it also helps the map
  // ! perform lazy checking where the value is checked and set
  @SuppressWarnings("unchecked") public Optional< Object > parse(String key)
  {
    String value = get_value(key);
    return switch (get_type(key)) {
      case Bool:
        // following log statements are ignored to remove verbosity
        /*------------------------------------------------------------ /
        / log("MAP_REGISTRY", "Parsing: " + key + " as type " + Bool); /
        /-------------------------------------------------------------*/
        boolean result_bool = parse_Bool.call(value);
        if (!value.equals(result_bool + ""))
          set_property(key, "" + result_bool);
        yield Optional.of(result_bool);
      case NumBound:
        /*---------------------------------------------------------------- /
        / log("MAP_REGISTRY", "Parsing: " + key + " as type " + IntBound); /
        /-----------------------------------------------------------------*/
        int result_intbound = ((stl_Callback< Integer, String >) get(key).first).call(value);
        if (!value.equals(result_intbound + ""))
          set_property(key,
              "" + result_intbound);
        yield Optional.of(result_intbound);
      case StrBound:
        /*---------------------------------------------------------------- /
        / log("MAP_REGISTRY", "Parsing: " + key + " as type " + StrBound); /
        /-----------------------------------------------------------------*/
        String[] temp_1 = get_allowed(key);
        String result_strbound = parse_StrBound(temp_1, temp_1[0])
            .call(value);
        if (!value.equals(result_strbound))
          set_property(key, result_strbound);
        yield Optional.of(result_strbound);
      case Any:
        /*----------------------------------------------------------- /
        / log("MAP_REGISTRY", "Parsing: " + key + " as type " + Any); /
        /------------------------------------------------------------*/
        yield Optional.of(parse_Any.call(value));
      default:
        log("MAP_REGISTRY", jm_Ansi.make().yellow().bold().toString("Parsing: " + key + " could not find a proper type")
            + " | " + jm_Ansi.make().underline().toString("{!} This is a bug"));
        gui_XErr.invoke(
            uwu.fowmat("assets/text/TEXT_exoad_skill_issue.html",
                "Parsing: " + key + " could not find a proper type\n" + toString() + "\n\n\n"),
            "BUG!", "https://github.com/exoad/Prismix.java", Err_CloseState.EXIT);
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
    return this.containsKey(key) ? (String) this.get(key).second[0] : "undef";
  }

  public void put_(String key, stl_Callback< ?, String > func, Object[] value)
  {
    if (key.contains("-"))
    {
      log("MAP_REGISTRY", jm_Ansi.make().red()
          .toString("Property " + key + " cannot have this invalid character '-', changing '-' to '_'"));
      key = key.replace("-", "_");
    }
    put(key, stl_Struct.make_pair(func, value));
  }

  @Override public struct_Pair< stl_Callback< ?, String >, Object[] > put(String key,
      struct_Pair< stl_Callback< ?, String >, Object[] > value)
  {
    /*------------------------------------------------------------------------------------- /
    / log("MAP_REGISTRY", jm_Ansi.make().magenta().toString(name + " validating: " + key)); /
    /--------------------------------------------------------------------------------------*/
    try
    {
      /*------------------------------------------------------------------------------------------------------- /
      / log("MAP_REGISTRY",                                                                                     /
      /     name + " " + jm_Ansi.make().blue().toString("value.length == 4 -> " + (value.second.length == 4))); /
      /--------------------------------------------------------------------------------------------------------*/
      if (value.second.length != 4)
        throw new ExceptionInInitializerError(new stl_AnsiMake(stl_AnsiColors.RED_BG,
            "[SYS_VAL#" + name + "] Failed component: " + key + " for: value.length == 4").toString());

      /*------------------------------------------------------------------------------------------------------------- /
      / log("MAP_REGISTRY", name + " " + jm_Ansi.make().blue().toString(value.second[0].getClass().getCanonicalName() /
      /     + " in value.second[0].getClass().equals(String.class) "                                                  /
      /     + String.class.getCanonicalName() + " -> "                                                                /
      /     + (value.second[0].getClass().equals(String.class))));                                                    /
      /--------------------------------------------------------------------------------------------------------------*/

      if (!value.second[0].getClass().equals(String.class))
        throw new ExceptionInInitializerError(new stl_AnsiMake(stl_AnsiColors.RED_BG,
            "[SYS_VAL#" + name + "] Failed component: " + key + " for: value[0].getClass().equals(String.class)")
                .toString());
      /*------------------------------------------------------------------------------------------------------------- /
      / log("MAP_REGISTRY", name + " " + jm_Ansi.make().blue().toString(value.second[1].getClass().getCanonicalName() /
      /     + " in value.second[1].getClass().equals(String.class) "                                                  /
      /     + String.class.getCanonicalName() + " -> "                                                                /
      /     + (value.second[1].getClass().equals(String.class))));                                                    /
      /--------------------------------------------------------------------------------------------------------------*/
      if (!value.second[1].getClass().equals(String.class))
        throw new ExceptionInInitializerError(new stl_AnsiMake(stl_AnsiColors.RED_BG,
            "[SYS_VAL#" + name + "] Failed component: " + key + " for: value[1].getClass().equals(String.class)")
                .toString());
      /*------------------------------------------------------------------------------------------------------------- /
      / log("MAP_REGISTRY", name + " " + jm_Ansi.make().blue().toString(value.second[2].getClass().getCanonicalName() /
      /     + " in value.second[2].getClass().equals(String[].class) "                                                /
      /     + String[].class.getCanonicalName() + "-> "                                                               /
      /     + (value.second[2].getClass().equals(String[].class))));                                                  /
      /--------------------------------------------------------------------------------------------------------------*/
      if (!value.second[2].getClass().equals(String[].class))
        throw new ExceptionInInitializerError(new stl_AnsiMake(stl_AnsiColors.RED_BG,
            "[SYS_VAL#" + name + "] Failed component: " + key + " for: value[2].getClass().equals(String[].class)")
                .toString());
      /*------------------------------------------------------------------------------------------------------------- /
      / log("MAP_REGISTRY", name + " " + jm_Ansi.make().blue().toString(value.second[3].getClass().getCanonicalName() /
      /     + " in value[3].getClass().equals(String.class) "                                                         /
      /     + String.class.getCanonicalName() + "-> "                                                                 /
      /     + (value.second[3].getClass().equals(String.class))));                                                    /
      /--------------------------------------------------------------------------------------------------------------*/
      if (!value.second[3].getClass().equals(String.class))
        throw new ExceptionInInitializerError(new stl_AnsiMake(stl_AnsiColors.RED_BG,
            "[SYS_VAL#" + name + "] Failed component: " + key + " for:  value[3].getClass().equals(String.class)")
                .toString());
    } catch (Exception e)
    {
      log("MAP_REGISTRY", jm_Ansi.make().red().toString("Failed component " + key + " with: " + e.getMessage()));
      throw e;
    }

    // checker phase for making sure the property are all met
    /*----------------------------- /
    / for (Object r : value.second) /
    /------------------------------*/
    /*--------------------------------------------------------------------------------------------- /
    / log("MAP_REGISTRY", "Checked property instance: " + r.getClass().getSimpleName() + " typed"); /
    /----------------------------------------------------------------------------------------------*/
    if (!listeners.containsKey(key))
      listeners.put(key, new stl_ListenerPool<>(name + "_" + key + "_listener"));
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

  public void _foreach(stx_TriConsumer< String, String, String > consumer)
  {
    forEach((key, value) -> consumer.accept(key, get_value(key), get_type(key)));
  }

  public void _foreach(BiConsumer< String, String > consumer)
  {
    forEach((key, value) -> consumer.accept(key, get_value(key)));
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