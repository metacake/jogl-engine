package outlast.engine.output.buffer;

import com.jogamp.common.nio.Buffers;
import outlast.engine.output.NativeObject;

import javax.media.opengl.GL3;
import java.nio.Buffer;
import java.nio.IntBuffer;

public class BufferObject extends NativeObject {
    private int target;

    public BufferObject(int target) {
        this.target = target;
    }

    public int getTarget() {
        return target;
    }

    public void bind(GL3 gl) {
        gl.glBindBuffer(target, getHandle());
    }

    public void unbind(GL3 gl) {
        gl.glBindBuffer(target, 0);
    }

    protected void setTarget(int target) {
        this.target = target;
    }

    protected void generateBuffer(GL3 gl, Buffer data, int stride, int hint) {
        int handle = generateBufferHandle(gl);
        gl.glBindBuffer(target, handle);
        gl.glBufferData(target, data.capacity() * stride, data, hint);
        gl.glBindBuffer(target, 0);
        this.setHandle(handle);
    }

    /**
     * Calls glGenBuffer to create a single buffer and returns the handle to it.
     */
    protected int generateBufferHandle(GL3 gl) {
        IntBuffer handleBuffer = Buffers.newDirectIntBuffer(1);
        gl.glGenBuffers(1, handleBuffer);
        return handleBuffer.get();
    }
}