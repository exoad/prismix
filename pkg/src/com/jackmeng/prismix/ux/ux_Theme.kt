package com.jackmeng.prismix.ux

import com.jackmeng.prismix._colors
import com.jackmeng.prismix.stl.extend_stl_Colors
import com.jackmeng.stl.stl_Colors
import com.jackmeng.prismix.jm_Prismix
import com.jackmeng.ansicolors.jm_Ansi
import com.formdev.flatlaf.intellijthemes.FlatHighContrastIJTheme
import com.formdev.flatlaf.intellijthemes.FlatGrayIJTheme
import com.formdev.flatlaf.intellijthemes.FlatCarbonIJTheme
import com.jackmeng.prismix._1const
import javax.swing.*
import javax.swing.plaf.ColorUIResource
import java.awt.Color
import java.lang.Boolean
import java.util.*

class ux_Theme private constructor()
{
	enum class ThemeType(val code:String)
	{
		GRAY("t_Gray") , DARK("t_Dark") , LIGHT("t_Light");
		
		fun code():String
		{
			return this.code
		}
	}
	
	private var mainTheme:ThemeType?=null
	private lateinit var dominant:FloatArray
	private lateinit var secondary:FloatArray
	
	init
	{ // default theme
		this.dominant(_colors.ROSE)
		this.secondary(_colors.MINT)
		this.theme(ThemeType.LIGHT)
	}
	
	fun theme():ThemeType?
	{
		return mainTheme
	}
	
	fun theme(e:ThemeType?)
	{
		mainTheme=e
		reload()
	}
	
	fun dominant():FloatArray
	{
		return dominant
	}
	
	fun secondary():FloatArray
	{
		return secondary
	}
	
	fun fg():FloatArray
	{
		return extend_stl_Colors.binary_fg_decider(
			if (mainTheme==ThemeType.DARK) floatArrayOf(
				0f ,
				0f ,
				0f
			)
			else if (mainTheme==ThemeType.LIGHT) floatArrayOf(255f , 255f , 255f)
			else floatArrayOf(
				255f/2f ,
				255f/2f ,
				255f/2f
			)
		)
	}
	
	fun fg_awt():Color
	{
		return extend_stl_Colors.awt_remake(fg())
	}
	
	fun secondary_awt():Color
	{
		return extend_stl_Colors.awt_remake(secondary)
	}
	
	fun dominant_awt():Color
	{
		return extend_stl_Colors.awt_remake(dominant)
	}
	
	operator fun get(colorName:String):String?
	{
		var colorName=colorName
		colorName=colorName.lowercase(Locale.getDefault())
		return if (colorCodes.containsKey(colorName)) colorCodes[colorName]
		else extend_stl_Colors.RGBToHex(
			dominant[0].toInt() , dominant[1].toInt() , dominant[2].toInt()
		)
	}
	
	fun get_awt(colorName:String):Color
	{
		return stl_Colors.hexToRGB(get(colorName))
	}
	
	fun dominant(dominant:FloatArray)
	{
		this.dominant=dominant
		jm_Prismix.log(
			"UXTHEME" ,
			jm_Ansi.make().yellow_bg().black()
				.toString("Themer "+this.hashCode()+" made an unsuggested move using **dominant(float[])!")
		)
		SwingUtilities.invokeLater { SwingUtilities.updateComponentTreeUI(__ux._ux.mainUI) }
	}
	
	fun secondary(secondary:FloatArray)
	{
		this.secondary=secondary
		jm_Prismix.log(
			"UXTHEME" ,
			jm_Ansi.make().yellow_bg().black()
				.toString("Themer "+this.hashCode()+" made an unsuggested move using **secondary(float[])!")
		)
		SwingUtilities.invokeLater { SwingUtilities.updateComponentTreeUI(__ux._ux.mainUI) }
	}
	
	fun secondary(secondary:String?)
	{
		this.secondary=extend_stl_Colors.stripHex(secondary)
		SwingUtilities.invokeLater { SwingUtilities.updateComponentTreeUI(__ux._ux.mainUI) }
	}
	
	fun dominant(dominant:String?)
	{
		this.dominant=extend_stl_Colors.stripHex(dominant)
		SwingUtilities.invokeLater { SwingUtilities.updateComponentTreeUI(__ux._ux.mainUI) }
	}
	
	@Synchronized
	fun reload() // reloads the master theme
	{
		SwingUtilities.invokeLater {
			try
			{
				jm_Prismix.log("UXTHEME" , jm_Ansi.make().blue().toString("Refreshing the entire theme"))
				val rsc_dominant_1=ColorUIResource(
					get()!!.dominant_awt()
				)
				UIManager.put("ScrollBar.thumb" , rsc_dominant_1)
				UIManager.put("Scrollbar.pressedThumbColor" , rsc_dominant_1)
				UIManager.put("ScrollBar.hoverThumbColor" , rsc_dominant_1)
				UIManager.put("Component.focusColor" , rsc_dominant_1.darker())
				UIManager.put("Component.focusedBorderColor" , rsc_dominant_1)
				UIManager.put("TabbedPane.underlineColor" , rsc_dominant_1)
				UIManager.put("TabbedPane.selectedColor" , rsc_dominant_1)
				UIManager.put(
					"TabbedPane.selectedBackground" ,
					if (this.theme()==ThemeType.DARK) Color.BLACK else Color.WHITE
				)
				UIManager.setLookAndFeel(if (this.theme()==ThemeType.DARK) FlatHighContrastIJTheme() else if (this.theme()==ThemeType.LIGHT) FlatGrayIJTheme() else FlatCarbonIJTheme())
				SwingUtilities.updateComponentTreeUI(__ux._ux.mainUI)
				__ux._ux.mainUI.pack()
			} catch (e:UnsupportedLookAndFeelException)
			{
				e.printStackTrace()
			}
		}
	}
	
	companion object
	{
		@JvmField
        var _theme:ux_Theme?=null
		@JvmStatic
        fun get():ux_Theme?
		{
			if (_theme==null) _theme=ux_Theme()
			return _theme
		}
		
		@Synchronized
		fun light_preset_1()
		{
			get()!!.dominant(_colors.ROSE)
			get()!!.secondary(_colors.MINT)
			get()!!.theme(ThemeType.LIGHT)
		}
		
		@Synchronized
		fun dark_preset_1()
		{
			get()!!.dominant(_colors.GRAPEFRUIT)
			get()!!.dominant(_colors.MAGENTA)
			get()!!.theme(ThemeType.DARK)
		}
		
		val colorCodes:MutableMap<String , String> = HashMap()
		
		init
		{
			colorCodes["rose"]=_colors.ROSE
			colorCodes["ocean"]=_colors.OCEAN
			colorCodes["blue"]=_colors.BLUE
			colorCodes["cyan"]=_colors.CYAN
			colorCodes["teal"]=_colors.TEAL
			colorCodes["green"]=_colors.GREEN
			colorCodes["yellow"]=_colors.YELLOW
			colorCodes["orange"]=_colors.ORANGE
			colorCodes["red"]=_colors.RED
			colorCodes["magenta"]=_colors.MAGENTA
			colorCodes["purple"]=_colors.PURPLE
		}
		
		@JvmStatic
        fun based_set_rrect(r:JButton)
		{
			r.isBorderPainted=false
			if (Boolean.FALSE==_1const.`val`.parse("containers_rounded").get()) r.border=
				BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color(0 , 0 , 0 , 0)) ,
					BorderFactory.createEmptyBorder(3 , 3 , 3 , 3)
				)
		}
		
		fun make_scrollbar(j:JScrollBar)
		{
			j.unitIncrement=8
			j.isFocusable=false
			j.foreground=_theme!!.dominant_awt()
		}
	}
}