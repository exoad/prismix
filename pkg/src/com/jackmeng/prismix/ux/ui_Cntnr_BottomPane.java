package com.jackmeng.prismix.ux;

import static com.jackmeng.prismix.jm_Prismix.log;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicLong;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import com.jackmeng.ansicolors.jm_Ansi;
import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.prismix.user.use_LSys;
import com.jackmeng.stl.stl_Colors;
import com.jackmeng.stl.stl_Listener;
import com.jackmeng.stl.stl_Struct;
import com.jackmeng.stl.stl_Ware;
import com.jackmeng.stl.stl_Struct.struct_Pair;

import lombok.Getter;

public class ui_Cntnr_BottomPane
		extends JSplitPane
		implements
		stl_Listener< stl_Struct.struct_Pair< Color, Boolean > >
{

	// we don't care about implementation for History as a controls component
	JPanel listHistory, controls;
	@Getter HashSet< String > history;

	static int HISTORY_BUTTON_HEIGHT = 35, HISTORY_BUTTON_WIDTH = 115;

	@SuppressWarnings("unchecked") public ui_Cntnr_BottomPane()
	{
		this.setPreferredSize(new Dimension(_2const.WIDTH, _2const.HEIGHT / 3));
		this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		this.setDividerLocation(_2const.WIDTH / 2 + _2const.WIDTH / 6 + _2const.WIDTH / 12);
		this.setBorder(BorderFactory.createEmptyBorder());

		this.listHistory = new JPanel();
		this.listHistory.setLayout(new ux_WrapLayout(FlowLayout.LEADING));

		this.controls = new JPanel();
		this.controls.setLayout(new GridLayout(0, 1));

		this.controls.add(stx_Helper.quick_btn("New color", gui_XAddColor::request));
		this.controls
				.add(Boolean.TRUE.equals(_1const.val.parse("queued_random_color").get()) ? new ui_R_ColorBtn()
						: stx_Helper.quick_btn("Random Color",
								() -> _1const.COLOR_ENQ.dispatch(stl_Struct.make_pair(extend_stl_Colors.awt_random_Color(), false))));
		this.controls.add(stx_Helper.quick_btn("Random Screen Color",
				() -> _1const.COLOR_ENQ.dispatch(stl_Struct.make_pair(stl_Ware.screen_colorAt_Rnd().get(), false))));
		this.controls.add(stx_Helper.quick_btn("Pick from screen",
				() -> {
				}));
		this.controls.add(stx_Helper.quick_btn("Clear History",
				new gui_XConfirm(use_LSys.read_all(_1const.fetcher.file("assets/text/TEXT_clear_history.html")),
						"Clear History?", () -> {
						}, this::clear_history, "Yes", "No")));

		JScrollPane history_JSP = new JScrollPane();
		history_JSP.setBorder(BorderFactory.createEmptyBorder());
		history_JSP.getVerticalScrollBar().setUnitIncrement(8);

		ui_LazyViewport history_Viewport = new ui_LazyViewport();
		history_Viewport.setView(listHistory);

		history_JSP.setViewportView(history_Viewport);

		JScrollPane controls_jsp = new JScrollPane();
		controls_jsp.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		controls_jsp.setViewportView(ui_LazyViewport.make(this.controls));

		this.setRightComponent(controls_jsp);
		this.setLeftComponent(history_JSP);

		_1const.COLOR_ENQ.add(this);

		_1const.shutdown_hook(() -> {
			log("BOT_G_HISTORY", "Finalizing and saving the history...");
			use_LSys.write_obj("state/palette_history.pal", getHistory());
		});

		this.history = (HashSet< String >) use_LSys.read_obj("state/palette_history.pal", new HashSet<>());

		if (history.size() > 0)
		{
			AtomicLong i = new AtomicLong(0);

			history.forEach(x -> {
				ui_Tag_Paletted jb = stx_Helper.history_palette_btn(stl_Colors.hexToRGB(x), HISTORY_BUTTON_WIDTH,
						HISTORY_BUTTON_HEIGHT);
				jb.setName(x);
				ux_Theme.based_set_rrect(jb);
				listHistory.add(jb, 0);
				i.set(i.get() + 1);
			});

			_1const.COLOR_ENQ.dispatch(
					stl_Struct.make_pair(stl_Colors.hexToRGB(history.toArray(new String[0])[history.size() - 1]), false));

			log("BOT_G_HISTORY", "Loaded " + i.getAcquire() + " history swatches from cold storage.");

			SwingUtilities.invokeLater(() -> {
				listHistory.revalidate();
				listHistory.repaint(75L);
			});
		}
		else log("BOT_G_HISTORY",
				jm_Ansi.make().blue_bg().white().toString("Did not load from cold storage for historical swatches"));
	}

	public synchronized void clear_history() // ! EXPORT
	{
		if (history.size() > 0)
		{
			history.clear();
			listHistory.removeAll();
			listHistory.repaint(100L);
		}
		/*------------------------------------------------------------------------------------------------------- /
		/ if (listHistory.getComponents().length > 0)                                                             /
		/ {                                                                                                       /
		/   log("BOT_G_HISTORY",                                                                                  /
		/       jm_Ansi.make().blue().toString("Cleared the current history, requested by user!") + "Timestamp: " /
		/           + System.currentTimeMillis()                                                                  /
		/           + " To remove: " + listHistory.getComponents().length);                                       /
		/   SwingUtilities.invokeLater(() -> {                                                                    /
		/     for(int i = 0; i < listHistory.getComponentCount(); i++) listHistory.remove(i);                     /
		/     listHistory.repaint(75L);                                                                           /
		/   });                                                                                                   /
		/ }                                                                                                       /
		/ else log("BOT_H_HISTORY", "Ignored clear request: history is empty!");                                  /
		/--------------------------------------------------------------------------------------------------------*/
	}

	@Override public Void call(struct_Pair< Color, Boolean > arg0)
	{
		String colorHex = extend_stl_Colors.RGBToHex(arg0.first.getRed(), arg0.first.getGreen(), arg0.first.getBlue());

		if (Boolean.FALSE.equals(arg0.second) && !history.contains(colorHex))
		{
			// make a popupmenu for this so we can have proper controls for this button
			// preferably also make a global one and then spawn a unique one for just this
			log("BOT_G_HISTORY", "Logs a new color to history: " + arg0.first);
			ui_Tag_Paletted jb = stx_Helper.history_palette_btn(arg0.first, HISTORY_BUTTON_WIDTH, HISTORY_BUTTON_HEIGHT);
			jb.setName(colorHex);
			ux_Theme.based_set_rrect(jb);

			// Remove duplicate if it exists
			for (Component component : listHistory.getComponents())
			{
				if (component instanceof ui_Tag_Paletted && colorHex.equals(component.getName()))
				{
					listHistory.remove(component);
					log("BOT_G_HISTORY", jm_Ansi.make().blue().underline()
							.toString("Found " + colorHex + " to be a duplicate. Popping it back."));
					break;
				}
			}

			listHistory.add(jb, 0);

			history.add(colorHex);

			SwingUtilities.invokeLater(() -> {
				listHistory.revalidate();
				listHistory.repaint(75L);
			});
		}

		return (Void) null;
	}

}