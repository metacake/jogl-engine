package outlast.engine.input.keyboard;

import com.jogamp.newt.event.KeyEvent;
import io.metacake.core.input.ActionTrigger;
import io.metacake.core.input.InputDeviceName;

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
        for (KeyRecognizer r : recognizers) {
            r.keyPressed(e);
        }
    }

    public void keyReleased(KeyEvent e) {
        for (KeyRecognizer recognizer : recognizers) {
            recognizer.keyReleased(e);
        }
    }

    // EFFECT: adds r to this
    // returns this
    public KeyTrigger bindRecognizer(KeyRecognizer...rs) {
        for(KeyRecognizer r : rs) {
            recognizers.add(r);
        }
        return this;
    }
}