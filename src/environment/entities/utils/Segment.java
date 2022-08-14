package environment.entities.utils;

import environment.entities.Entity;

import java.awt.geom.Line2D;

public class Segment {

    private Vertex vertex1;
    private Vertex vertex2;

    public Segment(Vertex vertex1, Vertex vertex2) {
        setVertices(vertex1, vertex1);
    }

    public void setVertices(Vertex vertex1, Vertex vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
    }

    public Line2D.Float getLine() {
        return new Line2D.Float(vertex1, vertex2);
    }

    public Entity getEntity() {
        return vertex1.getIntersectedEntity();
    }
}
