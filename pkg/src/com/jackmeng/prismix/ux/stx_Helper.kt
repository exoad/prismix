// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import com.jackmeng.prismix._1const
import com.jackmeng.prismix.stl.extend_stl_Colors
import javax.swing.*
import javax.swing.border.Border
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.Point
import java.awt.event.ActionEvent

object stx_Helper
{
	@JvmStatic
    fun quick_btn(name:String? , r:Runnable):JButton
	{
		val t=JButton(name)
		t.isBorderPainted=false
		t.isFocusPainted=false
		t.background=ux_Theme._theme.dominant_awt()
		t.foreground=Color.black
		t.addActionListener { e:ActionEvent?-> r.run() }
		return t
	}
	
	@JvmStatic
    fun from_h(height:Int):Dimension
	{
		return Dimension(0 , height)
	}
	
	fun from_w(width:Int):Dimension
	{
		return Dimension(width , 0)
	}
	
	fun wrap_fill_center(r2:JComponent?):JPanel
	{
		val r=JPanel()
		r.layout=BorderLayout()
		r.add(r2 , BorderLayout.CENTER)
		return r
	}
	
	fun debug_(t:JComponent):JComponent
	{
		t.border=BorderFactory.createLineBorder(Color.magenta)
		return t
	}
	
	fun overlay_db(r:JComponent):JComponent
	{
		return if (java.lang.Boolean.TRUE==_1const.`val`.parse("block_dev_features").get()) stack(
			ui_Whoops(
				20 ,
				4
			).with_size(r.size) , r
		)
		else r
	}
	
	fun stack(top:JComponent , bottom:JComponent):JComponent
	{
		val main=JPanel()
		main.layout=OverlayLayout(main)
		main.preferredSize=
			if (top.width*top.height>bottom.width*bottom.height) top.preferredSize else bottom.preferredSize
		main.add(top)
		main.add(bottom)
		return main
	}
	
	fun bottom_container_AttributesBorder(name:String):Border
	{
		return BorderFactory.createTitledBorder(
			BorderFactory.createEmptyBorder(5 , 0 , 5 , 0) ,
			"<html><strong><p style=\"font-size:12.5px;color:"+extend_stl_Colors.RGBToHex(
				ux_Theme.get().dominant()[0].toInt() ,
				ux_Theme.get().dominant()[1].toInt() ,
				ux_Theme.get().dominant()[2].toInt()
			)+"\">"+name+"</p></strong></strong>"
		)
	}
	
	@JvmStatic
    fun cpick_suggestions_AttributesBorder(name:String):Border
	{ // basically the same thing as bottom_container_AttributesBorder(String)
		return BorderFactory.createTitledBorder(
			BorderFactory.createEmptyBorder(5 , 4 , 5 , 4) ,
			"<html><strong><p style=\"font-size:12.5px;color:"+extend_stl_Colors.RGBToHex(
				ux_Theme.get().dominant()[0].toInt() ,
				ux_Theme.get().dominant()[1].toInt() ,
				ux_Theme.get().dominant()[2].toInt()
			)+"\">"+name+"</p></strong></strong>"
		)
	}
	
	fun cpick_zoomer_jsp(component:JComponent?):JScrollPane // originally made for the gradient rectangle color
	// picker
	{
		val viewport=ui_LazyViewport()
		viewport.view=component
		val jsp=JScrollPane()
		jsp.verticalScrollBarPolicy=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
		jsp.horizontalScrollBarPolicy=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
		jsp.setViewportView(viewport)
		return jsp
	}
	
	@JvmStatic
    fun make_simple(title:String? , action:Runnable):JMenuItem
	{
		val item=JMenuItem(title)
		item.addActionListener { action.run() }
		return item
	}
	
	@JvmStatic
    fun history_palette_btn(clr:Color? , w:Int , h:Int):ui_Tag_Paletted
	{
		val r=ui_Tag_Paletted(clr , true , true)
		r.preferredSize=Dimension(w , h)
		r.minimumSize=r.preferredSize
		r.isRolloverEnabled=false
		r.isFocusPainted=false
		r.isBorderPainted=false
		r.isFocusable=false
		return r
	}
	
	@JvmStatic
    fun within(target:Point , topLeft:Point , dim:Dimension):Boolean
	{
		return (target.x<=topLeft.x+dim.width&&target.x>=topLeft.x&&target.y<=topLeft.y+dim.height&&target.y>=topLeft.y)
	}
	
	fun transform_within(raw:Point? , topLeft:Point? , dim:Dimension?):Point
	{
		return Point()
	}
	
	class Exposed_UISlider:JSlider()
}