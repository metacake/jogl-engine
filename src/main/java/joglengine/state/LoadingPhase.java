package joglengine.state;

import io.metacake.core.output.InspectingRenderingInstructionBundle;

import java.util.function.Supplier;

public class LoadingPhase {
    private Supplier<InspectingRenderingInstructionBundle> supplier;
    private InspectingRenderingInstructionBundle cached;
    private boolean isCached = false;

    public LoadingPhase(Supplier<InspectingRenderingInstructionBundle> supplier) {
        this.supplier = supplier;
    }

    public InspectingRenderingInstructionBundle getBundle() {
        if(!isCached) {
            cached = supplier.get();
            isCached = true;
        }
        return cached;
    }

    public boolean isDone() {
        return isCached && cached.isDone();
    }
}