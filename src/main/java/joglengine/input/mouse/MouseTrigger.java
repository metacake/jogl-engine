package joglengine.input.mouse;

import com.jogamp.newt.event.MouseEvent;
import io.metacake.core.input.ActionTrigger;
import io.metacake.core.input.InputDeviceName;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MouseTrigger implements ActionTrigger<Integer> {
    private List<Integer> codes;
    private List<MouseRecognizer> recognizers = new LinkedList<>();

    public MouseTrigger(Integer... codes) {
        this.codes = Arrays.asList(codes);
    }

    @Override
    public List<Integer> getCodes() {
        return codes;
    }

    public void buttonPress(MouseEvent e) {
        for (MouseRecognizer r : recognizers) {
            r.buttonPress(e);
        }
    }

    public void buttonRelease(MouseEvent e) {
        for (MouseRecognizer recognizer : recognizers) {
            recognizer.buttenRelease(e);
        }
    }

    public void mouseMotion(MouseEvent e) {
        for (MouseRecognizer recognizer : recognizers) {
            recognizer.mouseMotion(e);
        }
    }

    public void mouseDragged(MouseEvent e) {
        for (MouseRecognizer recognizer : recognizers) {
            recognizer.mouseMotion(e);
        }
    }

    // EFFECT: adds r to this
    // returns this
    public MouseTrigger bindRecognizer(MouseRecognizer r) {
        recognizers.add(r);
        return this;
    }

    @Override
    public InputDeviceName bindingDevice() {
        return MouseDevice.NAME;
    }
}
