// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.*;
import javax.swing.*;

import com.jackmeng.prismix.ux.model.h_Graff;
import com.jackmeng.prismix.ux.model.h_GraphPoint;

import java.awt.geom.*;
import java.text.MessageFormat;
import java.util.ArrayList;

public class ui_Graff
        extends
        JPanel
{
    public enum DrawType {
        STRAIGHT, CURVE, BOTH;
    }

    public enum DrawAxis {
        X, Y, BOTH, NONE;
    }

    private final int max_value;
    private final ArrayList< h_GraphPoint > graphPoints;
    private final DrawType drawType;
    private final DrawAxis drawAxis;
    private int margin = 20;
    private String pointFormat = "{0},{1}";

    private final h_Graff config;

    public ui_Graff(int max_value, ArrayList< h_GraphPoint > graphPoints, DrawType drawType, DrawAxis drawAxis,
            h_Graff config)
    {
        this.max_value = max_value;
        this.graphPoints = graphPoints;
        this.drawType = drawType;
        this.drawAxis = drawAxis;
        this.config = config;
    }

    public ui_Graff(int margin, int max_value, ArrayList< h_GraphPoint > graphPoints, DrawType drawType,
            DrawAxis drawAxis,
            h_Graff config)
    {
        this.max_value = max_value;
        this.graphPoints = graphPoints;
        this.drawType = drawType;
        this.drawAxis = drawAxis;
        this.config = config;
        this.margin = margin;
    }

    public ui_Graff(int margin, int max_value, ArrayList< h_GraphPoint > graphPoints, DrawType drawType,
            DrawAxis drawAxis,
            h_Graff config, String pointFormat)
    {
        this.max_value = max_value;
        this.graphPoints = graphPoints;
        this.drawType = drawType;
        this.drawAxis = drawAxis;
        this.config = config;
        this.margin = margin;
        this.pointFormat = pointFormat;
    }

    @Override protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (config.normalizeAA())
        {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        }
        drawGraph(g);
    }

    private void drawGraph(Graphics g)
    {
        int size = Math.min(getWidth(), getHeight());
        int squareSize = size - 2 * margin;
        g.setColor(config.enclosureColor());
        g.drawRect(margin, margin, squareSize, squareSize);

        g.drawString(config.name(), getWidth() / 2, getHeight() / 2);

        if (drawAxis == DrawAxis.X || drawAxis == DrawAxis.BOTH)
        {
            for (int x = 0; x <= max_value; x += max_value / 10)
            {
                int tickX = margin + (x * squareSize) / max_value;
                int tickY = margin + squareSize;
                g.drawLine(tickX, tickY, tickX, tickY + 5);
                g.drawString(String.valueOf(x), tickX - 10, tickY + 20);
            }
        }

        if (drawAxis == DrawAxis.Y || drawAxis == DrawAxis.BOTH)
        {
            for (int y = 0; y <= max_value; y += max_value / 10)
            {
                int tickX = margin - 5;
                int tickY = margin + (max_value - y) * squareSize / max_value;
                g.drawLine(tickX, tickY, tickX - 5, tickY);
                g.drawString(String.valueOf(y), tickX - 30, tickY + 5);
            }
        }

        g.setColor(config.dotGridColor());
        int gridSize = max_value / 10;
        for (int x = gridSize; x < max_value; x += gridSize)
        {
            for (int y = gridSize; y < max_value; y += gridSize)
            {
                int dotX = margin + (x * squareSize) / max_value;
                int dotY = margin + ((max_value - y) * squareSize) / max_value;
                g.fillOval(dotX - 1, dotY - 1, 2, 2);
            }
        }

        if (drawType == DrawType.STRAIGHT || drawType == DrawType.BOTH)
            drawStraightGraph(g, margin, squareSize);
        if (drawType == DrawType.CURVE || drawType == DrawType.BOTH)
            drawCurvedGraph(g, margin, squareSize);

    }

    private void drawStraightGraph(Graphics g, int margin, int squareSize)
    {
        for (h_GraphPoint point : graphPoints)
        {
            int scaledX = margin + (point.x() * squareSize) / max_value;
            int scaledY = margin + ((max_value - point.y()) * squareSize) / max_value;

            g.setColor(point.labelColor());
            g.fillOval(scaledX - 5, scaledY - 5, 10, 10);

            if (point.toLabelWithValue())
            {
                g.setColor(config.labelColor());
                g.drawString(MessageFormat.format(pointFormat, point.x(), point.y()), scaledX + 10, scaledY - 10);
            }
        }

        g.setColor(config.lineColor());
        ((Graphics2D) g).setStroke(new BasicStroke(config.lineWidth()));
        for (int i = 0; i < graphPoints.size() - 1; i++)
        {
            h_GraphPoint p1 = graphPoints.get(i);
            h_GraphPoint p2 = graphPoints.get(i + 1);
            int p1X = margin + (p1.x() * squareSize) / max_value;
            int p1Y = margin + ((max_value - p1.y()) * squareSize) / max_value;
            int p2X = margin + (p2.x() * squareSize) / max_value;
            int p2Y = margin + ((max_value - p2.y()) * squareSize) / max_value;
            g.drawLine(p1X, p1Y, p2X, p2Y);
        }
    }

    private void drawCurvedGraph(Graphics g, int margin, int squareSize)
    {
        if (graphPoints.isEmpty())
            return;

        int[] xPoints = new int[graphPoints.size()];
        int[] yPoints = new int[graphPoints.size()];
        for (int i = 0; i < graphPoints.size(); i++)
        {
            h_GraphPoint point = graphPoints.get(i);
            int scaledX = margin + (point.x() * squareSize) / max_value;
            int scaledY = margin + ((max_value - point.y()) * squareSize) / max_value;
            xPoints[i] = scaledX;
            yPoints[i] = scaledY;

            g.setColor(point.labelColor());
            g.fillOval(scaledX - 5, scaledY - 5, 10, 10);

            if (point.toLabelWithValue())
            {
                g.setColor(config.labelColor());
                g.drawString(MessageFormat.format(pointFormat, point.x(), point.y()), scaledX + 10, scaledY - 10);
            }
        }

        ((Graphics2D) g).setStroke(new BasicStroke(config.curveWidth()));

        if (xPoints.length > 1)
        {
            Path2D path = new Path2D.Double();
            path.moveTo(xPoints[0], yPoints[0]);
            for (int i = 1; i < xPoints.length; i++)
            {
                int x1 = xPoints[i - 1];
                int y1 = yPoints[i - 1];
                int x2 = xPoints[i];
                int y2 = yPoints[i];
                int ctrlX = (x1 + x2) / 2;
                int ctrlY = (y1 + y2) / 2;
                path.quadTo(x1, y1, ctrlX, ctrlY);
            }
            path.lineTo(xPoints[xPoints.length - 1], yPoints[yPoints.length - 1]);
            g.setColor(config.curveColor());
            ((Graphics2D) g).draw(path);
        }
    }

    public static void main(String[] args)
    {
        ArrayList< h_GraphPoint > points = new ArrayList<>();
        points.add(new h_GraphPoint(20, 30, true, Color.RED));
        points.add(new h_GraphPoint(60, 80, true, Color.BLUE));
        points.add(new h_GraphPoint(80, 40, true, Color.GREEN));

        int max_value = 100;

        JFrame frame = new JFrame("Graph Component");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(
                new ui_Graff(max_value, points, DrawType.BOTH, DrawAxis.BOTH, new h_Graff(true, Color.gray, Color.gray,
                        ux_Theme._theme.dominant_awt(), ux_Theme._theme.get_awt("teal"), Color.gray, "Test", 2, 2)));
        frame.pack();
        frame.setVisible(true);
    }
}
