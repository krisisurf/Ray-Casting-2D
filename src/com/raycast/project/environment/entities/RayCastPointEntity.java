package com.raycast.project.environment.entities;

import com.raycast.project.environment.Handler;
import com.raycast.project.environment.camera.ViewCamera;
import com.raycast.project.environment.entities.utils.Segment;
import com.raycast.project.environment.entities.utils.Shape;
import com.raycast.project.environment.entities.utils.Vertex;
import com.raycast.project.tracer.Ray;
import com.raycast.project.utils.GeometryUtils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RayCastPointEntity extends Entity {

    private final List<Ray> rays;

    public RayCastPointEntity(Handler handler, float x, float y) {
        super(handler, x, y, 0, 0);

        rays = new ArrayList<>();
    }

    @Override
    public void update() {
        castRays();
    }

    @Override
    public void render(Graphics g) {
        ViewCamera cam = handler.getViewCamera();

        // Draw rays and intersection vertices
        for (Ray r : rays) {
            r.drawRay(g, cam);
            r.drawIntersectionVertices(g, cam);
        }

        // Draw origin point
        g.setColor(Color.YELLOW);
        g.fillOval(cam.toScreenX(getX() - 5), cam.toScreenY(getY() - 5), 10, 10);
    }

    protected final void castRays() {
        rays.clear();
        List<Vertex> vertices = new ArrayList<>();
        List<Segment> segments = new ArrayList<>();

        // Find all vertices and segments
        handler.getWorld().getEntities().forEach(
                e -> {
                    Optional<Shape> shapeOptional = e.getShape();
                    if (shapeOptional.isEmpty())
                        return;

                    Shape shape = shapeOptional.get();
                    vertices.addAll(shape.getVertices());
                    segments.addAll(shape.getSegments());
                }
        );

        for (Vertex v : vertices) {
            Ray ray = calculateRayAndIntersections(v, segments);
            rays.add(ray);
        }
    }

    private Ray calculateRayAndIntersections(Vertex vertex, List<Segment> segments) {
        Ray ray = new Ray(getX(), getY(), vertex.x, vertex.y);

        for (Segment s : segments) {
            Point2D.Float intersectionPoint = GeometryUtils.getLineIntersectionPoint(ray.getLine(), s.getLine());

            if (intersectionPoint != null)
                ray.addIntersectionVertex(intersectionPoint, s.getEntity());
        }

        return ray;
    }
}


