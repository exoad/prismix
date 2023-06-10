// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.use_Maker;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.stl.stl_AnsiColors;
import com.jackmeng.stl.stl_AnsiMake;
import com.jackmeng.stl.stl_Callback;
import com.jackmeng.stl.stl_Listener;
import com.jackmeng.stl.stl_Colors;
import com.jackmeng.stl.stl_Struct;
import com.jackmeng.stl.stl_Struct.struct_Pair;

// Implementations of color pickers visually
// All of these should be able to dispatch events to the color queue found in _1const
public final class ui_ColorPicker
{
  private ui_ColorPicker()
  {
  }
  /*----------------------------------------------------------------------------------------- /
  /                                                                                           /
  / @SuppressWarnings("unchecked") public static JComponent attach_asis(JComponent e)         /
  / {                                                                                         /
  /   if (e instanceof stl_Callback) // finalized types at compile time are so fucking stupid /
  /     _1const.COLOR_ENQ.add((stl_Listener< struct_Pair< Color, Boolean > >) e);             /
  /   return e;                                                                               /
  / }                                                                                         /
  /------------------------------------------------------------------------------------------*/

  public static final class CPick_GenericDisp
      extends JPanel
      implements
      stl_Listener< stl_Struct.struct_Pair< Color, Boolean > >
  {

    public CPick_GenericDisp()
    {
      this.setFocusable(true);
    }

    @Override public void paintComponent(final Graphics g)
    {
      super.paintComponent(g);
      use_Maker.db_timed(() -> {
        final Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2.drawImage(
            extend_stl_Colors.cpick_gradient2(this.getParent().getPreferredSize().height,
                _1const.last_color()),
            null, 0,
            0);
        g2.dispose();
      });
    }

    @Override public Void call(final struct_Pair< Color, Boolean > arg0)
    {
      if (this.isVisible() || this.isFocusOwner())
      {
        System.out.println("[CPick_Debug" + this.hashCode() + "] Picks up a valid color call from the Color queue!");
        this.repaint(75L);
      }
      return (Void) null;
    }

  }

  // Creates a Gradient of White-Black-Color rectangular with sliders to adjust
  // the colors only
  // This should be the default option for the color picker
  public static final class CPick_GradRect
      extends JPanel
      implements
      stl_Listener< stl_Struct.struct_Pair< Color, Boolean > > // Optional to be added but the color picker should
                                                               // be always attached to the currently color picker
  {

    @Override public Void call(final struct_Pair< Color, Boolean > arg0)
    {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'call'");
    }

  }

  // For creating a list of potential "suggestions" for a color using a table.
  // Shades, complementary, etc..
  // This should be a secondary option
  @SuppressWarnings("unchecked") public static final class CPick_SuggestionsList
      extends JPanel
      implements stl_Listener< stl_Struct.struct_Pair< Color, Boolean > >
  {

    private static JPanel acquire_defpane(final String name)
    {
      final JPanel jp = new JPanel() {
        @Override public Component add(final Component e)
        {
          if (e instanceof final JComponent r)
            r.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
          return super.add(e);
        }
      };
      jp.setBorder(ux_Helper.cpick_suggestions_AttributesBorder(name));
      jp.setLayout(new GridBagLayout());

      return jp;
    }

    transient java.util.List< JButton > tones_List, tint_List, shades_List, complements_List;
    transient ActionListener shades_Listeners = ev -> {
      final JButton e = ((JButton) ev.getSource()); // no additional checks needed, we shld be assured that this
                                                    // listener is
      // only added to a JButton. additional checks can cause additional
      // optimization strains on the jvm

      if (e.getText().length() > 0)
      {
        System.out.println(
            new stl_AnsiMake(stl_AnsiColors.YELLOW_TXT, "[CPick_Suggestions] Actions acquiring a proper menu handle"));
        SwingUtilities.invokeLater(() -> {
          final Optional< JPopupMenu > r = use_Maker.jpop(e.getText(), new stl_Struct.struct_Pair[] {
              stl_Struct.make_pair("Copy to clipboard", (stl_Callback< Void, Void >) nil -> {
                Toolkit.getDefaultToolkit().getSystemClipboard()
                    .setContents(new java.awt.datatransfer.StringSelection(e.getText()), null);
                System.out.println(new stl_AnsiMake(stl_AnsiColors.GREEN_TXT,
                    "[CPick_Suggestions] {1} Copied the target color to the clipboard: " + e.getText()));
                return (Void) null;
              }),
              stl_Struct.make_pair("Inspect color", (stl_Callback< Void, Void >) nil -> {
                _1const.COLOR_ENQ.dispatch(stl_Struct.make_pair(stl_Colors.hexToRGB(e.getText()), false));
                System.out.println(new stl_AnsiMake(stl_AnsiColors.GREEN_TXT,
                    "[CPick_Suggestions] {1} Dispatched the target color to the global inspect queue: " + e.getText()));
                return (Void) null;
              }),
              stl_Struct.make_pair("Inspect & Copy", (stl_Callback< Void, Void >) nil -> {
                Toolkit.getDefaultToolkit().getSystemClipboard()
                    .setContents(new java.awt.datatransfer.StringSelection(e.getText()), null);
                System.out.println(new stl_AnsiMake(stl_AnsiColors.GREEN_TXT,
                    "[CPick_Suggestions] {2} Copied the target color to the clipboard: " + e.getText()));
                _1const.COLOR_ENQ.dispatch(stl_Struct.make_pair(stl_Colors.hexToRGB(e.getText()), false));
                System.out.println(new stl_AnsiMake(stl_AnsiColors.GREEN_TXT,
                    "[CPick_Suggestions] {2} Dispatched the target color to the global inspect queue: " + e.getText()));
                return (Void) null;
              })
          });
          System.out.println(
              new stl_AnsiMake(stl_AnsiColors.BLUE_TXT, "[CPick_Suggestions] Action received the desired state!"));
          r.ifPresent(x -> {
            System.out.println("[CPick_Suggestions] Rendering the final popup menu to the screen!");
            ((javax.swing.JPopupMenu) x).show(this, e.getX(), e.getY() + 30);
          });
        });
      }
    };

    JPanel shades_Tones, shades_Tint, shades_Shades, shades_Complements;
    final int tones_cols = 10, tones_rows = 15, tint_cols = 10, tint_rows = 15, shades_cols = 10, shades_rows = 15,
        complement_cols = 2, complement_rows = 3;

    public CPick_SuggestionsList()
    {
      final JScrollPane masterScroll = new JScrollPane();
      masterScroll.getHorizontalScrollBar().setUnitIncrement(5);
      masterScroll.setBorder(BorderFactory.createEmptyBorder());

      final ui_LazyViewport mainViewport = new ui_LazyViewport();
      final JPanel contentWrapper = new JPanel();
      contentWrapper.setBorder(BorderFactory.createEmptyBorder());
      contentWrapper.setLayout(new BoxLayout(contentWrapper, BoxLayout.X_AXIS));

      this.shades_Tones = CPick_SuggestionsList.acquire_defpane("Tones");
      this.shades_Tint = CPick_SuggestionsList.acquire_defpane("Tints");
      this.shades_Shades = CPick_SuggestionsList.acquire_defpane("Shades");
      this.shades_Complements = CPick_SuggestionsList.acquire_defpane("Complementary");

      this.tones_List = new ArrayList<>(); // i originally used the bound initialCapacity param, but that is such a
      // scam, it doesnt actually work, so you have to use .add() in the loop, im
      // dumb or is java dumb. i also thought 10*10 was 0 cuz Java kept sayign the
      // size was zero LOL
      this.tint_List = new ArrayList<>();
      this.shades_List = new ArrayList<>();
      this.complements_List = new ArrayList<>();

      for (int i = 0; i < this.complement_cols * this.complement_rows; i++)
      {
        /*------------------------------------------------------------------------------------------------------------ /
        / System.out.println(                                                                                          /
        /     new stl_AnsiMake(stl_AnsiColors.MAGENTA_TXT, "[CPick_Suggestions] Creating a new ComplementsButton[" + i + "]")); /
        /-------------------------------------------------------------------------------------------------------------*/
        final JButton r = new JButton("");
        r.setFocusPainted(false);
        r.setBorderPainted(false);
        r.setFocusable(false);
        r.setForeground(Color.WHITE);
        r.addActionListener(this.shades_Listeners);
        r.setRolloverEnabled(false); // technically if setFocusable -> false, then this should not be needed, but ok
        this.complements_List.add(r);
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = i % this.tones_cols;
        gbc.gridy = i / this.tones_rows;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        this.shades_Tones.add(this.complements_List.get(i), gbc);
      }

      for (int i = 0; i < this.tones_cols * this.tones_rows; i++)
      {
        /*------------------------------------------------------------------------------------------------------------ /
        / System.out.println(                                                                                          /
        /     new stl_AnsiMake(stl_AnsiColors.MAGENTA_TXT, "[CPick_Suggestions] Creating a new ToneButton[" + i + "]")); /
        /-------------------------------------------------------------------------------------------------------------*/
        final JButton r = new JButton("");
        r.setFocusPainted(false);
        r.setBorderPainted(false);
        r.setFocusable(false);
        r.setForeground(Color.WHITE);
        r.addActionListener(this.shades_Listeners);
        r.setRolloverEnabled(false); // technically if setFocusable -> false, then this should not be needed, but ok
        this.tones_List.add(r);
        /*------------------------------------------------------------------------------------- /
        / if (_1const.SOFT_DEBUG)                                                               /
        /   tones_List.get(i).setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 2)); /
        /--------------------------------------------------------------------------------------*/
        /*-------------------------------------------------- /
        / tones_List.get(i).setForeground(Color.WHITE); /
        / tones_List.get(i).setBackground(Color.BLACK); /
        /---------------------------------------------------*/
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = i % this.tones_cols;
        gbc.gridy = i / this.tones_rows;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        this.shades_Tones.add(this.tones_List.get(i), gbc);
      }
      for (int i = 0; i < this.tint_cols * this.tint_rows; i++)
      {
        /*------------------------------------------------------------------------------------------------------------ /
        / System.out.println(                                                                                          /
        /     new stl_AnsiMake(stl_AnsiColors.MAGENTA_TXT, "[CPick_Suggestions] Creating a new TintButton[" + i + "]")); /
        /-------------------------------------------------------------------------------------------------------------*/
        final JButton r = new JButton("");
        r.setRolloverEnabled(false);
        r.setFocusPainted(false);
        r.setBorderPainted(false);
        r.setFocusable(false);
        r.setForeground(Color.WHITE);
        r.addActionListener(this.shades_Listeners);
        this.tint_List.add(r);
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = i % this.tint_cols;
        gbc.gridy = i / this.tint_rows;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        this.shades_Tint.add(this.tint_List.get(i), gbc);
      }

      for (int i = 0; i < this.shades_cols * this.shades_rows; i++)
      {
        /*------------------------------------------------------------------------------------------------------------ /
        / System.out.println(                                                                                          /
        /     new stl_AnsiMake(stl_AnsiColors.MAGENTA_TXT, "[CPick_Suggestions] Creating a new ShadesButton[" + i + "]")); /
        /-------------------------------------------------------------------------------------------------------------*/
        final JButton r = new JButton("");
        r.setFocusPainted(false);
        r.setBorderPainted(false);
        r.setFocusable(false);
        r.setForeground(Color.WHITE);
        r.setRolloverEnabled(false);
        r.addActionListener(this.shades_Listeners);
        this.shades_List.add(r);
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = i % this.tint_cols;
        gbc.gridy = i / this.tint_rows;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        this.shades_Shades.add(this.shades_List.get(i), gbc);
      }

      System.out.println(new stl_AnsiMake(stl_AnsiColors.BLUE_BG,
          "[CPick_Suggestions] Registered " + this.shades_cols * this.shades_rows + " ShadesButtons | Registered "
              + this.tones_cols * this.tones_rows + " TonesButtons | Registered " + this.tint_cols * this.tint_rows
              + " TintsButtons"));

      contentWrapper.add(this.shades_Tint);
      contentWrapper.add(this.shades_Shades);
      contentWrapper.add(this.shades_Tones);

      mainViewport.setView(contentWrapper);
      masterScroll.setViewport(mainViewport);

      this.setLayout(new BorderLayout());
      this.add(masterScroll, BorderLayout.CENTER);
    }

    @Override public Void call(final struct_Pair< Color, Boolean > arg0)
    {
      if (Boolean.FALSE.equals(arg0.second))
      {
        System.out.println("[CPick_Suggestions] Called to revalidate the current colors");
        boolean use_sorted = "true".equalsIgnoreCase(_1const.val.get_value("suggestions-sorted")),
            use_l2d = "true".equalsIgnoreCase(_1const.val.get_value("suggestions_sorted_light_to_dark"));
        use_Maker.db_timed(() -> {
          if (this.shades_Tones.isVisible())
          {
            final float[][] gen_tones = extend_stl_Colors.tones(extend_stl_Colors.awt_strip_rgba(arg0.first),
                this.tones_cols * this.tones_rows);
            if (use_sorted)
            {
              if(use_l2d) extend_stl_Colors.sort_l2d(gen_tones);
              else extend_stl_Colors.sort_d2l(gen_tones);
            }
            final Color[] remadeTones = new Color[this.tones_cols * this.tones_rows];
            final String[] hexTones = new String[this.tones_cols * this.tones_rows];
            _render.unify_(this.shades_Shades, nil -> {
              for (int i = 0; i < this.tones_cols * this.tones_rows; i++)
              {
                final JButton button = this.tones_List.get(i);
                remadeTones[i] = extend_stl_Colors.awt_remake(gen_tones[i]);
                button.setBackground(remadeTones[i]);
                button.setForeground(extend_stl_Colors.awt_remake(extend_stl_Colors.binary_fg_decider(gen_tones[i])));
                hexTones[i] = extend_stl_Colors.RGBToHex((int) gen_tones[i][0], (int) gen_tones[i][1],
                    (int) gen_tones[i][2]);
                button.setText(hexTones[i]);
                button.setToolTipText(hexTones[i]);
              }
              return (Void) null;
            });

          }

          if (this.shades_Complements.isVisible())
          {
            float[][] gen_complements = extend_stl_Colors.complementaries(extend_stl_Colors.awt_strip_rgba(arg0.first),
                this.complement_cols * this.complement_rows);
            if (use_sorted)
            {
              for (float[] e : gen_complements)
              {
                Arrays.sort(e);
                e = use_Maker.rev(e);
              }
            }

          }

          if (this.shades_Tint.isVisible())
          {
            final float[][] gen_tints = extend_stl_Colors.tints(extend_stl_Colors.awt_strip_rgba(arg0.first),
                this.tint_cols * this.tint_rows);
            if (use_sorted)
              extend_stl_Colors.sort_l2d(gen_tints);
            final Color[] remadeTints = new Color[this.tint_cols * this.tint_rows];
            final String[] hexTints = new String[this.tint_cols * this.tint_rows];
            _render.unify_(this.shades_Tint, nil -> {
              for (int i = 0; i < this.tint_cols * this.tint_rows; i++)
              {
                final JButton button = this.tint_List.get(i);
                remadeTints[i] = extend_stl_Colors.awt_remake(gen_tints[i]);
                button.setBackground(remadeTints[i]);
                button.setForeground(extend_stl_Colors.awt_remake(extend_stl_Colors.binary_fg_decider(gen_tints[i])));
                hexTints[i] = extend_stl_Colors.RGBToHex((int) gen_tints[i][0], (int) gen_tints[i][1],
                    (int) gen_tints[i][2]);
                button.setText(hexTints[i]);
                button.setToolTipText(hexTints[i]);
              }
              return (Void) null;
            });
          }

          if (this.shades_Shades.isVisible())
          {
            final float[][] gen_shades = extend_stl_Colors.shades(extend_stl_Colors.awt_strip_rgba(arg0.first),
                this.shades_cols * this.shades_rows);
            if (use_sorted)
              for (float[] e : gen_shades)
              {
                Arrays.sort(e);
                e = use_Maker.rev(e);
              }
            final Color[] remadeShades = new Color[this.shades_cols * this.shades_rows];
            final String[] hexShades = new String[this.shades_cols * this.shades_rows];
            _render.unify_(this.shades_Shades, nil -> {
              for (int i = 0; i < this.shades_cols * this.shades_rows; i++)
              {
                final JButton button = this.shades_List.get(i);
                remadeShades[i] = extend_stl_Colors.awt_remake(gen_shades[i]);
                button.setBackground(remadeShades[i]);
                button.setForeground(extend_stl_Colors.awt_remake(extend_stl_Colors.binary_fg_decider(gen_shades[i])));
                hexShades[i] = extend_stl_Colors.RGBToHex((int) gen_shades[i][0], (int) gen_shades[i][1],
                    (int) gen_shades[i][2]);
                button.setText(hexShades[i]);
                button.setToolTipText(hexShades[i]);
              }
              return (Void) null;
            });

          }
        });
      }
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
}
