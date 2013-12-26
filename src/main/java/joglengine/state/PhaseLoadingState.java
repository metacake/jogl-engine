package joglengine.state;

import io.metacake.core.common.CustomizableMap;
import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.ActionRecognizer;
import io.metacake.core.process.ActionRecognizerName;
import io.metacake.core.process.state.GameState;
import io.metacake.core.process.state.UserState;

import java.util.LinkedList;
import java.util.Queue;

public abstract class PhaseLoadingState extends UserState {

    private Queue<LoadingPhase> loadingPhases;
    private boolean currentWasSent;

    protected PhaseLoadingState() {
        this.loadingPhases = new LinkedList<>();
        this.currentWasSent = false;
    }

    protected void addLoadingPhase(LoadingPhase phase) {
        loadingPhases.add(phase);
    }

    @Override
    public GameState tick(long time, CustomizableMap<ActionRecognizerName,ActionRecognizer> recogs) {
        return (loadingPhases.isEmpty()) ? this.nextState() : this;
    }

    @Override
    public RenderingInstructionBundle renderingInstructions() {
        if (!loadingPhases.isEmpty() && loadingPhases.peek().isDone()) {
            loadingPhases.poll();
            currentWasSent = false;
        }
        if (!loadingPhases.isEmpty() && !currentWasSent) {
            currentWasSent = true;
            return loadingPhases.peek().getBundle();
        } else {
            return RenderingInstructionBundle.EMPTY_BUNDLE;
        }
    }

    protected abstract GameState nextState();
}