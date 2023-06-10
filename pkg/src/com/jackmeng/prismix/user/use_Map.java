// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.user;

import java.util.Arrays;
import java.util.HashMap;

import com.jackmeng.prismix._1const;
import com.jackmeng.stl.stl_AnsiColors;
import com.jackmeng.stl.stl_AnsiMake;

public final class use_Map
    extends HashMap< String, Object[] >
{

  public final String name;

  public use_Map()
  {
    this(_1const.RNG.nextLong(4000) + "");
  }

  public use_Map(String name)
  {
    super();
    this.name = name;
  }

  /**
   * Sets just the property or the second element of the array
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
      key = key.toLowerCase();
      // perform preliminary checks on if the value is allowed
      if (((String[]) this.get(key)[2]).length > 0)
      {
        if (Arrays.binarySearch((String[]) this.get(key)[2], key) >= 0)
        {
          this.put(key, new Object[] { this.get(key)[0], new_value, this.get(key)[2], this.get(key)[3] });
          System.out.println(new stl_AnsiMake(stl_AnsiColors.GREEN_BG,
              "[SYS_VAL" + hashCode() + "] Changed value of " + key + " to " + new_value));
        }
        else
          System.out.println(new stl_AnsiMake(stl_AnsiColors.RED_BG,
              "[SYS_VAL" + hashCode() + "] Failed to set property: " + key + " to " + new_value
                  + " | Value unchanged"));
      }
      else
      {
        System.out.println(new stl_AnsiMake(stl_AnsiColors.YELLOW_TXT,
            "SYS_VAL" + hashCode()
                + "] This is a dangerous operation for a property to not have proper checked values!"));
        this.put(key, new Object[] { this.get(key)[0], new_value, this.get(key)[2], this.get(key)[3] });
        System.out.println(new stl_AnsiMake(stl_AnsiColors.GREEN_BG,
            "[SYS_VAL" + hashCode() + "] Changed value of " + key + " to " + new_value));

      }

    }
  }

  public String get_value(String key)
  {
    key = key.toLowerCase();
    return this.containsKey(key) ? (String) this.get(key)[1] : "undef"; // casting guranteed due
                                                                        // to #put override
                                                                        // implementation
  }

  public String[] get_allowed(String key)
  {
    key = key.toLowerCase();
    return this.containsKey(key) ? (String[]) this.get(key)[2] : new String[0]; // casting guranteed
  }

  @Override public synchronized Object[] put(String key, Object[] value)
  {
    key = key.toLowerCase();
    System.out.println(
        new stl_AnsiMake(stl_AnsiColors.MAGENTA_BG,
            "[SYS_VAL" + hashCode() + "] Validating {program_value}: jm.prismix." + key));
    try
    {
      System.out.println("[SYS_VAL" + hashCode() + "] value.length == 4 -> " + (value.length == 4));
      if (value.length != 4)
        throw new ExceptionInInitializerError(new stl_AnsiMake(stl_AnsiColors.RED_BG,
            "[SYS_VAL" + hashCode() + "] Failed component: " + key + " for: value.length == 4").toString());

      System.out.println(
          "[SYS_VAL" + hashCode() + "] " + value[0].getClass().getCanonicalName()
              + " in value[0].getClass().equals(String.class) "
              + String.class.getCanonicalName() + " -> "
              + (value[0].getClass().equals(String.class)));
      if (!value[0].getClass().equals(String.class))
        throw new ExceptionInInitializerError(new stl_AnsiMake(stl_AnsiColors.RED_BG,
            "[SYS_VAL" + hashCode() + "] Failed component: " + key + " for: value[0].getClass().equals(String.class)")
                .toString());

      System.out.println(
          "[SYS_VAL" + hashCode() + "] " + value[1].getClass().getCanonicalName()
              + " in value[1].getClass().equals(String.class) "
              + String.class.getCanonicalName() + " -> "
              + (value[1].getClass().equals(String.class)));
      if (!value[1].getClass().equals(String.class))
        throw new ExceptionInInitializerError(new stl_AnsiMake(stl_AnsiColors.RED_BG,
            "[SYS_VAL" + hashCode() + "] Failed component: " + key + " for: value[1].getClass().equals(String.class)")
                .toString());
      System.out.println(
          "[SYS_VAL" + hashCode() + "] " + value[2].getClass().getCanonicalName()
              + " in value[2].getClass().equals(String[].class) "
              + String[].class.getCanonicalName() + "-> "
              + (value[2].getClass().equals(String[].class)));
      if (!value[2].getClass().equals(String[].class))
        throw new ExceptionInInitializerError(new stl_AnsiMake(stl_AnsiColors.RED_BG,
            "[SYS_VAL" + hashCode() + "] Failed component: " + key + " for: value[2].getClass().equals(String[].class)")
                .toString());
      System.out.println(
          "[SYS_VAL" + hashCode() + "] " + value[3].getClass().getCanonicalName()
              + " in value[3].getClass().equals(String.class) "
              + String.class.getCanonicalName() + "-> "
              + (value[3].getClass().equals(String.class)));
      if (!value[3].getClass().equals(String.class))
        throw new ExceptionInInitializerError(new stl_AnsiMake(stl_AnsiColors.RED_BG,
            "[SYS_VAL" + hashCode() + "] Failed component: " + key + " for:  value[3].getClass().equals(String.class)")
                .toString());
    } catch (Exception e)
    {
      System.out.println(
          new stl_AnsiMake(stl_AnsiColors.RED_BG, "[SYS_VAL] Failed component: " + key + " with: " + e.getMessage()));
      throw e;
    }

    // checker phase for making sure the property are all met
    for (Object r : value)
      System.out.println("[SYS_VAL] Checked property instance: " + r.getClass().getSimpleName() + " typed");

    return super.put("jm.prismix." + key, value); // for simplification purposes
  }

  @Override public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("use_Map[" + name + "@" + hashCode()
        + "]");
    for(String )
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