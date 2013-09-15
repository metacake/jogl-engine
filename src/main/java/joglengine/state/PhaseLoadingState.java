package joglengine.state;

import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.state.GameState;
import io.metacake.core.process.state.UserState;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public abstract class PhaseLoadingState extends UserState {

    private Queue<LoadingPhase> phases;
    private boolean firstInUse;

    public PhaseLoadingState(Queue<LoadingPhase> phases) {
        this.phases = phases;
        this.firstInUse = false;
    }

    public PhaseLoadingState(LoadingPhase... phases) {
        this(new LinkedList<>(Arrays.asList(phases)));
    }

    public void addPhase(LoadingPhase phase) {
        this.phases.add(phase);
    }

    @Override
    public final GameState tick() {
        if (!phases.isEmpty() && firstInUse && phases.peek().isDone()) {
            firstInUse = false;
            phases.poll();
        }
        return phases.isEmpty() ? nextState() : this;
    }

    protected abstract GameState nextState();

    @Override
    public final RenderingInstructionBundle renderingInstructions() {
        if (phases.isEmpty() || (firstInUse && !phases.peek().isDone())) {
            return RenderingInstructionBundle.EMPTY_BUNDLE;
        } else {
            firstInUse = true;
            return phases.peek().createBundle();
        }
    }
}