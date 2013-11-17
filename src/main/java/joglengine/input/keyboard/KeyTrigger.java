package joglengine.input.keyboard;

import com.jogamp.newt.event.KeyEvent;
import io.metacake.core.input.ActionTrigger;
import io.metacake.core.input.InputDeviceName;
import joglengine.process.KeyRecognizer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class KeyTrigger implements ActionTrigger<Integer> {

    private List<Integer> codes;
    private List<KeyRecognizer> recognizers = new LinkedList<>();

    public KeyTrigger(Integer...codes) {
        this.codes = Arrays.asList(codes);
    }

    @Override
    public List<Integer> getCodes() {
        return codes;
    }

    @Override
    public InputDeviceName bindingDevice() {
        return KeyboardDevice.NAME;
    }

    public void keyPressed(KeyEvent e) {
        recognizers.forEach(recognizer -> recognizer.keyPressed(e));
    }

    public void keyReleased(KeyEvent e) {
        recognizers.forEach(recognizer -> recognizer.keyReleased(e));
    }

    // EFFECT: adds r to this
    // returns this
    public KeyTrigger bindRecognizer(KeyRecognizer...rs) {
        Arrays.asList(rs).forEach(recognizers::add);
        return this;
    }
}