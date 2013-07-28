package outlast.game;

import io.metacake.core.output.RenderingInstructionBundle;

/**
 * A SAM for Loading Renderables in order.
 */
public interface LoadingPhase {

    /**
     * @return Get the RenderingInstructionBundle for this phase of loading.
     */
    RenderingInstructionBundle getRenderBundle();
}