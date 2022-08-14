package environment.entities.utils;

import environment.entities.Entity;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Shape extends Polygon {

    private Entity entity;

    public Shape(int[] xpoints, int[] ypoints, int npoints, Entity entity) {
        super(xpoints, ypoints, npoints);
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public List<Vertex> getVertices() {
        List<Vertex> vertices = new ArrayList<>(super.npoints);
        for (int i = 0; i < super.npoints; i++)
            vertices.add(new Vertex(super.xpoints[i], super.ypoints[i], entity));

        return vertices;
    }

    public List<Segment> getSegments() {
        List<Segment> segments = new ArrayList<>(super.npoints);
        List<Vertex> vertices = getVertices();

        for(int i = 0; i < vertices.size() - 1; i++)
            segments.add(new Segment(vertices.get(i), vertices.get(i + 1)));

        segments.add(new Segment(vertices.get(vertices.size() - 1), vertices.get(0)));
        return segments;
    }
}
