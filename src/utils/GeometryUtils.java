package utils;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class GeometryUtils {

    /**
     * Code from: https://stackoverflow.com/questions/45112876/java-trying-to-raycast-in-2d
     *
     * @param ray
     * @param segment
     * @return
     */
    public static Point2D.Float getLineIntersectionPoint(Line2D.Float ray, Line2D.Float segment) {
        if (ray.intersectsLine(segment)) {
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

            t2 = (rdx * (sy1 - ry1) + rdy * (rx1 - sx1)) / (sdx * rdy - sdy * rdx);
            t1 = (sx1 + sdx * t2 - rx1) / rdx;

            if (t1 > 0) {
                ix = rx1 + rdx * t1;
                iy = ry1 + rdy * t1;
                return new Point2D.Float((int) ix, (int) iy);
            } else
                return null;
        } else
            return null;
    }

    public static double getLineLength(Line2D.Float line) {
        return line.getP1().distance(line.getP1());
    }
}
