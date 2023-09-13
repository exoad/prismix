package com.jackmeng.prismix.ux;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class stx_HTMLDebuff extends HTMLEditorKit.ParserCallback
{
	private stx_HTMLDebuff()
	{
	}

	StringBuffer s;

	public void parse(Reader in)
	{
		s = new StringBuffer();

		ParserDelegator delegator = new ParserDelegator();

		try
		{
			delegator.parse(in, this, Boolean.TRUE);
		} catch (IOException ignore)
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

	public String text()
	{
		return s.toString();
	}
	private static stx_HTMLDebuff v = null;
	public static String parse(String content)
	{
		if(v == null)
			v = new stx_HTMLDebuff();
		v.parse(new StringReader(content));
		String temp = v.text();
		v.clean();
		return temp;
	}
}
