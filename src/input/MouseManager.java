package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;

public class MouseManager implements MouseListener, MouseMotionListener {

    private boolean[] buttons;
    private boolean leftPressed, middlePressed, rightPressed;
    private int mouseX, mouseY;

    public MouseManager() {
        buttons = new boolean[4];
    }

    public void update() {
        leftPressed = buttons[MouseEvent.BUTTON1];
        middlePressed = buttons[MouseEvent.BUTTON2];
        rightPressed = buttons[MouseEvent.BUTTON3];
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttons[e.getButton()] = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            leftPressed = true;
        else if (e.getButton() == MouseEvent.BUTTON3)
            rightPressed = true;

        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    public void reset(){
        Arrays.fill(buttons, false);
    }
}