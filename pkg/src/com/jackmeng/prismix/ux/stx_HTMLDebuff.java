// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class stx_HTMLDebuff
		extends
		HTMLEditorKit.ParserCallback
{
	StringBuffer s;

	private stx_HTMLDebuff()
	{
	}

	private static stx_HTMLDebuff instance = null;

	public static String parse(String r)
	{
		if (instance == null)
			instance = new stx_HTMLDebuff();
		instance.parse(new StringReader(r));
		String str = instance.getText();
		instance.clean();
		return str;
	}

	public void parse(Reader in)
	{
		s = new StringBuffer();
		ParserDelegator delegator = new ParserDelegator();
		try
		{
			delegator.parse(in, this, Boolean.TRUE);
		} catch (IOException e)
		{
		}
	}

	@Override public void handleText(char[] text, int pos)
	{
		s.append(text);
		s.append("\n");
	}

	public void clean()
	{
		s.delete(0, s.length() - 1);
	}

	public String getText()
	{
		return s.toString();
	}
}