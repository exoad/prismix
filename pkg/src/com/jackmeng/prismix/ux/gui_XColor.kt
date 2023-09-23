// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import com.jackmeng.prismix._1const
import com.jackmeng.prismix.jm_Prismix
import com.jackmeng.prismix.stl.extend_stl_Colors
import com.jackmeng.prismix.ux.model.h_Graff
import com.jackmeng.prismix.ux.model.h_GraphPoint
import com.jackmeng.prismix.ux.stx_Helper.quick_btn
import com.jackmeng.prismix.ux.ui_Graff.DrawAxis
import com.jackmeng.prismix.ux.ui_Graff.DrawType
import com.jackmeng.prismix.ux.ui_LazyViewport.Companion.make
import com.jackmeng.stl.stl_Colors
import javax.swing.*
import lombok.Getter
import lombok.Setter
import java.awt.*
import java.awt.datatransfer.StringSelection
import java.awt.geom.Rectangle2D
import java.util.*

class gui_XColor(@field:Setter @field:Getter private val color:Color):gui(
	"Inspect: "+extend_stl_Colors.RGBToHex(
		color.red , color.green , color.blue
	)
)
{
	@Getter
	private val jsp:JSplitPane
	
	init
	{
		val r=extend_stl_Colors.RGBToHex(color.red , color.green , color.blue)
		preferredSize=Dimension(670 , 350)
		jsp=JSplitPane(JSplitPane.HORIZONTAL_SPLIT)
		jsp.border=BorderFactory.createEmptyBorder(2 , 2 , 2 , 2)
		jsp.setDividerLocation(0.5)
		val image:JPanel=object:JPanel()
		{
			public override fun paintComponent(g:Graphics)
			{
				val g2=g as Graphics2D
				g2.clearRect(0 , 0 , width , height)
				g2.color=color
				g2.fill(Rectangle2D.Float(0F , 0F , width.toFloat() , height.toFloat()))
				g2.dispose()
			}
		}
		image.preferredSize=Dimension(preferredSize.width/3 , preferredSize.height)
		val textLabel=JLabel("<html><strong style>$r</strong></html>")
		textLabel.horizontalAlignment=SwingConstants.CENTER
		textLabel.alignmentX=CENTER_ALIGNMENT
		textLabel.foreground=
			extend_stl_Colors.awt_remake(extend_stl_Colors.binary_fg_decider(extend_stl_Colors.stripHex(r)))
		val imageOverlay=JPanel()
		imageOverlay.border=BorderFactory.createEmptyBorder(10 , 10 , 10 , 10)
		imageOverlay.layout=OverlayLayout(imageOverlay)
		imageOverlay.add(textLabel)
		imageOverlay.add(image)
		val rightInfoPane=JScrollPane()
		ux_Theme.make_scrollbar(
			rightInfoPane.verticalScrollBar
		)
		ux_Theme.make_scrollbar(
			rightInfoPane.horizontalScrollBar
		)
		rightInfoPane.border=BorderFactory.createEmptyBorder()
		rightInfoPane.preferredSize=Dimension(preferredSize.width/2 , preferredSize.height)
		val information=JPanel()
		information.border=BorderFactory.createEmptyBorder(10 , 5 , 4 , 5)
		information.layout=BoxLayout(information , BoxLayout.Y_AXIS)
		val hsv=extend_stl_Colors.rgbToHsv(extend_stl_Colors.awt_strip_rgba(color))
		val cmyk=extend_stl_Colors.rgbToCmyk(extend_stl_Colors.awt_strip_rgba(color))
		val hsl=extend_stl_Colors.rgbToHsl(extend_stl_Colors.awt_strip_rgba(color))
		val ciexyz_temp=color.getComponents(FloatArray(4))
		val ciexyz=color.colorSpace.toCIEXYZ(ciexyz_temp)
		jm_Prismix.debug(Arrays.toString(ciexyz))
		information.add(_m("Hex" , r))
		information.add(_m("ARGB" , stl_Colors.RGBAtoHex(color.alpha , color.red , color.green , color.blue)+"</html>"))
		information.add(_m("RGB" , color.rgb))
		information.add(
			_m(
				"<code style=\"background-color:"+extend_stl_Colors.RGBToHex(
					255 , 0 , 0
				)+";color:"+extend_stl_Colors.RGBToHex(255 , 0 , 0)+"\">[]</code> Red [R]" ,
				color.red.toString()+"&emsp;&emsp;<em>("+color.red/255f+"%)</em>"
			)
		)
		information.add(
			_m(
				"<code style=\"background-color:"+extend_stl_Colors.RGBToHex(
					0 , 255 , 0
				)+";color:"+extend_stl_Colors.RGBToHex(0 , 255 , 0)+"\">[]</code> Green [G]" ,
				color.red.toString()+"&emsp;&emsp;<em>("+color.green/255f+"%)</em>"
			)
		)
		information.add(
			_m(
				"<code style=\"background-color:"+extend_stl_Colors.RGBToHex(
					0 , 0 , 255
				)+";color:"+extend_stl_Colors.RGBToHex(0 , 0 , 255)+"\">[]</code> Blue [B]" ,
				color.blue.toString()+"&emsp;&emsp;<em>("+color.blue/255f+"%)</em>"
			)
		)
		information.add(_m("Alpha [A]" , color.alpha.toString()+"&emsp;&emsp;<em>("+color.alpha/255f+"%)</em>"))
		information.add(_m("Hue [H]" , hsv[0]))
		information.add(_m("Saturation [S]" , hsv[1]))
		information.add(_m("Value [V]" , hsv[2]))
		information.add(_m("Lightness [L]" , hsl[2]))
		information.add(
			_m(
				"<code style=\"background-color:"+extend_stl_Colors.RGBToHex(
					0 , 255 , 255
				)+";color:"+extend_stl_Colors.RGBToHex(0 , 255 , 255)+"\">[]</code> Cyan [C]" ,
				String.format("%,.4f" , cmyk[0])+"&emsp;&emsp;<em>("+String.format("%,.2f" , 100*cmyk[0])+"%)</em>"
			)
		)
		information.add(
			_m(
				"<code style=\"background-color:"+extend_stl_Colors.RGBToHex(
					255 , 0 , 255
				)+";color:"+extend_stl_Colors.RGBToHex(255 , 0 , 255)+"\">[]</code> Magenta [M]" ,
				String.format("%,.4f" , cmyk[1])+"&emsp;&emsp;<em>("+String.format("%,.2f" , 100*cmyk[1])+"%)</em>"
			)
		)
		information.add(
			_m(
				"<code style=\"background-color:"+extend_stl_Colors.RGBToHex(
					255 , 255 , 0
				)+";color:"+extend_stl_Colors.RGBToHex(255 , 255 , 0)+"\">[]</code> Yellow [Y]" ,
				String.format("%,.4f" , cmyk[2])+"&emsp;&emsp;<em>("+String.format("%,.2f" , 100*cmyk[2])+"%)</em>"
			)
		)
		information.add(
			_m(
				"Key [K]" ,
				String.format("%,.4f" , cmyk[3])+"&emsp;&emsp;<em>("+String.format("%,.2f" , 100*cmyk[3])+"%)</em>"
			)
		)
		information.add(_m("Luma (Formula 1)" , extend_stl_Colors.relative_luminance_1(color)))
		information.add(_m("Luma (Formula 2)" , extend_stl_Colors.relative_luminance_2(color)))
		information.add(_m("Luma (Formula 3)" , extend_stl_Colors.relative_luminance_3(color)))
		information.add(_m("Luma (Formula 4)" , extend_stl_Colors.relative_luminance_4(color)))
		information.add(_m("Color space" , extend_stl_Colors.awt_colorspace_NameMatch(color.colorSpace)))
		information.add(_m("Temperature" , extend_stl_Colors.color_temp(color)))
		information.add(
			_m(
				"CSS RGB" , "<code style=\"color:"+extend_stl_Colors.RGBToHex(
					ux_Theme.get().dominant()[0].toInt() ,
					ux_Theme.get().dominant()[1].toInt() ,
					ux_Theme.get().dominant()[2].toInt()
				)+"\">rgb</code><code>("+color.red+", "+color.green+", "+color.blue+")</code>"
			)
		)
		information.add(
			_m(
				"CSS RGBA" , "<code style=\"color:"+extend_stl_Colors.RGBToHex(
					ux_Theme.get().dominant()[0].toInt() ,
					ux_Theme.get().dominant()[1].toInt() ,
					ux_Theme.get().dominant()[2].toInt()
				)+"\">rgba</code><code>("+color.red+", "+color.green+", "+color.blue+", "+color.alpha+")</code>"
			)
		)
		information.add(
			_m(
				"CSS CMYK" , "<code style=\"color:"+extend_stl_Colors.RGBToHex(
					ux_Theme.get().dominant()[0].toInt() ,
					ux_Theme.get().dominant()[1].toInt() ,
					ux_Theme.get().dominant()[2].toInt()
				)+"\">cmyk</code><code>("+cmyk[0]*100f+"%, "+cmyk[1]*100f+"%, "+cmyk[2]*100f+"%"+cmyk[3]*100f+"%)</code>"
			)
		)
		information.add(
			_m(
				"CSS HSV" , "<code style=\"color:"+extend_stl_Colors.RGBToHex(
					ux_Theme.get().dominant()[0].toInt() ,
					ux_Theme.get().dominant()[1].toInt() ,
					ux_Theme.get().dominant()[2].toInt()
				)+"\">hsv</code><code>("+hsv[0]+", "+hsv[1]*100f+", "+hsv[2]*100f+")</code>"
			)
		)
		information.add(Box.createVerticalGlue())
		rightInfoPane.setViewportView(make(information))
		jsp.leftComponent=imageOverlay
		jsp.rightComponent=rightInfoPane
		val rgbValues_JSP=JSplitPane(JSplitPane.HORIZONTAL_SPLIT)
		val colorPoints=ArrayList<h_GraphPoint>()
		colorPoints.add(h_GraphPoint(255/4-255/4/2 , color.red , true , Color.red))
		colorPoints.add(h_GraphPoint(255/4*2-255/4/2 , color.green , true , Color.green))
		colorPoints.add(h_GraphPoint(255-255/4/2 , color.blue , true , Color.blue))
		colorPoints.add(h_GraphPoint(255-255/4*2-255/4/2 , color.alpha , true , Color.white))
		val cmykPoints=ArrayList<h_GraphPoint>()
		cmykPoints.add(h_GraphPoint(100/4-100/4/2 , (cmyk[0]*100).toInt() , true , Color.CYAN))
		cmykPoints.add(h_GraphPoint(100/4*2-100/4/2 , (cmyk[1]*100).toInt() , true , Color.MAGENTA))
		cmykPoints.add(h_GraphPoint(100-100/4/2 , (cmyk[2]*100).toInt() , true , Color.YELLOW))
		cmykPoints.add(h_GraphPoint(100-100/4*2-100/4/2 , (cmyk[3]*100).toInt() , true , Color.DARK_GRAY))
		val ttttt=Color(0.8f , 0.8f , 0.8f)
		val config=h_Graff(
			true ,
			ttttt ,
			Color(0 , 0 , 0 , 0) ,
			ux_Theme._theme.get_awt("rose") ,
			ux_Theme._theme.secondary_awt() ,
			Color.gray ,
			"RGBA" ,
			2 ,
			2 ,
			_1const._Mono_Medium_().deriveFont(12.5f)
		)
		val graff_RGB=ui_Graff(10 , 255 , colorPoints , DrawType.BOTH , DrawAxis.Y , config , "{0}")
		graff_RGB.font=_1const._Mono_().deriveFont(9f)
		graff_RGB.preferredSize=Dimension(preferredSize.height , preferredSize.height)
		val graff_CMYK=ui_Graff(10 , 100, cmykPoints , DrawType.BOTH , DrawAxis.Y , h_Graff(
			true ,
			ttttt ,
			Color(0 , 0 , 0 , 0) ,
			ux_Theme._theme.get_awt("rose") ,
			ux_Theme._theme.secondary_awt() ,
			Color.gray ,
			"CMYK" ,
			2 ,
			2 ,
			_1const._Mono_Medium_().deriveFont(12.5f)) , "{0}")
		graff_CMYK.font=_1const._Mono_().deriveFont(9f)
		graff_CMYK.preferredSize=Dimension(preferredSize.height , preferredSize.height)
		val colorGraphs=JPanel()
		colorGraphs.preferredSize=Dimension(250 , 250)
		colorGraphs.border=BorderFactory.createEmptyBorder(0 , 5 , 0 , 0)
		colorGraphs.layout=ux_WrapLayout(FlowLayout.LEFT , 4 , 4)
		colorGraphs.add(graff_RGB)
		colorGraphs.add(graff_CMYK)
		rgbValues_JSP.dividerLocation=410
		rgbValues_JSP.rightComponent=colorGraphs
		rgbValues_JSP.leftComponent=jsp
		contentPane.add(rgbValues_JSP)
	}
	
	fun _m(title:String , content:Any):JComponent
	{
		val jtp=JTextPane()
		jtp.contentType="text/html"
		jtp.border=BorderFactory.createEmptyBorder()
		jtp.isEditable=false
		jtp.text="<html><p><strong>$title</strong></p>&emsp;&emsp;$content</html>"
		jtp.font=jtp.font.deriveFont(13f)
		val wrap=JPanel()
		wrap.layout=FlowLayout(FlowLayout.LEFT , 8 , 0)
		val copy=quick_btn("Copy") {
			Toolkit.getDefaultToolkit().systemClipboard.setContents(
				StringSelection(stx_HTMLDebuff.parse(content.toString())) , null
			)
		}
		/*-------------------------------------------------------------------------------------------------------- /
		/ copy.setBorder(BorderFactory.createLineBorder(new Color(255 / 2, 255 / 2, 255 / 2, (int) (0.3 * 255)))); /
		/---------------------------------------------------------------------------------------------------------*/
		/*--------------------------------------------- /
		/ copy.setPreferredSize(new Dimension(46, 25)); /
		/----------------------------------------------*/
		wrap.add(copy)
		wrap.add(jtp)
		return wrap
		
		/*------------------------------------------------------------------------------------------------------------------ /
		/ JLabel r = new JLabel("<html><p><strong>" + title + "</strong></p>&emsp;&emsp;" + content.toString() + "</html>"); /
		/ r.setRequestFocusEnabled(true);                                                                                    /
		/ r.setFocusCycleRoot(true);                                                                                         /
		/ r.setAlignmentX(Component.LEFT_ALIGNMENT);                                                                         /
		/ r.setHorizontalAlignment(SwingConstants.LEFT);                                                                     /
		/ return r;                                                                                                          /
		/-------------------------------------------------------------------------------------------------------------------*/
	}
}