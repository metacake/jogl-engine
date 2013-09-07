package joglengine.output.buffer;

import com.jogamp.common.nio.Buffers;
import joglengine.output.JOGLInstruction;

import javax.media.opengl.GL3;
import java.nio.Buffer;
import java.nio.IntBuffer;

public class GenerateBufferInstruction implements JOGLInstruction<GL3> {

    public static GenerateBufferInstruction generateBuffer(int target) {
        return new GenerateBufferInstruction(target);
    }

    private BufferObject bufferObject;
    private int target, type, hint;
    private Buffer data;

    protected GenerateBufferInstruction(int target) {
        this.target = target;
        this.bufferObject = new BufferObject(target);
    }

    public GenerateBufferInstruction withFloatData(float[] data) {
        this.data = Buffers.newDirectFloatBuffer(data).asReadOnlyBuffer();
        this.type = Buffers.SIZEOF_FLOAT;
        return this;
    }

    public GenerateBufferInstruction withShortData(short[] data) {
        this.data = Buffers.newDirectShortBuffer(data).asReadOnlyBuffer();
        this.type = Buffers.SIZEOF_SHORT;
        return this;
    }

    public GenerateBufferInstruction withRenderingHint(int hint) {
        this.hint = hint;
        return this;
    }

    public BufferObject getBufferObject() {
        return bufferObject;
    }

    @Override
    public void render(GL3 gl) {
        generateBuffer(gl);
    }

    protected void generateBuffer(GL3 gl) {
        int handle = generateBufferHandle(gl);
        gl.glBindBuffer(target, handle);
        gl.glBufferData(target, data.capacity() * type, data, hint);
        gl.glBindBuffer(target, 0);
        bufferObject.setHandle(handle);
    }

    protected int generateBufferHandle(GL3 gl) {
        IntBuffer handleBuffer = Buffers.newDirectIntBuffer(1);
        gl.glGenBuffers(1, handleBuffer);
        return handleBuffer.get();
    }
}