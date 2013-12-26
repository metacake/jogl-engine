package joglengine.input.keyboard;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.input.ActionTrigger;
import io.metacake.core.input.InputDeviceName;
import io.metacake.core.input.system.InputDevice;
import joglengine.window.JOGLWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class KeyboardDevice implements InputDevice, KeyListener {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final InputDeviceName NAME = new InputDeviceName();
    private List<KeyTrigger> triggers = new ArrayList<>();

    @Override
    public InputDeviceName name() {
        return NAME;
    }

    @Override
    public void shutdown() {}

    @Override
    public void bind(CakeWindow cakeWindow) {
        JOGLWindow window = (JOGLWindow) cakeWindow;
        window.getRawWindow().addKeyListener(this);
    }

    @Override
    public void addTrigger(ActionTrigger actionTrigger) {
        triggers.add((KeyTrigger) actionTrigger);
    }

    @Override
    public void releaseTriggers() {
        triggers = new ArrayList<>();
    }

    @Override
    public void startInputLoop() {}

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        logger.debug("Pressed: {}", keyEvent);
        handleKey(keyEvent, (k) -> k.keyPressed(keyEvent));
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        handleKey(keyEvent, (k) -> k.keyReleased(keyEvent));
    }

    private void handleKey(KeyEvent keyEvent, Consumer<KeyTrigger> consumer) {
        logger.debug("Pressed: {}", keyEvent);
        triggers.forEach(trigger -> {
            if(trigger.isTriggeredBy((int) keyEvent.getKeyCode())) {
                consumer.accept(trigger);
            }
        });
    }
}