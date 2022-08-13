package environment.entities;

import environment.Handler;
import environment.camera.ViewCamera;
import environment.entities.utils.Shape;
import input.KeyManager;
import tracer.Ray;
import utils.GeometryUtils;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class RayCastPointEntity extends Entity {

    private List<Ray> rays;

    public RayCastPointEntity(Handler handler, float x, float y) {
        super(handler, x, y, 0, 0);

        rays = new ArrayList<>();
    }

    @Override
    public void update() {
        move();
        rays.clear();
        castRays();
    }

    private void move() {
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
        ViewCamera cam = handler.getGameCamera();

        g.setColor(Color.RED);
        g.fillOval(cam.toScreenX(x - 3), cam.toScreenY(y - 3), 6, 6);

        for (Ray r : rays) {
            //if (r.getIntersectionPoints().size() == 1)
            r.drawRay(g, cam);

            r.drawIntersectionPoints(g, cam);
        }
    }

    @Override
    public Shape getShape() {
        return null;
    }

    private void castRays() {
        List<Point2D.Float> vertices = new ArrayList<>();
        List<Line2D.Float> segments = new ArrayList<>();
        handler.getWorld().getEntities().stream().filter(e -> e.getShape() != null).forEach(
                e -> {
                    Shape shape = e.getShape();
                    vertices.addAll(shape.getPoints());
                    segments.addAll(shape.getLines());
                }
        );

        for (Point2D.Float v : vertices) {
            Ray ray = calculateRayAndIntersections(v, segments);
            rays.add(ray);
        }
    }

    private Ray calculateRayAndIntersections(Point2D.Float vertex, List<Line2D.Float> segments) {
        Ray ray = new Ray(x, y, vertex.x, vertex.y);

        for (Line2D.Float s : segments) {
            Point2D.Float intersectionPoint = GeometryUtils.getLineIntersectionPoint(ray.getLine(), s);

            if (intersectionPoint != null)
                ray.addIntersectionPoint(intersectionPoint);
        }

        return ray;
    }
}


