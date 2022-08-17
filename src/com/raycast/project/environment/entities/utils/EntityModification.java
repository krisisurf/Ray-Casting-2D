package com.raycast.project.environment.entities.utils;

import java.awt.*;

public abstract class EntityModification {

    /**
     * Updates entity after fixedUpdate() and before update(), lateUpdate()
     */
    public abstract void earlyUpdate();

    /**
     * Renders entity after fixedRender(...) and before render(...), lateRender(...)
     *
     * @param g Graphics
     */
    public abstract void earlyRender(Graphics g);

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
