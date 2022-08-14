package environment.entities;

import environment.Handler;
import environment.entities.utils.Shape;

import java.awt.*;


public class CustomShapeEntity extends Entity{

    public CustomShapeEntity(Handler handler, Shape shape) {
        super(handler);
        setShape(shape);

        Point originPoint = shape.getOriginPoint();
        setLocation(originPoint.x, originPoint.y);

        Polygon polygon = shape.getPolygon();
        Rectangle bounds = polygon.getBounds();
        setSize(bounds.width, bounds.height);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        if(getShape().isPresent())
            g.fillPolygon(getShape().get().getPolygon());
    }
}
