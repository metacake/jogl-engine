package joglengine.state;

import io.metacake.core.output.RenderingInstructionBundle;

/**
 * A SAM for LoadingState Renderables in order.
 */
public interface LoadingPhase {

    /**
     * @return Get the RenderingInstructionBundle for this phase of loading.
     */
    RenderingInstructionBundle getRenderBundle();
}