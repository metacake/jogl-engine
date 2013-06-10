package outlast.engine.output;

import com.jogamp.newt.opengl.GLWindow;
import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.output.OutputDeviceName;
import io.metacake.core.output.RenderingInstruction;
import io.metacake.core.output.system.OutputDevice;

import java.util.List;

public class JOGLDevice implements OutputDevice {
    public static final OutputDeviceName NAME = new OutputDeviceName();
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
    public void startOutputLoop() {}

    @Override
    public void shutdown() {}

    @Override
    @SuppressWarnings("unchecked")
    public void bind(CakeWindow cakeWindow) {
        GLWindow glWindow = ((CakeWindow<GLWindow>) cakeWindow).getRawWindow();
        glWindow.addGLEventListener(new JOGLEventListener(sync));
    }
}