package com.jackmeng.prismix.ux

import com.jackmeng.prismix.ux.ux_Theme.Companion.get
import com.jackmeng.prismix.use_Maker.db_timed
import com.jackmeng.prismix.ux.ux_Listen.VISIBILITY
import com.jackmeng.prismix.ux.stx_Helper.within
import com.jackmeng.stl.stl_Listener
import com.jackmeng.stl.stl_Struct.struct_Pair
import com.jackmeng.prismix.stl.extend_stl_Wrap
import com.jackmeng.stl.stl_Wrap
import com.jackmeng.prismix._1const
import com.jackmeng.stl.stl_Struct
import com.jackmeng.stl.stl_Colors
import com.jackmeng.prismix.stl.extend_stl_Colors
import com.jackmeng.prismix.jm_Prismix
import com.jackmeng.stl.stl_Ware
import javax.swing.*
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener
import java.awt.*
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import java.util.*

// Creates a Gradient of White-Black-Color rectangular with sliders to adjust
// the colors only
// This should be the default option for the color picker
class ui_CPick_GradRect // This for picking RGBA colors
	:JSplitPane() , stl_Listener<struct_Pair<Color? , Boolean?>?> , MouseMotionListener , MouseListener
{
	@Transient
	private var toPaintCurr=true
	
	@Transient
	private lateinit var size_SquareGrad:extend_stl_Wrap<Int>
	
	@Transient
	private var transformed_x=-1
	
	@Transient
	private var transformed_y=-1 // transformed which represent the relative mouse
	
	// location relative to prismix
	/*-------------------------------------------------------------------- /
  / private int selector_cursor_radius = 10, selector_cursor_stroke = 2; /
  /---------------------------------------------------------------------*/
	@Transient
	private val c=stl_Wrap(Color.gray)
	private var first=true
	private var rgb_Sliders_Listen=false
	private val controls_RED:JSlider
	private val controls_GREEN:JSlider
	private val controls_BLUE:JSlider
	
	init
	{
		val controls_ScrollView=JScrollPane()
		controls_ScrollView.border=BorderFactory.createEmptyBorder()
		val controls=JPanel()
		controls.layout=BoxLayout(controls , BoxLayout.X_AXIS)
		val use_theme_based_tooling=_1const.`val`.parse("use_theme_based_tooling").get() as Boolean
		controls_RED=JSlider(SwingConstants.VERTICAL , 0 , 255 , 255/2)
		controls_RED.paintTrack=true
		controls_RED.paintTicks=true
		controls_RED.majorTickSpacing=20
		controls_RED.minorTickSpacing=5
		controls_RED.paintLabels=true
		controls_GREEN=JSlider(SwingConstants.VERTICAL , 0 , 255 , 255/2)
		controls_GREEN.paintTrack=true
		controls_GREEN.paintTicks=true
		controls_GREEN.majorTickSpacing=20
		controls_GREEN.minorTickSpacing=5
		controls_GREEN.paintLabels=true
		controls_GREEN.foreground=if (use_theme_based_tooling) Color.GREEN
		else stl_Colors.hexToRGB(
			Objects.requireNonNull(get()!!["green"])
		)
		controls_BLUE=JSlider(SwingConstants.VERTICAL , 0 , 255 , 255/2)
		controls_BLUE.paintTrack=true
		controls_BLUE.paintTicks=true
		controls_BLUE.majorTickSpacing=20
		controls_BLUE.minorTickSpacing=5
		controls_BLUE.paintLabels=true
		controls_BLUE.foreground=if (use_theme_based_tooling) Color.BLUE
		else stl_Colors.hexToRGB(
			Objects.requireNonNull(get()!!["ocean"])
		)
		val syncSliders=
			if (java.lang.Boolean.TRUE==_1const.`val`.parse("stoopid_sliders").get()) ChangeListener { ev:ChangeEvent?->
				rgb_Sliders_Listen=false
				_1const.COLOR_ENQ.dispatch(
					stl_Struct.make_pair(
							Color(controls_RED.value , controls_GREEN.value , controls_BLUE.value) , false
						)
				)
				rgb_Sliders_Listen=true
			}
			else ChangeListener { ev:ChangeEvent->
				if (!(ev.source as JSlider).valueIsAdjusting)
				{
					rgb_Sliders_Listen=false
					_1const.COLOR_ENQ.dispatch(
						stl_Struct.make_pair(
								Color(controls_RED.value , controls_GREEN.value , controls_BLUE.value) , false
							)
					)
					rgb_Sliders_Listen=true
				}
			}
		controls_RED.addChangeListener(syncSliders)
		controls_RED.foreground=if (use_theme_based_tooling) Color.RED
		else stl_Colors.hexToRGB(
			Objects.requireNonNull(get()!!["rose"])
		)
		controls_BLUE.addChangeListener(syncSliders)
		controls_GREEN.addChangeListener(syncSliders)

		controls.add(Box.createHorizontalStrut(30))
		controls.add(controls_RED)
		controls.add(Box.createHorizontalStrut(20))
		controls.add(controls_GREEN)
		controls.add(Box.createHorizontalStrut(20))
		controls.add(controls_BLUE)
		val lazyViewport_controls=ui_LazyViewport()
		lazyViewport_controls.view=controls
		controls_ScrollView.setViewportView(lazyViewport_controls)
		_1const.COLOR_ENQ.add { x:struct_Pair<Color , Boolean?>->
			if (first)
			{
				c.obj(x.first)
				first=false
			}
			else
			{
				if (!toPaintCurr) if (java.lang.Boolean.TRUE==!x.second!!) c.obj(x.first)
				toPaintCurr=!toPaintCurr
			}
			repaint(75L)
			null
		}
		// draw the gradient
		// width == height
		// draw the color cursor
		// prob does
		// something
		// we are drawing
		// a
		// circle here,
		// so
		// gotta make
		// sure
		// the strokes
		// are
		// smooth
		/*------------------------------------------------------------------------------------------------------- /
            / g2.setStroke(new java.awt.BasicStroke(selector_cursor_stroke));                                         /
            / g2.setColor(Color.white);                                                                               /
            / g2.drawOval(transformed_x - (selector_cursor_radius / 2), transformed_y - (selector_cursor_radius / 2), /
            /     selector_cursor_radius, selector_cursor_radius); // i got so confused, i                            /
            / // thought it took                                                                                      /
            / // x,y,x2,y2 so cringe                                                                                  /
            /--------------------------------------------------------------------------------------------------------*/
		val gradientView:JPanel=object:JPanel()
		{
			public override fun paintComponent(g:Graphics)
			{
				super.paintComponent(g)
				db_timed {
					val g2=g as Graphics2D
					g2.setRenderingHint(
						RenderingHints.KEY_RENDERING ,
						RenderingHints.VALUE_RENDER_SPEED
					) // draw the gradient
					val v=extend_stl_Colors.cpick_gradient2(
						this.size.width , c.obj()
					)
					g2.drawImage(
						v , null , 0 , (height-v.height)/2
					)
					size_SquareGrad.obj(v.width) // width == height
					if (transformed_x!=-1&&transformed_y!=-1)
					{
						
						// draw the color cursor
						g2.setRenderingHint(
							RenderingHints.KEY_STROKE_CONTROL ,
							RenderingHints.VALUE_STROKE_PURE
						) // prob does
						// something
						g2.setRenderingHint(
							RenderingHints.KEY_ANTIALIASING ,
							RenderingHints.VALUE_ANTIALIAS_ON
						) // we are drawing
						// a
						// circle here,
						// so
						// gotta make
						// sure
						// the strokes
						// are
						// smooth
						/*------------------------------------------------------------------------------------------------------- /
            / g2.setStroke(new java.awt.BasicStroke(selector_cursor_stroke));                                         /
            / g2.setColor(Color.white);                                                                               /
            / g2.drawOval(transformed_x - (selector_cursor_radius / 2), transformed_y - (selector_cursor_radius / 2), /
            /     selector_cursor_radius, selector_cursor_radius); // i got so confused, i                            /
            / // thought it took                                                                                      /
            / // x,y,x2,y2 so cringe                                                                                  /
            /--------------------------------------------------------------------------------------------------------*/
					}
					g2.dispose()
				}
			}
		}
		size_SquareGrad=extend_stl_Wrap(gradientView.size.width) // width == height
		setRightComponent(lazyViewport_controls)
		setLeftComponent(gradientView)
		setOrientation(HORIZONTAL_SPLIT)
		dividerLocation=200 // fuck proportions, we hardcode this shit
		autoscrolls=false
		name="ui_CPick_GradRect"
		addComponentListener(VISIBILITY())
		addMouseMotionListener(this)
		addMouseListener(this)
	}
	
	override fun call(arg0:struct_Pair<Color? , Boolean?>?):Void?
	{
		SwingUtilities.invokeLater { this.repaint(75L) }
		if (rgb_Sliders_Listen)
		{
			if (arg0!=null) controls_RED.value=arg0.first?.red!!
			if (arg0!=null) controls_GREEN.value=arg0.first?.green!!
			if (arg0!=null) controls_BLUE.value=arg0.first?.blue!!
		}
		return null
	}
	
	override fun mouseDragged(e:MouseEvent)
	{
		if (within(
					e.point ,
					Point(0 , (height-size_SquareGrad.get()!!)/2) ,
					Dimension(size_SquareGrad.get()!! , size_SquareGrad.get()!!)
				)
		)
		{
			jm_Prismix.log(
				"CPick_GradRect" , "Mouse [ DRAGGED ] at: "+e.x+", "+(e.y-(height-size_SquareGrad.get()!!)/2)
			) // we
			// have
			// to
			// normalize
			// Y
			transformed_x=e.x // wtf
			transformed_y=e.y // wtf here i got confused with relative coordinates and absolute coordintes in
			// regards to the components (screen versus prismix)
			cursor=Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR)
			toPaintCurr=false
			SwingUtilities.invokeLater { repaint(70L) }
		}
		else cursor=Cursor.getDefaultCursor()
	}
	
	override fun mouseExited(e:MouseEvent)
	{
		cursor=Cursor(Cursor.DEFAULT_CURSOR)
	}
	
	override fun mouseReleased(e:MouseEvent)
	{
		if (within(
					e.point ,
					Point(0 , (height-size_SquareGrad.get()!!)/2) ,
					Dimension(size_SquareGrad.get()!! , size_SquareGrad.get()!!)
				)
		)
		{
			toPaintCurr=true
			_1const.COLOR_ENQ.dispatch(
					stl_Struct.make_pair(
						stl_Ware.screen_colorAt(e.xOnScreen , e.yOnScreen).get() ,
						false
					)
				)
		}
	}
	
	override fun mouseClicked(e:MouseEvent)
	{ // to
		// normalize
		// Y
		transformed_x=e.x // wtf
		transformed_y=e.y // wtf here i got confused with relative coordinates and absolute coordintes in
		// regards to the components (screen versus prismix)
		cursor=Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR)
		toPaintCurr=false
		SwingUtilities.invokeLater { repaint(70L) }
	}
	
	override fun mouseMoved(e:MouseEvent)
	{/*-------------------------------------------------------------------------------------------------------------- /
    / log("CPick_GradRect",                                                                                          /
    /     "Mouse within? " + ux_Helper.within(e.getPoint(), new Point(0, (getHeight() - size_SquareGrad.get()) / 2), /
    /         new Dimension(size_SquareGrad.get(), size_SquareGrad.get())));                                         /
    / if (!ux_Helper.within(e.getPoint(), new Point(0, (getHeight() - size_SquareGrad.get()) / 2),                   /
    /     new Dimension(size_SquareGrad.get(), size_SquareGrad.get())))                                              /
    /   setCursor(Cursor.getDefaultCursor());                                                                        /
    /---------------------------------------------------------------------------------------------------------------*/
		
		/*----------------------------------------------------------------------------------------------------------------- /
    / // we ignore this event                                                                                           /
    / if (ux_Helper.within(e.getPoint(), new Point(0, (getHeight() - size_SquareGrad.get()) / 2),                       /
    /     new Dimension(size_SquareGrad.get(), size_SquareGrad.get())))                                                 /
    /   log("CPick_GradRect",                                                                                           /
    /       "Mouse [ MOVED ] at: " + e.getX() + ", " + (e.getY() - (getHeight() - size_SquareGrad.get()) / 2)); // we   /
    /                                                                                                           // have /
    / // to                                                                                                             /
    / // normalize                                                                                                      /
    / // Y                                                                                                              /
    /------------------------------------------------------------------------------------------------------------------*/
	}
	
	override fun mousePressed(e:MouseEvent)
	{
	}
	
	override fun mouseEntered(e:MouseEvent)
	{
		cursor=Cursor(Cursor.CROSSHAIR_CURSOR)
	}
}