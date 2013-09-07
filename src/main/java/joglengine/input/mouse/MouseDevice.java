package joglengine.input.mouse;

import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;
import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.input.ActionTrigger;
import io.metacake.core.input.InputDeviceName;
import io.metacake.core.input.system.InputDevice;
import joglengine.window.JOGLWindow;

import java.util.ArrayList;
import java.util.List;

public class MouseDevice extends MouseAdapter implements InputDevice {
    public static final InputDeviceName NAME = new InputDeviceName();
    private List<MouseTrigger> triggers = new ArrayList<>();

    @Override
    public void shutdown() {}

    @Override
    public void bind(CakeWindow cakeWindow) {
        JOGLWindow window = (JOGLWindow) cakeWindow;
        window.getRawWindow().addMouseListener(this);
    }

    @Override
    public void addTrigger(ActionTrigger actionTrigger) {
        triggers.add((MouseTrigger) actionTrigger);
    }

    @Override
    public void releaseTriggers() {
        triggers = new ArrayList<>();
    }

    @Override
    public void startInputLoop() {}

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        for (MouseTrigger trigger : triggers) {
            if (trigger.getCodes().contains(mouseEvent.getButton())) {
                trigger.buttonPress(mouseEvent);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        for (MouseTrigger trigger : triggers) {
            if (trigger.getCodes().contains(mouseEvent.getButton())) {
                trigger.buttonRelease(mouseEvent);
            }
        }
    }

    @Override
    public InputDeviceName name() {
        return NAME;
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        for (MouseTrigger trigger : triggers) {
            if (trigger.getCodes().contains(MouseEvent.EVENT_MOUSE_MOVED)) {
                trigger.mouseMotion(mouseEvent);
            }
        }
    }
}