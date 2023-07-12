package com.jackmeng.prismix.ux;

import static com.jackmeng.prismix.jm_Prismix.log;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.jackmeng.ansicolors.jm_Ansi;
import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.use_Maker;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.stl.stl_Listener;
import com.jackmeng.stl.stl_Struct;
import com.jackmeng.stl.stl_Struct.struct_Pair;

// For creating a list of potential "suggestions" for a color using a table.
// Shades, complementary, etc..
// This should be a secondary option
public final class ui_CPick_SugList
    extends JPanel
    implements stl_Listener< stl_Struct.struct_Pair< Color, Boolean > >
{

  private static JPanel acquire_defpane(final String name)
  {
    final JPanel jp = new JPanel();
    jp.setBorder(stx_Helper.cpick_suggestions_AttributesBorder(name));
    jp.setLayout(new GridBagLayout());

    return jp;
  }
  /*----------------------------------------------------------------------------------------------------------------- /
  / transient ActionListener shades_Listeners = ev -> {                                                               /
  /   final JButton e = ((JButton) ev.getSource()); // no additional checks needed, we shld be assured that this      /
  /                                                 // listener is                                                    /
  /   // only added to a JButton. additional checks can cause additional                                              /
  /   // optimization strains on the jvm                                                                              /
  /                                                                                                                   /
  /   if (e.getText().length() > 0)                                                                                   /
  /   {                                                                                                               /
  /     log("CPickSGTN", "Actions acquiring a proper menu handle");                                                   /
  /     SwingUtilities.invokeLater(() -> {                                                                            /
  /       final Optional< JPopupMenu > r = use_Maker.jpop(e.getText(), new stl_Struct.struct_Pair[] {                 /
  /           stl_Struct.make_pair("Copy to clipboard", (stl_Callback< Void, Void >) nil -> {                         /
  /             Toolkit.getDefaultToolkit().getSystemClipboard()                                                      /
  /                 .setContents(new java.awt.datatransfer.StringSelection(e.getText()), null);                       /
  /             log("CPickSGTN",                                                                                      /
  /                 jm_Ansi.make().green().toString("{1} Copied the target color to the clipboard: ") + e.getText()); /
  /             return (Void) null;                                                                                   /
  /           }),                                                                                                     /
  /           stl_Struct.make_pair("Inspect color", (stl_Callback< Void, Void >) nil -> {                             /
  /             _1const.COLOR_ENQ.dispatch(stl_Struct.make_pair(stl_Colors.hexToRGB(e.getText()), false));            /
  /             log("CPickSGTN",                                                                                      /
  /                 jm_Ansi.make().green().toString("{2} Dispatched the target color to the global inspect queue: ")  /
  /                     + e.getText());                                                                               /
  /             return (Void) null;                                                                                   /
  /           }),                                                                                                     /
  /           stl_Struct.make_pair("Inspect & Copy", (stl_Callback< Void, Void >) nil -> {                            /
  /             Toolkit.getDefaultToolkit().getSystemClipboard()                                                      /
  /                 .setContents(new java.awt.datatransfer.StringSelection(e.getText()), null);                       /
  /             _1const.COLOR_ENQ.dispatch(stl_Struct.make_pair(stl_Colors.hexToRGB(e.getText()), false));            /
  /                                                                                                                   /
  /             log("CPickSGTN", jm_Ansi.make().green().toString(                                                     /
  /                 "{3} Dispatched & Copied the target color: ")                                                     /
  /                 + e.getText());                                                                                   /
  /             return (Void) null;                                                                                   /
  /           })                                                                                                      /
  /       });                                                                                                         /
  /       log("CPickSGTN", "Action received the desired state!");                                                     /
  /       r.ifPresent(x -> {                                                                                          /
  /         java.awt.Point pt = new Point(_1const.MOUSE_LOCATION.first, _1const.MOUSE_LOCATION.second);               /
  /         log("CPickSGTN", "Rendering the final popup menu to the screen at: " + pt.x + ","                         /
  /             + pt.y);                                                                                              /
  /         ((javax.swing.JPopupMenu) x).show(this, pt.x, pt.y); // !needs fix                                        /
  /       });                                                                                                         /
  /     });                                                                                                           /
  /   }                                                                                                               /
  / };                                                                                                                /
  /------------------------------------------------------------------------------------------------------------------*/

  class Base_Palette
  {
    private java.util.List< ui_Tag > tags;
    public final JPanel component;
    public final int rows, cols;

    public Base_Palette(String name, int rows, int cols)
    {
      this.rows = rows;
      this.cols = cols;
      this.tags = new ArrayList<>(rows * cols);
      this.component = ui_CPick_SugList.acquire_defpane(name);
      for (int i = 0; i < this.rows * this.cols; i++)
      {
        ui_Tag r = new ui_Tag();
        r.setFocusPainted(false);
        r.setBorderPainted(false);
        r.setFocusable(false);
        r.setForeground(Color.WHITE);
        r.setRolloverEnabled(false);
        this.tags.add(r);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = i % this.cols;
        gbc.gridy = i / this.rows;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.2;
        gbc.weighty = 0.4;
        if ("compact".equals(_1const.val.parse("compact_suggestions_layout").get()))
          gbc.gridwidth = 69;
        else
          gbc.ipadx = 15;

        gbc.insets = new Insets(2, 2, 2, 2);

        this.component.add(this.tags.get(i), gbc);
      }
    }

    public synchronized void _update(float[][] e)
    {
      for (int i = 0; i < this.cols * this.rows; i++)
        this.tags.get(i).sync(extend_stl_Colors.awt_remake(e[i]));
    }

  }

  final transient Base_Palette tones, tints, shades, complementary, mix;

  public ui_CPick_SugList()
  {
    final JScrollPane masterScroll = new JScrollPane();
    masterScroll.getHorizontalScrollBar().setUnitIncrement(5);
    masterScroll.setBorder(BorderFactory.createEmptyBorder());

    final ui_LazyViewport mainViewport = new ui_LazyViewport();
    final JPanel contentWrapper = new JPanel();
    contentWrapper.setBorder(BorderFactory.createEmptyBorder());
    contentWrapper.setLayout(new BoxLayout(contentWrapper,
        "vertical".equals(_1const.val.parse("compact_suggestions_layout").get()) ? BoxLayout.Y_AXIS
            : BoxLayout.X_AXIS));
    if ("compact".equals(_1const.val.parse("compact_suggestions_layout").get()))
    {
      tones = new Base_Palette("Tones", 1, 10);
      tints = new Base_Palette("Tints", 1, 10);
      shades = new Base_Palette("Shades", 1, 10);
      mix = new Base_Palette("Mixed", 1, 7);
      complementary = new Base_Palette("Complementary", 1, 4);
      contentWrapper.add(tints.component);
      contentWrapper.add(shades.component);
      contentWrapper.add(tones.component);
      contentWrapper.add(complementary.component);
      contentWrapper.add(mix.component);
    }
    else
    {
      tones = new Base_Palette("Tones", 15, 10);
      tints = new Base_Palette("Tints", 15, 10);
      shades = new Base_Palette("Shades", 15, 10);
      mix = new Base_Palette("Mixed", 15, 10);
      complementary = new Base_Palette("Complementary", 5, 3);
      contentWrapper.add(tints.component);
      contentWrapper.add(shades.component);
      contentWrapper.add(tones.component);
      contentWrapper.add(mix.component);
      contentWrapper.add(complementary.component);
    }

    mainViewport.setView(contentWrapper);
    masterScroll.setViewport(mainViewport);

    this.setLayout(new BorderLayout());
    this.add(masterScroll, BorderLayout.CENTER);

    setName("ui_CPick_SuggestionsList");
    addComponentListener(ux_Listen.VISIBILITY());
  }

  @Override public Void call(final struct_Pair< Color, Boolean > arg0)
  {
    if (Boolean.FALSE.equals(arg0.second)) // second arg to make sure that this does not paint
      // when the user is on a different tab
      /*------------------------------------------------------------------------------------------------------------------ /
      / {                                                                                                                  /
      /   log("CPickSGTN", jm_Ansi.make().blue().toString("Called to revalidate the current colors"));                     /
      /   boolean use_sorted = "true".equalsIgnoreCase(_1const.val.get_value("suggestions_sorted")),                       /
      /       use_l2d = "true".equalsIgnoreCase(_1const.val.get_value("suggestions_sort_light_to_dark"));                  /
      /   use_Maker.db_timed(() -> {                                                                                       /
      /     if (this.shades_Tones.isVisible())                                                                             /
      /     {                                                                                                              /
      /       final float[][] gen_tones = extend_stl_Colors.tones(extend_stl_Colors.awt_strip_rgba(arg0.first),            /
      /           this.tones_cols * this.tones_rows);                                                                      /
      /       if (use_sorted)                                                                                              /
      /       {                                                                                                            /
      /         if (use_l2d)                                                                                               /
      /           extend_stl_Colors.sort_l2d(gen_tones);                                                                   /
      /         else extend_stl_Colors.sort_d2l(gen_tones);                                                                /
      /       }                                                                                                            /
      /       final Color[] remadeTones = new Color[this.tones_cols * this.tones_rows];                                    /
      /       final String[] hexTones = new String[this.tones_cols * this.tones_rows];                                     /
      /       _render.unify_(this.shades_Shades, nil -> {                                                                  /
      /         for (int i = 0; i < this.tones_cols * this.tones_rows; i++)                                                /
      /         {                                                                                                          /
      /           final JButton button = this.tones_List.get(i);                                                           /
      /           remadeTones[i] = extend_stl_Colors.awt_remake(gen_tones[i]);                                             /
      /           button.setBackground(remadeTones[i]);                                                                    /
      /           button.setForeground(extend_stl_Colors.awt_remake(extend_stl_Colors.binary_fg_decider(gen_tones[i])));   /
      /           hexTones[i] = extend_stl_Colors.RGBToHex((int) gen_tones[i][0], (int) gen_tones[i][1],                   /
      /               (int) gen_tones[i][2]);                                                                              /
      /           button.setText(hexTones[i]);                                                                             /
      /           button.setBorderPainted(true);                                                                           /
      /           button.setToolTipText(hexTones[i]);                                                                      /
      /         }                                                                                                          /
      /         return (Void) null;                                                                                        /
      /       });                                                                                                          /
      /                                                                                                                    /
      /     }                                                                                                              /
      /                                                                                                                    /
      /     if (this.shades_Complements.isVisible())                                                                       /
      /     {                                                                                                              /
      /       float[][] gen_complements = extend_stl_Colors.complementaries(extend_stl_Colors.awt_strip_rgba(arg0.first),  /
      /           this.complement_cols * this.complement_rows);                                                            /
      /       if (use_sorted)                                                                                              /
      /       {                                                                                                            /
      /         if (use_l2d)                                                                                               /
      /           extend_stl_Colors.sort_l2d(gen_complements);                                                             /
      /         else extend_stl_Colors.sort_d2l(gen_complements);                                                          /
      /       }                                                                                                            /
      /       final Color[] remadeComplements = new Color[this.complement_cols * this.complement_rows];                    /
      /       final String[] hexComplements = new String[this.complement_cols * this.complement_rows];                     /
      /       _render.unify_(this.shades_Tint, nil -> {                                                                    /
      /         for (int i = 0; i < this.complement_cols * this.complement_rows; i++)                                      /
      /         {                                                                                                          /
      /           final JButton button = this.complements_List.get(i);                                                     /
      /           remadeComplements[i] = extend_stl_Colors.awt_remake(gen_complements[i]);                                 /
      /           button.setBackground(remadeComplements[i]);                                                              /
      /           button.setForeground(                                                                                    /
      /               extend_stl_Colors.awt_remake(extend_stl_Colors.binary_fg_decider(gen_complements[i])));              /
      /           hexComplements[i] = extend_stl_Colors.RGBToHex((int) gen_complements[i][0], (int) gen_complements[i][1], /
      /               (int) gen_complements[i][2]);                                                                        /
      /           button.setText(hexComplements[i]);                                                                       /
      /           button.setBorderPainted(true);                                                                           /                                    /
      /           button.setToolTipText(hexComplements[i]);                                                                /
      /         }                                                                                                          /
      /         return (Void) null;                                                                                        /
      /       });                                                                                                          /
      /                                                                                                                    /
      /     }                                                                                                              /
      /                                                                                                                    /
      /     if (this.shades_Tint.isVisible())                                                                              /
      /     {                                                                                                              /
      /       final float[][] gen_tints = extend_stl_Colors.tints(extend_stl_Colors.awt_strip_rgba(arg0.first),            /
      /           this.tint_cols * this.tint_rows);                                                                        /
      /       if (use_sorted)                                                                                              /
      /       {                                                                                                            /
      /         if (use_l2d)                                                                                               /
      /           extend_stl_Colors.sort_l2d(gen_tints);                                                                   /
      /         else extend_stl_Colors.sort_d2l(gen_tints);                                                                /
      /       }                                                                                                            /
      /       final Color[] remadeTints = new Color[this.tint_cols * this.tint_rows];                                      /
      /       final String[] hexTints = new String[this.tint_cols * this.tint_rows];                                       /
      /       _render.unify_(this.shades_Tint, nil -> {                                                                    /
      /         for (int i = 0; i < this.tint_cols * this.tint_rows; i++)                                                  /
      /         {                                                                                                          /
      /           final JButton button = this.tint_List.get(i);                                                            /
      /           remadeTints[i] = extend_stl_Colors.awt_remake(gen_tints[i]);                                             /
      /           button.setBackground(remadeTints[i]);                                                                    /
      /           button.setForeground(extend_stl_Colors.awt_remake(extend_stl_Colors.binary_fg_decider(gen_tints[i])));   /
      /           hexTints[i] = extend_stl_Colors.RGBToHex((int) gen_tints[i][0], (int) gen_tints[i][1],                   /
      /               (int) gen_tints[i][2]);                                                                              /
      /           button.setText(hexTints[i]);                                                                             /
      /           button.setBorderPainted(true);                                                                           //
      /           button.setToolTipText(hexTints[i]);                                                                      /
      /         }                                                                                                          /
      /         return (Void) null;                                                                                        /
      /       });                                                                                                          /
      /     }                                                                                                              /
      /                                                                                                                    /
      /     if (this.shades_Shades.isVisible())                                                                            /
      /     {                                                                                                              /
      /       final float[][] gen_shades = extend_stl_Colors.shades(extend_stl_Colors.awt_strip_rgba(arg0.first),          /
      /           this.shades_cols * this.shades_rows);                                                                    /
      /       if (use_sorted)                                                                                              /
      /       {                                                                                                            /
      /         if (use_l2d)                                                                                               /
      /           extend_stl_Colors.sort_l2d(gen_shades);                                                                  /
      /         else extend_stl_Colors.sort_d2l(gen_shades);                                                               /
      /       }                                                                                                            /
      /       final Color[] remadeShades = new Color[this.shades_cols * this.shades_rows];                                 /
      /       final String[] hexShades = new String[this.shades_cols * this.shades_rows];                                  /
      /       _render.unify_(this.shades_Shades, nil -> {                                                                  /
      /         for (int i = 0; i < this.shades_cols * this.shades_rows; i++)                                              /
      /         {                                                                                                          /
      /           final JButton button = this.shades_List.get(i);                                                          /
      /           remadeShades[i] = extend_stl_Colors.awt_remake(gen_shades[i]);                                           /
      /           button.setBackground(remadeShades[i]);                                                                   /
      /           button.setForeground(extend_stl_Colors.awt_remake(extend_stl_Colors.binary_fg_decider(gen_shades[i])));  /
      /           hexShades[i] = extend_stl_Colors.RGBToHex((int) gen_shades[i][0], (int) gen_shades[i][1],                /
      /               (int) gen_shades[i][2]);                                                                             /
      /           button.setText(hexShades[i]);                                                                            /
      /           button.setBorderPainted(true);                                                                           /                                   /
      /           button.setToolTipText(hexShades[i]);                                                                     /
      /         }                                                                                                          /
      /         return (Void) null;                                                                                        /
      /       });                                                                                                          /
      /                                                                                                                    /
      /     }                                                                                                              /
      /   });                                                                                                              /
      / }                                                                                                                  /
      /-------------------------------------------------------------------------------------------------------------------*/
      log("CPickSGTN", jm_Ansi.make().blue().toString("Called to revalidate the current colors"));
    boolean use_sorted = "true".equalsIgnoreCase(_1const.val.get_value("suggestions_sorted")),
        use_l2d = "true".equalsIgnoreCase(_1const.val.get_value("suggestions_sort_light_to_dark"));
    use_Maker.db_timed(() -> {
      float[][] gen_tones = extend_stl_Colors.tones(extend_stl_Colors.awt_strip_rgba(arg0.first),
          this.tones.cols * this.tones.rows);
      float[][] gen_complements = extend_stl_Colors.complementaries(extend_stl_Colors.awt_strip_rgba(arg0.first),
          this.complementary.cols * this.complementary.rows);
      float[][] gen_tints = extend_stl_Colors.tints(extend_stl_Colors.awt_strip_rgba(arg0.first),
          this.tints.rows * this.tints.cols);
      float[][] gen_shades = extend_stl_Colors.shades(extend_stl_Colors.awt_strip_rgba(arg0.first),
          this.shades.cols * this.shades.rows);
      float[][] gen_mix = extend_stl_Colors.awt_ran_gen(arg0.first, this.mix.cols * this.mix.rows);

      if (use_sorted)
      {
        if (use_l2d)
        {
          extend_stl_Colors.sort_l2d(gen_tones);
          extend_stl_Colors.sort_l2d(gen_complements);
          extend_stl_Colors.sort_l2d(gen_tints);
          extend_stl_Colors.sort_l2d(gen_shades);
          extend_stl_Colors.sort_l2d(gen_mix);
        }
        else
        {
          extend_stl_Colors.sort_d2l(gen_tones);
          extend_stl_Colors.sort_d2l(gen_complements);
          extend_stl_Colors.sort_d2l(gen_tints);
          extend_stl_Colors.sort_d2l(gen_shades);
          extend_stl_Colors.sort_d2l(gen_mix);
        }
      }
      // unified rendering doesn't work here, causes tab compositing to fuck up.
      SwingUtilities.invokeLater(() -> {
        this.tints._update(gen_tints);
        this.shades._update(gen_shades);
        this.complementary._update(gen_complements);
        this.tones._update(gen_tones);
        this.mix._update(gen_mix);
      });

    });
    return null;
  }
  // defunct version (works but not good enuf for performance)
  /*---------------------------------------------------------------------------------------------------------------- /
  / @Override public Void call(struct_Pair< Color, Boolean > arg0)                                                   /
  / {                                                                                                                /
  /   if (Boolean.FALSE.equals(arg0.second))                                                                         /
  /   {                                                                                                              /
  /     System.out.println("[CPick_Suggestions] Called to revalidate the current colors");                           /
  /                                                                                                                  /
  /     if (shades_Tones.isVisible())                                                                                /
  /     {                                                                                                            /
  /       float[][] gen_tones = extend_stl_Colors.tones(extend_stl_Colors.awt_strip_rgba(arg0.first),                /
  /           tones_cols * tones_rows);                                                                              /
  /       SwingUtilities.invokeLater(() -> {                                                                         /
  /         for (int i = 0; i < tones_cols * tones_rows; i++)                                                        /
  /         {                                                                                                        /
  /           JButton button = tones_List.get(i);                                                                    /
  /           Color c = extend_stl_Colors.awt_remake(gen_tones[i]);                                                  /
  /           button.setBackground(c);                                                                               /
  /           button.setForeground(extend_stl_Colors.awt_remake(extend_stl_Colors.binary_fg_decider(gen_tones[i]))); /
  /           button.setText(                                                                                        /
  /               extend_stl_Colors.RGBToHex((int) gen_tones[i][0], (int) gen_tones[i][1], (int) gen_tones[i][2]));  /
  /           button.setToolTipText(button.getText());                                                               /
  /         }                                                                                                        /
  /       });                                                                                                        /
  /     }                                                                                                            /
  /                                                                                                                  /
  /     if (shades_Tint.isVisible())                                                                                 /
  /     {                                                                                                            /
  /       float[][] gen_tints = extend_stl_Colors.tints(extend_stl_Colors.awt_strip_rgba(arg0.first),                /
  /           tint_cols * tint_rows);                                                                                /
  /       SwingUtilities.invokeLater(() -> {                                                                         /
  /         for (int i = 0; i < tint_cols * tint_rows; i++)                                                          /
  /         {                                                                                                        /
  /           JButton button = tint_List.get(i);                                                                     /
  /           Color c = extend_stl_Colors.awt_remake(gen_tints[i]);                                                  /
  /           button.setBackground(c);                                                                               /
  /           button.setForeground(extend_stl_Colors.awt_remake(extend_stl_Colors.binary_fg_decider(gen_tints[i]))); /
  /           button.setText(                                                                                        /
  /               extend_stl_Colors.RGBToHex((int) gen_tints[i][0], (int) gen_tints[i][1], (int) gen_tints[i][2]));  /
  /           button.setToolTipText(button.getText());                                                               /
  /         }                                                                                                        /
  /       });                                                                                                        /
  /     }                                                                                                            /
  /   }                                                                                                              /
  /   return null;                                                                                                   /
  / }                                                                                                                /
  /                                                                                                                  /
  /-----------------------------------------------------------------------------------------------------------------*/
}