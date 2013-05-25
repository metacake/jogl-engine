package outlast.engine.output;

import io.metacake.core.output.RenderingInstruction;

import javax.media.opengl.GL4bc;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class JOGLEventListener implements GLEventListener {
    private SyncState state;

    public JOGLEventListener(SyncState state) {
        this.state = state;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL4bc gl = drawable.getGL().getGL4bc();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL4bc.GL_DEPTH_TEST);
        gl.glDepthFunc(GL4bc.GL_LEQUAL);
        gl.glHint(GL4bc.GL_PERSPECTIVE_CORRECTION_HINT, GL4bc.GL_NICEST);
        gl.glShadeModel(GL4bc.GL_SMOOTH);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {}

    @Override
    public void display(GLAutoDrawable drawable) {
        GL4bc gl = drawable.getGL().getGL4bc();
        for(RenderingInstruction instruction : state.getState()) {
            ((JOGLInstruction<GL4bc>) instruction).render(gl);
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
}