// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.use_Maker;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.stl.stl_AnsiColors;
import com.jackmeng.stl.stl_AnsiMake;
import com.jackmeng.stl.stl_Listener;
import com.jackmeng.stl.stl_Struct;
import com.jackmeng.stl.stl_Struct.struct_Pair;

import java.awt.event.ActionListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

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
      setFocusable(true);
      setBorder(new ui_RoundBorder(10));
    }

    @Override public void paintComponent(Graphics g)
    {
      super.paintComponent(g);
      use_Maker.db_timed(() -> {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2.drawImage(
            extend_stl_Colors.cpick_gradient2(getParent().getPreferredSize().height,
                _1const.last_color()),
            null, 0,
            0);
        g2.dispose();
      });
    }

    @Override public Void call(struct_Pair< Color, Boolean > arg0)
    {
      if (this.isVisible() || this.isFocusOwner())
      {
        System.out.println("[CPick_Debug" + this.hashCode() + "] Picks up a valid color call from the Color queue!");
        repaint(75L);
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

    @Override public Void call(struct_Pair< Color, Boolean > arg0)
    {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'call'");
    }

  }

  // For creating a list of potential "suggestions" for a color using a table.
  // Shades, complementary, etc..
  // This should be a secondary option
  public static final class CPick_SuggestionsList
      extends JPanel
      implements stl_Listener< stl_Struct.struct_Pair< Color, Boolean > >
  {

    private static JPanel acquire_defpane(String name)
    {
      JPanel jp = new JPanel() {
        @Override public Component add(Component e)
        {
          if (e instanceof JComponent r)
            r.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
          return super.add(e);
        }
      };
      jp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5), name));
      jp.setLayout(new GridBagLayout());

      return jp;
    }

    transient java.util.List< JButton > tones_List, tint_List, shades_List;
    transient ActionListener shades_Listeners = ev -> {
      JButton e = ((JButton) ev.getSource()); // no additional checks needed

      if (e.getText().length() > 0)
      {
        Toolkit.getDefaultToolkit().getSystemClipboard()
            .setContents(new java.awt.datatransfer.StringSelection(e.getText()), null);
        System.out.println(new stl_AnsiMake(stl_AnsiColors.GREEN_TXT,
            "[CPick_Suggestions] Copied the target color to the clipboard: " + e.getText()));
      }
    };
    JPanel shades_Tones, shades_Tint, shades_Shades;
    final int tones_cols = 5, tones_rows = 9, tint_cols = 10, tint_rows = 15, shades_cols = 10, shades_rows = 15;

    public CPick_SuggestionsList()
    {
      JScrollPane masterScroll = new JScrollPane();
      masterScroll.setBorder(BorderFactory.createEmptyBorder());

      ui_LazyViewport mainViewport = new ui_LazyViewport();
      JPanel contentWrapper = new JPanel();
      contentWrapper.setBorder(BorderFactory.createEmptyBorder());
      contentWrapper.setLayout(new BoxLayout(contentWrapper, BoxLayout.X_AXIS));

      shades_Tones = acquire_defpane("<html><strong>Tones</strong></html>");
      shades_Tint = acquire_defpane("<html><strong>Tints</strong></html>");
      shades_Shades = acquire_defpane("<html><strong>Shades</strong></html>");

      tones_List = new ArrayList<>(); // i originally used the bound initialCapacity param, but that is such a
      // scam, it doesnt actually work, so you have to use .add() in the loop, im
      // dumb or is java dumb. i also thought 10*10 was 0 cuz Java kept sayign the
      // size was zero LOL
      tint_List = new ArrayList<>();
      shades_List = new ArrayList<>();

      for (int i = 0; i < tones_cols * tones_rows; i++)
      {
        System.out.println(
            new stl_AnsiMake(stl_AnsiColors.MAGENTA_TXT, "[CPick_Suggestions] Creating a new ToneButton[" + i + "]"));
        JButton r = new JButton("");
        r.setFocusPainted(false);
        r.setBorderPainted(false);
        r.setFocusable(false);
        r.setForeground(Color.WHITE);
        r.addActionListener(shades_Listeners);
        r.setRolloverEnabled(false); // technically if setFocusable -> false, then this should not be needed, but ok
        tones_List.add(r);
        /*------------------------------------------------------------------------------------- /
        / if (_1const.SOFT_DEBUG)                                                               /
        /   tones_List.get(i).setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 2)); /
        /--------------------------------------------------------------------------------------*/
        /*-------------------------------------------------- /
        / tones_List.get(i).setForeground(Color.WHITE); /
        / tones_List.get(i).setBackground(Color.BLACK); /
        /---------------------------------------------------*/
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = i % tones_cols;
        gbc.gridy = i / tones_rows;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        shades_Tones.add(tones_List.get(i), gbc);
      }
      for (int i = 0; i < tint_cols * tint_rows; i++)
      {
        System.out.println(
            new stl_AnsiMake(stl_AnsiColors.MAGENTA_TXT, "[CPick_Suggestions] Creating a new TintButton[" + i + "]"));
        JButton r = new JButton("");
        r.setRolloverEnabled(false);
        r.setFocusPainted(false);
        r.setBorderPainted(false);
        r.setFocusable(false);
        r.setForeground(Color.WHITE);
        r.addActionListener(shades_Listeners);
        tint_List.add(r);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = i % tint_cols;
        gbc.gridy = i / tint_rows;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        shades_Tint.add(tint_List.get(i), gbc);
      }

      for (int i = 0; i < shades_cols * shades_rows; i++)
      {
        System.out.println(
            new stl_AnsiMake(stl_AnsiColors.MAGENTA_TXT, "[CPick_Suggestions] Creating a new ShadesButton[" + i + "]"));
        JButton r = new JButton("");
        r.setFocusPainted(false);
        r.setBorderPainted(false);
        r.setFocusable(false);
        r.setForeground(Color.WHITE);
        r.setRolloverEnabled(false);
        r.addActionListener(shades_Listeners);
        shades_List.add(r);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = i % tint_cols;
        gbc.gridy = i / tint_rows;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        shades_Shades.add(shades_List.get(i), gbc);
      }

      contentWrapper.add(shades_Tint);
      contentWrapper.add(shades_Shades);
      contentWrapper.add(shades_Tones);

      mainViewport.setView(contentWrapper);
      masterScroll.setViewport(mainViewport);

      setLayout(new BorderLayout());
      add(masterScroll, BorderLayout.CENTER);
    }

    @Override public Void call(struct_Pair< Color, Boolean > arg0)
    {
      if (Boolean.FALSE.equals(arg0.second))
      {
        System.out.println("[CPick_Suggestions] Called to revalidate the current colors");
        use_Maker.db_timed(() -> {
          if (shades_Tones.isVisible())
          {
            float[][] gen_tones = extend_stl_Colors.tones(extend_stl_Colors.awt_strip_rgba(arg0.first),
                tones_cols * tones_rows);
            if (ux.PROPERTY_USE_SORTED_COLOR_SUGGESTIONS.get())
              for (float[] e : gen_tones)
              {
                Arrays.sort(e);
                e = use_Maker.rev(e);
              }
            Color[] remadeTones = new Color[tones_cols * tones_rows];
            String[] hexTones = new String[tones_cols * tones_rows];

            for (int i = 0; i < tones_cols * tones_rows; i++)
            {
              JButton button = tones_List.get(i);
              remadeTones[i] = extend_stl_Colors.awt_remake(gen_tones[i]);
              button.setBackground(remadeTones[i]);
              button.setForeground(extend_stl_Colors.awt_remake(extend_stl_Colors.binary_fg_decider(gen_tones[i])));
              hexTones[i] = extend_stl_Colors.RGBToHex((int) gen_tones[i][0], (int) gen_tones[i][1],
                  (int) gen_tones[i][2]);
              button.setText(hexTones[i]);
              button.setToolTipText(hexTones[i]);
            }
          }

          if (shades_Tint.isVisible())
          {
            float[][] gen_tints = extend_stl_Colors.tints(extend_stl_Colors.awt_strip_rgba(arg0.first),
                tint_cols * tint_rows);
            if (ux.PROPERTY_USE_SORTED_COLOR_SUGGESTIONS.get())
              for (float[] e : gen_tints)
              {
                Arrays.sort(e);
                e = use_Maker.rev(e);
              }
            Color[] remadeTints = new Color[tint_cols * tint_rows];
            String[] hexTints = new String[tint_cols * tint_rows];

            for (int i = 0; i < tint_cols * tint_rows; i++)
            {
              JButton button = tint_List.get(i);
              remadeTints[i] = extend_stl_Colors.awt_remake(gen_tints[i]);
              button.setBackground(remadeTints[i]);
              button.setForeground(extend_stl_Colors.awt_remake(extend_stl_Colors.binary_fg_decider(gen_tints[i])));
              hexTints[i] = extend_stl_Colors.RGBToHex((int) gen_tints[i][0], (int) gen_tints[i][1],
                  (int) gen_tints[i][2]);
              button.setText(hexTints[i]);
              button.setToolTipText(hexTints[i]);
            }
          }

          if (shades_Shades.isVisible())
          {
            float[][] gen_shades = extend_stl_Colors.shades(extend_stl_Colors.awt_strip_rgba(arg0.first),
                shades_cols * shades_rows);
            if (ux.PROPERTY_USE_SORTED_COLOR_SUGGESTIONS.get())
              for (float[] e : gen_shades)
              {
                Arrays.sort(e);
                e = use_Maker.rev(e);
              }
            Color[] remadeShades = new Color[shades_cols * shades_rows];
            String[] hexShades = new String[shades_cols * shades_rows];
            for (int i = 0; i < shades_cols * shades_rows; i++)
            {
              JButton button = shades_List.get(i);
              remadeShades[i] = extend_stl_Colors.awt_remake(gen_shades[i]);
              button.setBackground(remadeShades[i]);
              button.setForeground(extend_stl_Colors.awt_remake(extend_stl_Colors.binary_fg_decider(gen_shades[i])));
              hexShades[i] = extend_stl_Colors.RGBToHex((int) gen_shades[i][0], (int) gen_shades[i][1],
                  (int) gen_shades[i][2]);
              button.setText(hexShades[i]);
              button.setToolTipText(hexShades[i]);
            }
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
