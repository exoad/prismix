// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jackmeng.stl.stl_ListenerPool;

public class ux_Palette
{
  private Set< List< Float > > colors_rgba;

  public static final class Palette_State
  {
    private final List< Float > payload;
    private final int startIndex, toIndex;

    /**
     * @param payload
     *          Payload
     * @param startIndex
     *          Where the payload started from (should always be original). If for
     *          append, then this value should be -1.
     * @param toIndex
     *          Where the payload should be moved to (-1 for removal,
     *          colors_rgba.length for add to top)
     */
    public Palette_State(List< Float > payload, int startIndex, int toIndex)
    {
      this.payload = payload;
      this.startIndex = startIndex;
      this.toIndex = toIndex;
    }

    public List< Float > payload()
    {
      return payload;
    }

    public int start()
    {
      return startIndex;
    }

    public int end()
    {
      return toIndex;
    }
  }

  public final stl_ListenerPool< Palette_State > LISTENER_POOL;

  public ux_Palette(int initial, String name)
  {
    colors_rgba = new HashSet<>();
    LISTENER_POOL = new stl_ListenerPool<>(name);
    LISTENER_POOL.add(x -> {
      System.out.println("[UX_PALETTE] Palette " + name + " received operation: " + x);
      return (Void) null;
    });
  }

  public void append(float[] e)
  {
    if (e == null || e.length < 4 || e.length > 4)
    {
      System.out.println("[UX_PALETTE] Palette" + hashCode() + " received an inproper constructed color array");
      return;
    }
    colors_rgba.add($toarr(e));
  }

  private static List< Float > $toarr(float[] r) // similar to Arrays.asList but instead provides a writable ArrayList
  {
    List< Float > temp = new ArrayList<>(r.length);
    for (float e : r)
      temp.add(e);
    return temp;
  }

  private static float[] $toprim(List< Float > r)
  {
    float[] temp = new float[r.size()];
    for (int i = 0; i < r.size(); i++)
      temp[i] = r.get(i);
    return temp;
  }

  public boolean remove(float[] e)
  {
    return colors_rgba.contains($toarr(e)) ? colors_rgba.remove($toarr(e)) : false;
  }

  public float[][] fetch()
  {
    float[][] temp = new float[colors_rgba.size()][4];
    int i = 0;
    colors_rgba.iterator().forEachRemaining(x -> temp[i] = $toprim(x));
    return temp;
  }
}