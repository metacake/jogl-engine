package outlast.game.instructions;

import outlast.engine.output.JOGLInstruction;

import javax.media.opengl.GL3;

public class ScreenClearInstruction implements JOGLInstruction<GL3> {

    private int red, blue, green, transparency;

    public ScreenClearInstruction() {
        this(0,0,0,0);
    }

    public ScreenClearInstruction(int red, int blue, int green, int transparency) {
        this.red = red;
        this.blue = blue;
        this.green = green;
        this.transparency = transparency;
    }

    @Override
    public void render(GL3 gl) {
        gl.glClearColor(red, blue, green, transparency);
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT);
    }
}