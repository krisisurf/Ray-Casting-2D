package environment.entities.utils;

import environment.entities.Entity;

import java.awt.geom.Point2D;

public class Vertex extends Point2D.Float {

    private Entity intersectedEntity;

    public Vertex(float x, float y, Entity intersectedEntity) {
        super(x, y);
        this.intersectedEntity = intersectedEntity;
    }

    public Vertex(Point2D.Float location, Entity intersectedEntity) {
        this(location.x, location.y, intersectedEntity);
    }

    public Entity getIntersectedEntity() {
        return intersectedEntity;
    }

    public void setIntersectedEntity(Entity intersectedEntity) {
        this.intersectedEntity = intersectedEntity;
    }
}
