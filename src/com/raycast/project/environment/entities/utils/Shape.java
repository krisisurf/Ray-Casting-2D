package com.raycast.project.environment.entities.utils;

import com.raycast.project.environment.entities.Entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Shape {

    private final Polygon polygon;
    private Entity entity;
    private int originPointIndex = 0;

    private final List<Vertex> calculatedVertices;
    private final List<Segment> calculatedSegments;

    public Shape(int[] xpoints, int[] ypoints, int npoints, Entity entity) {
        this.polygon = new Polygon(xpoints, ypoints, npoints);

        if (xpoints.length != ypoints.length)
            throw new IllegalArgumentException("xpoints.length does not match ypoints.length");

        this.entity = entity;

        calculatedVertices = new ArrayList<>(npoints);
        calculatedSegments = new ArrayList<>(npoints);

        for (int i = 0; i < polygon.npoints; i++)
            calculatedVertices.add(new Vertex(polygon.xpoints[i], polygon.ypoints[i], entity));

        for (int i = 0; i < calculatedVertices.size() - 1; i++)
            calculatedSegments.add(new Segment(calculatedVertices.get(i), calculatedVertices.get(i + 1)));

        calculatedSegments.add(new Segment(calculatedVertices.get(calculatedVertices.size() - 1), calculatedVertices.get(0)));
    }

    /**
     * Calculating location of all points from the shape, depending on origin point
     *
     * @param x coordinate
     * @param y coordinate
     */
    public void setLocation(int x, int y) {
        int originX = polygon.xpoints[originPointIndex];
        int originY = polygon.ypoints[originPointIndex];

        int differenceX = x - originX;
        int differenceY = y - originY;

        polygon.translate(differenceX, differenceY);

        calculateVertices();
        calculateSegments();
    }

    private void calculateVertices() {
        for (int i = 0; i < polygon.npoints; i++)
            calculatedVertices.get(i).setLocation(polygon.xpoints[i], polygon.ypoints[i]);
    }

    private void calculateSegments() {
        for (int i = 0; i < polygon.npoints - 1; i++)
            calculatedSegments.get(i).setVertices(calculatedVertices.get(i), calculatedVertices.get(i + 1));

        calculatedSegments.get(polygon.npoints - 1).setVertices(calculatedVertices.get(polygon.npoints - 1), calculatedVertices.get(0));
    }

    public Entity getEntity() {
        return entity;
    }

    public Point getOriginPoint() {
        return new Point(polygon.xpoints[originPointIndex], polygon.ypoints[originPointIndex]);
    }

    public void setOriginPointIndex(int originPointIndex) {
        if (originPointIndex < 0 || originPointIndex >= polygon.npoints)
            throw new IllegalArgumentException("originPointIndex is outside the bounds [0; npoints)");

        setLocation(polygon.xpoints[this.originPointIndex], polygon.ypoints[this.originPointIndex]);
        this.originPointIndex = originPointIndex;
    }

    public List<Vertex> getVertices() {
        return calculatedVertices;
    }

    public List<Segment> getSegments() {
        return calculatedSegments;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
