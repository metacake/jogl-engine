package joglengine.process;

import com.jogamp.newt.event.KeyEvent;
import io.metacake.core.common.MilliTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code KeyHeldRecognizer} is a {@link KeyRecognizer} that recognizes
 * when a key on the keyboard is being held down.
 */
public class KeyHeldRecognizer implements KeyRecognizer {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private MilliTimer timer = new MilliTimer();
    private boolean isHeld, wasTriggered;
    private int savedAmount = 0;
    private final int timerFactor = 10;

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (!isHeld) {
            timer.update();
            wasTriggered = true;
            isHeld = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        savedAmount += amountOfTimePassed();
        isHeld = false;
    }

    @Override
    public boolean wasTriggered() {
        return wasTriggered;
    }

    @Override
    public int triggerWeight() {
        int weight = savedAmount + (isHeld ? amountOfTimePassed() : 0);
        savedAmount = 0;
        if (!isHeld) {
            wasTriggered = false;
        }
        return weight;
    }

    @Override
    public void forgetActions() {
        isHeld = false;
        wasTriggered = false;
        savedAmount = 0;
        timer.update();
    }

    /**
     * @return Returns the amount of time that has passed sine the timer was last updated.
     */
    private int amountOfTimePassed() {
        return (int) (timer.update() / timerFactor);
    }
}