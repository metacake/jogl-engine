package outlast.engine.output;

import io.metacake.core.output.RenderingInstruction;

import javax.media.opengl.GL3;
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
        GL3 gl = drawable.getGL().getGL3();
        for(RenderingInstruction instruction : state.getState()) {
            ((JOGLInstruction<GL3>) instruction).render(gl);
        }
        state.setState(Collections.<RenderingInstruction>emptyList());
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
}