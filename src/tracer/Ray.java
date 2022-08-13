package tracer;

import environment.camera.ViewCamera;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Ray {

    public static final Color RAY_COLOR = Color.WHITE;
    public static final Color INTERSECTION_POINT_COLOR = Color.RED;

    private Point2D.Float origin;
    private Point2D.Float direction;

    private List<Point2D.Float> intersectionPoints;

    public Ray(Point2D.Float origin, Point2D.Float direction) {
        this.origin = origin;
        this.direction = direction;
        intersectionPoints = new ArrayList<>();
    }

    public Ray(float xorigin, float yorigin, float xdirection, float ydirection) {
        this(new Point2D.Float(xorigin, yorigin), new Point2D.Float(xdirection, ydirection));
    }

    public void addIntersectionPoint(Point2D.Float point) {
        intersectionPoints.add(point);
    }

    public void removeIntersectionPoint(Point2D.Float point) {
        intersectionPoints.remove(point);
    }

    public void addAllIntersectionPoints(List<Point2D.Float> points) {
        intersectionPoints.addAll(points);
    }

    public void clearIntersectionPoints() {
        intersectionPoints.clear();
    }

    /**
     * @return copy of intersectionPoints list
     */
    public List<Point2D.Float> getIntersectionPoints() {
        return intersectionPoints.stream().toList();
    }

    public void drawRay(Graphics g, ViewCamera camera) {
        g.setColor(RAY_COLOR);
        g.drawLine(camera.toScreenX(origin.x), camera.toScreenY(origin.y), camera.toScreenX(direction.x), camera.toScreenY(direction.y));
    }

    public void drawIntersectionPoints(Graphics g, ViewCamera camera) {
        g.setColor(INTERSECTION_POINT_COLOR);
        for(Point2D.Float p : intersectionPoints)
            g.drawOval(camera.toScreenX(p.x - 3), camera.toScreenY(p.y - 3), 6, 6);
    }

    public Point2D.Float getOrigin() {
        return origin;
    }

    public void setOrigin(Point2D.Float origin) {
        this.origin = (Point2D.Float) origin.clone();
    }

    public Point2D.Float getDirection() {
        return direction;
    }

    public void setDirection(Point2D.Float direction) {
        this.direction = (Point2D.Float) direction.clone();
    }

    public Line2D.Float getLine() {
        return new Line2D.Float(origin, direction);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "origin=" + origin +
                ", direction=" + direction +
                '}';
    }
}
