package com.jackmeng.prismix.ux

import com.jackmeng.prismix.ux.stx_HTMLDebuff
import javax.swing.text.html.HTMLEditorKit
import javax.swing.text.html.parser.ParserDelegator
import java.io.IOException
import java.io.Reader
import java.io.StringReader
import java.lang.Boolean

class stx_HTMLDebuff private constructor():HTMLEditorKit.ParserCallback()
{
	var s:StringBuffer?=null
	fun parse(`in`:Reader?)
	{
		s=StringBuffer()
		val delegator=ParserDelegator()
		try
		{
			delegator.parse(`in` , this , Boolean.TRUE)
		} catch (ignore:IOException)
		{
		}
	}
	
	override fun handleText(text:CharArray , pos:Int)
	{
		s!!.append(text)
		s!!.append("\n")
	}
	
	fun clean()
	{
		s!!.delete(0 , s!!.length-1)
	}
	
	fun text():String
	{
		return s.toString()
	}
	
	companion object
	{
		private var v:stx_HTMLDebuff?=null
		fun parse(content:String?):String
		{
			if (v==null) v=stx_HTMLDebuff()
			v!!.parse(StringReader(content))
			val temp=v!!.text()
			v!!.clean()
			return temp
		}
	}
}