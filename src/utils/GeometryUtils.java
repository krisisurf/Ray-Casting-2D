package utils;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class GeometryUtils {

    /**
     * Reworked code with base idea from:
     * link: https://stackoverflow.com/questions/45112876/java-trying-to-raycast-in-2d
     *
     * @param ray
     * @param segment
     * @return
     */
    public static Point2D.Float getLineIntersectionPoint(Line2D.Float ray, Line2D.Float segment) {
        if (!ray.intersectsLine(segment))
            return null;

        double rx1 = ray.getX1(),
                ry1 = ray.getY1(),
                rx2 = ray.getX2(),
                ry2 = ray.getY2(),
                sx1 = segment.getX1(),
                sy1 = segment.getY1(),
                sx2 = segment.getX2(),
                sy2 = segment.getY2(),
                rdx = rx2 - rx1,
                rdy = ry2 - ry1,
                sdx = sx2 - sx1,
                sdy = sy2 - sy1,
                t1, t2,
                ix, iy;

        // When the RAY is vertical and the SEGMENT is horizontal
        if(rdx == 0)
            return new Point2D.Float((float) rx1, (float) sy1);

        t2 = (rdx * (sy1 - ry1) + rdy * (rx1 - sx1)) / (sdx * rdy - sdy * rdx);
        t1 = (sx1 + sdx * t2 - rx1) / rdx;

        if (t1 > 0) {
            ix = rx1 + rdx * t1;
            iy = ry1 + rdy * t1;
            return new Point2D.Float((float) ix, (float) iy);
        }

        return null;
    }

    public static double getLineLength(Line2D.Float line) {
        return line.getP1().distance(line.getP1());
    }

    public static double getLocationXOnCircle(double angle, double radius) {
        return radius * Math.sin(Math.PI * 2 * angle / 360);
    }

    public static double getLocationYOnCircle(double angle, double radius) {
        return radius * Math.cos(Math.PI * 2 * angle / 360);
    }
}
