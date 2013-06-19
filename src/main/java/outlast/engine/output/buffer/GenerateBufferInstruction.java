package outlast.engine.output.buffer;

import com.jogamp.common.nio.Buffers;
import outlast.engine.output.Asset;
import outlast.engine.output.JOGLInstruction;

import javax.media.opengl.GL3;
import java.nio.Buffer;
import java.nio.IntBuffer;

public class GenerateBufferInstruction extends JOGLInstruction<GL3> {
    private Asset<BufferObject> bufferObject;
    private int stride, hint;
    private Buffer data;

    public GenerateBufferInstruction withFloatData(float[] data) {
        this.data = Buffers.newDirectFloatBuffer(data);
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
        IntBuffer handleBuffer = Buffers.newDirectIntBuffer(1);
        gl.glGenBuffers(1, handleBuffer);
        int handle = handleBuffer.get();
        int target = bufferObject.getValue().getTarget();

        gl.glBindBuffer(target, handle);
        gl.glBufferData(target, data.capacity() * stride, data, hint);
        gl.glBindBuffer(target, 0);
        bufferObject.getValue().setHandle(handle);
    }
}