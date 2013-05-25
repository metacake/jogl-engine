package outlast.engine.input;

import com.jogamp.newt.event.KeyEvent;
import io.metacake.core.process.ActionRecognizer;

public interface KeyRecognizer extends ActionRecognizer {
    public void keyPressed(KeyEvent keyEvent);

    public void keyReleased(KeyEvent keyEvent);
}