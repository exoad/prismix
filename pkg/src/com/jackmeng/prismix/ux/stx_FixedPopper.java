// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.util.Collections;
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

	private final LinkedList< T > items;
	private final int maxLen;
	private final Popper_Viewer viewer;
	private Popper_Priority priority;
	private final Consumer< String > logger;
	private Consumer< T > janitor;

	/**
	 * @param maxLen
	 *          Max Length
	 * @param priority
	 *          Head or Tail priority preservation
	 * @param logger
	 *          An optional state logger
	 * @param janitor
	 *          A handler for consuming any values ejected from the structure
	 */
	public stx_FixedPopper(int maxLen, Popper_Priority priority, Consumer< String > logger, Consumer< T > janitor)
	{
		this.items = new LinkedList<>();
		for (int i = 0; i < maxLen; i++)
			items.add(null);
		this.maxLen = maxLen;
		this.viewer = new Popper_Viewer();
		this.priority = priority;
		this.logger = logger;
		this.janitor = janitor;
	}

	public stx_FixedPopper(int maxLen)
	{
		this(maxLen, Popper_Priority.TAIL, x -> {
		}, x -> {
		});
	}

	public synchronized void janitor(Consumer< T > janitor)
	{
		this.janitor = janitor;
	}

	public int maxLen()
	{
		return maxLen;
	}

	public int size()
	{
		// stable
		return items.size();
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
		if (items.contains(element))
		{
			items.remove(element);
			janitor.accept(element);
		}
		else if (items.size() >= maxLen)
		{
			janitor.accept(items.getFirst());
			items.removeFirst();
		}
		items.addLast(element);
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
		i = i > maxLen - 1 ? maxLen : i < 0 ? 0 : i;
		items.add(viewer.currentIndex, element);
		if (items.size() > maxLen)
		{
			if (priority == Popper_Priority.HEAD)
			{
				janitor.accept(items.getLast());
				items.removeLast();
			}
			else
			{
				janitor.accept(items.getFirst());
				items.removeFirst();
				if (viewer.currentIndex > 0)
					viewer.currentIndex--;
			}
		}
	}

	public void toHead()
	{
		viewer.currentIndex = 0;
	}

	public void toTail()
	{
		viewer.currentIndex = items.size() - 1;
	}

	@Override public String toString()
	{
		StringBuilder sb = new StringBuilder();
		items.forEach(x -> sb.append(x).append(","));
		return hashCode() + "{maxLen=" + maxLen + ",itrPos=" + viewer.currentIndex + "}[" + sb.toString() + "]";
	}

	private class Popper_Viewer
			implements Iterator< T >
	{
		private int currentIndex;

		private Popper_Viewer()
		{
			this.currentIndex = 0;
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
			{
				currentIndex--;

				return items.get(currentIndex);
			}
			return items.get(0);

		}

		@Override public T next()
		{
			if (hasNext())
			{
				currentIndex++;
				return items.get(currentIndex);
			}
			return items.get(items.size() - 1);
		}

		@Override public void remove()
		{
			throw new UnsupportedOperationException("Remove operation is not supported");
		}
	}
}