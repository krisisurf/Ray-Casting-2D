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
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class LampEntity extends Entity {

    private final List<Ray> rays;
    private final int raysQuantity;
    private float radius;

    public LampEntity(Handler handler, float x, float y, int raysQuantity, float radius) {
        super(handler, x, y, 0, 0);

        try {
            if (raysQuantity < 12)
                throw new IllegalArgumentException("raysQuantity must be at least 12");

            if (radius <= 0)
                throw new IllegalArgumentException("radius must be a positive number");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        rays = new ArrayList<>(raysQuantity);
        this.raysQuantity = raysQuantity;
        this.radius = radius;
    }

    @Override
    public void update() {
        rays.clear();
        move();

        shotRays();
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

        g.setColor(Color.YELLOW);
        g.fillOval(cam.toScreenX(x - 3), cam.toScreenY(y - 3), 6, 6);

        rays.forEach(ray -> {
            ray.drawRay(g, cam);
            ray.drawIntersectionVertices(g, cam);
        });
    }

    @Override
    public Shape getShape() {
        return null;
    }

    private void shotRays() {
        for (int i = 0; i < raysQuantity; i++) {
            double angle = i * (360.0 / raysQuantity);
            Ray ray = new Ray(x, y, x + (float) GeometryUtils.getLocationXOnCircle(angle, radius), y + (float) GeometryUtils.getLocationYOnCircle(angle, radius));
            rays.add(ray);

            handler.getWorld().getEntities().stream().filter(e -> e.getShape() != null).forEach(
                    e -> {
                        Shape shape = e.getShape();
                        for (Segment segment : shape.getSegments()) {
                            Point2D.Float intersectionPoint = GeometryUtils.getLineIntersectionPoint(ray.getLine(), segment.getLine());

                            if (intersectionPoint != null)
                                ray.addIntersectionVertex(intersectionPoint, segment.getEntity());
                        }
                    }
            );
        }
    }
}
