package environment.entities;

import environment.Handler;
import environment.camera.ViewCamera;
import environment.entities.utils.Segment;
import environment.entities.utils.Shape;
import environment.entities.utils.Vertex;
import input.KeyManager;
import tracer.Ray;
import utils.GeometryUtils;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class RayCastPointEntity extends Entity {

    List<Ray> rays;

    public RayCastPointEntity(Handler handler, float x, float y) {
        super(handler, x, y, 0, 0);

        rays = new ArrayList<>();
    }

    @Override
    public final void update() {
        move();
        rays.clear();
        castRays();

        updateThird();
    }

    public void updateThird() {
    }

    protected void move() {
        float xSpeed = 3, ySpeed = 3;

        KeyManager keyManager = handler.getKeyManager();

        if (keyManager.down)
            y += ySpeed;
        else if (keyManager.up)
            y -= ySpeed;
        else if (keyManager.left)
            x -= xSpeed;
        else if (keyManager.right)
            x += xSpeed;

    }

    @Override
    public void render(Graphics g) {
        ViewCamera cam = handler.getViewCamera();

        g.setColor(Color.RED);
        g.fillOval(cam.toScreenX(x - 3), cam.toScreenY(y - 3), 6, 6);

        for (Ray r : rays) {
            r.drawRay(g, cam);
            r.drawIntersectionVertices(g, cam);
        }
    }

    @Override
    public Shape getShape() {
        return null;
    }

    private void castRays() {
        List<Vertex> vertices = new ArrayList<>();
        List<Segment> segments = new ArrayList<>();

        handler.getWorld().getEntities().stream().filter(e -> e.getShape() != null).forEach(
                e -> {
                    Shape shape = e.getShape();
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
        Ray ray = new Ray(x, y, vertex.x, vertex.y);

        for (Segment s : segments) {
            Point2D.Float intersectionPoint = GeometryUtils.getLineIntersectionPoint(ray.getLine(), s.getLine());

            if (intersectionPoint != null)
                ray.addIntersectionVertex(intersectionPoint, s.getEntity());
        }

        return ray;
    }
}


