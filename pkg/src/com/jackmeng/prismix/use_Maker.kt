// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix

import com.jackmeng.ansicolors.jm_Ansi
import com.jackmeng.stl.stl_Struct.struct_Pair
import com.jackmeng.stl.stl_Callback
import com.jackmeng.stl.types.Null_t
import com.jackmeng.prismix.stl.extend_stl_Wrap
import com.jackmeng.stl.stl_Wrap
import javax.swing.*
import java.awt.BorderLayout
import java.awt.Color
import java.lang.StringBuilder
import java.util.*

/**
 * Utility methods container
 * Class contains a bunch of uncategorized helper methods
 *
 * @author Jack Meng
 */
object use_Maker
{
	@JvmStatic
    fun db_timed(r:Runnable)
	{
		if (java.lang.Boolean.TRUE==_1const.`val`.parse("debug_gui").get())
		{
			val i=System.currentTimeMillis()
			r.run()
			jm_Prismix.log(
				"DEBUG" ,
				jm_Ansi.make().yellow().toString("Timed a run for: "+r+" took "+(System.currentTimeMillis()-i)+"ms")
			)
		}
		else r.run()
	}
	
	@JvmStatic
	fun makeJMenu(text:String? , vararg items:JMenuItem?):JMenu
	{
		val menu=JMenu(text)
		for (i in items.indices)
		{
			menu.add(items[i])
			if (i!=items.size-1) menu.addSeparator()
		}
		return menu
	}
	
	@JvmStatic
	fun wrap(r:JComponent?):JPanel
	{
		val t=JPanel()
		t.layout=BorderLayout()
		if (r!=null)
		{
			t.add(r , BorderLayout.CENTER)
		}
		return t
	}
	
	@JvmStatic
	fun debug(e:JComponent?)
	{
		if (e!=null) if (e.border!=null) e.border=BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(Color.pink , 2) , e.border
		)
	}
	
	@JvmStatic
	fun make(items:Array<struct_Pair<String , stl_Callback<Boolean , Null_t?>>>):Array<JMenuItem?>
	{
		val e=arrayOfNulls<JMenuItem>(items.size)
		val i=extend_stl_Wrap(0)
		while (i.get()!!<e.size)
		{
			val callback=items[i.get()!!].second
			val name=items[i.get()!!].first
			jm_Prismix.log("MENUBAR" , "Checks for: "+name+" | Itr: "+i.get())
			e[i.get()!!]=JCheckBoxMenuItem(name)
			(e[i.get()!!] as JCheckBoxMenuItem?)!!.state=items[i.get()!!].second.call(null)
			e[i.get()!!]?.addActionListener { callback.call(null) }
			i.set(i.get()!!+1)
		}
		return e
	}
	
	@JvmStatic
	fun wrap(e:JComponent? , parent:JComponent):JPanel
	{
		val r=JPanel()
		r.preferredSize=parent.preferredSize
		r.add(e)
		return r
	}
	
	@JvmStatic
	fun lr_wrap(left:JComponent? , right:JComponent?):JPanel
	{
		val r=JPanel()
		r.layout=BorderLayout()
		if (left!=null)
		{
			r.add(left , BorderLayout.WEST)
		}
		if (right!=null)
		{
			r.add(right , BorderLayout.EAST)
		}
		return r
	}
	
	@JvmStatic
    fun jpop(
			name:String? , entities:Array<struct_Pair<String? , stl_Callback<Void , Void?>>>?
	):Optional<JPopupMenu>
	{
		if (entities!=null&&entities.isNotEmpty())
		{
			val jp=JPopupMenu(name)
			jp.name=name
			jp.isLightWeightPopupEnabled=
				true // for some reaosn the below with a regular for loop never works, and im too
			// tired to figure out why. so im going to try with a for-each
			/*-------------------------------------------------------------------------------------------------------------- /
      / for (AtomicInteger i = new AtomicInteger(0); i.get() < entities.length; i.accumulateAndGet(1, (a, b) -> a + b)) // do /
      / // not                                                                                                         /
      / // use                                                                                                         /
      / // an                                                                                                          /
      / // AtomicInteger,                                                                                              /
      / // causes                                                                                                      /
      / // this shit                                                                                                   /
      / // to lag tf                                                                                                   /
      / // out (LOL,                                                                                                   /
      / // always use                                                                                                  /
      / // a wrap for                                                                                                  /
      / // these                                                                                                       /
      / // simple                                                                                                      /
      / // operations                                                                                                  /
      / // instead of                                                                                                  /
      / // trying to                                                                                                   /
      / // force with                                                                                                  /
      / // atomicity)                                                                                                  /
      / {                                                                                                              /
      /   System.out.println(new stl_AnsiMake(stl_AnsiColors.BLUE_BG, "[DEBUG] JPOP_" + i));                           /
      /   final JMenuItem item = new JMenuItem(entities[i.get()].first); // name of the component                      /
      /   item.setBorderPainted(false);                                                                                /
      /   item.addActionListener(ev -> {                                                                               /
      /     int l = i.getAcquire() - 1;                                                                                /
      /     final stl_Struct.struct_Pair< String, stl_Callback< Void, Void > > rr = entities[l];                       /
      /     System.out.println(new stl_AnsiMake(new stl_AnsiColors[] { stl_AnsiColors.BOLD, stl_AnsiColors.UNDERLINE }, /
      /         new Object[] {                                                                                         /
      /             "[use_Maker_JPOP#" + item.hashCode() + "] {" + l + "} Got called for: " + rr.first + " with -> "   /
      /                 + rr.second }));                                                                               /
      /     rr.second.call((Void) null);                                                                               /
      /   });                                                                                                          /
      /   jp.add(item);                                                                                                /
      / }                                                                                                              /
      /---------------------------------------------------------------------------------------------------------------*/
			
			// ok for-each works LOL
			for (e in entities)
			{
				val item=JMenuItem(e.first)
				item.isBorderPainted=false
				item.addActionListener { e.second.call(null) }
				jp.add(item)
			}
			return Optional.of(jp)
		}
		return Optional.empty()
	}
	
	@JvmStatic
	fun rev(arr:FloatArray):FloatArray
	{
		for (i in 0 until arr.size/2)
		{
			val temp=arr[i]
			arr[i]=arr[arr.size-1-i]
			arr[arr.size-1-i]=temp
		}
		return arr
	}
	
	@JvmStatic
	fun interpolate_generate(step:Float , start:Float , end:Float , sample:Int):FloatArray
	{
		val t=FloatArray(sample)
		var i=start
		var x=1
		while (x<sample&&i<=end)
		{
			t[x]=t[x-1]+start
			i+=step
			x++
		}
		return t
	}
	
	@JvmStatic
	fun fake_stack_trace(exceptionName:String , numTraces:Int):List<String>
	{
		val stackTraces:MutableList<String> =ArrayList()
		for (i in 0 until numTraces)
		{
			val stackTrace=StringBuilder("$exceptionName: ")
			val numFrames=Random().nextInt(10)+1
			for (j in 0 until numFrames)
			{
				val functionName=fake_ste(5 , 10)
				val moduleName=fake_ste(8 , 15)
				val lineNumber=Random().nextInt(9999)+1
				stackTrace.append("\tat ").append(functionName).append("(").append(moduleName).append(".java:")
					.append(lineNumber).append(")\n")
			}
			stackTraces.add(stackTrace.toString())
		}
		return stackTraces
	}
	
	private fun fake_ste(minLength:Int , maxLength:Int):String
	{
		val length=_1const.RNG.nextInt(maxLength-minLength+1)+minLength
		val name=StringBuilder()
		for (i in 0 until length)
		{
			val randomChar=(_1const.RNG.nextInt(26)+'a'.code).toChar()
			name.append(randomChar)
		}
		return name.toString()
	}
	
	@JvmStatic
    fun equals(e:String , vararg er:String):Boolean
	{
		for (r in er) if (r==e) return true
		return false
	}
	
	@JvmStatic
	fun make(
			name:String , callback:stl_Callback<Boolean , Null_t>
	):struct_Pair<String , stl_Callback<Boolean , Null_t>>
	{
		return struct_Pair(name , callback)
	}
	
	@JvmStatic
    fun make(component:JComponent):stl_Callback<Boolean , Null_t>
	{
		val firstSet_Component=stl_Wrap(true)
		return stl_Callback<Boolean , Null_t> {
			if (java.lang.Boolean.FALSE==firstSet_Component.obj)
			{
				component.isVisible=!component.isVisible
				SwingUtilities.invokeLater { component.repaint() }
			}
			else firstSet_Component.obj(false)
			component.isVisible
		}
	}
}