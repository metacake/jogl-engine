package outlast;

import io.metacake.core.BootstrapBuilder;
import outlast.engine.output.JOGLDevice;
import outlast.engine.window.JOGLWindow;
import outlast.game.LoadingState;

public class Main {
    public static void main(String...args) {
        BootstrapBuilder builder = new BootstrapBuilder();
        builder.withWindow(new JOGLWindow(800, 600)).withInitialState(new LoadingState());
        builder.withInputDevices().withOutputDevices(new JOGLDevice());
        builder.createAndLaunch();
    }
}