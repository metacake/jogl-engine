package joglengine.input.keyboard;

import com.jogamp.newt.event.KeyEvent;
import io.metacake.core.process.ActionRecognizer;

public interface KeyRecognizer extends ActionRecognizer {
    public void keyPressed(KeyEvent keyEvent);

    public void keyReleased(KeyEvent keyEvent);
}