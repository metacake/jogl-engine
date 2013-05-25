package outlast.engine.window;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.event.WindowListener;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.AnimatorBase;
import com.jogamp.opengl.util.FPSAnimator;
import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.common.window.CloseObserver;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;

public class JOGLWindow implements CakeWindow<GLWindow> {
    private GLWindow window;
    private AnimatorBase animator;

    WindowListener listener = new WindowAdapter() {
        @Override
        public void windowDestroyNotify(WindowEvent windowEvent) {
            close();
        }
    };

    public JOGLWindow(int width, int height) {
        GLCapabilities capabilities = new GLCapabilities(GLProfile.getDefault());
        window = GLWindow.create(capabilities);
        window.setSize(width, height);
        window.setVisible(true);
        window.addWindowListener(listener);
        animator = new FPSAnimator(window, 60, true);
        animator.start();
    }

    @Override
    public void close() {
        window.setVisible(false);
        animator.stop();
        window.destroy();
    }

    @Override
    public int getX() {
        return window.getX();
    }

    @Override
    public int getY() {
        return window.getY();
    }

    @Override
    public int getWidth() {
        return window.getWidth();
    }

    @Override
    public int getHeight() {
        return window.getHeight();
    }

    @Override
    public void addCloseObserver(CloseObserver closeObserver) {}

    @Override
    public GLWindow getRawWindow() {
        return window;
    }
}