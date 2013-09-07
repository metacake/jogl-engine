package joglengine.input.keyboard;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import joglengine.window.JOGLWindow;
import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.input.ActionTrigger;
import io.metacake.core.input.InputDeviceName;
import io.metacake.core.input.system.InputDevice;

import java.util.ArrayList;
import java.util.List;

public class KeyboardDevice implements InputDevice, KeyListener {
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
        for(KeyTrigger trigger : triggers) {
            if(trigger.getCodes().contains(keyEvent.getKeyCode())) {
                trigger.keyPressed(keyEvent);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        for(KeyTrigger trigger : triggers) {
            if(trigger.getCodes().contains(keyEvent.getKeyCode())) {
                trigger.keyReleased(keyEvent);
            }
        }
    }
}