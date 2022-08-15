package environment.entities.utils;

import java.awt.*;

public abstract class EntityModification {
    /**
     * Updates entity right after update()
     */
    public abstract void lateUpdate();

    /**
     * Renders entity right after render(...)
     *
     * @param g Graphics
     */
    public abstract void lateRender(Graphics g);
}
