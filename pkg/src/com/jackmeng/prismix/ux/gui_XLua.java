// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.stl.stl_Colors;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jack Meng (exoad)
 */
public final class gui_XLua
		extends
		gui
{
	private static gui_XLua __instance = null;

	public static gui_XLua instance()
	{
		if (__instance == null)
			__instance = new gui_XLua();
		return __instance;
	}

	@Setter @Getter private JEditorPane output;
	/*---------------------------- /
	/ private final JPanel notifs; /
	/-----------------------------*/

	public gui_XLua()
	{
		super("Prismix ~ Lua");
		setName("LUAOP_WINDOW");
		setPreferredSize(new Dimension(740, 560));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		output = new JEditorPane();
		output.setContentType("text/html");

		output.setDocument(new HTMLDocument());
		output.setEditable(false);
		output.setEditorKit(new ux_HTMLPRE());
		output.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		output.setPreferredSize(new Dimension(740, 505));
		output.setText("<html><body>Type \"clear\" to clear output.<br></body></html>");

		JScrollPane jsp_output = new JScrollPane(output);
		jsp_output.setBorder(BorderFactory.createEmptyBorder());
		jsp_output.getVerticalScrollBar().setBackground(Color.black);
		jsp_output.getHorizontalScrollBar().setBackground(Color.black);

		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		bottom.setPreferredSize(new Dimension(740, 555));

		JEditorPane enterField = new JEditorPane();
		enterField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(ux_Theme._theme.fg_awt()),
				BorderFactory.createEmptyBorder(7, 7, 7, 7)));
		enterField.setContentType("text");

		JButton execEnter = new JButton(">");
		execEnter.addActionListener(ev -> {
			if (!enterField.getText().isBlank())
			{
				out(enterField.getText());
				enterField.setText("");
			}
		});

		ux_Theme.based_set_rrect(execEnter);
		execEnter.setBackground(stl_Colors.hexToRGB(ux_Theme._theme.get("rose")));
		execEnter.setForeground(ux_Theme._theme.fg_awt());
		execEnter.setPreferredSize(new Dimension(50, 50));

		bottom.add(enterField, BorderLayout.CENTER);
		bottom.add(execEnter, BorderLayout.EAST);

		bottom.addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override public void componentResized(java.awt.event.ComponentEvent evt)
			{
				Dimension size = bottom.getSize();
				int buttonWidth = (int) (size.width * 0.1);
				execEnter.setSize(new Dimension(buttonWidth, buttonWidth));
				enterField.setPreferredSize(new Dimension(size.width - buttonWidth, size.height));
				bottom.revalidate();
			}
		});

		JSplitPane master_jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		master_jsp.setBorder(BorderFactory.createEmptyBorder());
		master_jsp.setDividerLocation(470);

		master_jsp.setTopComponent(output);
		master_jsp.setBottomComponent(bottom);

		getContentPane().add(master_jsp);

		attach_vnotifier(getContentPane());
		/*----------------------------------------------------------------------------------- /
		/                                                                                     /
		/ notifs = new JPanel();                                                              /
		/ notifs.setOpaque(false);                                                            /
		/ notifs.setLayout(new ux_WrapLayout(FlowLayout.CENTER, 5, 10));                      /
		/ notifs.setBounds(0, getPreferredSize().width - 300, getPreferredSize().width, 200); /
		/                                                                                     /
		/ JLayeredPane wrapper1 = new JLayeredPane();                                         /
		/ wrapper1.setLayout(new OverlayLayout(wrapper1));                                    /
		/ wrapper1.add(notifs, 2, 1);                                                         /
		/ wrapper1.add(getContentPane(), 1, 2);                                               /
		/                                                                                     /
		/ setLayeredPane(wrapper1);                                                           /
		/------------------------------------------------------------------------------------*/
	}

	void out(String content) // content preferably should be formatted with html for best viewing
	{

		if (content.equalsIgnoreCase("clear"))
		{
			output.setText("<html><body></body></html>");
			deploy_notif("<html><strong>Cleared</strong></html>", stl_Colors.hexToRGB(ux_Theme._theme.get("yellow")));
		}
		else
		{
			boolean erred = false;
			String message = "";
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try
			{
				Globals globals = JsePlatform.standardGlobals();
				globals.STDOUT = new PrintStream(baos, true, StandardCharsets.UTF_8);
				LuaValue outp = globals.load(content);
				outp.call();

			} catch (Throwable e)
			{
				if (!(e instanceof LuaError))
					e.printStackTrace();
				else
				{
					erred = true;
					message = ((LuaError) e).getMessage();
				}
			}

			try
			{
				float[] a = extend_stl_Colors
						.binary_fg_decider(extend_stl_Colors.stripHex(ux_Theme._theme.get(erred ? "red" : "teal")));
				((HTMLEditorKit) output.getEditorKit()).insertHTML((HTMLDocument) output.getDocument(),
						output.getDocument().getLength(),
						"""
									<p style="color:%s;background-color:%s">
									<strong>
										&gt;&gt;&gt; %s %s
									</strong>
									</p>
									<br />
									%s
								""".formatted(extend_stl_Colors.RGBToHex((int) a[0], (int) a[1], (int) a[2]),
								ux_Theme._theme.get(erred ? "red" : "teal"),
								content.replace("\n", "<br>"), erred ? "<pre>  ^^^^^ ERROR!</pre>" : "",
								erred ? "[Error]: <strong>" + message + "</strong>"
										: baos.toString(StandardCharsets.UTF_8)),
						0, 0, null);
			} catch (BadLocationException | IOException e)
			{
				// shitty error wont occur unless we fucked up the gui layout (not my fault)
				e.printStackTrace();
			}
			deploy_notif("<html><strong>" + (erred ? "Error!" : "Ok") + "</strong></html>",
					stl_Colors.hexToRGB(ux_Theme._theme.get(erred ? "red" : "teal")));

		}
	}

	public String final_history() // should be used for like logging purposes especially logging to a file for
																// future reference
	{
		return output.getText();
	}
}