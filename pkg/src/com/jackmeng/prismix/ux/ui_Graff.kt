// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import com.jackmeng.prismix.ux.ux_Theme.dominant_awt
import com.jackmeng.prismix.ux.ux_Theme.get_awt
import com.jackmeng.prismix.ux.model.h_GraphPoint
import com.jackmeng.prismix.ux.ui_Graff.DrawType
import com.jackmeng.prismix.ux.ui_Graff.DrawAxis
import com.jackmeng.prismix.ux.model.h_Graff
import kotlin.jvm.JvmStatic
import com.jackmeng.prismix.ux.ui_Graff
import com.jackmeng.prismix.ux.ux_Theme
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants
import java.awt.*
import java.awt.geom.Path2D
import java.text.MessageFormat
import java.util.ArrayList

class ui_Graff:JPanel
{
	enum class DrawType
	{
		STRAIGHT , CURVE , BOTH
	}
	
	enum class DrawAxis
	{
		X , Y , BOTH , NONE
	}
	
	private val max_value:Int
	private val graphPoints:ArrayList<h_GraphPoint>
	private val drawType:DrawType
	private val drawAxis:DrawAxis
	private var margin=20
	private var pointFormat="{0},{1}"
	private val config:h_Graff
	
	constructor(
			max_value:Int , graphPoints:ArrayList<h_GraphPoint> , drawType:DrawType , drawAxis:DrawAxis , config:h_Graff
	)
	{
		this.max_value=max_value
		this.graphPoints=graphPoints
		this.drawType=drawType
		this.drawAxis=drawAxis
		this.config=config
	}
	
	constructor(
			margin:Int , max_value:Int , graphPoints:ArrayList<h_GraphPoint> , drawType:DrawType , drawAxis:DrawAxis , config:h_Graff
	)
	{
		this.max_value=max_value
		this.graphPoints=graphPoints
		this.drawType=drawType
		this.drawAxis=drawAxis
		this.config=config
		this.margin=margin
	}
	
	constructor(
			margin:Int , max_value:Int , graphPoints:ArrayList<h_GraphPoint> , drawType:DrawType , drawAxis:DrawAxis , config:h_Graff , pointFormat:String
	)
	{
		this.max_value=max_value
		this.graphPoints=graphPoints
		this.drawType=drawType
		this.drawAxis=drawAxis
		this.config=config
		this.margin=margin
		this.pointFormat=pointFormat
	}
	
	override fun paintComponent(g:Graphics)
	{
		super.paintComponent(g)
		if (config.normalizeAA())
		{
			val g2=g as Graphics2D
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON)
			g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL , RenderingHints.VALUE_STROKE_NORMALIZE)
			g2.setRenderingHint(RenderingHints.KEY_RENDERING , RenderingHints.VALUE_RENDER_SPEED)
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION , RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR)
		}
		drawGraph(g)
	}
	
	private fun drawGraph(g:Graphics)
	{
		g.font=config.font()
		val size=Math.min(width , height)
		val squareSize=size-2*margin
		g.color=config.enclosureColor()
		g.drawRect(margin , margin , squareSize , squareSize)
		g.drawString(config.name() , width/2 , height/2)
		if (drawAxis==DrawAxis.X||drawAxis==DrawAxis.BOTH)
		{
			run {
				var x=0
				while (x<=max_value)
				{
					val tickX=margin+x*squareSize/max_value
					val tickY=margin+squareSize
					g.drawLine(tickX , tickY , tickX , tickY+5)
					g.drawString(x.toString() , tickX-10 , tickY+20)
					x+=max_value/10
				}
			}
		}
		if (drawAxis==DrawAxis.Y||drawAxis==DrawAxis.BOTH)
		{
			run {
				var y=0
				while (y<=max_value)
				{
					val tickX=margin-5
					val tickY=margin+(max_value-y)*squareSize/max_value
					g.drawLine(tickX , tickY , tickX-5 , tickY)
					g.drawString(y.toString() , tickX-10 , tickY+5)
					y+=max_value/10
				}
			}
		}
		g.color=config.dotGridColor()
		val gridSize=max_value/10
		run {
			var x=gridSize
			while (x<max_value)
			{
				run {
					var y=gridSize
					while (y<max_value)
					{
						val dotX=margin+x*squareSize/max_value
						val dotY=margin+(max_value-y)*squareSize/max_value
						g.fillOval(dotX-1 , dotY-1 , 2 , 2)
						y+=gridSize
					}
				}
				x+=gridSize
			}
		}
		if (drawType==DrawType.STRAIGHT||drawType==DrawType.BOTH) drawStraightGraph(g , margin , squareSize)
		if (drawType==DrawType.CURVE||drawType==DrawType.BOTH) drawCurvedGraph(g , margin , squareSize)
		g.dispose() // forgot this part in later bits??
	}
	
	private fun drawStraightGraph(g:Graphics , margin:Int , squareSize:Int)
	{
		g.color=config.lineColor()
		g.font=config.font()
		(g as Graphics2D).stroke=BasicStroke(config.lineWidth().toFloat())
		for (i in 0 until graphPoints.size-1)
		{
			val p1=graphPoints[i]
			val p2=graphPoints[i+1]
			val p1X=margin+p1.x()*squareSize/max_value
			val p1Y=margin+(max_value-p1.y())*squareSize/max_value
			val p2X=margin+p2.x()*squareSize/max_value
			val p2Y=margin+(max_value-p2.y())*squareSize/max_value
			g.drawLine(p1X , p1Y , p2X , p2Y)
		}
		for (point in graphPoints)
		{
			val scaledX=margin+point.x()*squareSize/max_value
			val scaledY=margin+(max_value-point.y())*squareSize/max_value
			g.setColor(point.labelColor())
			g.fillOval(scaledX-5 , scaledY-5 , 10 , 10)
			if (point.toLabelWithValue())
			{
				g.setColor(config.labelColor())
				g.drawString(MessageFormat.format(pointFormat , point.x() , point.y()) , scaledX+10 , scaledY-10)
			}
		}
	}
	
	private fun drawCurvedGraph(g:Graphics , margin:Int , squareSize:Int)
	{
		if (graphPoints.isEmpty()) return
		g.font=config.font()
		val xPoints=IntArray(graphPoints.size)
		val yPoints=IntArray(graphPoints.size)
		for (i in graphPoints.indices)
		{
			val point=graphPoints[i]
			val scaledX=margin+point.x()*squareSize/max_value
			val scaledY=margin+(max_value-point.y())*squareSize/max_value
			xPoints[i]=scaledX
			yPoints[i]=scaledY
			g.color=point.labelColor()
			g.fillOval(scaledX-5 , scaledY-5 , 10 , 10)
			if (point.toLabelWithValue())
			{
				g.color=config.labelColor()
				g.drawString(MessageFormat.format(pointFormat , point.x() , point.y()) , scaledX+10 , scaledY-10)
			}
		}
		(g as Graphics2D).stroke=BasicStroke(config.curveWidth().toFloat())
		if (xPoints.size>1)
		{
			val path:Path2D=Path2D.Double()
			path.moveTo(xPoints[0].toDouble() , yPoints[0].toDouble())
			for (i in 1 until xPoints.size)
			{
				val x1=xPoints[i-1]
				val y1=yPoints[i-1]
				val x2=xPoints[i]
				val y2=yPoints[i]
				val ctrlX=(x1+x2)/2
				val ctrlY=(y1+y2)/2
				path.quadTo(x1.toDouble() , y1.toDouble() , ctrlX.toDouble() , ctrlY.toDouble())
			}
			path.lineTo(xPoints[xPoints.size-1].toDouble() , yPoints[yPoints.size-1].toDouble())
			g.setColor(config.curveColor())
			g.draw(path)
		}
	}
	
	companion object
	{
		@JvmStatic
		fun main(args:Array<String>)
		{
			val points=ArrayList<h_GraphPoint>()
			points.add(h_GraphPoint(20 , 30 , true , Color.RED))
			points.add(h_GraphPoint(60 , 80 , true , Color.BLUE))
			points.add(h_GraphPoint(80 , 40 , true , Color.GREEN))
			val max_value=100
			val frame=JFrame("Graph Component")
			frame.defaultCloseOperation=WindowConstants.EXIT_ON_CLOSE
			frame.contentPane.add(
				ui_Graff(
					max_value , points , DrawType.BOTH , DrawAxis.BOTH , h_Graff(
						true ,
						Color.gray ,
						Color.gray ,
						ux_Theme._theme!!.dominant_awt() ,
						ux_Theme._theme!!.get_awt("teal") ,
						Color.gray ,
						"Test" ,
						2 ,
						2 ,
						Font.getFont(
							Font.MONOSPACED
						)
					)
				)
			)
			frame.pack()
			frame.isVisible=true
		}
	}
}