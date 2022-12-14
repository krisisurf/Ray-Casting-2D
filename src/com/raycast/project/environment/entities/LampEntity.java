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

public class LampEntity extends Entity {

    private final List<Ray> rays;
    private final int raysQuantity;
    private float radius;

    private Polygon lightPolygon;

    public LampEntity(Handler handler, float x, float y, int raysQuantity, float radius) {
        super(handler, x, y, 0, 0);

        if (raysQuantity < 12)
            throw new IllegalArgumentException("raysQuantity must be at least 12");


        rays = new ArrayList<>(raysQuantity);
        this.raysQuantity = raysQuantity;
        setRadius(radius);

        lightPolygon = new Polygon();
        lightPolygon.npoints = raysQuantity;
        lightPolygon.xpoints = new int[raysQuantity];
        lightPolygon.ypoints = new int[raysQuantity];
    }

    @Override
    public void update() {
        shotRays();
    }


    @Override
    public void render(Graphics g) {
        ViewCamera cam = handler.getViewCamera();

        g.setColor(Color.WHITE);
        g.fillPolygon(lightPolygon);

        g.setColor(Color.YELLOW);
        g.fillOval(cam.toScreenX(getX() - 3), cam.toScreenY(getY() - 3), 6, 6);

        // Test purpose
//        rays.forEach(ray -> {
//            ray.drawRay(g, cam);
//            ray.drawIntersectionVertices(g, cam);
//        });
    }

    private void shotRays() {
        rays.clear();
        for (int i = 0; i < raysQuantity; i++) {
            double angle = i * (360.0 / raysQuantity);
            Ray ray = new Ray(getX(), getY(), getX() + (float) GeometryUtils.getLocationXOnCircle(angle, radius), getY() + (float) GeometryUtils.getLocationYOnCircle(angle, radius));

            for (Entity entity : handler.getWorld().getEntities()) {
                Optional<Shape> shapeOptional = entity.getShape();
                if (shapeOptional.isEmpty())
                    continue;

                Shape shape = shapeOptional.get();
                for (Segment segment : shape.getSegments()) {
                    Point2D.Float intersectionPoint = GeometryUtils.getLineIntersectionPoint(ray.getLine(), segment.getLine());

                    if (intersectionPoint != null)
                        ray.addIntersectionVertex(intersectionPoint, segment.getEntity());
                }
            }

            Optional<Vertex> closestToOriginVertex = ray.getClosestToOriginVertex();
            Vertex firstIntersectedVertex;

            if (closestToOriginVertex.isEmpty()) {
                firstIntersectedVertex = new Vertex(ray.getDirection(), null);
            } else {
                firstIntersectedVertex = closestToOriginVertex.get();
            }

            lightPolygon.xpoints[i] = (int) firstIntersectedVertex.x;
            lightPolygon.ypoints[i] = (int) firstIntersectedVertex.y;
            rays.add(ray);
        }
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        if (radius <= 0)
            throw new IllegalArgumentException("radius must be a positive number");
        this.radius = radius;
    }
}
