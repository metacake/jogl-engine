package game.instructions;

import joglengine.output.JOGLInstruction;

import javax.media.opengl.GL3;

/**
 *  The {@code ScreenClearInstruction} instructs the screen to clear itself.
 *  When this instruction is rendered it clears both the color and depth buffers.
 *  <p>
 *      In principle, this instruction should be executed every time the {@code JOGLDevice} renders
 *      instructions. If the screen is not cleared then odd things start to happen.
 */
public class ScreenClearInstruction implements JOGLInstruction<GL3> {

    private float red, blue, green, transparency;
    private double clearDepth;

    /**
     * Instantiates a {@code ScreenClearInstruction} with a transparent clear color.
     */
    public ScreenClearInstruction() {
        this(0, 0, 0, 0, 1.0);
    }

    /**
     * @param red The red value of the clear color. Must be on the range [0.0, 1.0]
     * @param blue The blue value of the clear color. Must be on the range [0.0, 1.0]
     * @param green The green value of the clear color. Must be on the range [0.0, 1.0]
     * @param transparency The alpha value of the clear color. Must be on the range [0.0, 1.0]
     */
    public ScreenClearInstruction(float red, float blue, float green, float transparency, double clearDepth) {
        this.red = red;
        this.blue = blue;
        this.green = green;
        this.transparency = transparency;
        this.clearDepth = clearDepth;
    }

    @Override
    public void render(GL3 gl) {
        gl.glClearColor(red, blue, green, transparency);
        gl.glClearDepth(clearDepth);
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);
    }
}