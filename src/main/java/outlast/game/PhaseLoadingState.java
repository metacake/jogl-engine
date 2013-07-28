package outlast.game;

import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.state.GameState;
import io.metacake.core.process.state.UserState;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public abstract class PhaseLoadingState extends UserState {

    private Queue<LoadingPhase> phases;
    private GameState nextState;

    public PhaseLoadingState(Queue<LoadingPhase> phases) {
        this.phases = phases;
    }

    public PhaseLoadingState(LoadingPhase...phases) {
        this(new LinkedList<>(Arrays.asList(phases)));
    }

    @Override
    public final GameState tick() {
        if (phases.isEmpty()) {
            return nextState();
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return this;
        }
    }

    protected abstract GameState nextState();

    @Override
    public final RenderingInstructionBundle renderingInstructions() {
        return (phases.isEmpty()) ? RenderingInstructionBundle.EMPTY_BUNDLE : phases.poll().getRenderBundle();
    }
}