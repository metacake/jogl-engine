package game.instructions;

import com.jogamp.common.nio.Buffers;
import joglengine.output.JOGLInstruction;
import joglengine.util.math.Matrix4f;
import joglengine.util.math.Transformation;

import javax.media.opengl.GL3;

public class Mesh implements JOGLInstruction<GL3> {

    private int count, drawPrimitive, type, offset;
    private Transformation transformation;

    public Mesh(int offset, int count, Transformation transformation) {
        this.offset = offset * Buffers.SIZEOF_SHORT;
        this.count = count;
        this.drawPrimitive = GL3.GL_TRIANGLES;
        this.type = GL3.GL_UNSIGNED_SHORT;
        this.transformation = transformation;
    }

    public Matrix4f getMatrix() {
        return transformation.getRawMatrix();
    }

    @Override
    public void render(GL3 gl) {
        gl.glDrawElements(drawPrimitive, count, type, offset);
    }
}