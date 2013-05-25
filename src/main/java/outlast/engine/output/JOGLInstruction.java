package outlast.engine.output;

import io.metacake.core.output.RenderingInstruction;

import javax.media.opengl.GL;

public abstract class JOGLInstruction<T extends GL> implements RenderingInstruction {
    public abstract void render(T gl);
}