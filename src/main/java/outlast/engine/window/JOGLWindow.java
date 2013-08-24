package outlast.engine.window;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.event.WindowListener;
import com.jogamp.newt.opengl.GLWindow;
import io.metacake.core.common.window.CakeWindow;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;

public class JOGLWindow extends CakeWindow<GLWindow> {
    private GLWindow window;

    WindowListener listener = new WindowAdapter() {
        @Override
        public void windowDestroyNotify(WindowEvent windowEvent) {
            close();
        }
    };

    public JOGLWindow(int width, int height) {
        GLCapabilities capabilities = new GLCapabilities(GLProfile.get(GLProfile.GL3));
        window = GLWindow.create(capabilities);
        window.setSize(width, height);
        window.setVisible(true);
        window.addWindowListener(listener);
    }

    @Override
    public void dispose() {
        window.setVisible(false);
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
    public GLWindow getRawWindow() {
        return window;
    }
}