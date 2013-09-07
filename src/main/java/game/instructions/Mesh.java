package game.instructions;

import com.jogamp.common.nio.Buffers;
import joglengine.output.JOGLInstruction;

import javax.media.opengl.GL3;

public class Mesh implements JOGLInstruction<GL3> {

    private int count, drawPrimitive, type, offset;

    public Mesh(int offset, int count) {
        this.offset = offset * Buffers.SIZEOF_SHORT;
        this.count = count;
        this.drawPrimitive = GL3.GL_TRIANGLES;
        this.type = GL3.GL_UNSIGNED_SHORT;
    }

    @Override
    public void render(GL3 gl) {
        gl.glDrawElements(drawPrimitive, count, type, offset);
    }
}