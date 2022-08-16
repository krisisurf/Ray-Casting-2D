package com.raycast.project.tracer;

import com.raycast.project.environment.camera.ViewCamera;
import com.raycast.project.environment.entities.Entity;
import com.raycast.project.environment.entities.utils.Vertex;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Ray {

    public static final Color RAY_COLOR = Color.GREEN;
    public static final Color INTERSECTION_POINT_COLOR = Color.RED;

    private Point2D.Float origin;
    private Point2D.Float direction;

    private List<Vertex> intersectionVertices;

    public Ray(Point2D.Float origin, Point2D.Float direction) {
        this.origin = origin;
        this.direction = direction;
        intersectionVertices = new ArrayList<>();
    }

    public Ray(float xorigin, float yorigin, float xdirection, float ydirection) {
        this(new Point2D.Float(xorigin, yorigin), new Point2D.Float(xdirection, ydirection));
    }

    public void addIntersectionVertex(Point2D.Float location, Entity intersectedEntity) {
        intersectionVertices.add(new Vertex(location, intersectedEntity));
    }

    public void removeIntersectionVertex(Point2D.Float point) {
        intersectionVertices.remove(point);
    }

    public void clearIntersectionVertices() {
        intersectionVertices.clear();
    }

    public List<Vertex> getIntersectionVertices() {
        return intersectionVertices;
    }

    public List<Vertex> getIntersectionVerticesSorted() {
        Comparator<Vertex> vertexComparator = Comparator.comparingDouble(v -> origin.distance(v));
        return intersectionVertices.stream().sorted(vertexComparator).toList();
    }

    public Optional<Vertex> getClosestToOriginVertex() {
        if (intersectionVertices.isEmpty())
            return Optional.empty();

        Comparator<Vertex> vertexComparator = Comparator.comparingDouble(v -> origin.distance(v));

        return intersectionVertices.stream().min(vertexComparator);
    }

    public void drawRay(Graphics g, ViewCamera camera) {
        g.setColor(RAY_COLOR);
        g.drawLine(camera.toScreenX(origin.x), camera.toScreenY(origin.y), camera.toScreenX(direction.x), camera.toScreenY(direction.y));
    }

    public void drawIntersectionVertices(Graphics g, ViewCamera camera) {
        g.setColor(INTERSECTION_POINT_COLOR);
        for (Point2D.Float p : intersectionVertices)
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
                ", intersectionVertices=" + intersectionVertices +
                '}';
    }
}
