// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.stl.stl_Colors;

import static com.jackmeng.prismix.jm_Prismix.*;

public final class gui_Lua
		extends
		gui
{
	private static gui_Lua __instance = null;

	public static gui_Lua instance()
	{
		if (__instance == null)
			__instance = new gui_Lua();
		return __instance;
	}

	private final JEditorPane output;
	private final JPanel notifs;

	public gui_Lua()
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

		notifs = new JPanel();
		notifs.setOpaque(false);
		notifs.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		notifs.setBounds(0, 0, notifs.getPreferredSize().width, notifs.getPreferredSize().height);


		JLayeredPane wrapper1 = new JLayeredPane();
		wrapper1.setLayout(new OverlayLayout(wrapper1));
		wrapper1.add(notifs, 2, 1);
		wrapper1.add(master_jsp, 1, 2);

		add(wrapper1);

	}

	void out(String content) // content preferably should be formatted with html for best viewing
	{
		if (content.equalsIgnoreCase("clear"))
			output.setText("<html><body></body></html>");
		else
		{
			boolean erred = false;
			try
			{

				Globals globals = JsePlatform.standardGlobals();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				globals.STDOUT = new PrintStream(baos, true, "utf-8");
				LuaValue outp = globals.load(content);
				outp.call();
				String r = new String(baos.toByteArray(), StandardCharsets.UTF_8);

				((HTMLEditorKit) output.getEditorKit()).insertHTML((HTMLDocument) output.getDocument(),
						output.getDocument().getLength(),
						r, 0, 0, null);
			} catch (Throwable e)
			{
				if (!(e instanceof LuaError))
					e.printStackTrace();
				else
					erred = true;
			}
			if (erred)
				deploy("<html><strong>Error!</strong></html>", stl_Colors.hexToRGB(ux_Theme._theme.get("rose")));
			else
				deploy("<html><strong>OK</strong></html>", stl_Colors.hexToRGB(ux_Theme._theme.get("mint")));

		}

	}

	void deploy(String content, Color c)
	{

		JLabel jl = new JLabel(content);
		jl.setHorizontalAlignment(SwingConstants.CENTER);
		jl.setVerticalAlignment(SwingConstants.CENTER);
		jl.setForeground(
				Color.white);
		ui_Faded faded = new ui_Faded(0.1F, 2300L, 70L) {
			@Override public void paintComponent(Graphics g)
			{

				super.paintComponent(g);

				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(c);
				g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);

				Graphics2D g2dLabel = (Graphics2D) g.create();
				g2dLabel.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				jl.paintComponents(g2dLabel);
				g2dLabel.dispose();
			}
		};

		faded.setPreferredSize(new Dimension(jl.getPreferredSize().width + 64, jl.getPreferredSize().height + 10));
		faded.setLayout(new BorderLayout());
		faded.setBounds((getWidth() / 2) - (faded.getPreferredSize().width / 2), output.getHeight() - 40,
				faded.getPreferredSize().width,
				faded.getPreferredSize().height);
		faded.setOpaque(true);
		faded.revalidate();
		notifs.add(faded);
		SwingUtilities.invokeLater(faded);

		debug(jl.isVisible());

		log("GLUA", "Deployed notif: " + content + " @ " + faded.getX() + "," + faded.getY());
	}

	public String final_history() // should be used for like logging purposes especially logging to a file for
																// future reference
	{
		return output.getText();
	}
}