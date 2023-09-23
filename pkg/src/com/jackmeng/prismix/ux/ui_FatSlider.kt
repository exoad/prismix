// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import com.jackmeng.stl.stl_ListenerPool
import com.jackmeng.stl.stl_Listener
import javax.swing.AbstractButton
import javax.swing.DefaultButtonModel
import lombok.Getter
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.geom.RoundRectangle2D

class ui_FatSlider(
		@field:Getter private val trueString:String , @field:Getter private val falseString:String , @field:Getter private val trueColor:Color , @field:Getter private val falseColor:Color , @field:Getter private val thumbColor:Color , initVal:Boolean , @field:Getter private val borderArc:Int , @field:Getter private val thumbWidth:Int , @field:Getter private val shadowDrawDist:Int , @field:Getter private val thumbBumpPad:Int
):AbstractButton()
{
	@Transient
	private val listenerPool:stl_ListenerPool<Boolean>=stl_ListenerPool("com.jackmeng.ui_FatSlider#"+hashCode())
	
	init  // thumb
	// is on
	// right
	// side
	// -> TRUE else FALSE
	{
		preferredSize=Dimension(70 , 40)
		setModel(DefaultButtonModel())
		isSelected=initVal
		addMouseListener(object:MouseAdapter()
		{
			override fun mouseReleased(e:MouseEvent)
			{
				if (RoundRectangle2D.Float(
							0F ,
							0F ,
							width.toFloat() ,
							height.toFloat() ,
							height.toFloat() ,
							height.toFloat()
						).contains(e.point)
				) isSelected=!isSelected
			}
		})
		preferredSize=Dimension(80 , 40)
	}
	
	fun add_listener(listener:stl_Listener<Boolean>)
	{
		listenerPool.add(listener)
	}
	
	fun rmf_listener(listener:stl_Listener<Boolean>?)
	{
		listenerPool.rmf(listener)
	}
	
	override fun setSelected(b:Boolean)
	{
		listenerPool.dispatch(b)
		toolTipText=if (b) trueString else falseString
		super.setSelected(b)
	}
	
	public override fun paintComponent(gr:Graphics)
	{
		val g=gr as Graphics2D
		g.clearRect(0 , 0 , width , height)
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON)
		g.setRenderingHint(RenderingHints.KEY_RENDERING , RenderingHints.VALUE_RENDER_SPEED)
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL , RenderingHints.VALUE_STROKE_NORMALIZE)
		g.color=Color.BLACK
		g.drawRect(0 , 0 , width-1 , height-1)
		g.color=if (isSelected) trueColor else falseColor
		g.fillRoundRect(0 , 0 , width , height , borderArc , borderArc)
		/*------------------------------------------------------------------------------------------------------------- /
		/ g.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight() + 2, getHeight() + 2);                             /
		/                                                                                                               /
		/ int thumbW = getHeight() - 4;                                                                                 /
		/                                                                                                               /
		/ g.setColor(thumbColor);                                                                                       /
		/ int thumbX = isSelected() ? getWidth() - thumbW + 2 : 2;                                                      /
		/ g.fillOval(thumbX, getHeight() % 2 == 0 ? 2 : 3, thumbW - 4, getHeight() - (getHeight() % 2 == 0 ? 4 : 5));   /
		/ System.out.println(thumbX + " ," + (getHeight() % 2 == 0 ? 2 : 3) + " ," + (thumbW - 2) + " ," + (getHeight() /
		/     - (getHeight() % 2 == 0 ? 4 : 5)));                                                                       /
		/--------------------------------------------------------------------------------------------------------------*/
		val shadowComposite=AlphaComposite.getInstance(
			AlphaComposite.SRC_OVER , 0.3f
		)
		val originalComposite=g.composite
		g.composite=shadowComposite
		val height=if (height%2==0) 2 else 3
		val end_height=getHeight()-if (getHeight()%2==0) 5 else 4
		if (isSelected) // right shadow
		{
			g.color=Color.BLACK
			g.fillRoundRect(
				width-thumbWidth-thumbBumpPad-shadowDrawDist ,
				height ,
				thumbWidth+shadowDrawDist ,
				end_height ,
				borderArc ,
				borderArc
			)
		}
		else
		{
			g.color=Color.BLACK
			g.fillRoundRect(
				thumbBumpPad , height , thumbWidth+shadowDrawDist , end_height , borderArc , borderArc
			)
		}
		g.composite=originalComposite
		g.color=thumbColor
		g.fillRoundRect(
			if (isSelected) width-thumbWidth-thumbBumpPad else thumbBumpPad ,
			height ,
			thumbWidth ,
			end_height ,
			borderArc ,
			borderArc
		)
		
		/*--------------------------------------------------------------------------------------------------------------- /
		/ g.setStroke(new BasicStroke(2, 2, 0));                                                                          /
		/                                                                                                                 /
		/ if (isSelected())                                                                                               /
		/ {                                                                                                               /
		/   g.setColor(extend_stl_Colors                                                                                  /
		/       .awt_remake(extend_stl_Colors.binary_fg_decider(extend_stl_Colors.awt_strip_rgba(trueColor))));           /
		/   g.drawLine((2 + (getWidth() - thumbWidth - thumbBumpPad - shadowDrawDist)) / 2, getHeight() % 2 == 0 ? 6 : 7, /
		/       (2 + (getWidth() - thumbWidth - thumbBumpPad - shadowDrawDist)) / 2,                                      /
		/       getHeight() - (getHeight() % 2 == 0 ? 8 : 9));                                                            /
		/ }                                                                                                               /
		/ else                                                                                                            /
		/ {                                                                                                               /
		/   g.setColor(Color.pink);                                                                                       /
		/   g.drawOval((2 + thumbWidth + shadowDrawDist) / 2, getHeight() % 2 == 0 ? 6 : 7,                               /
		/       (2 + thumbWidth + shadowDrawDist) / 2,                                                                    /
		/       getHeight() - (getHeight() % 2 == 0 ? 8 : 9));                                                            /
		/ }                                                                                                               /
		/----------------------------------------------------------------------------------------------------------------*/g.dispose()
	}
}