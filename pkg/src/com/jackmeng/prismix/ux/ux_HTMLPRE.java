// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

public class ux_HTMLPRE extends HTMLEditorKit
{

	transient ViewFactory viewFactory = new HTMLFactory() {

		@Override public View create(Element elem)
		{
			AttributeSet attrs = elem.getAttributes();
			Object elementName = attrs.getAttribute(AbstractDocument.ElementNameAttribute);
			Object o = (elementName != null) ? null : attrs.getAttribute(StyleConstants.NameAttribute);
			if (o instanceof HTML.Tag kind)
				if (kind == HTML.Tag.IMPLIED || kind == HTML.Tag.PRE)
					return new javax.swing.text.html.ParagraphView(elem);
			return super.create(elem);
		}
	};

	@Override public ViewFactory getViewFactory()
	{
		return this.viewFactory;
	}
}