package environment.entities;

import environment.Handler;
import environment.camera.ViewCamera;
import environment.entities.utils.Segment;
import environment.entities.utils.Shape;
import input.KeyManager;
import tracer.Ray;
import utils.GeometryUtils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LampEntity extends Entity {

    private final List<Ray> rays;
    private final int raysQuantity;
    private float radius;

    public LampEntity(Handler handler, float x, float y, int raysQuantity, float radius) {
        super(handler, x, y, 0, 0);

        if (raysQuantity < 12)
            throw new IllegalArgumentException("raysQuantity must be at least 12");


        rays = new ArrayList<>(raysQuantity);
        this.raysQuantity = raysQuantity;
        setRadius(radius);
    }

    @Override
    public void update() {
        rays.clear();
        move();

        shotRays();
    }

    protected void move() {
        float speed = 3.5f;

        KeyManager keyManager = handler.getKeyManager();

        if (keyManager.down)
            setLocationOffset(0, speed);
        else if (keyManager.up)
           setLocationOffset(0, -speed);
        else if (keyManager.left)
           setLocationOffset(-speed, 0);
        else if (keyManager.right)
            setLocationOffset(speed, 0);

    }

    @Override
    public void render(Graphics g) {
        ViewCamera cam = handler.getViewCamera();

        g.setColor(Color.YELLOW);
        g.fillOval(cam.toScreenX(getX() - 3), cam.toScreenY(getY() - 3), 6, 6);

        rays.forEach(ray -> {
            ray.drawRay(g, cam);
            ray.drawIntersectionVertices(g, cam);
        });
    }

    private void shotRays() {
        for (int i = 0; i < raysQuantity; i++) {
            double angle = i * (360.0 / raysQuantity);
            Ray ray = new Ray(getX(), getY(), getX() + (float) GeometryUtils.getLocationXOnCircle(angle, radius), getY() + (float) GeometryUtils.getLocationYOnCircle(angle, radius));
            rays.add(ray);

            handler.getWorld().getEntities().forEach(
                    e -> {
                        Optional<Shape> shapeOptional = e.getShape();
                        if (shapeOptional.isEmpty())
                            return;

                        Shape shape = shapeOptional.get();
                        for (Segment segment : shape.getSegments()) {
                            Point2D.Float intersectionPoint = GeometryUtils.getLineIntersectionPoint(ray.getLine(), segment.getLine());

                            if (intersectionPoint != null)
                                ray.addIntersectionVertex(intersectionPoint, segment.getEntity());
                        }
                    }
            );
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
