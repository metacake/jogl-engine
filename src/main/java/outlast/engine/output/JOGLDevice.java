package outlast.engine.output;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.AnimatorBase;
import com.jogamp.opengl.util.FPSAnimator;
import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.output.OutputDeviceName;
import io.metacake.core.output.RenderingInstruction;
import io.metacake.core.output.system.OutputDevice;
import outlast.engine.window.JOGLWindow;

import java.util.List;

public class JOGLDevice implements OutputDevice {
    public static final OutputDeviceName NAME = new OutputDeviceName();
    private AnimatorBase animator;
    private GLWindow glWindow;
    private SyncState sync = new SyncState();

    @Override
    public OutputDeviceName name() {
        return NAME;
    }

    @Override
    public void render(List<RenderingInstruction> instructions) {
        sync.setState(instructions);
    }

    @Override
    public void startOutputLoop() {
        animator.start();
    }

    @Override
    public void shutdown() {
        glWindow.destroy();
        animator.stop();
    }

    @Override
    public void bind(CakeWindow cakeWindow) {
        glWindow = ((JOGLWindow) cakeWindow).getRawWindow();
        glWindow.addGLEventListener(new JOGLEventListener(sync));
        animator = new FPSAnimator(glWindow, 60, true);
    }
}