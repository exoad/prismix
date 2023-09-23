// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import com.jackmeng.prismix.uwu.fowmat
import com.jackmeng.prismix.ux.stx_Helper.from_h
import com.jackmeng.prismix.jm_Prismix
import com.jackmeng.prismix.user.use_LSys
import com.jackmeng.prismix._1const
import javax.swing.*
import javax.swing.event.ChangeEvent
import java.awt.*
import java.text.MessageFormat

class gui_XConfig:gui("Primsix ~ Config")
{
	private val pane:JPanel
	
	init
	{
		defaultCloseOperation=DISPOSE_ON_CLOSE
		preferredSize=Dimension(710 , 830)
		pane=JPanel()
		pane.preferredSize=preferredSize
		pane.layout=BoxLayout(pane , BoxLayout.Y_AXIS)
		val title=JLabel()
		title.text=fowmat("assets/text/TEXT_configui_title.html" , jm_Prismix._VERSION_.toString()+"")
		title.horizontalAlignment=SwingConstants.LEFT
		pane.add(title)
		val loaded=use_LSys.read_all(_1const.fetcher.file("assets/text/TEXT_config_item_tagtitle.html"))
		_1const.`val`._foreach { key:String? , value:String? , type:String?->
			val jl=JLabel(
				MessageFormat.format(loaded , stx_Map.normalize_key(key) , _1const.`val`.get_description(key))
			)
			jl.horizontalAlignment=SwingConstants.LEFT
			val wrapper=JPanel()
			wrapper.isOpaque=true/*------------------------------------------------------------------------------------------- /
      / wrapper.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 0));                             /
      / wrapper.setBackground(new Color(255, 255, 255, 75)); // 75 ~ 0.3*255 quick math ^w^ oh yea! /
      /--------------------------------------------------------------------------------------------*/
			wrapper.layout=
			BorderLayout()
			wrapper.add(jl , BorderLayout.CENTER)
			when (type)
			{
				stx_Map.Bool->
				{
					val toggle=ui_FatSlider(
						"True | On" ,
						"False | Off" ,
						ux_Theme._theme!!.get_awt("teal") ,
						ux_Theme._theme!!.get_awt("rose") ,
						Color(230 , 230 , 230) ,
						(_1const.`val`.parse(key).get() as Boolean) ,
						10 ,
						16 ,
						2 ,
						3
					)
					toggle.alignmentY=CENTER_ALIGNMENT
					toggle.preferredSize=Dimension(40 , 24)
					val temp_wrap=JPanel()
					temp_wrap.componentOrientation=ComponentOrientation.RIGHT_TO_LEFT
					temp_wrap.layout=GridBagLayout()
					_1const.`val`.add_change_listener(key) {
						toggle.isSelected=(_1const.`val`.parse(key).get() as Boolean)
						null
					} // took me too long lol
					val gbc=GridBagConstraints()
					gbc.anchor=GridBagConstraints.CENTER
					temp_wrap.add(toggle , gbc)
					wrapper.add(
						temp_wrap , BorderLayout.EAST
					)
				}
				stx_Map.StrBound->
				{
					val list=JComboBox<String>()
					for (r in _1const.`val`.get_allowed(key)) list.addItem(r)
					list.selectedItem=_1const.`val`.parse(key).get()
					val temp_wrap1=JPanel()
					temp_wrap1.componentOrientation=ComponentOrientation.RIGHT_TO_LEFT
					temp_wrap1.layout=GridBagLayout()
					val gbc1=GridBagConstraints()
					gbc1.anchor=GridBagConstraints.CENTER
					temp_wrap1.add(list , gbc1)
					wrapper.add(temp_wrap1 , BorderLayout.EAST)
				}
				stx_Map.NumBound->
				{
					val slider=JSlider(SwingConstants.HORIZONTAL)
					slider.minimum=_1const.`val`.get_allowed(key)[0].toDouble().toInt() // implied
					slider.maximum=_1const.`val`.get_allowed(key)[1].toDouble().toInt() // implied
					slider.value=_1const.`val`.parse(key).get().toString().toDouble().toInt() // toString implied here
					
					// for the fact that a
					// numeric value is
					// required
					slider.addChangeListener {
						_1const.`val`.set_property(
							key , slider.value.toString()
						)
					}
					val gbc2=GridBagConstraints()
					gbc2.anchor=GridBagConstraints.CENTER
					val temp_wrap2=JPanel()
					temp_wrap2.componentOrientation=ComponentOrientation.RIGHT_TO_LEFT
					temp_wrap2.layout=GridBagLayout()
					temp_wrap2.add(slider , gbc2)
					wrapper.add(temp_wrap2 , BorderLayout.EAST)
				}
				stx_Map.Any->
				{
					val jt=JTextArea(1 , 0)
					jt.text=_1const.`val`.parse(key).get().toString()
					val temp_wrap3=JPanel()
					temp_wrap3.componentOrientation=ComponentOrientation.RIGHT_TO_LEFT
					temp_wrap3.layout=GridBagLayout()
					val gbc3=GridBagConstraints()
					gbc3.anchor=GridBagConstraints.CENTER
					temp_wrap3.add(jt , gbc3) // random
					
					// size
					// constraints
					// who
					// gives
					// a
					// fuck
					wrapper.add(temp_wrap3 , BorderLayout.EAST)
				}
			}
			pane.add(wrapper)
			pane.add(Box.createVerticalStrut(10))
			pane.add(ui_Line(ux_Theme._theme!!.get_awt("rose") , 4 , true , 2))
		}
		pane.preferredSize=from_h(preferredSize.height)
		val jsp=JScrollPane(pane)
		jsp.preferredSize=pane.preferredSize
		pane.border=BorderFactory.createEmptyBorder(8 , 8 , 8 , 8)
		jsp.verticalScrollBar.background=Color.black
		jsp.verticalScrollBar.unitIncrement=7
		jsp.horizontalScrollBar.background=Color.black
		jsp.border=BorderFactory.createLineBorder(Color.white , 2 , false)
		contentPane.add(jsp)
		jm_Prismix.log("CONF_GUI" , "Loaded the GUI")
	}
}