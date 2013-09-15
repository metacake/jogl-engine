package joglengine.state;

import io.metacake.core.output.OutputDeviceName;
import io.metacake.core.output.RenderingInstruction;
import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.output.SpiedRenderingBundle;

public abstract class LoadingPhase {

    protected SpiedRenderingBundle bundle = new SpiedRenderingBundle();

    public boolean isDone() {
        return bundle.isDone();
    }

    public abstract RenderingInstructionBundle createBundle();

    public void add(OutputDeviceName name, RenderingInstruction inst) {
        bundle.add(name, inst);
    }

    public void add(OutputDeviceName name, RenderingInstruction... insts) {
        bundle.add(name, insts);
    }
}