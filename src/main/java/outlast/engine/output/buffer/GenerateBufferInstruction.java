package outlast.engine.output.buffer;

import com.jogamp.common.nio.Buffers;
import outlast.engine.output.Asset;
import outlast.engine.output.JOGLInstruction;

import javax.media.opengl.GL3;
import java.nio.Buffer;

public class GenerateBufferInstruction extends JOGLInstruction<GL3> {

    public static GenerateBufferInstruction generateBuffer(Asset<BufferObject> bufferObject) {
        return new GenerateBufferInstruction(bufferObject);
    }

    private Asset<BufferObject> bufferObject;
    private int stride, hint;
    private Buffer data;

    private GenerateBufferInstruction(Asset<BufferObject> bufferObject) {
        this.bufferObject = bufferObject;
    }

    public GenerateBufferInstruction withFloatData(float[] data) {
        this.data = Buffers.newDirectFloatBuffer(data).asReadOnlyBuffer();
        return this;
    }

    public GenerateBufferInstruction withRenderingHint(int hint) {
        this.hint = hint;
        return this;
    }

    public GenerateBufferInstruction withStride(int stride) {
        this.stride = stride;
        return this;
    }

    @Override
    public void render(GL3 gl) {
        bufferObject.getValue().generateBuffer(gl, data, stride, hint);
    }
}