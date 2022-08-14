package environment.entities;

import environment.Handler;
import environment.entities.utils.Shape;

import java.awt.*;

public class CrossShapeEntity extends Entity {

    private static final Color color = Color.DARK_GRAY;

    private int branchThickness;

    public CrossShapeEntity(Handler handler, float x, float y, int width, int height, int branchThickness) {
        super(handler, x, y, width, height);
        setBranchThickness(branchThickness);
        initShape();
    }

    void initShape() {
        int npoints = 12;

        int xm1 = (int) getX() + (getWidth() - branchThickness) / 2;
        int xm2 = xm1 + branchThickness;
        int xw = (int) getX() + getWidth();

        int ym1 = (int) getY() + (getHeight() - branchThickness) / 2;
        int ym2 = ym1 + branchThickness;
        int yh = (int) getY() + getHeight();

        int[] xpoints = {(int) getX(), xm1, xm1, xm2, xm2, xw, xw, xm2, xm2, xm1, xm1, (int) getX()};
        int[] ypoints = {ym1, ym1, (int) getY(), (int) getY(), ym1, ym1, ym2, ym2, yh, yh, ym2, ym2};

        setShape(new Shape(xpoints, ypoints, npoints, this));
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        if (getShape().isPresent()) {
            g.setColor(color);
            g.fillPolygon(getShape().get().getPolygon());
        }
    }

    public int getBranchThickness() {
        return branchThickness;
    }

    public void setBranchThickness(int branchThickness) {
        this.branchThickness = branchThickness;
        if (branchThickness > getWidth() || branchThickness > getHeight())
            throw new IllegalArgumentException("branchThickness must not be bigger than the width or height");
    }
}
