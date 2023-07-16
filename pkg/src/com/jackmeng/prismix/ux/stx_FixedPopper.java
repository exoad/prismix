// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;

/**
 * A simple Fixed Array with a lot of neat utilities for working
 * with Next Back functionalities. This is primarily good for viewing a fixed
 * length amount of data such as history for something.
 *
 * @author Jack Meng
 */
public class stx_FixedPopper< T >
		implements
		Serializable
{
	public enum Popper_Priority {
		/**
		 * The head would be preferred on an insertion, meaning on an insertion when the
		 * structure is full, elements from the tail will get eliminated.
		 */
		HEAD,

		/**
		 * Read the docs for the HEAD, but opposite of HEAD.
		 */
		TAIL;
	}

	private LinkedList< T > items;
	private final int maxLen;
	private final Popper_Viewer viewer;
	private boolean isUnique = true;
	private Popper_Priority priority;
	private transient Consumer< T > janitor;

	/**
	 * @param maxLen
	 *          Max Length
	 * @param priority
	 *          Head or Tail priority preservation
	 * @param logger
	 *          An optional state logger
	 * @param janitor
	 *          A handler for consuming any values ejected from the structure
	 * @param isUnique
	 *          Determines whether this structure should only keep unique elements.
	 *          For a force push, duplicate elements will be removed and appended to
	 *          the tail. An insertion push would simply remove the duplicate with
	 *          respect to the priority rule.
	 */
	public stx_FixedPopper(int maxLen, Popper_Priority priority, boolean isUnique,
			Consumer< T > janitor)
	{
		assert maxLen >= 0;
		this.items = new LinkedList<>();
		for (int i = 0; i < maxLen; i++)
			items.add(i, null);
		this.maxLen = maxLen;
		this.isUnique = isUnique;
		this.viewer = new Popper_Viewer();
		this.priority = priority;
		this.janitor = janitor;
	}

	/**
	 * A default simple constructor for stx_FixedPopper
	 *
	 * @param maxLen
	 *          Max Length
	 */
	public stx_FixedPopper(int maxLen)
	{
		this(maxLen, Popper_Priority.TAIL, true, x -> {
		});
	}

	public synchronized void janitor(Consumer< T > janitor)
	{
		this.janitor = janitor;
	}

	public int size()
	{
		// unstable
		/*-------------------- /
		/ return items.size(); /
		/---------------------*/

		// stable
		return maxLen;
	}

	public int where()
	{
		return viewer.currentIndex;
	}

	public Popper_Priority priority()
	{
		return priority;
	}

	public synchronized void priority(Popper_Priority priority)
	{
		this.priority = priority;
	}

	/**
	 * Ignores the modifierIterator and pushes the desired element to the end
	 *
	 * @param element
	 *          The desired element to the end
	 */
	public synchronized void force_push(T element)
	{
		/*---------------------------------------- /
		/ if (isUnique && items.contains(element)) /
		/ {                                        /
		/   items.remove(element);                 /
		/   janitor.accept(element);               /
		/ }                                        /
		/ else if (items.size() >= maxLen)         /
		/ {                                        /
		/   janitor.accept(items.getFirst());      /
		/   items.removeFirst();                   /
		/ }                                        /
		/                                          /
		/ if (items.size() < maxLen)               /
		/ {                                        /
		/   int nullIndex = items.indexOf(null);   /
		/   if (nullIndex != -1)                   /
		/     items.set(nullIndex, element);       /
		/   else                                   /
		/     items.add(element);                  /
		/ }                                        /
		/-----------------------------------------*/
		if (isUnique && items.contains(element))
		{
			System.out.println("dupped: " + element);
			items.remove(element);
			janitor.accept(element);
		}
		else
		{
			if (items.size() >= maxLen)
			{
				janitor.accept(items.getFirst());
				items.removeFirst();
			}
			items.addLast(element);
			if (items.size() > maxLen)
			{
				items.removeFirst();
			}
		}

	}

	public boolean is_full()
	{
		return items.size() == maxLen;
	}

	public T previous()
	{
		return viewer.previous();
	}

	public int previous_where()
	{
		return Math.max(0, viewer.currentIndex - 1);
	}

	public T next()
	{
		return viewer.next();
	}

	public int next_where()
	{
		return Math.min(items.size() - 1, viewer.currentIndex + 1);
	}

	public T at(int index)
	{
		return index < 0 ? items.get(0) : index >= items.size() ? items.get(items.size() - 1) : items.get(index);
	}

	public T at()
	{
		return items.get(viewer.currentIndex);
	}

	public synchronized void push_at(int i, T element)
	{
		i = Math.max(0, Math.min(i, items.size()));

		if (priority == Popper_Priority.HEAD)
		{
			if (i <= viewer.currentIndex)
			{
				janitor.accept(items.getLast());
				items.removeLast();
				viewer.currentIndex--;
			}
		}
		else
		{
			if (i >= viewer.currentIndex)
			{
				janitor.accept(items.getFirst());
				items.removeFirst();
			}
		}

		items.add(i, element);
		viewer.currentIndex = i;
	}

	public synchronized void push(T element)
	{
		push_at(viewer.currentIndex, element);
	}

	public void toHead()
	{
		viewer.currentIndex = 0;
	}

	public void toTail()
	{
		viewer.currentIndex = maxLen - 1;
	}

	public void itr_to(int i)
	{
		viewer.move(i);
	}


	@Override public String toString()
	{
		return hashCode() + "{space=" + items.size() + ",maxLen=" + maxLen + ",itrPos=" + viewer.currentIndex + "}"
				+ toStringArr();
	}

	public String toStringArr()
	{
		StringBuilder sb = new StringBuilder();
		items.forEach(x -> sb.append(x).append(","));
		return "[" + sb.substring(0, sb.length() - 1) + "]";
	}

	private class Popper_Viewer
			implements
			Iterator< T >,
			Serializable
	{
		private int currentIndex;

		private Popper_Viewer()
		{
			this.currentIndex = 0;
		}

		public void move(int i)
		{
			currentIndex = Math.max(0, Math.min(currentIndex + i, items.size() - 1));
		}

		@Override public boolean hasNext()
		{
			return currentIndex < items.size() - 1;
		}

		public boolean hasPrevious()
		{
			return currentIndex > 0;
		}

		public T previous()
		{
			if (hasPrevious())
				currentIndex--;
			return items.get(currentIndex);
		}

		@Override public T next()
		{
			if (hasNext())
				currentIndex++;
			return items.get(currentIndex);
		}

		@Override public void remove()
		{
			throw new UnsupportedOperationException("Remove operation is not supported");
		}
	}

}