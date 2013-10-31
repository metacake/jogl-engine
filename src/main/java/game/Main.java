package game;

import io.metacake.core.BootstrapBuilder;
import joglengine.input.keyboard.KeyboardDevice;
import joglengine.output.JOGLDevice;
import joglengine.window.JOGLWindow;

public class Main {
    public static void main(String...args) {
        BootstrapBuilder builder = new BootstrapBuilder();
        builder.withWindow(new JOGLWindow(800, 600)).withInitialState(new LoadingState());
        builder.withInputDevices(new KeyboardDevice()).withOutputDevices(new JOGLDevice());
        builder.createAndLaunch();
    }
}