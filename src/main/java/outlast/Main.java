package outlast;

import io.metacake.core.Bootstrapper;
import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.input.InputDeviceName;
import io.metacake.core.input.system.InputDevice;
import io.metacake.core.output.OutputDeviceName;
import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.output.system.OutputDevice;
import io.metacake.core.process.state.GameState;
import io.metacake.core.process.state.UserState;
import outlast.engine.output.JOGLDevice;
import outlast.engine.output.JOGLInstruction;
import outlast.engine.window.JOGLWindow;

import javax.media.opengl.GL4bc;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String...args) {
        Map<InputDeviceName, InputDevice> inputs = new HashMap<>();
        Map<OutputDeviceName, OutputDevice> outputs = new HashMap<>();
        outputs.put(JOGLDevice.NAME, new JOGLDevice());
        CakeWindow window = new JOGLWindow(800, 600);
        Bootstrapper strapper = new Bootstrapper(window, inputs,outputs, new DummyState());
        strapper.setupAndLaunchGame();
    }
}

class DummyState extends UserState {
    @Override
    public GameState tick() {
        return this;
    }

    @Override
    public RenderingInstructionBundle renderingInstructions() {
        RenderingInstructionBundle bundle = new RenderingInstructionBundle();
        bundle.add(JOGLDevice.NAME, new TestInstruction());
        return bundle;
    }
}

class TestInstruction extends JOGLInstruction<GL4bc> {
    @Override
    public void render(GL4bc gl) {
        gl.glClear(GL4bc.GL_COLOR_BUFFER_BIT | GL4bc.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glBegin(GL4bc.GL_TRIANGLES);
        gl.glColor3f(1.0f, 0f, 0f);
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 0.0f);
        gl.glEnd();
    }
}