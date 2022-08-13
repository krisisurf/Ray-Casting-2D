package environment.entities.utils;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Shape extends Polygon {

    public Shape(int[] xpoints, int[] ypoints, int npoints) {
        super(xpoints, ypoints, npoints);
    }

    public List<Point2D.Float> getPoints() {
        List<Point2D.Float> points = new ArrayList<>(super.npoints);
        for (int i = 0; i < super.npoints; i++)
            points.add(new Point2D.Float(super.xpoints[i], super.ypoints[i]));

        return points;
    }

    public List<Line2D.Float> getLines() {
        List<Line2D.Float> lines = new ArrayList<>(super.npoints);
        for (int i = 0; i < super.npoints - 1; i++)
            lines.add(new Line2D.Float(xpoints[i], ypoints[i], xpoints[i + 1], ypoints[i + 1]));

        lines.add(new Line2D.Float(xpoints[super.npoints - 1], ypoints[super.npoints - 1], xpoints[0], ypoints[0]));
        return lines;
    }
}
