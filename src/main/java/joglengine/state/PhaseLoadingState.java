package joglengine.state;

import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.state.GameState;
import io.metacake.core.process.state.UserState;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public abstract class PhaseLoadingState extends UserState {

    private Queue<LoadingPhase> phases;

    public PhaseLoadingState(Queue<LoadingPhase> phases) {
        this.phases = phases;
    }

    public PhaseLoadingState(LoadingPhase...phases) {
        this(new LinkedList<>(Arrays.asList(phases)));
    }

    public void addPhase(LoadingPhase phase) {
        this.phases.add(phase);
    }

    @Override
    public final GameState tick() {
        return phases.isEmpty() ? nextState() : this;
    }

    protected abstract GameState nextState();

    @Override
    public final RenderingInstructionBundle renderingInstructions() {
        return phases.isEmpty() ? RenderingInstructionBundle.EMPTY_BUNDLE : phases.poll().getRenderBundle();
    }
}