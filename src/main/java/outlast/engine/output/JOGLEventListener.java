package outlast.engine.output;

import io.metacake.core.output.RenderingInstruction;

import javax.media.opengl.GL3bc;
import javax.media.opengl.GL4bc;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import java.util.Collections;

public class JOGLEventListener implements GLEventListener {
    private SyncState state;

    public JOGLEventListener(SyncState state) {
        this.state = state;
    }

    @Override
    public void init(GLAutoDrawable drawable) {}

    @Override
    public void dispose(GLAutoDrawable drawable) {}

    @Override
    public void display(GLAutoDrawable drawable) {
        GL4bc gl = drawable.getGL().getGL4bc();
        for(RenderingInstruction instruction : state.getState()) {
            ((JOGLInstruction<GL3bc>) instruction).render(gl);
        }
        state.setState(Collections.<RenderingInstruction>emptyList());
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
}