package joglengine.input.mouse;

import com.jogamp.newt.event.MouseEvent;
import io.metacake.core.process.ActionRecognizer;

public abstract class MouseRecognizer implements ActionRecognizer {
    protected int x, y;

    public abstract void onPress(MouseEvent e);

    public abstract void onRelease(MouseEvent e);

    public abstract void onMotion(MouseEvent e);

    final void buttonPress(MouseEvent e) {
        setCoordinates(e);
        this.onPress(e);
    }

    final void buttenRelease(MouseEvent e) {
        setCoordinates(e);
        this.onRelease(e);
    }

    final void mouseMotion(MouseEvent e) {
        setCoordinates(e);
        this.onMotion(e);
    }

    protected void setCoordinates(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}