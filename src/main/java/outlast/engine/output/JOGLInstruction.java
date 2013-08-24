package outlast.engine.output;

import io.metacake.core.output.RenderingInstruction;

import javax.media.opengl.GL;

public interface JOGLInstruction<T extends GL> extends RenderingInstruction {
    void render(T gl);
}